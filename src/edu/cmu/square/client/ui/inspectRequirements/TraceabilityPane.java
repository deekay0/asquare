package edu.cmu.square.client.ui.inspectRequirements;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtArtifact;
import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtBusinessGoal;
import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.model.GwtRisk;
import edu.cmu.square.client.model.GwtSubGoal;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.CategorizeRequirementsService;
import edu.cmu.square.client.remoteService.step.interfaces.CategorizeRequirementsServiceAsync;
import edu.cmu.square.client.remoteService.step.interfaces.CollectArtifactsService;
import edu.cmu.square.client.remoteService.step.interfaces.CollectArtifactsServiceAsync;
import edu.cmu.square.client.remoteService.step.interfaces.ElicitRequirementService;
import edu.cmu.square.client.remoteService.step.interfaces.ElicitRequirementServiceAsync;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsService;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsServiceAsync;
import edu.cmu.square.client.remoteService.step.interfaces.RiskAssessmentService;
import edu.cmu.square.client.remoteService.step.interfaces.RiskAssessmentServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;


public class TraceabilityPane extends BasePane
{
	private final InspectRequirementsPaneMessages messages = GWT.create(InspectRequirementsPaneMessages.class);
	
	private List<GwtRisk> risks;
	private List<GwtAsset> assets;
	private List<GwtSubGoal> subGoals;
	private List<GwtRequirement> requirements;
	private List<GwtArtifact> artifacts;
	private List<GwtCategory> categories;
	
	private VerticalPanel stackPanelFilterLayout = new VerticalPanel();
	private int projectId;  //used for convenience to hold the project id from the State Info class.
	private Filters filterSyncObj = new Filters();
	private VerticalPanel dispPanel = filterSyncObj.setRenderingPanel();
	private StackPanel filterPanelA;
	private ListBox dropDownFilter = new ListBox(false);
	private List<SquareHyperlink> links = new ArrayList<SquareHyperlink>();
	private HorizontalPanel  filtersSelectedTextPanel  = new HorizontalPanel();
	protected String displayText;
	
	
	public TraceabilityPane(State stateInfo)
	{
		super(stateInfo);
		filterPanelA = new StackPanel();
		this.projectId = stateInfo.getProjectID();
		this.populateDataStructuresFromDB();
	}

	
	/**
	 * Retrieve values from the db to populate the data structures
	 */
	private void populateDataStructuresFromDB()
	{
		this.showLoadingStatusBar();
		loadAssetsAndSubGoalsFromProject(); // load all the data from the
											// database
	}

	private void showTraceabilityPane()
	{
		Label associationTitle = new Label(messages.viewTraceability());
		associationTitle.setStyleName("section-heading");
		Button completionButton = new Button(messages.done());
		completionButton.addStyleName("square-button");
		completionButton.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					History.newItem(InspectRequirementsPilot.generateNavigationId(InspectRequirementsPilot.PageId.inspectionTechniqueDisplay));
				}

			});

		HorizontalPanel titlePanelLayout = new HorizontalPanel();
		titlePanelLayout.setWidth("100%");
		titlePanelLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		titlePanelLayout.add(associationTitle);
		titlePanelLayout.setHeight("25px");

		stackPanelFilterLayout.setWidth("100%");
		stackPanelFilterLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		stackPanelFilterLayout.add(renderStackPanel(stackPanelFilterLayout));
		// stackPanelFilterLayout.setHeight("500px");

		VerticalPanel dropDownFilterPanelLayout = new VerticalPanel();
		dropDownFilterPanelLayout.setWidth("100%");
		dropDownFilterPanelLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		dropDownFilterPanelLayout.add(renderDropDownFilterPanel());
		// dropDownFilterPanelLayout.setHeight("500px");

		HorizontalPanel buttonPanelLayout = new HorizontalPanel();
		buttonPanelLayout.setWidth("100%");
		buttonPanelLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		buttonPanelLayout.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		buttonPanelLayout.add(completionButton);
		buttonPanelLayout.setHeight("40px");

		DockPanel associationPanel = new DockPanel();
		associationPanel.setWidth("85%");
		associationPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		associationPanel.add(titlePanelLayout, DockPanel.NORTH);
		associationPanel.add(buttonPanelLayout, DockPanel.SOUTH);
		associationPanel.add(stackPanelFilterLayout, DockPanel.WEST);
		associationPanel.add(dropDownFilterPanelLayout, DockPanel.EAST);
		associationPanel.setCellWidth(stackPanelFilterLayout, "20%");
		associationPanel.setCellWidth(dropDownFilterPanelLayout, "80%");


		this.hideStatusBar();
		this.getContent().add(associationPanel);

	}

	/**
	 * Render the panel for the drop down box
	 * 
	 * @param dropDownFilterPanelLayout
	 *            the base panel
	 * @return
	 */
	private VerticalPanel renderDropDownFilterPanel()
	{
		VerticalPanel basePanel = new VerticalPanel();
		basePanel.setWidth("100%");
		basePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		HorizontalPanel basePanelForFilterPanel = new HorizontalPanel();
		basePanelForFilterPanel.setHeight("27px");
		basePanelForFilterPanel.setWidth("100%");
		basePanelForFilterPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		basePanelForFilterPanel.setSpacing(5);
		
		HorizontalPanel filterPanelB = new HorizontalPanel();
		filterPanelB.setHeight("27px");
		filterPanelB.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		filterPanelB.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		filterPanelB.setWidth("98%");
		filterSyncObj.setFilterBType(messages.requirements());
		drawFilterSelectionTextPanel();
		

		dropDownFilter.setWidth("150px");
		dropDownFilter.addItem(messages.assets());
		dropDownFilter.addItem(messages.risks());
		dropDownFilter.addItem(messages.goals());
		dropDownFilter.addItem(messages.requirements());
		dropDownFilter.addItem(messages.artifacts());
		dropDownFilter.addItem(messages.categories());

		dropDownFilter.addClickHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{
					String selected_filter = null;
					selected_filter = dropDownFilter.getItemText(dropDownFilter.getSelectedIndex());
					if (selected_filter.equalsIgnoreCase(messages.assets()))
					{
						filterSyncObj.setFilterBType(messages.assets());
					}
					else if (selected_filter.equalsIgnoreCase(messages.risks()))
					{
						filterSyncObj.setFilterBType(messages.risks());
					}
					else if (selected_filter.equalsIgnoreCase(messages.goals()))
					{
						filterSyncObj.setFilterBType(messages.goals());
					}
					else if (selected_filter.equalsIgnoreCase(messages.requirements()))
					{
						filterSyncObj.setFilterBType(messages.requirements());
					}
					else if (selected_filter.equalsIgnoreCase(messages.artifacts()))
					{
						filterSyncObj.setFilterBType(messages.artifacts());
					}
					else if (selected_filter.equalsIgnoreCase(messages.categories()))
					{
						filterSyncObj.setFilterBType(messages.categories());
					}
					drawFilterSelectionTextPanel();
				}
			});

		
		filterPanelB.add(filtersSelectedTextPanel);
		
		HorizontalPanel dropDownFilterPanel = new HorizontalPanel();
		dropDownFilterPanel.add(new Label(messages.showAll()));
		dropDownFilterPanel.add(dropDownFilter);
		filterPanelB.add(dropDownFilterPanel);

		basePanelForFilterPanel.add(filterPanelB);
		
		basePanel.add(basePanelForFilterPanel);
		basePanel.add(dispPanel); // RENDER DISPLAY
		filterSyncObj.setFilterBType(messages.requirements());
		dropDownFilter.setItemSelected(3, true);
		return basePanel;
	}

	private void drawFilterSelectionTextPanel()
	{
		Label selectedText = new Label(); 
		String tempString;
		if(filterSyncObj.getFilterBType()!=null)
		{
			if(filterSyncObj.getFilterAType()!=null)
			{
				tempString = filterSyncObj.getFilterAType().toLowerCase().trim();
				selectedText.setText(messages.viewingAll() + filterSyncObj.getFilterBType() + " " + messages.forFun() + tempString.substring(0, tempString.length()-1) + ": " + displayText); 
			}
			else
			{
				selectedText.setText(messages.noValSelectedFromFilter());
			}
		}
		else
		{
			if(filterSyncObj.getFilterAType()!=null)
			{
				selectedText.setText(messages.noValDDFilter());
			}
		}
		filtersSelectedTextPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		filtersSelectedTextPanel.setWidth("100%");
		filtersSelectedTextPanel.setSpacing(5);
		filtersSelectedTextPanel.clear();
		filtersSelectedTextPanel.add(selectedText);
	}


	private VerticalPanel renderStackPanel(VerticalPanel stackPanelFilterLayout2)
	{
		VerticalPanel basePanel = new VerticalPanel();
		basePanel.setWidth("100%");
		
		HorizontalPanel filterLabelPanel = new HorizontalPanel();
		filterLabelPanel.setWidth("100%");
		filterLabelPanel.setHeight("39px");
		filterLabelPanel.setSpacing(5);
		filterLabelPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		filterLabelPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		filterLabelPanel.add(new Label(messages.filterBy()));
		basePanel.add(filterLabelPanel);

		VerticalPanel assetStackPanel = new VerticalPanel(); // panel for list
																// of assets in
																// stack panel
		VerticalPanel riskStackPanel = new VerticalPanel();
		VerticalPanel goalStackPanel = new VerticalPanel();
		VerticalPanel requirementStackPanel = new VerticalPanel();
		VerticalPanel artifactStackPanel = new VerticalPanel();
		VerticalPanel categoryStackPanel = new VerticalPanel();
		assetStackPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		riskStackPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		goalStackPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		requirementStackPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		artifactStackPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		categoryStackPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		links.clear();
		// populate stack panels
		populateStackPanelCategories(assetStackPanel, messages.assets());
		populateStackPanelCategories(riskStackPanel, messages.risks());
		populateStackPanelCategories(goalStackPanel, messages.goals());
		populateStackPanelCategories(requirementStackPanel, messages.requirements());
		populateStackPanelCategories(artifactStackPanel, messages.artifacts());
		populateStackPanelCategories(categoryStackPanel, messages.categories());

		stackPanelFilterLayout2.clear();

		filterPanelA.setWidth("100%");
		filterPanelA.add(assetStackPanel, messages.assets(), true);
		filterPanelA.add(riskStackPanel, messages.risks(), true);
		filterPanelA.add(goalStackPanel, messages.goals(), true);
		filterPanelA.add(requirementStackPanel, messages.requirements(), true);
		filterPanelA.add(artifactStackPanel, messages.artifacts(), true);
		filterPanelA.add(categoryStackPanel, messages.categories(), true);

		// filterPanelA.setStylePrimaryName("section-heading");

		basePanel.add(filterPanelA);
		basePanel.setSpacing(2);

		return basePanel;
	}

	/**
	 * Populate the various sub panels within a stack panel
	 * 
	 * @param referencedStackPanelCategory
	 *            the sub panel within the stack panel
	 * @param string
	 *            the category title to know which data structures to populate
	 */
	private void populateStackPanelCategories(VerticalPanel referencedStackPanelCategory, String string)
	{
		referencedStackPanelCategory.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		referencedStackPanelCategory.setSpacing(7);

		if (string.equalsIgnoreCase(messages.assets()))
		{
			for (int i = 0; i < assets.size(); i++)
			{
				SquareHyperlink tempLink = new SquareHyperlink(assets.get(i).getDescription());
				
				addListenerToLink(string.trim(), referencedStackPanelCategory, tempLink, i);
				links.add(tempLink);
			}
		}
		if (string.equalsIgnoreCase(messages.risks()))
		{
			for (int i = 0; i < risks.size(); i++)
			{
				SquareHyperlink tempLink = new SquareHyperlink(risks.get(i).getRiskTitle());
				
				addListenerToLink(string.trim(), referencedStackPanelCategory, tempLink, i);
				links.add(tempLink);
			}
		}
		if (string.equalsIgnoreCase(messages.goals()))
		{
			for (int i = 0; i < subGoals.size(); i++)
			{
				SquareHyperlink tempLink = new SquareHyperlink(subGoals.get(i).getDescription());
				
				addListenerToLink(string.trim(), referencedStackPanelCategory, tempLink, i);
				links.add(tempLink);
			}
		}
		if (string.equalsIgnoreCase(messages.requirements()))
		{
			for (int i = 0; i < requirements.size(); i++)
			{
				SquareHyperlink tempLink = new SquareHyperlink(requirements.get(i).getTitle());
				
				addListenerToLink(string.trim(), referencedStackPanelCategory, tempLink, i);
				links.add(tempLink);
			}
		}
		if (string.equalsIgnoreCase(messages.artifacts()))
		{
			for (int i = 0; i < artifacts.size(); i++)
			{
				SquareHyperlink tempLink = new SquareHyperlink(artifacts.get(i).getName());
				
				addListenerToLink(string.trim(), referencedStackPanelCategory, tempLink, i);
				links.add(tempLink);
			}
		}
		if (string.equalsIgnoreCase(messages.categories()))
		{
			for (int i = 0; i < categories.size(); i++)
			{
				SquareHyperlink tempLink = new SquareHyperlink(categories.get(i).getCategoryName());
				
				addListenerToLink(string.trim(), referencedStackPanelCategory, tempLink, i);
				links.add(tempLink);
			}
		}
	}

	/**
	 * Add the listener to the hyper-link
	 * 
	 * @param referencedStackPanelCategory
	 *            subpanel of stack pane;
	 * @param tempLink
	 *            hyperlink
	 * @param i
	 *            loop variable i
	 */
	private void addListenerToLink(final String type, VerticalPanel referencedStackPanelCategory, final SquareHyperlink tempLink, final int i)
	{
		referencedStackPanelCategory.add(tempLink);

		tempLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					//clear out everything else
					for (SquareHyperlink link: links) 
					{
						link.setStyleName("link-notselect");
					}
					tempLink.setStyleName("square-Categorize-selected");
					
					if (type.equalsIgnoreCase(messages.assets()))
					{
						dropDownFilter.clear();
						addItemsToDropDownFilterExcept(messages.assets());
						if(filterSyncObj.getFilterBType()!=null)
						{
							if(filterSyncObj.getFilterBType().equalsIgnoreCase(messages.assets()))
							{
								filterSyncObj.setFilterBType(null);
							}
							else
							{
								for(int i=0;i<dropDownFilter.getItemCount();i++)
								{
									if(dropDownFilter.getItemText(i).equalsIgnoreCase(filterSyncObj.getFilterBType().trim()))
									{
										dropDownFilter.setItemSelected(i, true);
										break;
									}
								}
							}
						}
						filterSyncObj.setFilterAType(messages.assets(), assets.get(i).getId().intValue());
						displayText = tempLink.getText();
					}
					if (type.equalsIgnoreCase(messages.risks()))
					{
						dropDownFilter.clear();
						addItemsToDropDownFilterExcept(messages.risks());
						if(filterSyncObj.getFilterBType()!=null)
						{
							if(filterSyncObj.getFilterBType().equalsIgnoreCase(messages.risks()))
							{
								filterSyncObj.setFilterBType(null);
							}
							else
							{
								for(int i=0;i<dropDownFilter.getItemCount();i++)
								{
									if(dropDownFilter.getItemText(i).equalsIgnoreCase(filterSyncObj.getFilterBType().trim()))
									{
										dropDownFilter.setItemSelected(i, true);
										break;
									}
								}
							}
						}
						filterSyncObj.setFilterAType(messages.risks(), risks.get(i).getId().intValue());
						displayText = tempLink.getText();
					}
					if (type.equalsIgnoreCase(messages.goals()))
					{
						dropDownFilter.clear();
						addItemsToDropDownFilterExcept(messages.goals());
						if(filterSyncObj.getFilterBType().equalsIgnoreCase(messages.goals()))
						{
							filterSyncObj.setFilterBType(null);
						}
						else
						{
							if(filterSyncObj.getFilterBType()!=null)
							{
								for(int i=0;i<dropDownFilter.getItemCount();i++)
								{
									if(dropDownFilter.getItemText(i).equalsIgnoreCase(filterSyncObj.getFilterBType().trim()))
									{
										dropDownFilter.setItemSelected(i, true);
										break;
									}
								}
							}
						}
					
						filterSyncObj.setFilterAType(messages.goals(), subGoals.get(i).getId().intValue());
						displayText = tempLink.getText();
					}
					if (type.equalsIgnoreCase(messages.requirements()))
					{
						dropDownFilter.clear();
						addItemsToDropDownFilterExcept(messages.requirements());
						if(filterSyncObj.getFilterBType().equalsIgnoreCase(messages.requirements()))
						{
							filterSyncObj.setFilterBType(null);
						}
						else
						{
							if(filterSyncObj.getFilterBType()!=null)
							{
								for(int i=0;i<dropDownFilter.getItemCount();i++)
								{
									if(dropDownFilter.getItemText(i).equalsIgnoreCase(filterSyncObj.getFilterBType().trim()))
									{
										dropDownFilter.setItemSelected(i, true);
										break;
									}
								}
							}
						}
						filterSyncObj.setFilterAType(messages.requirements(), requirements.get(i).getId().intValue());
						displayText = tempLink.getText();
					}
					if (type.equalsIgnoreCase(messages.artifacts()))
					{
						dropDownFilter.clear();
						addItemsToDropDownFilterExcept(messages.artifacts());
						if(filterSyncObj.getFilterBType().equalsIgnoreCase(messages.artifacts()))
						{
							filterSyncObj.setFilterBType(null);
						}
						else
						{
							if(filterSyncObj.getFilterBType()!=null)
							{
								for(int i=0;i<dropDownFilter.getItemCount();i++)
								{
									if(dropDownFilter.getItemText(i).equalsIgnoreCase(filterSyncObj.getFilterBType().trim()))
									{
										dropDownFilter.setItemSelected(i, true);
										break;
									}
								}
							}
						}
						filterSyncObj.setFilterAType(messages.artifacts(), artifacts.get(i).getId());
						displayText = tempLink.getText();
					}
					if (type.equalsIgnoreCase(messages.categories()))
					{
						dropDownFilter.clear();
						addItemsToDropDownFilterExcept(messages.categories()); 
						if(filterSyncObj.getFilterBType().equalsIgnoreCase(messages.categories()))
						{
							filterSyncObj.setFilterBType(null);
						}
						else
						{
							if(filterSyncObj.getFilterBType()!=null)
							{
								for(int i=0;i<dropDownFilter.getItemCount();i++)
								{
									if(dropDownFilter.getItemText(i).equalsIgnoreCase(filterSyncObj.getFilterBType().trim()))
									{
										dropDownFilter.setItemSelected(i, true);
										break;
									}
								}
							}
						}
						filterSyncObj.setFilterAType(messages.categories(), categories.get(i).getId());
						displayText = tempLink.getText();
					}
					
					//if nothing has been selected, select assets by default
					if(filterSyncObj.getFilterBType()==null)
					{
						if(!type.equalsIgnoreCase(messages.assets()))
						{
							filterSyncObj.setFilterBType(messages.assets());
							dropDownFilter.setItemSelected(0, true);
						}
						else
						{
							filterSyncObj.setFilterBType(messages.risks());
							dropDownFilter.setItemSelected(0, true);
						}
					}
					
					drawFilterSelectionTextPanel();
				}

				private void addItemsToDropDownFilterExcept(String string)
				{
					if (!string.equalsIgnoreCase(messages.assets()))
					{
						dropDownFilter.addItem(messages.assets());
					}
					if (!string.equalsIgnoreCase(messages.risks()))
					{
						dropDownFilter.addItem(messages.risks());
					}
					if (!string.equalsIgnoreCase(messages.goals()))
					{
						dropDownFilter.addItem(messages.goals());
					}
					if (!string.equalsIgnoreCase(messages.requirements()))
					{
						dropDownFilter.addItem(messages.requirements());
					}
					if (!string.equalsIgnoreCase(messages.artifacts()))
					{
						dropDownFilter.addItem(messages.artifacts());
					}
					if (!string.equalsIgnoreCase(messages.categories()))
					{
						dropDownFilter.addItem(messages.categories());
					}
				}
			});
	}

	public void loadTraceInfo(int projectId)
	{
		this.projectId = projectId;
		loadAssetsAndSubGoalsFromProject();

	}

	private void loadAssetsAndSubGoalsFromProject()
	{
		IdentifyGoalsAssetsServiceAsync service = GWT.create(IdentifyGoalsAssetsService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");
		service.loadBusinessGoalInfo(this.projectId, new AsyncCallback<GwtBusinessGoal>()
		{
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.retrievingGoals());
			}

			public void onSuccess(GwtBusinessGoal result)
			{
				subGoals = result.getSubGoals();
				assets = result.getAssets();
				loadArtifactsFromProject();
			}
		});
	}
	
	private void loadArtifactsFromProject()
	{
		CollectArtifactsServiceAsync service = GWT.create(CollectArtifactsService.class);

		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "collectArtifacts.rpc");
		service.getAllArtifacts(projectId, new AsyncCallback<List<GwtArtifact>>()
		{
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.retrievingArtifacts());
			}

			public void onSuccess(List<GwtArtifact> result)
			{
				artifacts = result;
				loadProjectRequirements();
			}
		});
	}

	private void loadProjectRequirements()
	{
		ElicitRequirementServiceAsync service = GWT.create(ElicitRequirementService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "elicitRequirement.rpc");
		service.getRequirementsFromProject(this.projectId, new AsyncCallback<List<GwtRequirement>>()
			{
				public void onFailure(Throwable caught)
				{

					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.retrievingRequirements());
				}
				public void onSuccess(List<GwtRequirement> result)
				{
					requirements = result;
					loadProjectRisks();
				}

			});
	}
	private void loadProjectRisks()
	{
		RiskAssessmentServiceAsync service = GWT.create(RiskAssessmentService.class);

		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "riskAssessment.rpc");
		service.getRisksFromProject(projectId, new AsyncCallback<List<GwtRisk>>()
			{

				public void onFailure(Throwable caught)
				{

					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.retrievingRisks());
				}

				public void onSuccess(List<GwtRisk> result)
				{

					risks = result;
					loadProjectCategories();
				}

			});

	}
	private void loadProjectCategories()
	{
		CategorizeRequirementsServiceAsync service = GWT.create(CategorizeRequirementsService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "elicitRequirement.rpc");

		GwtProject project = new GwtProject();
		project.setId(this.projectId);
		service.getCategories(project, new AsyncCallback<List<GwtCategory>>()
			{
				public void onFailure(Throwable caught)
				{

					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.retrievingProjectCategories());
				}
				public void onSuccess(List<GwtCategory> result)
				{
					categories = result;
					showTraceabilityPane();
					filterSyncObj.setArtifacts(artifacts);
					filterSyncObj.setAssets(assets);
					filterSyncObj.setCategories(categories);
					filterSyncObj.setRequirements(requirements);
					filterSyncObj.setRisks(risks);
					filterSyncObj.setSubGoals(subGoals);
				}

			});
	}

}
