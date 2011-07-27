package edu.cmu.square.client.ui.ManageProject;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRole;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectServiceAsync;

public class EditUserInProjectDialog extends DialogBox{
	private final ListBox rolesComboBox = new ListBox(false);
	private ManageProjectPane manageProjectPane;
	private GwtUser selectedUser;
	private List<GwtRole> gwtRoles;
	private GwtProject gwtProject;
	private ManageProjectServiceAsync manageProjectService;
	final ManageProjectPaneMessages messages = (ManageProjectPaneMessages)GWT.create(ManageProjectPaneMessages.class);
	public EditUserInProjectDialog(ManageProjectPane manageProjectPane, GwtUser gwtUser, GwtProject gwtProject, List<GwtRole> list, ManageProjectServiceAsync mpsa) {
		this.manageProjectPane = manageProjectPane;
		this.selectedUser=gwtUser;
		this.gwtRoles = list;
		this.manageProjectService = mpsa;
		this.gwtProject = gwtProject;
		this.initializeDialogBox();
		
	}
	private void initializeDialogBox() {
		int index = 0;
		for (GwtRole r: gwtRoles) {
			rolesComboBox.addItem(r.getName());
			if (r.getName().equals(selectedUser.getRole())) {
				rolesComboBox.setSelectedIndex(index);
			}
			index++;
		}
		
		 // layout the controls for user input in the dialog
	    FlexTable layout = new FlexTable();
	    layout.setCellSpacing(10);
	    
	    layout.setWidget(0, 0, new Label("Name:"));
	    layout.setWidget(0, 1, new Label(selectedUser.getFullName()));
	    
	    layout.setWidget(1, 0, new Label("Project Role:"));
	    layout.setWidget(1, 1, this.rolesComboBox);
	    
	    this.setWidget(layout);
	    
	    // Set up the buttons
	    Button saveButton = new Button("Save", new SaveHandler(this, selectedUser, gwtProject));
	    Button cancelButton = new Button("Cancel", new CancelHandler(this));
	    
	    saveButton.setWidth("100px");
	    cancelButton.setWidth("100px");
	    
	    HorizontalPanel buttons = new HorizontalPanel();
	    buttons.setSpacing(10);
	    buttons.add(saveButton);
	    buttons.add(cancelButton);
	    layout.setWidget(5, 0, buttons);
	    
	    FlexCellFormatter formatter = layout.getFlexCellFormatter();
	    formatter.setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    formatter.setColSpan(5, 0, 2);
		
	}
	
// ******* Handlers for the buttons *************
	
	private class SaveHandler implements ClickHandler
	{
		private EditUserInProjectDialog _dialog = null;
		private GwtUser _user = null;
		private GwtProject _project = null;
		public SaveHandler(EditUserInProjectDialog dialog, GwtUser user, GwtProject project)
		{
			super();
			this._dialog = dialog;
			this._user = user;
			this._project = project;
		}
		
		
		public void onClick(ClickEvent event)
		{
    		
    		
    		this._user.setRole(rolesComboBox.getItemText(this._dialog.rolesComboBox.getSelectedIndex()));
    		//make a call to the db with this
    		
    		manageProjectService.editRole(_user, _project, new AsyncCallback<Void>() {

				
				public void onFailure(Throwable caught) {
					if (caught instanceof SquareException) {
						SquareException se = (SquareException) caught;
						switch (se.getType()) {
						case authorization:
							Window.alert(messages.errorAuthorization());
							break;
						
						default:
							Window.alert("Error updating the user.");
							break;
						}

					} else {
						Window.alert("Error removing the user.");
					}
					
		    		//if it fails, 
		    		//show the error on the popup					
				}

				
				public void onSuccess(Void result) {
					_dialog.hide();
					//if it succeeds(on success)
		    		//refresh the table
					manageProjectPane.refreshProjectUserTable();
				}
    			
    		});
    		
    		
		}
	}
	
	private class CancelHandler implements ClickHandler
	{
		private EditUserInProjectDialog _dialog = null;
		
		public CancelHandler(EditUserInProjectDialog dialog)
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
