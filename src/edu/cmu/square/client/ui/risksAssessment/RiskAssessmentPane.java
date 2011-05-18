package edu.cmu.square.client.ui.risksAssessment;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtArtifact;
import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtBusinessGoal;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRisk;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.CollectArtifactsService;
import edu.cmu.square.client.remoteService.step.interfaces.CollectArtifactsServiceAsync;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsService;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsServiceAsync;
import edu.cmu.square.client.remoteService.step.interfaces.RiskAssessmentService;
import edu.cmu.square.client.remoteService.step.interfaces.RiskAssessmentServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;

public class RiskAssessmentPane extends BasePane implements Command
{
	final RiskAssessmentPaneMessages messages = (RiskAssessmentPaneMessages) GWT.create(RiskAssessmentPaneMessages.class);
	private RiskAssessmentServiceAsync service = GWT.create(RiskAssessmentService.class);
	private CollectArtifactsServiceAsync service1 = GWT.create(CollectArtifactsService.class);

	private GwtProject currentProject;
	private FlexTable matrix = new FlexTable();

	private VerticalPanel assetsPane = new VerticalPanel();
	private VerticalPanel artifactPane = new VerticalPanel();

	private Label riskTitleLabel = new Label(messages.labelRiskTitle());
	private Label threadSourceLabel = new Label(messages.labelThreadSource());
	private Label threadActionLabel = new Label(messages.labelThreadAction());
	private Label vulnerabilityLabel = new Label(messages.labelVulnerability());
	private Label controlMeasureLabel = new Label(messages.labelControlMeasure());
	private Label likelihoodLabel = new Label(messages.labelLikelihood());
	private Label impactLabel = new Label(messages.labelImpact());
	private Label exposureLabel = new Label(messages.labelExposure());
	private Label exposureValue = new Label("");
	private Label assetsLabel = new Label(messages.labelAssets());
	private Label artifactsLabel = new Label(messages.labelArtifacts());


	private TextBox riskTitleTextBox = new TextBox();
	private TextArea threadSourceTextArea = new TextArea();
	private TextArea threadActionTextArea = new TextArea();
	private TextArea vulnerabilityTextArea = new TextArea();
	private TextArea controlMeasureTextArea = new TextArea();

	private DisclosurePanel disPanel;

	private ListBox likelihoodListBox = new ListBox();
	private ListBox impactBox = new ListBox();

	private GwtRisk currentRisk;
	private Button save;
	private Button cancel;

	private SquareHyperlink editRisk;
	private SquareHyperlink deleteRisk;
	private SquareHyperlink gotToSummary;

	private List<GwtRisk> lisOfRisks = new ArrayList<GwtRisk>();
	private List<GwtAsset> listOfProjectAssets = new ArrayList<GwtAsset>();
	private List<GwtAsset> listOfAssetsMapToRisk = new ArrayList<GwtAsset>();
	private List<GwtArtifact> listOfProjectArtifacts = new ArrayList<GwtArtifact>();
	private List<GwtArtifact> listOfArtifactsMapToRisk = new ArrayList<GwtArtifact>();

	private int currentIndex = -1;
	private int currentRiskID = -1;

	private enum CommandTypes
	{
		insert, update, read
	};
	private CommandTypes currentCommand;

	private AssetDialogBox assetDialog;
	private ArtifactDialogBox artifactDialog;

	private GwtBusinessGoal businessGoalInfo = new GwtBusinessGoal();

	public RiskAssessmentPane(final State stateInfo)
		{
			super(stateInfo);
			currentProject = new GwtProject();
			currentProject.setId(this.getCurrentState().getProjectID());
			currentRiskID = currentState.getCurrentRisk();
			currentCommand = getCurrentCommand();
			
			loadAssestFromProject();

		}

	// ------------------------------------------------------
	// RPC Asynchronous calls section
	// ------------------------------------------------------

	public void loadAssestFromProject()
	{
		this.showLoadingStatusBar();
		IdentifyGoalsAssetsServiceAsync service = GWT.create(IdentifyGoalsAssetsService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");
		service.loadBusinessGoalInfo(getCurrentState().getProjectID(), new AsyncCallback<GwtBusinessGoal>()
			{
				public void onFailure(Throwable caught)
				{
					Window.alert(caught.getMessage());
				}
				public void onSuccess(GwtBusinessGoal result)
				{
					businessGoalInfo = result;
					listOfProjectAssets = businessGoalInfo.getAssets();
					loadArtifactsFromProject();

				}
			});
	}
	public void loadArtifactsFromProject()
	{

		int projectId = this.getCurrentState().getProjectID();
		ServiceDefTarget endpoint = (ServiceDefTarget) service1;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "collectArtifacts.rpc");
		service1.getAllArtifacts(projectId, new AsyncCallback<List<GwtArtifact>>()
			{

				
				public void onFailure(Throwable caught)
				{
					Window.alert(caught.getMessage());
				}

				
				public void onSuccess(List<GwtArtifact> result)
				{

					listOfProjectArtifacts = result;
					loadRiskFromProject();

				}

			});

	}
	public void loadRiskFromProject()
	{

		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "riskAssessment.rpc");
		this.service.getRisksFromProject(this.currentProject.getId(), new AsyncCallback<List<GwtRisk>>()
			{
				public void onFailure(Throwable caught)
				{
					Window.alert(messages.errorGettingRisks());
				}
				public void onSuccess(List<GwtRisk> result)
				{
					lisOfRisks = result;
					currentRisk = getRiskFromListByID(currentRiskID);
					if (currentRisk != null)
					{
						listOfAssetsMapToRisk = currentRisk.getAssets();
						listOfArtifactsMapToRisk = currentRisk.getArtifact();
					}
					
					PaneInitialization();

				}
			});
	}
	public void updateRisk(int riskID, GwtRisk gwtRisk)
	{
		gwtRisk.setId(riskID);
		this.service.updateRisk(gwtRisk, new AsyncCallback<Void>()
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
							Window.alert(messages.errorUpdatingRisks());
							break;
					}
				}

				
				public void onSuccess(Void result)
				{
					currentCommand = CommandTypes.read;
					loadRiskFromProject();
				}
			});

	}
	public void removeRisk(int riskID)
	{
		boolean response = Window.confirm(messages.confirmDelete() + "?");
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

						History.newItem(RiskAssessmentPilot.generateNavigationId(RiskAssessmentPilot.PageId.riskAssessmentsummary));
						// loadRiskFromProject();
					}
				});
		}
	}

	// ------------------------------------------------------
	// Risk Manipulation section
	// ------------------------------------------------------

	public void PaneInitialization()
	{
		this.hideStatusBar();
		this.save = new Button(messages.save());
		
		this.cancel = new Button(messages.cancel());
		this.save.setWidth("80px");
		this.cancel.setWidth("80px");

		this.getContent().clear();
		getContent().add(loadRiskNavigator());

		this.disPanel = new DisclosurePanel();
		this.disPanel.setAnimationEnabled(true);
		Label errorMessage = new Label(messages.labelFieldsRequired());
		errorMessage.setStyleName("square-RequiredMessage");

		this.disPanel.add(errorMessage);
		this.getContent().add(disPanel);
		this.matrix = new FlexTable();
		this.likelihoodListBox = new ListBox();
		this.impactBox = new ListBox();

		
		this.getContent().add(this.matrix);
		loadRiskForm();

		this.save.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					GwtRisk gwtRisk = getFormRiskValues();
					saveRisk(currentRiskID, gwtRisk);
				}
			});

		cancel.addClickHandler(new ClickHandler()
			{

				public void onClick(ClickEvent event)
				{
					if (CommandTypes.insert == currentCommand)
					{
						History.newItem(RiskAssessmentPilot.generateNavigationId(RiskAssessmentPilot.PageId.riskAssessmentsummary));
					}
					else
					{
						currentCommand = CommandTypes.read;
						PaneInitialization();
					}
				}
			});
	}
	public void loadRiskForm()
	{

		this.matrix.getCellFormatter().setWidth(1, 0, "100px");
		this.matrix.getCellFormatter().setWidth(1, 1, "80%");

		this.matrix.setWidth("85%");
		this.matrix.setCellPadding(10);
		this.matrix.setStyleName("square-flex");

		initializeRiskFormWidgets();

		if (!CommandTypes.insert.equals(currentCommand))
		{
			setFormRiskValues(currentRisk);
		}

		this.matrix.setWidget(1, 0, threadSourceLabel);
		this.matrix.setWidget(1, 1, getField(threadSourceTextArea));
		this.matrix.setWidget(2, 0, threadActionLabel);
		this.matrix.setWidget(2, 1, getField(threadActionTextArea));
		this.matrix.setWidget(3, 0, vulnerabilityLabel);
		this.matrix.setWidget(3, 1, getField(vulnerabilityTextArea));
		this.matrix.setWidget(4, 0, controlMeasureLabel);
		this.matrix.setWidget(4, 1, getField(controlMeasureTextArea));

		this.matrix.getCellFormatter().setAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		this.matrix.getCellFormatter().setAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		this.matrix.getCellFormatter().setAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		this.matrix.getCellFormatter().setAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);

		this.matrix.getCellFormatter().setAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		this.matrix.getCellFormatter().setAlignment(2, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		this.matrix.getCellFormatter().setAlignment(3, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		this.matrix.getCellFormatter().setAlignment(4, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);

		if (currentCommand == CommandTypes.read)
		{
			// Configure the table when the command is to view.
			loadReadOnlyRiskForm();
		}
		else
		{
			// Configure the table when the command is to Update or Create.
			loadEditableRiskForm();
		}

		
			this.matrix.setWidget(7, 1, createHTMLAssetList());
			this.matrix.setWidget(8, 1, createHTMLArtifactsList());
		

		this.matrix.getCellFormatter().setAlignment(7, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		this.matrix.getCellFormatter().setAlignment(7, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		this.matrix.getCellFormatter().setAlignment(8, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		this.matrix.getCellFormatter().setAlignment(8, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);

	}

	public void initializeRiskFormWidgets()
	{
		this.editRisk = new SquareHyperlink(messages.linkEditRisk());
		this.deleteRisk = new SquareHyperlink(messages.linkDeleteRisk());
		this.gotToSummary = new SquareHyperlink(messages.linkBackRiskSummary());

		this.riskTitleLabel.setStyleName("Risk-Input-Labels");
		this.threadSourceLabel.setStyleName("Risk-Input-Labels");
		this.threadActionLabel.setStyleName("Risk-Input-Labels");
		this.vulnerabilityLabel.setStyleName("Risk-Input-Labels");
		this.controlMeasureLabel.setStyleName("Risk-Input-Labels");
		this.likelihoodLabel.setStyleName("Risk-Input-Labels");
		this.impactLabel.setStyleName("Risk-Input-Labels");
		this.exposureLabel.setStyleName("Risk-Input-Labels");
		this.assetsLabel.setStyleName("Risk-Input-Labels");
		this.artifactsLabel.setStyleName("Risk-Input-Labels");

		this.riskTitleTextBox.setStyleName("square-long-textBox");
		this.threadSourceTextArea.setStyleName("square-long-textBox");
		this.threadActionTextArea.setStyleName("square-long-textBox");
		this.vulnerabilityTextArea.setStyleName("square-long-textBox");
		this.controlMeasureTextArea.setStyleName("square-long-textBox");

		this.likelihoodListBox.addItem("", "0");
		this.likelihoodListBox.addItem("High", "1");
		this.likelihoodListBox.addItem("Medium", "2");
		this.likelihoodListBox.addItem("Low", "3");
		this.likelihoodListBox.setStyleName("Risk-Input-listBox");

		this.impactBox.addItem("", "0");
		this.impactBox.addItem("High", "1");
		this.impactBox.addItem("Medium", "2");
		this.impactBox.addItem("Low", "3");
		this.impactBox.setStyleName("Risk-Input-listBox");

	}
	public void loadReadOnlyRiskForm()
	{
		this.matrix.setWidget(0, 0, getField(riskTitleTextBox));
		this.matrix.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		this.matrix.getCellFormatter().setStyleName(0, 0, "step-Title-heading");
		((FlexCellFormatter) this.matrix.getCellFormatter()).setColSpan(0, 0, 2);

		String exposure = RiskExposure.getExposure(currentRisk.getImpact(), currentRisk.getLikelihood());

		this.exposureValue.setText(exposure);
		this.exposureLabel.setStyleName("Risk-Exposure-Labels");
		this.likelihoodLabel.setStyleName("Risk-Exposure-Labels");
		this.impactLabel.setStyleName("Risk-Exposure-Labels");

		this.matrix.setWidget(5, 0, getExposureTable());
		this.matrix.setWidget(7, 0, assetsLabel);
		this.matrix.setWidget(8, 0, artifactsLabel);

		this.matrix.getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_CENTER);
		((FlexCellFormatter) matrix.getCellFormatter()).setColSpan(5, 0, 2);
		this.matrix.getCellFormatter().setStyleName(5, 0, "inner-table");
		this.matrix.getCellFormatter().setAlignment(7, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		this.matrix.getCellFormatter().setAlignment(7, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);

		
		
		
		
		if (currentState.getMode() == GwtModesType.ReadWrite)
		{
			FlexTable bottonControlPanel = new FlexTable();
			bottonControlPanel.setWidth("85%");
			bottonControlPanel.setWidget(0, 0, editRisk);
			bottonControlPanel.setWidget(1, 0, deleteRisk);
			bottonControlPanel.setWidget(2, 0, new Label(" "));
			bottonControlPanel.setWidget(3, 0, gotToSummary);
			bottonControlPanel.getCellFormatter().setHorizontalAlignment(0,0,HasHorizontalAlignment.ALIGN_LEFT);
			bottonControlPanel.getCellFormatter().setHorizontalAlignment(1,0,HasHorizontalAlignment.ALIGN_LEFT);
			bottonControlPanel.getCellFormatter().setHorizontalAlignment(2,0,HasHorizontalAlignment.ALIGN_LEFT);
			bottonControlPanel.getCellFormatter().setHorizontalAlignment(3,0,HasHorizontalAlignment.ALIGN_LEFT);
			this.getContent().add(bottonControlPanel);
			
		}
		

		this.editRisk.addClickHandler(new ClickHandler()
			{

				public void onClick(ClickEvent event)
				{
					currentCommand = CommandTypes.update;
					PaneInitialization();

				}
			});

		this.deleteRisk.addClickHandler(new ClickHandler()
			{

				public void onClick(ClickEvent event)
				{
					removeRisk(currentRiskID);

				}
			});

		this.gotToSummary.addClickHandler(new ClickHandler()
			{

				public void onClick(ClickEvent event)
				{
					History.newItem(RiskAssessmentPilot.generateNavigationId(RiskAssessmentPilot.PageId.riskAssessmentsummary));

				}
			});
	}
	public Widget getExposureTable()
	{
		FlexTable flex = new FlexTable();

		flex.setCellSpacing(8);
		flex.setWidget(0, 0, this.likelihoodLabel);
		flex.setWidget(0, 2, this.impactLabel);
		flex.setWidget(0, 4, this.exposureLabel);
		flex.setWidget(1, 0, getField(this.likelihoodListBox));
		flex.setWidget(1, 1, new Label("X"));
		flex.setWidget(1, 2, getField(this.impactBox));
		flex.setWidget(1, 3, new Label("="));
		flex.setWidget(1, 4, this.exposureValue);
		flex.getFlexCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flex.setStyleName("inner-table");
		return flex;
	}
	public void loadEditableRiskForm()
	{
		matrix.setWidget(0, 0, riskTitleLabel);
		matrix.setWidget(0, 1, getField(riskTitleTextBox));
		matrix.setWidget(5, 0, likelihoodLabel);
		matrix.setWidget(5, 1, likelihoodListBox);
		matrix.setWidget(6, 0, impactLabel);
		matrix.setWidget(6, 1, impactBox);

		this.matrix.setWidget(7, 0, getEditAssetsHyperLink(this));
		this.matrix.setWidget(8, 0, getEditArtifactsHyperLink(this));

		exposureLabel.setStyleName("Risk-Input-Labels");
		assetsLabel.setStyleName("Risk-Input-Labels");
		artifactsLabel.setStyleName("Risk-Input-Labels");

		matrix.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		matrix.getCellFormatter().setAlignment(5, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		matrix.getCellFormatter().setAlignment(6, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		matrix.getCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		matrix.getCellFormatter().setAlignment(5, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		matrix.getCellFormatter().setAlignment(6, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);

		if (CommandTypes.insert.equals(currentCommand))
		{
			this.save.setText(messages.create());
		}
		else if (CommandTypes.update.equals(currentCommand))
		{
			this.save.setText(messages.save());
		}

		FlexTable flexTable = new FlexTable();
		flexTable.setWidget(0, 0, save);
		flexTable.setWidget(0, 1, cancel);
		flexTable.setCellSpacing(10);

		this.getContent().add(flexTable);
	}
	public Widget getField(Widget w)
	{
		boolean readOnly = false;
		if (CommandTypes.read.equals(currentCommand))
		{
			readOnly = true;
		}

		if (w instanceof TextBox && readOnly)
		{
			return new Label(((TextBox) w).getText());
		}
		else if (w instanceof TextArea && readOnly)
		{
			return new Label(((TextArea) w).getText());
		}
		else if (w instanceof ListBox && readOnly)
		{
			ListBox list = (ListBox) w;
			String value = list.getItemText(list.getSelectedIndex());
			return new Label(value);
		}
		return w;
	}

	// This sets the mode the of form based on the commandType.
	public CommandTypes getCurrentCommand()
	{
		int command = currentState.getRiskCommand();

		if (command == 0)
		{
			return CommandTypes.insert;
		}
		else if (command == 1)
		{
			return CommandTypes.read;
		}
		else if (command == 2)
		{
			return CommandTypes.update;
		}

		return CommandTypes.read;
	}
	public GwtRisk getFormRiskValues()
	{
		GwtRisk gwtRisk = new GwtRisk();
		gwtRisk.setRiskTitle(this.riskTitleTextBox.getText());
		gwtRisk.setThreatSource(this.threadSourceTextArea.getText());
		gwtRisk.setThreatAction(this.threadActionTextArea.getText());
		gwtRisk.setVulnerability(this.vulnerabilityTextArea.getText());
		gwtRisk.setCurrentMeasures(this.controlMeasureTextArea.getText());
		gwtRisk.setLikelihood(this.likelihoodListBox.getSelectedIndex());
		gwtRisk.setImpact(this.impactBox.getSelectedIndex());
		gwtRisk.setPlannedMeasures("");
		gwtRisk.setAssets(this.listOfAssetsMapToRisk);
		gwtRisk.setArtifact(this.listOfArtifactsMapToRisk);
		return gwtRisk;
	}
	public void setFormRiskValues(GwtRisk gwtRisk)
	{
		this.riskTitleTextBox.setText(gwtRisk.getRiskTitle());
		this.threadSourceTextArea.setText(gwtRisk.getThreatSource());
		this.threadActionTextArea.setText(gwtRisk.getThreatAction());
		this.vulnerabilityTextArea.setText(gwtRisk.getVulnerability());
		this.controlMeasureTextArea.setText(gwtRisk.getCurrentMeasures());
		this.likelihoodListBox.setSelectedIndex(gwtRisk.getLikelihood());
		this.impactBox.setSelectedIndex(gwtRisk.getImpact());
		this.listOfAssetsMapToRisk = gwtRisk.getAssets();
		this.listOfArtifactsMapToRisk = gwtRisk.getArtifact();

	}
	public GwtRisk getRiskFromListByID(int riskID)
	{

		int count = 0;
		for (GwtRisk r : lisOfRisks)
		{
			if (r.getId() == riskID)
			{
				currentIndex = count;
				return r;
			}
			count++;
		}
		return currentRisk;

	}
	public GwtRisk getRiskFromListByIndex(int index)
	{
		int count = 0;
		for (GwtRisk r : lisOfRisks)
		{
			if (count == index)
			{

				return r;
			}
			count++;
		}
		return currentRisk;

	}

	public Widget loadRiskNavigator()
	{
	
		SquareHyperlink previousRisk = new SquareHyperlink(messages.linkPreviousRisk());
		SquareHyperlink nextRisk = new SquareHyperlink(messages.linkNextRisk());
		SquareHyperlink gotToSummaryTop = new SquareHyperlink(messages.linkBackRiskSummary());

		FlexTable riskNavigatorWidget = new FlexTable();

		gotToSummaryTop.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		riskNavigatorWidget.setWidget(0, 1, gotToSummaryTop);
		riskNavigatorWidget.getCellFormatter().setWidth(0, 1, "33%");
		riskNavigatorWidget.getCellFormatter().setWidth(0, 0, "33%");
		riskNavigatorWidget.getCellFormatter().setWidth(0, 2, "33%");

		// It should only show the risk navigation bar when is an existing risk
		if (CommandTypes.insert != currentCommand)
		{
			if (currentIndex > 0)
			{
				riskNavigatorWidget.setWidget(0, 0, previousRisk);

				previousRisk.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			}
			else
			{
				Label label = new Label(" ");
				label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				riskNavigatorWidget.setWidget(0, 0, label);

			}

			if (currentIndex < (lisOfRisks.size() - 1))
			{

				riskNavigatorWidget.setWidget(0, 2, nextRisk);
				nextRisk.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			}
			else
			{
				Label label = new Label(" ");
				label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
				riskNavigatorWidget.setWidget(0, 2, label);

			}
		}

		gotToSummaryTop.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		riskNavigatorWidget.setWidth("85%");

		gotToSummaryTop.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					History.newItem(RiskAssessmentPilot.generateNavigationId(RiskAssessmentPilot.PageId.riskAssessmentsummary));
				}
			});

		previousRisk.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					currentRisk = getRiskFromListByIndex(currentIndex - 1);
					currentRiskID = currentRisk.getId();
					currentIndex = currentIndex - 1;
					// Reloads the risk form
					PaneInitialization();
				}
			});

		nextRisk.addClickHandler(new ClickHandler()
			{

				public void onClick(ClickEvent event)
				{
					currentRisk = getRiskFromListByIndex(currentIndex + 1);
					currentRiskID = currentRisk.getId();
					currentIndex = currentIndex + 1;
					// Reloads the risk form
					PaneInitialization();

				}
			});

		return riskNavigatorWidget;
	}

	public void saveRisk(int riskID, GwtRisk gwtRisk)
	{
		boolean isFormValid = validateRequiredFields();

		if (isFormValid)
		{

			if (riskID == -1)// Is an insert action
			{
				addRiskToProject(gwtRisk);
			}
			else
			{

				updateRisk(riskID, gwtRisk);
			}
		}
	}
	public void addRiskToProject(GwtRisk gwtRisk)
	{

		this.service.addRisksToProject(currentProject.getId(), gwtRisk, new AsyncCallback<Integer>()
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
							Window.alert(messages.errorAddingRisks());
							break;
					}
				}

				
				public void onSuccess(Integer result)
				{

					currentRiskID = result;
					currentCommand = CommandTypes.read;
					loadRiskFromProject();
				}
			});

	}

	// ------------------------------------------------------
	// Field Validator Methods
	// ------------------------------------------------------

	public void setValidationStyle(Widget w, String textValue)
	{
		if (textValue.length() == 0)
		{
			w.setStyleName("square-RequiredField");

			if (w instanceof ListBox)
			{
				w.setStyleName("square-RequiredFieldListBox");
			}
		}
		else
		{
			w.setStyleName("square-long-textBox");

			if (w instanceof ListBox)
			{
				w.setStyleName("Risk-Input-listBox");
			}
		}

	}
	public boolean validateRequiredFields()
	{
		for (int j = 0; j < matrix.getRowCount(); j++)
		{

			Widget w = matrix.getWidget(j, 1);
			String textValue = "Ok";
			if (w instanceof TextBox)
			{
				TextBox field = (TextBox) w;
				textValue = field.getText().trim();
				setValidationStyle(field, textValue);
			}
			else if (w instanceof TextArea)
			{
				TextArea field = (TextArea) w;
				textValue = field.getText().trim();
				setValidationStyle(field, textValue);

			}
			else if (w instanceof ListBox)
			{
				ListBox field = (ListBox) w;
				textValue = field.getItemText(field.getSelectedIndex()).trim();
				setValidationStyle(field, textValue);
			}

			if (textValue.length() == 0)
			{
				disPanel.setOpen(true);
				return false;
			} 
			
			
		}
		 if(listOfAssetsMapToRisk.size()==0)
		{
			disPanel.setOpen(true);
			assetsEmptyLabel.setStyleName("square-RequiredMessage");
			return false;
		}
		else if(listOfArtifactsMapToRisk.size()==0)
		{
			disPanel.setOpen(true);
			artifactsEmptyLabel.setStyleName("square-RequiredMessage");
			return false;
		}
		disPanel.setOpen(false);
		return true;

	}

	// ------------------------------------------------------
	// Risk Mapping to Assets and Artifacts Methods
	// ------------------------------------------------------

	boolean isAssetCurrentDialog = false;// This variable lets me know what
	// dialog box was the last open, to
	// handle the execute.
	public Widget getEditAssetsHyperLink(final RiskAssessmentPane riksPane)
	{

		SquareHyperlink associateLink = new SquareHyperlink(messages.editAssets());
		associateLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					assetDialog = new AssetDialogBox(riksPane, listOfProjectAssets, listOfAssetsMapToRisk);
					assetDialog.setModal(false);
					assetDialog.center();
					assetDialog.show();
					isAssetCurrentDialog = true;
				}
			});
		return associateLink;

	}

	public Widget getEditArtifactsHyperLink(final RiskAssessmentPane riksPane)
	{
		SquareHyperlink associateLink = new SquareHyperlink(messages.editArtifacts());
		associateLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					artifactDialog = new ArtifactDialogBox(riksPane, listOfProjectArtifacts, listOfArtifactsMapToRisk, messages.associateArtifacts());
					artifactDialog.setModal(false);
					artifactDialog.center();
					artifactDialog.show();
					isAssetCurrentDialog = false;
				}
			});
		return associateLink;
	}

	Label assetsEmptyLabel;
	public Widget createHTMLAssetList()
	{

		if (listOfAssetsMapToRisk.size() > 0)
		{
			String listStart = "<UL>";
			String listEnd = "</UL>";
			StringBuilder listBuilder = new StringBuilder();

			listBuilder.append(listStart);
			for (GwtAsset a : listOfAssetsMapToRisk)
			{
				listBuilder.append("<LI><font color=\"black\">" + a.getDescription() + "</font></LI>");
			}
			listBuilder.append(listEnd);
			
			HTML list =new HTML(listBuilder.toString());
			list.setStyleName("square-sssets-listing");
			
			return list;
		}
		else
		{
			assetsEmptyLabel = new Label(messages.noAssetSelected());
			assetsEmptyLabel.setStyleName("square-note");
			return assetsEmptyLabel;
		}

	}
	Label artifactsEmptyLabel;
	public Widget createHTMLArtifactsList()
	{
		if (listOfArtifactsMapToRisk.size() > 0)
		{
			String listStart = "<UL>";
			String listEnd = "</UL>";
			StringBuilder listBuilder = new StringBuilder();
			listBuilder.append(listStart);
			for (GwtArtifact a : listOfArtifactsMapToRisk)
			{
				listBuilder.append("<LI><font color=\"black\">" + a.getName() + "</font></LI>");
			}
			listBuilder.append(listEnd);
			HTML list =new HTML(listBuilder.toString());
			list.setStyleName("square-sssets-listing");
			
			return list;
		}
		else
		{
			artifactsEmptyLabel = new Label(messages.noArtifactSelected());
			artifactsEmptyLabel.setStyleName("square-note");
			return artifactsEmptyLabel;
		}

	}
	// This handless the call back when the dialog boxes are closed.
	//
	public void execute()
	{
		if (isAssetCurrentDialog)
		{
			listOfAssetsMapToRisk = assetDialog.getNewSelectedAssets();
			this.matrix.setWidget(7, 1, createHTMLAssetList());
		}
		else
		{
			listOfArtifactsMapToRisk = artifactDialog.getNewSelectedArtifacts();
			this.matrix.setWidget(8, 1, createHTMLArtifactsList());
		}

	}

}
