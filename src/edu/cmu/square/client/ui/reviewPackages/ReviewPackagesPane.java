package edu.cmu.square.client.ui.reviewPackages;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.History;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtRating;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.ReviewPackagesService;
import edu.cmu.square.client.remoteService.step.interfaces.ReviewPackagesServiceAsync;
import edu.cmu.square.client.ui.SelectSecurityTechnique.SelectSecurityElicitationTechniquePilot;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;
import edu.cmu.square.server.dao.model.QualityAttribute;
import edu.cmu.square.server.dao.model.SoftwarePackage;

public class ReviewPackagesPane extends BasePane
{
	final private ReviewPackagesPaneMessages messages = (ReviewPackagesPaneMessages)GWT.create(ReviewPackagesPaneMessages.class);
	
	private ReviewPackagesServiceAsync service = GWT.create(ReviewPackagesService.class);
	
	private List<GwtSoftwarePackage> softwarePackages;
	private List<GwtQualityAttribute> attributes;
	private List<GwtRating> ratings;
	
	GwtProject currentProject;
	
 
	private FlexTable matrix = new FlexTable();
	private FlexTable matrixHeader = new FlexTable();
	
	SquareHyperlink editRatingsLink = new SquareHyperlink(messages.editRatingsLink());		
	SquareHyperlink finishRatingsLink = new SquareHyperlink(messages.finishRatingsLink());
	SquareHyperlink editPackagesLink = new SquareHyperlink(messages.editPackagesLink());
	SquareHyperlink finishPackagesLink = new SquareHyperlink(messages.finishPackagesLink());
	SquareHyperlink editAttributesLink = new SquareHyperlink(messages.editAttributesLink());		
	SquareHyperlink finishAttributesLink = new SquareHyperlink(messages.finishAttributesLink());
	
	ArrayList<SquareHyperlink> editQAsLinks = new ArrayList<SquareHyperlink>();
	ArrayList<SquareHyperlink> editSPsLinks = new ArrayList<SquareHyperlink>();
	
	Button cmdLoadTopTechnique = new Button(messages.buttonGotoSelect());
	
	Button addSoftwarePackage = new Button(messages.addSoftwarePackage());
	Button addQualityAttribute = new Button(messages.addQualityAttribute());
	
	boolean isReadOnly=false;

	protected EditQualityAttributeDialog editQualityAttributeDialog;
	protected EditSoftwarePackageDialog editSoftwarePackageDialog;

	protected CreateSoftwarePackageDialog addSoftwarePackageDialog;
	protected CreateQualityAttributeDialog addQualityAttributeDialog;

	public ReviewPackagesPane(final State stateInfo)
	{
		super(stateInfo);
		
		this.showLoadingStatusBar();
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "reviewPackages.rpc");
		
		currentProject = new GwtProject();
		currentProject.setId(this.getCurrentState().getProjectID());
		
		
	
		/**
		 * load the comparison matrix in the read-only mode
		 */
		isReadOnly = true;
		
		addSoftwarePackage = new Button(messages.createSoftwarePackageDialogBoxAdd(), new ClickHandler()
		{
			
			@Override
			public void onClick(ClickEvent event)
			{
				addSoftwarePackageDialog = new CreateSoftwarePackageDialog(softwarePackages, ReviewPackagesPane.this);
				addSoftwarePackageDialog.center();
				addSoftwarePackageDialog.setModal(true);
				addSoftwarePackageDialog.show();
			}
		});
		
		addQualityAttribute = new Button(messages.createQualityAttributeDialogBoxAdd(), new ClickHandler()
		{
			
			@Override
			public void onClick(ClickEvent event)
			{
				addQualityAttributeDialog = new CreateQualityAttributeDialog(attributes, ReviewPackagesPane.this);
				addQualityAttributeDialog.center();
				addQualityAttributeDialog.setModal(true);
				addQualityAttributeDialog.show();
			}
		});
		
		addSoftwarePackage.setVisible(false);
		addQualityAttribute.setVisible(false);
		
		loadAttributes();
		
	}
	
	public void PaneInitialization()
	{
		this.hideStatusBar();
		VerticalPanel layout = new VerticalPanel();
		editRatingsLink.addClickHandler(new ClickHandler(){

		
			public void onClick(ClickEvent event) {
				isReadOnly=false;
				//loadEvaluationsCriteria();
				
				addSoftwarePackage.setVisible(true);
				addQualityAttribute.setVisible(true);
				
				drawRateMatrix();
				getTotalsFromMatrix();
				drawQualityAttributes();
				drawSoftwarePackages();
				PaneInitialization();
				changeLink();
				
			}});
		finishRatingsLink.addClickHandler(new ClickHandler(){

		
			public void onClick(ClickEvent event) {
				isReadOnly=true;
				//loadEvaluationsCriteria();
				
				addSoftwarePackage.setVisible(false);
				addQualityAttribute.setVisible(false);
				
				
				drawRateMatrix();
				getTotalsFromMatrix();
				PaneInitialization();
				changeLink();
				
			}});
		
editPackagesLink.addClickHandler(new ClickHandler(){

			
			public void onClick(ClickEvent event) {
				isReadOnly=false;
				//loadEvaluationsCriteria();
				drawRateMatrix();
				getTotalsFromMatrix();
				PaneInitialization();
				changeLink();
				
			}});
		finishPackagesLink.addClickHandler(new ClickHandler(){

		
			public void onClick(ClickEvent event) {
				isReadOnly=true;
				//loadEvaluationsCriteria();
				drawRateMatrix();
				getTotalsFromMatrix();
				PaneInitialization();
				changeLink();
				
			}});
		
		editAttributesLink.addClickHandler(new ClickHandler(){

			
			public void onClick(ClickEvent event) {
				isReadOnly=false;
				//loadEvaluationsCriteria();
				drawRateMatrix();
				getTotalsFromMatrix();
				PaneInitialization();
				changeLink();
				
			}});
		finishAttributesLink.addClickHandler(new ClickHandler(){

		
			public void onClick(ClickEvent event) {
				isReadOnly=true;
				//loadEvaluationsCriteria();
				drawRateMatrix();
				getTotalsFromMatrix();
				PaneInitialization();
				changeLink();
				
			}});
		
		drawMatrixPage();

		cmdLoadTopTechnique.addClickHandler(new ClickHandler(){

			
			public void onClick(ClickEvent event) {
				List<GwtSoftwarePackage> list= getTopValues();
				
				currentState.setTopPackageList(list);
				
				History.newItem(SelectSecurityElicitationTechniquePilot.generateNavigationId(SelectSecurityElicitationTechniquePilot.PageId.selectTechnique));
			}});

		cmdLoadTopTechnique.addStyleName("square-button");
		
		this.getContent().clear();
		//layout.add(comparisonMatrixLabel);
		layout.add(matrixHeader);
		this.getContent().add(layout);
		
		//initWidget(layout);
	}

	private void addQAtoDB(GwtQualityAttribute qa)
	{
		this.service.addQualityAttribute(currentProject, qa, new AsyncCallback<GwtQualityAttribute>()
		{
			public void onFailure(Throwable caught) 
			{
				if (caught instanceof SquareException) 
				{
					SquareException se = (SquareException) caught;
					switch (se.getType()) {
					case authorization:
						Window.alert(messages.rateAuthorization());
						break;
				
					default:
						Window.alert(messages.error());
						break;
					}

				} else {
					Window.alert(messages.error());
				}
			}
			
			public void onSuccess(GwtQualityAttribute result) 
			{
				
			}
		});		
	}
	
	private void addSPtoDB(GwtSoftwarePackage qa)
	{
		this.service.addSoftwarePackage(currentProject, qa, new AsyncCallback<GwtSoftwarePackage>()
		{
			public void onFailure(Throwable caught) 
			{
				if (caught instanceof SquareException) 
				{
					SquareException se = (SquareException) caught;
					switch (se.getType()) {
					case authorization:
						Window.alert(messages.rateAuthorization());
						break;
				
					default:
						Window.alert(messages.error());
						break;
					}

				} else {
					Window.alert(messages.error());
				}
			}
			
			public void onSuccess(GwtSoftwarePackage result) 
			{
				
			}
		});		
	}

	protected void updateSPinDB(GwtSoftwarePackage gwtSoftwarePackage)
	{
		this.service.updateSoftwarePackage(gwtSoftwarePackage, currentProject, new AsyncCallback<Void>()
				{
					public void onFailure(Throwable caught) 
					{
						if (caught instanceof SquareException) 
						{
							SquareException se = (SquareException) caught;
							switch (se.getType()) {
							case authorization:
								Window.alert(messages.rateAuthorization());
								break;
						
							default:
								Window.alert(messages.error());
								break;
							}

						} else {
							Window.alert(messages.error());
						}
					}
					
					public void onSuccess(Void result) 
					{
						
					}
				});	
	}
	
	protected void updateQAinDB(GwtQualityAttribute gwtQualityAttribute)
	{
		this.service.updateQualityAttribute(gwtQualityAttribute, currentProject, new AsyncCallback<Void>()
				{
					public void onFailure(Throwable caught) 
					{
						if (caught instanceof SquareException) 
						{
							SquareException se = (SquareException) caught;
							switch (se.getType()) {
							case authorization:
								Window.alert(messages.rateAuthorization());
								break;
						
							default:
								Window.alert(messages.error());
								break;
							}

						} else {
							Window.alert(messages.error());
						}
					}
					
					public void onSuccess(Void result) 
					{
						
					}
				});	
	}

	private void changeLink()
	{
		if(isReadOnly)
		{
			this.matrixHeader.setWidget(2, 1, editRatingsLink);
			this.matrixHeader.setWidget(3, 1, matrix);
			
		}
		else
		{
			this.matrixHeader.setWidget(2, 1, finishRatingsLink);
			this.matrixHeader.setWidget(3, 1, matrix);
		}		
	}
	
	private void drawMatrixPage()
	{
		FlexCellFormatter formatter1 = this.matrixHeader.getFlexCellFormatter();
		this.matrixHeader.setWidth("100%");

		VerticalPanel XPanel = new VerticalPanel();
		VerticalPanel YPanel = new VerticalPanel();
		
		XPanel.add(new Label(messages.matrixLableX()));
		XPanel.add(addQualityAttribute);
		
		YPanel.add(new Label(messages.matrixLableY()));
		YPanel.add(addSoftwarePackage);
		
		this.matrixHeader.setWidget(1, 1, XPanel);
		this.matrixHeader.setWidget(3, 0,YPanel);
		this.matrixHeader.setWidget(4, 1, new Label(messages.rateLegend()));
//		this.matrixHeader.setWidget(5, 1, cmdLoadTopTechnique);
		
		formatter1.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter1.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);
		formatter1.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		formatter1.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_LEFT);
		formatter1.setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter1.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_LEFT);
		formatter1.setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		if(isReadOnly)
		{
			if(GwtModesType.ReadWrite==this.currentState.getMode())
			{
				this.matrixHeader.setWidget(2, 1, editRatingsLink);
			}
			
			this.matrixHeader.setWidget(3, 1, matrix);
			
			
			
		}
		else
		{
			this.matrixHeader.setWidget(2, 1, finishRatingsLink);
			this.matrixHeader.setWidget(3, 1, matrix);
		}
		
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
				System.out.println("We got software packages: "+result.size());
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

	private void loadRatings()
	{
		service.getRateValues(currentProject.getId(), new AsyncCallback<List<GwtRating > >()
				{
					
					@Override
					public void onSuccess(List<GwtRating >  result)
					{
						System.out.println("We got ratings: "+result.size());
						ratings = result;
						drawRateMatrix();
						getTotalsFromMatrix();
						PaneInitialization();
					}
					
					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert(messages.ratingsRetrievalError());
						ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Retrieving Ratings");
					}
				});
		
	}

	/**
	 * Contains RPC call to Set the rates
	 * @param packageID
	 * @param attributeID
	 * @param value
	 */
	private void setRateValue(final int packageID, final int attributeID, final int value)
	{
		this.service.setRateValue(currentProject.getId(), packageID, attributeID, value, new AsyncCallback<Void>(){

			
			public void onFailure(Throwable caught) {
				if (caught instanceof SquareException) {
					SquareException se = (SquareException) caught;
					switch (se.getType()) {
					case authorization:
						Window.alert(messages.rateAuthorization());
						break;
				
					default:
						Window.alert(messages.error());
						break;
					}

				} else {
					Window.alert(messages.error());
				}
				
			}

			
			public void onSuccess(Void result) {
				setValueFromlistOfRateValues(packageID, attributeID, value);
				
			}});
	
	}
	
	public void drawRateMatrix()
	{
		matrix.clear();
		//matrix.setBorderWidth(1);
		matrix.setWidth("100%");
		matrix.setStyleName("square-Matrix");
		matrix.setCellSpacing(0);
		drawQualityAttributes();
		drawSoftwarePackages();
		drawRateMatrixValues();

	}
	
	public void drawQualityAttributes()
	{
		FlexCellFormatter formatter = this.matrix.getFlexCellFormatter();
		matrix.setWidget(0, 0, new Label(" "));
		formatter.setStyleName(0, 0, "square-Matrix");

		// Set the header rows  with techniques
		for(int i=1; i<=attributes.size();i++)
		{
			if(this.isReadOnly)
			{
				Label techniqueLabel = new Label(attributes.get(i-1).getName());

				final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
				// simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
				simplePopup.setWidth("150px");
				simplePopup.setWidget(new HTML(attributes.get(i-1).getDescription()));

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
			else
			{
				final SquareHyperlink qualityAttributeLink = new SquareHyperlink(attributes.get(i-1).getName());
				final int index = i;
				qualityAttributeLink.addClickHandler(new ClickHandler(){

					
					public void onClick(ClickEvent event) {
						editQualityAttributeDialog = new EditQualityAttributeDialog(attributes.get(index-1), attributes, ReviewPackagesPane.this);
						editQualityAttributeDialog.center();
						editQualityAttributeDialog.setModal(true);
						editQualityAttributeDialog.show();
						
					}});
				
				matrix.setWidget(0, i, qualityAttributeLink);
				formatter.setHorizontalAlignment(0, i, HasHorizontalAlignment.ALIGN_CENTER);
				formatter.setStyleName(0, i, "square-Matrix");
			}				
		}

		matrix.setWidget(0, attributes.size()+1, new Label("Total"));
		formatter.setHorizontalAlignment(0, attributes.size()+1, HasHorizontalAlignment.ALIGN_RIGHT);
		formatter.setStyleName(0, attributes.size()+1,"square-Matrix");

	}

	public void drawSoftwarePackages()
	{

		FlexCellFormatter formatter = this.matrix.getFlexCellFormatter();
		// Set the left columns with the evaluation criteria 
		for(int j=0, widgetCount=0; j<softwarePackages.size();j++, ++widgetCount)
		{
			if(this.isReadOnly)
			{
				Label softwarePackageLabel = new Label(softwarePackages.get(j).getName());

				final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
				simplePopup.setWidth("150px");
				simplePopup.setWidget(new HTML(softwarePackages.get(j).getDescription()));

				softwarePackageLabel.addMouseOverHandler(new MouseOverHandler(){

					public void onMouseOver(MouseOverEvent event) {
						Widget source = (Widget) event.getSource();
						int left = source.getAbsoluteLeft() + 40;
						int top = source.getAbsoluteTop() + 20;
						simplePopup.setPopupPosition(left, top);
						simplePopup.show();

					}});
				softwarePackageLabel.addMouseOutHandler(new MouseOutHandler(){


					public void onMouseOut(MouseOutEvent event) {
						simplePopup.hide();

					}});

				if(j == 0)
				{
					matrix.setWidget(j+1,0 , softwarePackageLabel);
					formatter.setHorizontalAlignment(j+1,0 , HasHorizontalAlignment.ALIGN_RIGHT);
					formatter.setStyleName(j+1,0 ,  "square-Matrix");
					matrix.setWidget(j+2,0 , new Label(""));
					++widgetCount;

				}
				else
				{
					matrix.setWidget(widgetCount+1,0 , softwarePackageLabel);
					formatter.setHorizontalAlignment(widgetCount+1,0 , HasHorizontalAlignment.ALIGN_RIGHT);
					formatter.setStyleName(widgetCount+1,0 ,  "square-Matrix");
				}
			}
			else
			{
				final SquareHyperlink softwarePackageLink = new SquareHyperlink(softwarePackages.get(j).getName());
				final int index = j;
				softwarePackageLink.addClickHandler(new ClickHandler(){

				
					public void onClick(ClickEvent event) {
						editSoftwarePackageDialog = new EditSoftwarePackageDialog(softwarePackages.get(index), softwarePackages, ReviewPackagesPane.this);
						editSoftwarePackageDialog.center();
						editSoftwarePackageDialog.setModal(true);
						editSoftwarePackageDialog.show();
						
					}});
				
				if(j == 0)
				{
					matrix.setWidget(j+1,0, new Label(softwarePackages.get(j).getName()));
					formatter.setHorizontalAlignment(j+1,0 , HasHorizontalAlignment.ALIGN_RIGHT);
					formatter.setStyleName(j+1,0 ,  "square-Matrix");
					matrix.setWidget(j+2,0 , new Label(""));
					++widgetCount;
				}
				else
				{
					matrix.setWidget(widgetCount+1,0 , softwarePackageLink);
					formatter.setHorizontalAlignment(widgetCount+1,0 , HasHorizontalAlignment.ALIGN_RIGHT);
					formatter.setStyleName(widgetCount+1,0 ,  "square-Matrix");
				}

			}

		}

	}
	
	public void drawRateMatrixValues()
	{
		FlexCellFormatter formatter = this.matrix.getFlexCellFormatter();
		for(int i=0; i<attributes.size();i++)
		{
			
			for(int j=0, widgetCount = 0; j<softwarePackages.size();j++, ++widgetCount)
			{
				int tID=attributes.get(i).getId();
				int eID=softwarePackages.get(j).getId();
				
				int value = getValueFromlistOfRateValues(eID, tID);
				
				if(isReadOnly)
				{
					Label valueLabel = new Label(String.valueOf(value));
					valueLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
					
					
					if(j==0)
					{
						matrix.setWidget(1, i+1, valueLabel);
						formatter.setHorizontalAlignment(1, i+1, HasHorizontalAlignment.ALIGN_CENTER);
						formatter.setStyleName(1, i+1,"square-Matrix");
						++widgetCount;
					}
					else
					{
						matrix.setWidget(widgetCount+1, i+1, valueLabel);
						formatter.setHorizontalAlignment(widgetCount+1, i+1, HasHorizontalAlignment.ALIGN_CENTER);
						formatter.setStyleName(widgetCount+1, i+1,"square-Matrix");
					}
					
					
				}
				else
				{
					//Allow only the digits 1,2 and 3 to be inputed in the rateValueTextBox
					final RateValueTextbox rateValueTextbox =new RateValueTextbox(tID,eID, value);
					rateValueTextbox.setTextAlignment(TextBox.ALIGN_CENTER);
					rateValueTextbox.addKeyPressHandler(new KeyPressHandler(){	
						public void onKeyPress(KeyPressEvent event) {
							char keyCode= event.getCharCode();
							 if (
									 ( (keyCode< '0' || keyCode>'3')) && (keyCode != (char) KeyCodes.KEY_TAB)
							            && (keyCode != (char) KeyCodes.KEY_BACKSPACE)
							            && (keyCode != (char) KeyCodes.KEY_DELETE) && (keyCode != (char) KeyCodes.KEY_ENTER) 
							            && (keyCode != (char) KeyCodes.KEY_HOME) && (keyCode != (char) KeyCodes.KEY_END)
							            && (keyCode != (char) KeyCodes.KEY_LEFT) && (keyCode != (char) KeyCodes.KEY_UP)
							            && (keyCode != (char) KeyCodes.KEY_RIGHT) && (keyCode != (char) KeyCodes.KEY_DOWN)) {
								 	
								 // rateValueTextbox.cancelKey() suppresses the current keyboard event.
								 	rateValueTextbox.cancelKey();
							 }
						}
						});
						rateValueTextbox.addFocusHandler(new FocusHandler(){

							
							public void onFocus(FocusEvent event)
							{
								rateValueTextbox.setSelectionRange(0, rateValueTextbox.getText().length());
								
							}});
				

						
						rateValueTextbox.addChangeHandler(new ChangeHandler(){
						public void onChange(ChangeEvent event) {
						//	Window.alert("a changed happened");
							
							if(rateValueTextbox.getText().trim().length()==0)
							 {
								 rateValueTextbox.setText("0");
							 }
							char keyCode;
							
							// Validation of the textbox from Copy and Paste
							if(rateValueTextbox.getText().trim().length()==1)
							{
								keyCode=rateValueTextbox.getText().trim().charAt(0);
							   if(keyCode>= '0' && keyCode<='3')
							   {
								   getTotalsFromMatrix();
								   setRateValue(rateValueTextbox.getTecniqueID(),rateValueTextbox.getEvaluationID(),Integer.parseInt(rateValueTextbox.getText()));
									rateValueTextbox.setOldValue(rateValueTextbox.getText());
							   }
							   else
							   {
								   rateValueTextbox.setText(rateValueTextbox.getOldValue());
							   }
							}
							else
							{
								rateValueTextbox.setText(rateValueTextbox.getOldValue());
							}

							
						}});

						if(j==0)
						{
							matrix.setWidget(1, i+1, rateValueTextbox);
							formatter.setHorizontalAlignment(1, i+1, HasHorizontalAlignment.ALIGN_CENTER);
							formatter.setStyleName(1, i+1,"square-Matrix");
							++widgetCount;
						}
						else
						{
							matrix.setWidget(widgetCount+1, i+1, rateValueTextbox);
							formatter.setHorizontalAlignment(widgetCount+1, i+1, HasHorizontalAlignment.ALIGN_CENTER);
							formatter.setStyleName(widgetCount+1, i+1,"square-Matrix");
						}
				}
				RateValueLabel totalLabel= new RateValueLabel(attributes.get(i).getId());
				totalLabel.setText("0");

				if(widgetCount>1)
				{
				matrix.setWidget(widgetCount+1, attributes.size()+1, totalLabel);
				formatter.setHorizontalAlignment(widgetCount+1, attributes.size()+1,  HasHorizontalAlignment.ALIGN_CENTER);
				formatter.setStyleName(widgetCount+1, attributes.size()+1,"square-Matrix");
				}
			}
			
						
		}
	}
	/**
	 * Get the totals traversing the current Matrix FlexTable
	 */
	public void getTotalsFromMatrix()
	{
		System.out.println("rows: "+matrix.getRowCount()+" columns: "+matrix.getCellCount(0));
		for(int i=3; i<=softwarePackages.size()+1;i++)
		{	
			int sum=0;
			
			for(int j=1; j<=attributes.size();j++)
			{
					Widget w =  matrix.getWidget(i, j);
					Widget weight =  matrix.getWidget(1, j);
					
					if ( w instanceof RateValueTextbox) 
					{
						RateValueTextbox new_name = (RateValueTextbox) w;
						RateValueTextbox weightVal = (RateValueTextbox) weight;
						//Window.alert(new_name.getText());
						sum += Integer.parseInt(new_name.getText()) * Integer.parseInt(weightVal.getText());
							
					}
					else if( w instanceof Label)
					{
						Label new_name = (Label) w;						
						Label weightVal = (Label) weight;
						//Window.alert(new_name.getText());
						sum += Integer.parseInt(new_name.getText()) * Integer.parseInt(weightVal.getText());
					}
					
			}
			
//				Widget totalLabel =  matrix.getWidget(softwarePackages.size()+1, i);
			Widget totalLabel =  matrix.getWidget(i, attributes.size()+1);

				
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
	private List<GwtSoftwarePackage> getTopValues()
	{
		List<GwtSoftwarePackage> topPackages = new ArrayList<GwtSoftwarePackage>();
	
		int max=-1;
		for(int i=1; i<=softwarePackages.size();i++)
		{
				Widget widget =  matrix.getWidget(attributes.size()+1, i);	
				RateValueLabel totalLabel = (RateValueLabel) widget;
			
				if(max<Integer.parseInt(totalLabel.getText()) )
					{
						max=Integer.parseInt(totalLabel.getText());
					}		
		}
		for(int i=1; i<=softwarePackages.size();i++)
		{
				Widget widget =  matrix.getWidget(attributes.size()+1, i);	
				RateValueLabel totalLabel = (RateValueLabel) widget;
			
				if(max==Integer.parseInt(totalLabel.getText()) )
					{
						topPackages.add(getPackageFromListOfPackages(totalLabel.getTecniqueID()));
					}		
		}
		
		return topPackages;
		
		
	}
	/**
	 * This method search the techniques in the listloaded by RCP initially
	 * @param tecniqueID
	 * @return GwtTechnique for that ID
	 */
	private GwtSoftwarePackage getPackageFromListOfPackages(int tecniqueID)
	{
		for(int i=0; i<softwarePackages.size();i++)
		{
				if(softwarePackages.get(i).getId()==tecniqueID)
				{
					return 	softwarePackages.get(i);
				}
		}
		return null;
	}
	/**
	 * This method search the rate values in the loaded by RCP initially
	 * @param techniqueID
	 * @param evaluationID
	 * @return rateValue
	 */
	private int getValueFromlistOfRateValues(int techniqueID, int evaluationID)
	{
		
		for(int j=0; j<ratings.size();j++)
		{
		
			int eID=ratings.get(j).getAttributeId();
			int tID =ratings.get(j).getPackageId();
			
			if(techniqueID==tID && evaluationID==eID)
			{
				return ratings.get(j).getValue();
			}
			
		}
		return 0;

	}
	
	private void setValueFromlistOfRateValues(int techniqueID, int evaluationID, int value)
	{
		
		for(int j=0; j<ratings.size();j++)
		{
		
			int eID=ratings.get(j).getAttributeId();
			int tID =ratings.get(j).getPackageId();
			
			if(techniqueID==tID && evaluationID==eID)
			{
				ratings.get(j).setValue(value);
			}
			
		}
	}
	/**
	 * 
	 * This class extend the texbox to add 
	 * the techniqueID and evaluationID as properties
	 *
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
			
			// TODO Auto-generated constructor stub
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

	/**
	 * 
	 * the techniqueID as properties
	 *
	 */
	class RateValueLabel extends Label
	{
		private int tecniqueID;
		public RateValueLabel(int tecniqueID) {
		
			this.tecniqueID=tecniqueID;
			
			this.setWidth("35px");
			
			// TODO Auto-generated constructor stub
		}
		public void setTecniqueID(int tecniqueID) {
			this.tecniqueID = tecniqueID;
		}
		public int getTecniqueID() {
			return tecniqueID;
		}

	}

	public void createCommand(GwtQualityAttribute qualityAttribute)
	{
		addQAtoDB(qualityAttribute); 		
	}
	
	public void createCommand(GwtSoftwarePackage gwtSoftwarePackage)
	{
		addSPtoDB(gwtSoftwarePackage);		
	}

	public void updateCommand(GwtQualityAttribute localQualityAttribute)
	{
		updateQAinDB(localQualityAttribute);
	}

	public void updateCommand(GwtSoftwarePackage localSoftwarePackage)
	{
		updateSPinDB(localSoftwarePackage);
	}


	
}