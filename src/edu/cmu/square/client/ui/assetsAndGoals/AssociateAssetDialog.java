package edu.cmu.square.client.ui.assetsAndGoals;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtBusinessGoal;
import edu.cmu.square.client.model.GwtSubGoal;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsService;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsServiceAsync;

/**
 * A Dialog Box for associating the assets with the sub-goals
 */
public class AssociateAssetDialog extends DialogBox
{
	
    private final IdentifyAssetsAndGoalsPaneMessages messages = (IdentifyAssetsAndGoalsPaneMessages) GWT.create(IdentifyAssetsAndGoalsPaneMessages.class);
    private final List<CustomizedCheckBox > assetsComboBox = new ArrayList<CustomizedCheckBox>();
    private IdentifyGoalsAssetsServiceAsync service = GWT.create(IdentifyGoalsAssetsService.class);
    private GwtBusinessGoal goal;
    private GwtSubGoal subGoal;
    private int projectIdentifier;
    private FetchBusinessGoalFromDB refresher;
    private FlexTable assetAssociationTable;
     
	public AssociateAssetDialog(final GwtBusinessGoal goal, int projectID, GwtSubGoal subGoal, FetchBusinessGoalFromDB commandClass, FlexTable assetAssociationTable)
	{
		super();
		
		this.setAnimationEnabled(true);
		this.center();
		
		this.goal = goal;
		this.projectIdentifier = projectID;
		this.subGoal = subGoal;
		this.refresher = commandClass;
		this.assetAssociationTable = assetAssociationTable;
		
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");	
		service.loadBusinessGoalInfo(projectIdentifier, new AsyncCallback<GwtBusinessGoal>()
		{
			public void onFailure(Throwable caught)
            {
            	Window.alert(caught.getMessage());
            }

			
			public void onSuccess(GwtBusinessGoal result)
			{
				 initializeDialog(result);
				 show();
			}
			   
	
		});
		
	}
	
	
	private void initializeDialog(GwtBusinessGoal businessGoal)
	{
		this.setText(messages.dialogTitle());
		this.center();
		this.goal = businessGoal;
		List<GwtAsset> assetList = this.goal.getAssets();
		for(int m=0;m<this.goal.getSubGoals().size(); m++)
		{
			if(this.goal.getSubGoals().get(m).getId().intValue() == this.subGoal.getId().intValue())
			{
				this.subGoal = this.goal.getSubGoals().get(m);
			}
		}
		List<GwtAsset> assetAssociatedWithSubGoals = this.subGoal.getAssets();
		for(int i=0; i<assetList.size();i++)
		{
			CustomizedCheckBox cb = new CustomizedCheckBox();
			cb.setText(assetList.get(i).getDescription());
			cb.setAsset(assetList.get(i));
			cb.setName(assetList.get(i).getId()+""); //associate the asset primary key
			
			//get the assets for this subgoal
			for(int j=0; j<assetAssociatedWithSubGoals.size(); j++)
			{
				if(assetAssociatedWithSubGoals.get(j).getId().intValue() == assetList.get(i).getId().intValue())
				{
					cb.setValue(true);			
					break;
				}
				else
				{
					cb.setValue(false);
				}
			}
			assetsComboBox.add(cb);
		}
	    	    
	    // layout the controls for user input in the dialog
	    FlexTable layout = new FlexTable();
	    layout.setCellSpacing(10);
	    for(int i=0; i<assetsComboBox.size();i++)
	    {
	    	layout.setWidget(i, 0, assetsComboBox.get(i));
	    }
	    
	    this.setWidget(layout);

	    // Set up the buttons
	    Button saveButton = new Button(messages.save(), new SaveHandler(this));
	    Button cancelButton = new Button(messages.cancel(), new CancelHandler(this));
	    
	    saveButton.setWidth("100px");
	    cancelButton.setWidth("100px");
	    
	    HorizontalPanel buttons = new HorizontalPanel();
	    buttons.setSpacing(10);
	    buttons.add(saveButton);
	    buttons.add(cancelButton);
	    layout.setWidget(assetsComboBox.size(), 0, buttons);
	    
	    FlexCellFormatter formatter = layout.getFlexCellFormatter();
	    formatter.setHorizontalAlignment(assetsComboBox.size(), 0, HasHorizontalAlignment.ALIGN_CENTER);
	    formatter.setColSpan(4, 0, 2);
	}
	
	// ******* Handlers for the buttons *************
	
	private class SaveHandler implements ClickHandler
	{
		private AssociateAssetDialog dialog = null;
		
		
		public SaveHandler(AssociateAssetDialog dialoPointer)
		{
			super();
			this.dialog = dialoPointer;
		
		}
		
	
		
		public void onClick(ClickEvent event)
		{
    		this.dialog.hide();
    		
    		ServiceDefTarget endpoint = (ServiceDefTarget)service;
    		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");	
    		for(int i=0; i< assetsComboBox.size(); i++)
    		{
    			if(assetsComboBox.get(i).getValue()==true)
    			{
    			service.associateSubGoalAndAsset(projectIdentifier, subGoal, assetsComboBox.get(i).getAsset(), new AsyncCallback<Void>()
    		    {

					
					public void onFailure(Throwable caught) 
					{
						if (caught instanceof SquareException) {
							SquareException se = (SquareException) caught;
							switch (se.getType()) {
							case authorization:
								Window.alert(messages.errorAssociateSubgoalAssetAuth());
								break;
						
							default:
								Window.alert(messages.errorAssociateGoalAsset());
								break;
							}

						} else {
							Window.alert(messages.errorAssociateGoalAsset());
						}
						
					}

					
					public void onSuccess(Void result) 
					{
						ServiceDefTarget endpoint = (ServiceDefTarget) service;
						endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");	
						service.loadBusinessGoalInfo(projectIdentifier, new AsyncCallback<GwtBusinessGoal>()
						{
							public void onFailure(Throwable caught)
				            {
				            	Window.alert(caught.getMessage());
				            }
							public void onSuccess(GwtBusinessGoal result)
							{
								 goal = result;
								 refresher.setUpTableRenderer(goal,assetAssociationTable,subGoal);
								 refresher.selectCommandToExecute("RefreshAssetsPane");
								 refresher.execute();
							}
						 });
					}
    		    });
    			}
    			else
    			{
    				try {
						service.removeAssociationGoalAsset(projectIdentifier, subGoal, assetsComboBox.get(i).getAsset(), new AsyncCallback<Void>()
								{

									
									public void onFailure(Throwable caught)
									{
										if (caught instanceof SquareException) {
											SquareException se = (SquareException) caught;
											switch (se.getType()) {
											case authorization:
												Window.alert(messages.errorRemoveAssociationAuth());
												break;
										
											default:
												Window.alert(messages.errorRemovingAssociation());
												break;
											}

										} else {
											Window.alert(messages.errorRemovingAssociation());
										}
									}

									
									public void onSuccess(Void result) 
									{
														
										ServiceDefTarget endpoint = (ServiceDefTarget) service;
										endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");	
										service.loadBusinessGoalInfo(projectIdentifier, new AsyncCallback<GwtBusinessGoal>()
										{
											public void onFailure(Throwable caught)
								            {
								            	Window.alert(caught.getMessage());
								            }
											public void onSuccess(GwtBusinessGoal result)
											{
												 goal = result;
												 refresher.setUpTableRenderer(goal,assetAssociationTable,subGoal);
												 refresher.selectCommandToExecute("RefreshAssetsPane");
												 refresher.execute();
											}
										 });
										
									
									}
							
								});
					} catch (Exception e) {
						
						e.printStackTrace();
					}
    			}
    		}		
		}
	}
	
	private class CancelHandler implements ClickHandler
	{
		private AssociateAssetDialog _dialog = null;
		
		public CancelHandler(AssociateAssetDialog dialog)
		{
			super();
			this._dialog = dialog;
		}
		
		
		public void onClick(ClickEvent event)
		{
    		this._dialog.hide(true);
		}
	}
}

class CustomizedCheckBox extends CheckBox
{
	private GwtAsset asset = null;
	private int priority = -1;
	public void setAsset(GwtAsset gwtAsset) 
	{
		asset = gwtAsset;		
	}
	
	public GwtAsset getAsset()
	{
		return asset;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public int getPriority() 
	{
		return priority;
	}
}

