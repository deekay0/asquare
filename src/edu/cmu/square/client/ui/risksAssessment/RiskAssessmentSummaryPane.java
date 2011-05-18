package edu.cmu.square.client.ui.risksAssessment;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRisk;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.RiskAssessmentService;
import edu.cmu.square.client.remoteService.step.interfaces.RiskAssessmentServiceAsync;
import edu.cmu.square.client.ui.ChooseStep.ChooseStepPilot;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;


public class RiskAssessmentSummaryPane extends BasePane
{
	final RiskAssessmentPaneMessages messages = (RiskAssessmentPaneMessages) GWT.create(RiskAssessmentPaneMessages.class);
	private RiskAssessmentServiceAsync service = GWT.create(RiskAssessmentService.class);
	private GwtProject currentProject;
	private List<GwtRisk> lisOfRisks = new ArrayList<GwtRisk>();
	private FlexTable riskTable = new FlexTable();

	private Button addRisk;
	
	public RiskAssessmentSummaryPane(final State stateInfo)
		{
			super(stateInfo);
			currentProject = new GwtProject();
			currentProject.setId(this.getCurrentState().getProjectID());
		
			this.showLoadingStatusBar();
			loadRiskFromProject();
		
		}
	// ------------------------------------------------------
	//      RPC Asynchronous calls section 
	// ------------------------------------------------------
	
	public void loadRiskFromProject()
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "riskAssessment.rpc");
		this.service.getRisksFromProject(this.currentProject.getId(), new AsyncCallback<List<GwtRisk>>()
			{

				public void onFailure(Throwable caught)
				{
					Window.alert(caught.getMessage());
				}

				public void onSuccess(List<GwtRisk> result)
				{
					lisOfRisks = result;
					
					PaneInitialization();
				}
			});
	}
	
	public void removeRisk(int riskID, final int flexRowID)
	{
		boolean response = Window.confirm(messages.confirmDelete());
		if (response)
		{
			this.service.deleteRisk(riskID, new AsyncCallback<Void>()
			{
				public void onFailure(Throwable caught)
				{
					SquareException se = (SquareException) caught;
					switch (se.getType())
					{
						case authorization :
							Window.alert(messages.errorAuthorization());
							break;

						default :
							Window.alert(messages.errorRemovingRisks());
							break;
					}

				}
		
				public void onSuccess(Void result)
				{
					riskTable.removeRow(flexRowID);
				}
			});
		}
	}

	// ------------------------------------------------------
	//      Risk Summary Rendering calls section 
	// ------------------------------------------------------
	
	public void PaneInitialization()
	{
		this.hideStatusBar();
		loadRiskTable();
		this.getContent().add(riskTable);
		
		FlexTable buttonPanel = new FlexTable();
		Button done = new Button(messages.done());
		done.addStyleName("square-button");
		done.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				History.newItem(ChooseStepPilot.generateNavigationId(ChooseStepPilot.PageId.home));
				
			}});
		
		buttonPanel.setWidget(0,0,new Label(" "));
		buttonPanel.setWidget(1, 0, done);
		buttonPanel.setWidth("60%");
		buttonPanel.getCellFormatter().setHorizontalAlignment(1,0,HasHorizontalAlignment.ALIGN_RIGHT);
		this.getContent().add(buttonPanel);
	}

	public void loadRiskTable()
	{
		initializeRiskTable();
		loadRiskItemsToTable();

	}
	public void initializeRiskTable()
	{
		this.riskTable.clear();

		this.riskTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		this.riskTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		this.riskTable.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);

		this.riskTable.getCellFormatter().setStyleName(0, 0, "square-TableHeader");
		this.riskTable.getCellFormatter().setStyleName(0, 1, "square-TableHeader");
		this.riskTable.getCellFormatter().setStyleName(0, 2, "square-TableHeader");

		this.riskTable.setWidth("60%");
		this.riskTable.setCellSpacing(0);
		this.riskTable.setStyleName("square-flex");

		this.riskTable.getCellFormatter().setWidth(0, 0, "60%");
		this.riskTable.getCellFormatter().setWidth(0, 1, "20%");
		this.riskTable.getCellFormatter().setWidth(0, 2, "10%");
		this.riskTable.getCellFormatter().setHeight(0, 0, "30px");

		addRisk = new Button(messages.buttonAddRisk());
		addRisk.addClickHandler(new ClickHandler()
			{

				public void onClick(ClickEvent event)
				{
					currentState.setCurrentRisk(-1);// No risk in context for
					// next pane
					currentState.setRiskCommand(0);
					History.newItem(RiskAssessmentPilot.generateNavigationId(RiskAssessmentPilot.PageId.riskAssessment));

				}
			});

		this.riskTable.setWidget(0, 1, new Label(messages.exposure()));
	}
	public void loadRiskItemsToTable()
	{
		
		
		if (currentState.getMode() == GwtModesType.ReadWrite)
		{
			riskTable.setWidget(0, 0, addRisk);
			
		} else
		{
			riskTable.setWidget(0, 0, new Label(messages.riskTitle()));
		}
		
		int rowNumber = 1;
		for (GwtRisk r : lisOfRisks)
		{
			final RiskSummaryHyperLinkElement riskTitle = new RiskSummaryHyperLinkElement(rowNumber, r.getId(), r.getRiskTitle());
			riskTitle.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					currentState.setCurrentRisk(riskTitle.riskID);
					currentState.setRiskCommand(1);
					History.newItem(RiskAssessmentPilot.generateNavigationId(RiskAssessmentPilot.PageId.riskAssessment));
				}
			});

			Label Exposure = new Label(RiskExposure.getExposure(r.getImpact(), r.getLikelihood()));

			final RiskSummaryHyperLinkElement change = new RiskSummaryHyperLinkElement(rowNumber, r.getId(), messages.change());
			change.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					currentState.setCurrentRisk(riskTitle.riskID);
					currentState.setRiskCommand(2);

					History.newItem(RiskAssessmentPilot.generateNavigationId(RiskAssessmentPilot.PageId.riskAssessment));
				}
			});

			final RiskSummaryHyperLinkElement remove = new RiskSummaryHyperLinkElement(rowNumber, r.getId(), messages.remove());
			remove.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					Integer rowId = riskTable.getCellForEvent(event).getRowIndex();
					removeRisk(remove.riskID, rowId);
				}
			});

			riskTable.setWidget(rowNumber, 0, riskTitle);
			riskTable.setWidget(rowNumber, 1, Exposure);

			if (currentState.getMode() == GwtModesType.ReadWrite)
			{
				riskTable.setWidget(0, 0, addRisk);
				
				HorizontalPanel linkBar = new HorizontalPanel();
				linkBar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				linkBar.setStyleName("flex-link-bar");
				
				linkBar.add(change);
				linkBar.add(remove);
				
				riskTable.setWidget(rowNumber, 2, linkBar);
			}
			else
			{
				riskTable.setWidget(0, 0, new Label(messages.riskTitle()));
			}

			FlexCellFormatter formatter = riskTable.getFlexCellFormatter();
			formatter.setHorizontalAlignment(rowNumber, 0, HasHorizontalAlignment.ALIGN_LEFT);
			formatter.setHorizontalAlignment(rowNumber, 1, HasHorizontalAlignment.ALIGN_LEFT);
			formatter.setHorizontalAlignment(rowNumber, 2, HasHorizontalAlignment.ALIGN_RIGHT);

			rowNumber++;
		}
		
		if (rowNumber == 1)
		{
			DisclosurePanel disclosure = new DisclosurePanel();
			disclosure.add(new Label(messages.noRisksFound()));

			disclosure.setAnimationEnabled(true);
			disclosure.setOpen(true);
			riskTable.setWidget(1, 0, disclosure);
			riskTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
			riskTable.getCellFormatter().setStyleName(1, 0, "inner-table");
			riskTable.getFlexCellFormatter().setColSpan(1, 0, 3);
		}
	
	}
	
	class RiskSummaryHyperLinkElement extends SquareHyperlink
	{

		private int rowID;
		private int riskID;

		public RiskSummaryHyperLinkElement(int rowID, int riskID, String text)
			{
				super(text);
				this.rowID = rowID;
				this.riskID = riskID;
			}

		public void setRowID(int rowID)
		{
			this.rowID = rowID;
		}

		public int getRowID()
		{
			return rowID;
		}

		public void setRiskID(int riskID)
		{
			this.riskID = riskID;
		}

		public int getRiskID()
		{
			return riskID;
		}

	}

}