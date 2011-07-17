package edu.cmu.square.client.ui.ManageProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtStep;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectService;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectServiceAsync;
import edu.cmu.square.client.remoteService.interfaces.StepService;
import edu.cmu.square.client.remoteService.interfaces.StepServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.StepRouter;
import edu.cmu.square.client.utils.SquareUtil;

public class ProjectDetailPane extends VerticalPanel
{
	private Label projectNameLabel = new Label();
	private List<GwtStepVerficationResult> listOfSteps = new ArrayList<GwtStepVerficationResult>();

	private String projectName = "";
	private int projectId = -1;

	FlexTable projectDetailsTable = new FlexTable();
	private TextBox projectNameText = new TextBox();

	private final ManageProjectPaneMessages messages = (ManageProjectPaneMessages) GWT.create(ManageProjectPaneMessages.class);
	private BasePane caller = null;
	ManageProjectServiceAsync manageProjectService = GWT.create(ManageProjectService.class);
	StepServiceAsync stepService = GWT.create(StepService.class);

	public ProjectDetailPane(int projectId, String projectName, BasePane owner)
		{
			this.projectName = projectName;
			this.projectId = projectId;

			this.caller = owner;

			ServiceDefTarget endpoint = (ServiceDefTarget) stepService;
			endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "stepService.rpc");
		}

	public void initializeTable()
	{
		this.clear();

		FlexCellFormatter formatter = projectDetailsTable.getFlexCellFormatter();
		projectDetailsTable.setWidth("100%");
		projectDetailsTable.setCellSpacing(15);

		// ----- This the project name panel section ------
		this.projectNameText.setWidth("400px");
		this.projectNameText.setStyleName("big-text");
		CreateAndAddNameLabel();

		// buttons
		formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);

		formatter.setWidth(0, 0, "155px");

		this.add(projectDetailsTable);

		getStepsForProject(new GwtProject(projectId));
	}

	public void updateProjectName()
	{
		final GwtProject project = new GwtProject();
		project.setId(projectId);
		project.setName(projectName);

		manageProjectService.updateProjectName(project, new AsyncCallback<Void>()
			{
				public void onFailure(Throwable caught)
				{
					if (caught instanceof SquareException)
					{
						SquareException se = (SquareException) caught;
						switch (se.getType())
						{
							case authorization :
								Window.alert(messages.errorNameUpdateAuthorization());
								break;

							default :
								Window.alert(messages.errorChangingProjectName());
								break;
						}
					}
					else
					{
						Window.alert(messages.errorChangingProjectName());
					}
				}

				public void onSuccess(Void result)
				{
					CreateAndAddNameLabel();
					caller.getCurrentState().setProjectName(projectName);
				}
			});
	}

	private void getStepsForProject(GwtProject project)
	{
		ManageProjectServiceAsync service = GWT.create(ManageProjectService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");

		stepService.getStepsWithVerification(project, new AsyncCallback<List<GwtStepVerficationResult>>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, "retrieving steps");
				}

				public void onSuccess(List<GwtStepVerficationResult> result)
				{
					//for(int i=0; i<result.size(); i++)
						//System.out.println("listOfSteps..."+result.get(i).getStep().getId()+"..."+result.get(i).getStep().getDescription());
					
					Collections.sort(result, new Comparator<GwtStepVerficationResult>()
							{

								@Override
								public int compare(GwtStepVerficationResult o1, GwtStepVerficationResult o2)
								{
									if (o1.getStep() == null
											|| o2.getStep() == null)
									{
										return 0;
									}
									return o1.getStep().getDescription().compareTo(o2.getStep().getDescription());
				
								}
						
							}
					);
					
					listOfSteps = result;
					
					
					CreateStepGrid();
				}
			});

	}

	private void CreateStepGrid()
	{
		FlexTable stepsTable = new FlexTable();
		stepsTable.setStyleName("square-flex");
		stepsTable.setWidth("100%");
		stepsTable.setCellPadding(3);
		stepsTable.setCellSpacing(0);
		FlexCellFormatter formatter = stepsTable.getFlexCellFormatter();

		// Add the header row
		formatter.setStyleName(0, 0, "square-TableHeader");
		formatter.setStyleName(0, 1, "square-TableHeader");
		formatter.setStyleName(0, 2, "square-TableHeader");
		stepsTable.setWidget(0, 0, new Label(messages.stepStatusLable()));
		stepsTable.setWidget(0, 1, new Label(messages.stepDescription()));
		stepsTable.setWidget(0, 2, new Label(messages.squareAnalysisStatus()));

		// Add the steps
		int rowCount = 1;
		for (final GwtStepVerficationResult stepResult : this.listOfSteps)
		{
			final ListBox statusList = new ListBox(false);
			statusList.setWidth("175px");
			statusList.addItem(StepStatus.NotStarted.getLabel());
			statusList.addItem(StepStatus.InProgress.getLabel());
			statusList.addItem(StepStatus.Complete.getLabel());
			final GwtStep step = stepResult.getStep();
			switch (step.getStatus())
			{
				case InProgress :
					statusList.setSelectedIndex(1);
					break;
				case Complete :
					statusList.setSelectedIndex(2);
					break;
				default :
					statusList.setSelectedIndex(0);
			}

			// Set up listeners for combo-boxes
			statusList.addChangeHandler(new ChangeHandler()
				{
					@Override
					public void onChange(ChangeEvent event)
					{
						// Get the index, make a service call with the text
						final String status = statusList.getItemText(statusList.getSelectedIndex());
						updateStepStatus(projectId, step.getId(), status);
					}
				});

			VerticalPanel analysisResults = new VerticalPanel();
			analysisResults.setStyleName("inner-table");
			analysisResults.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

			HTML warningLabel = new HTML();
			Label resultLabel = new Label();
			this.verifyStep(this.projectId, step.getId(), warningLabel, resultLabel, stepResult);
			
			System.out.println("project detial..."+step.getId()+"..."+step.getDescription());
			
			analysisResults.add(new Hyperlink(step.getDescription(), StepRouter.CreateStepLink(step)));
			analysisResults.add(warningLabel);

			stepsTable.setWidget(rowCount, 0, statusList);
			stepsTable.setWidget(rowCount, 1, analysisResults);
			stepsTable.setWidget(rowCount, 2, resultLabel);

			formatter.setVerticalAlignment(rowCount, 0, HasVerticalAlignment.ALIGN_TOP);
			formatter.setWidth(rowCount, 0, "180px");

			rowCount++;
		}

		this.add(stepsTable);
	}

	private void verifyStep(int projectId, int stepId, final HTML warningLabel, final Label warningMessage, GwtStepVerficationResult result)
	{

		if (result.hasWarning())
		{
			warningLabel.setHTML(SquareUtil.createHTMLList(result.getMessages()));
			warningLabel.setStyleName("error-list");
			warningMessage.setText(messages.warning());
			warningMessage.setStyleName("bad");
		}
		else
		{
			warningLabel.setHTML("");
			warningMessage.setText(messages.ok());
			warningMessage.setStyleName("good");
		}
	}

	private String createWarningList(List<String> warnings)
	{
		if (warnings.size() == 0)
		{
			return "";
		}

		StringBuilder listBuilder = new StringBuilder();
		listBuilder.append("<ul>");

		for (String warning : warnings)
		{
			listBuilder.append("<li>" + warning + "</li>");
		}

		listBuilder.append("</ul>");

		return listBuilder.toString();
	}

	private void updateStepStatus(int projectId, int stepId, String status)
	{
		StepServiceAsync service = GWT.create(StepService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "stepService.rpc");

		service.updateStepStatus(projectId, stepId, status, new AsyncCallback<Void>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, "update step status");
				}

				@Override
				public void onSuccess(Void result)
				{
					caller.showStatusBarWithoutAnimation(messages.statusUpdated());
				}
			});
	}

	private void CreateAndAddNameLabel()
	{
		projectDetailsTable.setWidget(0, 1, projectNameLabel);
		projectNameLabel.setText(this.projectName);
		projectNameLabel.setStyleName("big-text");

		projectNameLabel.addClickHandler(new EditClickHandler());
		projectNameLabel.addMouseOverHandler(new MouseOverHandler()
			{
				@Override
				public void onMouseOver(MouseOverEvent event)
				{
					projectNameLabel.setStyleName("click-me");
				}
			});

		projectNameLabel.addMouseOutHandler(new MouseOutHandler()
			{
				@Override
				public void onMouseOut(MouseOutEvent event)
				{
					projectNameLabel.removeStyleName("click-me");
					projectNameLabel.setStyleName("big-text");
				}
			});
	}

	private class EditClickHandler implements ClickHandler
	{
		private HorizontalPanel layout = null;;

		public EditClickHandler()
			{
				layout = new HorizontalPanel();
				layout.setWidth("575px");
				layout.setSpacing(0);

				projectNameText.setText(projectName);
				Button saveButton = new Button(messages.save());
				Button cancelButton = new Button(messages.cancel());

				layout.add(projectNameText);
				layout.add(saveButton);
				layout.add(cancelButton);

				saveButton.addClickHandler(new ClickHandler()
					{
						@Override
						public void onClick(ClickEvent event)
						{
							if (projectNameText.getText().equals(""))
							{
								Window.alert(messages.emptyProjectName());
							}
							else if(caller.getCurrentState().getProjectName().trim().equals(projectNameText.getText().trim()))
							{
								CreateAndAddNameLabel(); //There is not change in the project name
							}
							else
							{
								projectName = projectNameText.getText();
								updateProjectName();
							}
						}
					});

				cancelButton.addClickHandler(new ClickHandler()
					{
						@Override
						public void onClick(ClickEvent event)
						{
							CreateAndAddNameLabel();
						}
					});
			}

		public void onClick(ClickEvent event)
		{
			projectDetailsTable.setWidget(0, 1, layout);
		}
	}
}
