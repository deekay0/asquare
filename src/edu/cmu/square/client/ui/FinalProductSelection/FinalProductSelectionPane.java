package edu.cmu.square.client.ui.FinalProductSelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtRating;
import edu.cmu.square.client.model.GwtRationale;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.FinalProductSelectionService;
import edu.cmu.square.client.remoteService.step.interfaces.FinalProductSelectionServiceAsync;
import edu.cmu.square.client.remoteService.step.interfaces.ReviewPackagesService;
import edu.cmu.square.client.remoteService.step.interfaces.ReviewPackagesServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;
import edu.cmu.square.server.dao.model.SoftwarePackage;

public class FinalProductSelectionPane extends BasePane
{
	final private FinalProductSelectionPaneMessages messages = (FinalProductSelectionPaneMessages)GWT.create(FinalProductSelectionPaneMessages.class);

	private ReviewPackagesServiceAsync service = GWT.create(ReviewPackagesService.class);
	private FinalProductSelectionServiceAsync finalProductSelectionService = GWT.create(FinalProductSelectionService.class);
	
	private Label rationaleHeaderLabel = null;
	private Label rationaleLabel = null;
	private List<GwtSoftwarePackage> softwarePackages;
	private List<GwtRequirement> listOfRequirements = new ArrayList<GwtRequirement>();
	private List<GwtQualityAttribute> attributes;
	private List<GwtRating> ratings;
	private List<GwtRequirementRating> requirementRatings;
	private List<GwtTradeoffReason> tradeoffReasons;
	
	private GwtRationale rationale;
	
	private EditTradeoffReasonDialog editTradeoffReasonDialog;
	private AddRationaleDialog addRationaleDialog;
	
	GwtProject currentProject;
	
 
	private FlexTable matrix = new FlexTable();
	private FlexTable matrixHeader = new FlexTable();
	
	boolean isReadOnly=false;

	public FinalProductSelectionPane(final State stateInfo)
	{
		super(stateInfo);
		
		this.showLoadingStatusBar();
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "reviewPackages.rpc");
		
		ServiceDefTarget endpoint2 = (ServiceDefTarget) finalProductSelectionService;
		endpoint2.setServiceEntryPoint(GWT.getModuleBaseURL() + "finalProductSelection.rpc");
		
		currentProject = new GwtProject();
		currentProject.setId(this.getCurrentState().getProjectID());
		
	
		isReadOnly = true;
		loadAttributes();
//		loadRationale();
//		loadTradeoffReasons();
	}
	
	public void PaneInitialization()
	{
		this.hideStatusBar();
		VerticalPanel layout = new VerticalPanel();
		
		drawMatrixPage();
	
		//this.getContent().clear();
		//layout.add(comparisonMatrixLabel);
		layout.add(matrixHeader);
		this.getContent().add(layout);
		
		rationaleHeaderLabel = new Label("Selection Rationale: ");
		layout.add(rationaleHeaderLabel);
		layout.add(rationaleLabel);
		//initWidget(layout);
	}
	
	public void loadRationale()
	{
		System.out.println("loading rationale");
		finalProductSelectionService.getRationale(currentProject, new AsyncCallback<GwtRationale >()
				{		
					@Override
					public void onSuccess(GwtRationale  result)
					{
						rationale = result;
						if(result == null)
						{
							rationaleLabel = new Label("No final product selection has been made yet.");
						}
						else
						{
							
							rationaleLabel = new Label(result.getRationale());
						}
						rationaleLabel.setWidth("500px");
						rationaleLabel.setSize("500px", "80px");
						loadTradeoffReasons();
					}			
					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert(messages.rationaleRetrievalError());
						ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Retrieving Rationale");
					}
				});		
	}
	
	public Widget getHeaderRow()
	{

		FlexTable searchTable = new FlexTable();
		searchTable.setWidth("100%");
		searchTable.setCellSpacing(0);
		HorizontalPanel searchBox = new HorizontalPanel();
		searchBox.setSpacing(0);
		Button clearButton = new Button("X");
		// clearButton.setStyleName("square-clear-label");
		clearButton.setSize("25px", "26px");

		searchTable.setWidget(0, 1, new Label(" "));
		searchTable.setWidget(0, 2, searchBox);

		searchTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		searchTable.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);

		searchTable.getCellFormatter().setStyleName(0, 0, "square-Categorize-TableHeader");
		searchTable.getCellFormatter().setStyleName(0, 1, "square-Categorize-TableHeader");
		searchTable.getCellFormatter().setStyleName(0, 2, "square-Categorize-TableHeader");

		return searchTable;
	}
	
	public Widget getDataRow(int rowCount, GwtRequirement req)
	{
		FlexTable rowTable = new FlexTable();
		rowTable.setStyleName("square-agree-on-definition-flex"); //ASQUARE change!!!!
		rowTable.setCellSpacing(4);
		rowTable.setSize("100%", "50px");

		Label reqTitleLabel = new Label(req.getTitle());
		Label reqDescriptionLabel = new Label(req.getDescription());
		Label reqStatusLabel = new Label(req.getStatus());
		
		
		final SummaryElementHyperLinkElement viewDetailLink = new SummaryElementHyperLinkElement(req.getId(), "View Detail");

		if (this.getCurrentState().getMode().equals(GwtModesType.ReadWrite))
		{
			HorizontalPanel links = new HorizontalPanel();
			links.setStyleName("flex-link-bar");
			links.add(viewDetailLink);
			rowTable.setWidget(0, 3, links);
		}
		else
		{
			rowTable.setWidget(0, 2, new Label(" "));
		}

		//view detail hyperlink
		viewDetailLink.addClickHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{
					currentState.setCurrentRisk(viewDetailLink.getRequirementId());
					currentState.setRiskCommand(1);
					//History.newItem(ReviewAndFinalizeRequirementsPilot.generateNavigationId(ReviewAndFinalizeRequirementsPilot.PageId.requirementDetail));
				}
			});

		rowTable.setWidget(0, 0, reqTitleLabel);
		rowTable.setWidget(0, 1, reqDescriptionLabel);
		rowTable.setWidget(0, 2, reqStatusLabel);

		rowTable.getCellFormatter().setWidth(0, 0, "20%");
		rowTable.getCellFormatter().setWidth(0, 1, "60%");
		rowTable.getCellFormatter().setWidth(0, 2, "15%");
		rowTable.getCellFormatter().setWidth(0, 3, "20%");
		
		rowTable.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);
		rowTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);

		rowTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		rowTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		rowTable.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);

		rowTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		rowTable.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		rowTable.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_TOP);

		return rowTable;
	}	
	
	private void drawMatrixPage()
	{
		FlexCellFormatter formatter1 = this.matrixHeader.getFlexCellFormatter();
		this.matrixHeader.setWidth("100%");

		this.matrixHeader.setWidget(1, 1, new Label(messages.matrixLableX()));
		this.matrixHeader.setWidget(3, 0, new Label(messages.matrixLableY()));
		this.matrixHeader.setWidget(4, 1, new Label(messages.rateLegend()));
		
		
		formatter1.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter1.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);
		formatter1.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		formatter1.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_LEFT);
		formatter1.setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter1.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_LEFT);
		formatter1.setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		this.matrixHeader.setWidget(3, 1, matrix);		
		
	}
	
	/**
	 * Contains RPC Calls to retrieve the evaluation criteria for the project
	 */
	
	private void loadAttributes()
	{
		final StepStatus stepStatus =  StepStatus.NotStarted;
		service.getQualityAttributes(currentProject,stepStatus, new AsyncCallback<List<GwtQualityAttribute> >()
				{
					@Override
					public void onSuccess(List<GwtQualityAttribute> result)
					{
						System.out.println("We got quality attribute: "+result.size());
						attributes = result;
						loadPackages();
						
					}
					
					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert(messages.attributesRetrievalError());
						ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Retrieving Quality Attributes");		
					}
				});
	}

	private void loadPackages()
	{
		final StepStatus stepStatus =  StepStatus.NotStarted;
		service.getSoftwarePackages(currentProject,stepStatus, new AsyncCallback<List<GwtSoftwarePackage>>()
		{
			@Override
			public void onSuccess(List<GwtSoftwarePackage> result)
			{
				softwarePackages = result;
				
				
				loadRatings();	
			}	
			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert(messages.packagesRetrievalError());
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Retrieving Software Packages");		
			}
		});
	}
	
	private void loadTradeoffReasons()
	{
		finalProductSelectionService.getTradeoffReasons(currentProject.getId(), new AsyncCallback<List<GwtTradeoffReason>>()		
				{		
					@Override
					public void onSuccess(List<GwtTradeoffReason> result)
					{
						
						tradeoffReasons = result;
						
						final List<GwtSoftwarePackage> temp = softwarePackages.subList(1, softwarePackages.size());
						for(int i=0; i<temp.size(); ++i)
							System.out.println(temp.get(i).getName());
						
						System.out.println("\n\n");
						
						for(int i=0; i<softwarePackages.size(); ++i)
							System.out.println(softwarePackages.get(i).getName());
						
						System.out.println("\n\n");
						
						for(int i=0; i<tradeoffReasons.size(); ++i)
							System.out.println(tradeoffReasons.get(i).getTradeoffreason());
						
						System.out.println("\n\n");
						
						Collections.sort(temp, new Comparator<GwtSoftwarePackage>()
								{
									public int compare(GwtSoftwarePackage o1, GwtSoftwarePackage o2) 
									{									
										int a, b;
										
										System.out.println(temp.get(temp.indexOf(o1)).getName()+ " has priority ");
										System.out.println(tradeoffReasons.get(temp.indexOf(o1)).getPriority());
										
										a = tradeoffReasons.get(temp.indexOf(o1)).getPriority();
										b = tradeoffReasons.get(temp.indexOf(o2)).getPriority();
										if(a > b)
											return -1;
										else if(a < b)
											return 1;
										else
											return 0;
								    }
								});
						softwarePackages = softwarePackages.subList(0, 1);
						softwarePackages.addAll(temp);
						
						loadRequirementRatings();
						
					}
					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert(messages.ratingsRetrievalError());
						ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Retrieving Tradeoff Reasons");
					}	
				});		
	}

	private void loadRatings()
	{
		service.getRateValues(currentProject.getId(), new AsyncCallback<List<GwtRating > >()
				{		
					@Override
					public void onSuccess(List<GwtRating >  result)
					{
						
						ratings = result;
						
						loadRationale();
					}			
					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert(messages.ratingsRetrievalError());
						ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Retrieving Ratings");
					}
				});		
	}
	
	private void loadRequirementRatings()
	{
		finalProductSelectionService.getRequirementRateValues(currentProject.getId(), new AsyncCallback<List<GwtRequirementRating > >()
				{		
					@Override
					public void onSuccess(List<GwtRequirementRating> result)
					{
						requirementRatings = result;
						drawRateMatrix();
						getTotalsFromMatrix();
						PaneInitialization();
					}
					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert(messages.ratingsRetrievalError());
						ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Retrieving Requirement Ratings");
					}	
				});		
	}

	
	
	
	
	
	public void drawRateMatrix()
	{
		matrix.clear();
		//matrix.setBorderWidth(1);
		matrix.setWidth("100%");
		matrix.setStyleName("square-Matrix");
		matrix.setCellSpacing(0);
		drawAttributesRequirements();
		drawSoftwarePackages();
		drawRateMatrixValues();
	}
	
	public void drawAttributesRequirements()
	{
		FlexCellFormatter formatter = this.matrix.getFlexCellFormatter();
		matrix.setWidget(0, 0, new Label(" "));
		formatter.setStyleName(0, 0, "square-Matrix");
			
		int i, j;
		// Set the header rows  with requirements
		for(i=1; i<=listOfRequirements.size();i++)
		{
			Label techniqueLabel = null;
			final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
			   // simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
			simplePopup.setWidth("150px");
			
			techniqueLabel = new Label(listOfRequirements.get(i-1).getTitle());
			simplePopup.setWidget(new HTML(listOfRequirements.get(i-1).getDescription()));
			
				//techniqueLabel = new Label(listOfRequirements.get(i-1).getTitle());
				//simplePopup.setWidget(new HTML(listOfRequirements.get(i-1).getDescription()));		   
			
			techniqueLabel.addMouseOverHandler(new MouseOverHandler(){
					public void onMouseOver(MouseOverEvent event) 
					{
						Widget source = (Widget) event.getSource();
			            int left = source.getAbsoluteLeft() + 20;
			            int top = source.getAbsoluteTop() + 20;
			            simplePopup.setPopupPosition(left, top);
						simplePopup.show();
					}});
			    
			techniqueLabel.addMouseOutHandler(new MouseOutHandler(){
					public void onMouseOut(MouseOutEvent event) {
						simplePopup.hide();				
					}});
			    
			matrix.setWidget(0, i, techniqueLabel);
			formatter.setHorizontalAlignment(0, i, HasHorizontalAlignment.ALIGN_CENTER);
			formatter.setStyleName(0, i, "square-Matrix");
		}
		// Set the header rows  with attributes
		for(j=1; j<=attributes.size();j++)
		{
			Label techniqueLabel = null;
			final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
			   // simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
			simplePopup.setWidth("150px");
			
			techniqueLabel = new Label(attributes.get(j-1).getName());
			simplePopup.setWidget(new HTML(attributes.get(j-1).getDescription()));
			
				//techniqueLabel = new Label(listOfRequirements.get(i-1).getTitle());
				//simplePopup.setWidget(new HTML(listOfRequirements.get(i-1).getDescription()));
					
			techniqueLabel.addMouseOverHandler(new MouseOverHandler(){
					public void onMouseOver(MouseOverEvent event) 
					{
						Widget source = (Widget) event.getSource();
			            int left = source.getAbsoluteLeft() + 20;
			            int top = source.getAbsoluteTop() + 20;
			            simplePopup.setPopupPosition(left, top);
						simplePopup.show();
					}});
			    
			techniqueLabel.addMouseOutHandler(new MouseOutHandler(){
					public void onMouseOut(MouseOutEvent event) {
						simplePopup.hide();				
					}});
			    
			matrix.setWidget(0, listOfRequirements.size()+j, techniqueLabel);
			formatter.setHorizontalAlignment(0, listOfRequirements.size()+j, HasHorizontalAlignment.ALIGN_CENTER);
			formatter.setStyleName(0, listOfRequirements.size()+j, "square-Matrix");
		}
		
		matrix.setWidget(0, attributes.size()+listOfRequirements.size()+1, new Label("Total"));
		matrix.setWidget(0, attributes.size()+listOfRequirements.size()+2, new Label("Tradeoff Reason"));
		matrix.setWidget(0, attributes.size()+listOfRequirements.size()+3, new Label("Priority"));
		matrix.setWidget(0, attributes.size()+listOfRequirements.size()+4, new Label("Final Selection"));
		formatter.setHorizontalAlignment(0, attributes.size()+listOfRequirements.size()+1, HasHorizontalAlignment.ALIGN_RIGHT);
		formatter.setStyleName(0, attributes.size()+listOfRequirements.size()+1,"square-Matrix");	
		formatter.setHorizontalAlignment(0, attributes.size()+listOfRequirements.size()+2, HasHorizontalAlignment.ALIGN_RIGHT);
		formatter.setStyleName(0, attributes.size()+listOfRequirements.size()+2,"square-Matrix");	
		formatter.setHorizontalAlignment(0, attributes.size()+listOfRequirements.size()+3, HasHorizontalAlignment.ALIGN_RIGHT);
		formatter.setStyleName(0, attributes.size()+listOfRequirements.size()+3,"square-Matrix");
		formatter.setHorizontalAlignment(0, attributes.size()+listOfRequirements.size()+4, HasHorizontalAlignment.ALIGN_RIGHT);
		formatter.setStyleName(0, attributes.size()+listOfRequirements.size()+4,"square-Matrix");
	}
	
	public void drawSoftwarePackages()
	{
		FlexCellFormatter formatter = this.matrix.getFlexCellFormatter();
		// Set the left columns with the package names 
		for(int j=0; j<softwarePackages.size();j++)
		{		
			Label packageLabel = new Label(softwarePackages.get(j).getName());
			
			final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
			simplePopup.setWidth("150px");
			simplePopup.setWidget(new HTML(softwarePackages.get(j).getDescription()));
			
			packageLabel.addMouseOverHandler(new MouseOverHandler(){		
				public void onMouseOver(MouseOverEvent event) {
						Widget source = (Widget) event.getSource();
			            int left = source.getAbsoluteLeft() + 40;
			            int top = source.getAbsoluteTop() + 20;
			            simplePopup.setPopupPosition(left, top);
						simplePopup.show();
				}});
			packageLabel.addMouseOutHandler(new MouseOutHandler(){	
				public void onMouseOut(MouseOutEvent event) {
						simplePopup.hide();
				}});
			matrix.setWidget(j+1,0 , packageLabel);
			
			
			final SummaryElementHyperLinkElement tradeoffReasonLink = new SummaryElementHyperLinkElement(softwarePackages.get(j).getId(), "Tradeoff Reason");
			final int index = j;
			tradeoffReasonLink.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					editTradeoffReasonDialog = new EditTradeoffReasonDialog(tradeoffReasons.get(index),tradeoffReasons,FinalProductSelectionPane.this);
					editTradeoffReasonDialog.center();
					editTradeoffReasonDialog.setModal(true);
					editTradeoffReasonDialog.show();
					
				}});
			
			RadioButton rButton = new RadioButton("group");
			
			if (this.getCurrentState().getMode().equals(GwtModesType.ReadWrite))
			{
				rButton.addClickHandler(new ClickHandler()
					{	
						@Override
						public void onClick(ClickEvent event)
						{
							addRationaleDialog = new AddRationaleDialog( FinalProductSelectionPane.this, currentProject, softwarePackages.get(index));
							addRationaleDialog.center();
							addRationaleDialog.setModal(true);
							addRationaleDialog.show();
							
						}}
				);
			}
			else
			{
				rButton.setEnabled(false);
			}
			if(rationale != null && softwarePackages.get(j).getId() == rationale.getPackage().getId())
				rButton.setValue(true);
			
			if(j>=1)
			{
				matrix.setWidget(j+1,attributes.size()+listOfRequirements.size()+2, tradeoffReasonLink);
				formatter.setHorizontalAlignment(j+1, attributes.size()+listOfRequirements.size()+2, HasHorizontalAlignment.ALIGN_RIGHT);
				formatter.setStyleName(j+1, attributes.size()+listOfRequirements.size()+2,  "square-Matrix");
				
				matrix.setWidget(j+1,attributes.size()+listOfRequirements.size()+3, new Label(tradeoffReasons.get(index).getPriority().toString()));
				formatter.setHorizontalAlignment(j+1, attributes.size()+listOfRequirements.size()+3, HasHorizontalAlignment.ALIGN_CENTER);
				formatter.setStyleName(j+1, attributes.size()+listOfRequirements.size()+3,  "square-Matrix");
			
				
				matrix.setWidget(j+1,attributes.size()+listOfRequirements.size()+4, rButton);
				formatter.setHorizontalAlignment(j+1, attributes.size()+listOfRequirements.size()+4, HasHorizontalAlignment.ALIGN_CENTER);
				formatter.setStyleName(j+1, attributes.size()+listOfRequirements.size()+4,  "square-Matrix");
				
				}
			formatter.setHorizontalAlignment(j+1,0 , HasHorizontalAlignment.ALIGN_RIGHT);
			formatter.setStyleName(j+1,0, "square-Matrix");		
		}	
	}
	
	public void drawRateMatrixValues()
	{
		FlexCellFormatter formatter = this.matrix.getFlexCellFormatter();
		for(int i=0;i<attributes.size()+listOfRequirements.size();i++)
		{
			for(int j=0; j<softwarePackages.size();j++)
			{
				
				if(i<listOfRequirements.size())
				{
					int rID=listOfRequirements.get(i).getId();
					int pID=softwarePackages.get(j).getId();			
					int value = getValueFromlistOfRequirementRateValues(pID,rID);
					
					
						Label valueLabel = new Label(String.valueOf(value));
						valueLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
						matrix.setWidget(j+1, i+1, valueLabel);
						formatter.setHorizontalAlignment(j+1, i+1, HasHorizontalAlignment.ALIGN_CENTER);
						formatter.setStyleName(j+1, i+1,"square-Matrix");
								
					RateValueLabel totalLabel= new RateValueLabel(listOfRequirements.get(i).getId());
					totalLabel.setText("0");
	
					//SummaryElementHyperLinkElement tradeoffReasonLink = new SummaryElementHyperLinkElement(softwarePackages.get(i).getId(), "Tradeoff Reason");
					if(j>=1)
					{
						matrix.setWidget(j+1, listOfRequirements.size()+1, totalLabel);
						//matrix.setWidget(j+1, listOfRequirements.size()+2, tradeoffReasonLink);
						formatter.setHorizontalAlignment(j+1, listOfRequirements.size()+1,  HasHorizontalAlignment.ALIGN_CENTER);
						formatter.setStyleName(j+1, listOfRequirements.size()+1,"square-Matrix");
					}
				}
				else
				{
					int aID=attributes.get(i-listOfRequirements.size()).getId();
					int pID=softwarePackages.get(j).getId();				
					int value = getValueFromlistOfRateValues(pID,aID);
					
					Label valueLabel = new Label(String.valueOf(value));
					valueLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
					matrix.setWidget(j+1, i+1, valueLabel);
					formatter.setHorizontalAlignment(j+1, i+1, HasHorizontalAlignment.ALIGN_CENTER);
					formatter.setStyleName(j+1, i+1,"square-Matrix");
		
					RateValueLabel totalLabel= new RateValueLabel(attributes.get(i-listOfRequirements.size()).getId());
					totalLabel.setText("0");
	
					
					if(j>=1)
					{
					matrix.setWidget(j+1, attributes.size()+listOfRequirements.size()+1, totalLabel);
					
					//System.out.println("here........"+softwarePackages.get(i).getId());
					//SummaryElementHyperLinkElement tradeoffReasonLink = new SummaryElementHyperLinkElement(softwarePackages.get(i).getId(), "Tradeoff Reason");
					//matrix.setWidget(j+1, listOfRequirements.size()+2, tradeoffReasonLink);
					
					formatter.setHorizontalAlignment(j+1, attributes.size()+listOfRequirements.size()+1,  HasHorizontalAlignment.ALIGN_CENTER);
					formatter.setStyleName(j+1, attributes.size()+listOfRequirements.size()+1,"square-Matrix");	
					}
				}

			}
		}
	}
	/**
	 * Get the totals traversing the current Matrix FlexTable
	 */
	public void getTotalsFromMatrix()
	{
		for(int i=2; i<=softwarePackages.size();i++)
		{	int sum=0;
			
			for(int j=1; j<=attributes.size()+listOfRequirements.size();j++)
			{
				
					Widget w =  matrix.getWidget(i, j);
					Widget weight =  matrix.getWidget(1, j);
					
					if ( w instanceof RateValueTextbox) 
					{
						RateValueTextbox new_name = (RateValueTextbox) w;
						RateValueTextbox weightVal = (RateValueTextbox) weight;
						//Window.alert(new_name.getText());
						//sum=sum+ Integer.parseInt(new_name.getText());
						sum += Integer.parseInt(new_name.getText()) * Integer.parseInt(weightVal.getText());
					}
					else if( w instanceof Label)
					{
						Label new_name = (Label) w;
						Label weightVal = (Label) weight;
						//Window.alert(new_name.getText());
						//sum=sum+ Integer.parseInt(new_name.getText());
						sum += Integer.parseInt(new_name.getText()) * Integer.parseInt(weightVal.getText());
					}
					
			}
			
//				Widget totalLabel =  matrix.getWidget(softwarePackages.size()+1, i);
			Widget totalLabel =  matrix.getWidget(i, attributes.size()+listOfRequirements.size()+1);
			RateValueLabel new_name = (RateValueLabel) totalLabel;
			new_name.setText(String.valueOf(sum));
				//new_name.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		}
		
		
	}


	/**
	 * Traverse the total row in the Matrix and get the top 1 rated technique
	 * including ties
	 * @return List<GwtTechnique> top 1 rated technique(s) remember it could be ties in 1st place
	 */


	/**
	 * This method search the rate values in the loaded by RCP initially
	 * @param techniqueID
	 * @param evaluationID
	 * @return rateValue
	 */
	private int getValueFromlistOfRateValues(int packageID, int attributeID)
	{
		for(int j=0; j<ratings.size();j++)
		{
			int aID=ratings.get(j).getAttributeId();
			int pID =ratings.get(j).getPackageId();
			
			if(packageID==pID && attributeID==aID)
			{
				return ratings.get(j).getValue();
			}			
		}
		return 0;
	}
	
	private int getValueFromlistOfRequirementRateValues(int packageID, int requirementID)
	{
		for(int j=0; j<requirementRatings.size();j++)
		{
			int rID=requirementRatings.get(j).getRequirementId();
			int pID=requirementRatings.get(j).getPackageId();
			
			if(requirementID==rID && packageID==pID)
			{
				return requirementRatings.get(j).getValue();
			}			
		}
		return 0;
	}
	
	/**
	 * This class extend the texbox to add 
	 * the techniqueID and evaluationID as properties
	 */
	class RateValueTextbox extends TextBox
	{
		private int tecniqueID;
		private int evaluationID;
		private String oldValue= "-1";
		public RateValueTextbox(int tecniqueID, int evaluationID, int value) {
			this.tecniqueID=tecniqueID;
			this.evaluationID=evaluationID;
			
			this.setWidth("35px");
			this.setText(String.valueOf(value));;
			this.setOldValue(String.valueOf(value));;
		}
		public void setTecniqueID(int tecniqueID) {
			this.tecniqueID = tecniqueID;
		}
		public int getTecniqueID() {
			return tecniqueID;
		}
		public void setEvaluationID(int evaluationID) {
			this.evaluationID = evaluationID;
		}
		public int getEvaluationID() {
			return evaluationID;
		}
		public void setOldValue(String oldValue) {
			this.oldValue = oldValue;
		}
		public String getOldValue() {
			return oldValue;
		}
	}

	class RateValueLabel extends Label
	{
		private int tecniqueID;
		
		public RateValueLabel(int tecniqueID) {
			this.tecniqueID=tecniqueID;		
			this.setWidth("35px");
		}
		public void setTecniqueID(int tecniqueID) {
			this.tecniqueID = tecniqueID;
		}
		public int getTecniqueID() {
			return tecniqueID;
		}
	}
	
	
	
	
	class SummaryElementHyperLinkElement extends SquareHyperlink
	{
		private int requirementID;
		public SummaryElementHyperLinkElement(int requirementID, String text)
			{
				super(text);
				this.requirementID = requirementID;
			}
		public void setRequirementID(int requirementID)
		{
			this.requirementID = requirementID;
		}
		public int getRequirementId()
		{
			return requirementID;
		}
	}

	
	
	public void updateCommand(GwtRationale localTradeoffReason)
	{
		updateFinalChoiceRationaleinDB(localTradeoffReason);
	}

	private void updateFinalChoiceRationaleinDB(GwtRationale localTradeoffReason)
	{
		
		rationaleLabel.setText(localTradeoffReason.getRationale());
		finalProductSelectionService.setRationale(localTradeoffReason, new AsyncCallback<Void>()
				{
			@Override
			public void onSuccess(Void result)
			{
			}
			
			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert(messages.rationaleUpdateError());
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Updating Rationale");		
			}
		});
	}

	public void updateCommand(GwtTradeoffReason localTradeoffReason)
	{
		// TODO Auto-generated method stub
		
	}
	
}