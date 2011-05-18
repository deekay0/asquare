/**
 * 
 */
package edu.cmu.square.client.ui.ManageProject;

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
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectServiceAsync;

/**
 * @author kaalpurush
 * 
 */
public class DeleteUserFromProjectDialog extends DialogBox {

	private ManageProjectPane manageProjectPane;
	private GwtUser selectedUser;

	private GwtProject gwtProject;
	private ManageProjectServiceAsync manageProjectService;
	final ManageProjectPaneMessages messages = (ManageProjectPaneMessages) GWT
			.create(ManageProjectPaneMessages.class);

	public DeleteUserFromProjectDialog(ManageProjectPane manageProjectPane,
			GwtUser gwtUser, GwtProject gwtProject,
			ManageProjectServiceAsync mpsa) {
		this.manageProjectPane = manageProjectPane;
		this.selectedUser = gwtUser;

		this.manageProjectService = mpsa;
		this.gwtProject = gwtProject;
		this.initializeDialogBox();

	}

	private void initializeDialogBox() {

		// layout the controls for user input in the dialog
		FlexTable layout = new FlexTable();
		layout.setCellSpacing(10);
		layout.setWidget(0, 0, new Label(
				messages.deleteUserConfirmation()));

		FlexTable formLayout = new FlexTable();

		formLayout.setWidget(1, 0, new Label(messages.nameText()));
		formLayout.setWidget(1, 1, new Label(selectedUser.getFullName() + "("
				+ selectedUser.getUserName() + ")"));

		formLayout.setWidget(2, 0, new Label(messages.projectRole()));
		formLayout.setWidget(2, 1, new Label(this.selectedUser.getRole()));

		// Set up the buttons
		Button saveButton = new Button(messages.delete(), new SaveHandler(this,
				selectedUser, gwtProject));
		Button cancelButton = new Button(messages.cancel(), new CancelHandler(this));

		saveButton.setWidth("100px");
		cancelButton.setWidth("100px");

		HorizontalPanel buttons = new HorizontalPanel();
		buttons.setSpacing(10);
		buttons.add(saveButton);
		buttons.add(cancelButton);
		formLayout.setWidget(5, 0, buttons);

		FlexCellFormatter formatter = formLayout.getFlexCellFormatter();
		formatter.setHorizontalAlignment(5, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter.setColSpan(5, 0, 2);

		layout.setWidget(1, 0, formLayout);

		this.setWidget(layout);

	}

	// ******* Handlers for the buttons *************

	private class SaveHandler implements ClickHandler {
		private DeleteUserFromProjectDialog _dialog = null;
		private GwtUser _user = null;
		private GwtProject _project = null;

		public SaveHandler(
				DeleteUserFromProjectDialog deleteUserFromProjectDialog,
				GwtUser user, GwtProject project) {
			super();
			this._dialog = deleteUserFromProjectDialog;
			this._user = user;
			this._project = project;
		}

		
		public void onClick(ClickEvent event) {

			manageProjectService.removeUserFromProject(_project, _user,
					new AsyncCallback<Void>() {

						
						public void onFailure(Throwable caught) {

							if (caught instanceof SquareException) {
								SquareException se = (SquareException) caught;
								switch (se.getType()) {
								case authorization:
									Window.alert(messages.errorDeleteAuthorization());
									break;
								case selfDelete:
									Window.alert(messages.errorSelfDelete());
									break;
								default:
									Window.alert(messages.userRemovalError());
									break;
								}

							} else {
								Window.alert(messages.userRemovalError());
							}
							// if it fails,
							// show the error on the popup
						}

						
						public void onSuccess(Void result) {
							_dialog.hide();
							// if it succeeds(on success)
							// refresh the table
							manageProjectPane.refreshProjectUserTable();
						}

					});

		}
	}

	private class CancelHandler implements ClickHandler {
		private DeleteUserFromProjectDialog _dialog = null;

		public CancelHandler(
				DeleteUserFromProjectDialog deleteUserFromProjectDialog) {
			super();
			this._dialog = deleteUserFromProjectDialog;
		}

		
		public void onClick(ClickEvent event) {
			this._dialog.hide(true);
		}
	}
}
