/**
 * 
 */
package edu.cmu.square.client.ui.ManageProject;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectServiceAsync;

/**
 * @author kaalpurush
 * 
 */
public class DeleteUserFromProjectHandler implements ClickHandler {

	private ManageProjectPane manageProjectPane;
	private GwtUser currentUser;
	private GwtProject gwtProject;
	private ManageProjectServiceAsync manageProjectService;
	private ManageProjectPaneMessages messages;

	public DeleteUserFromProjectHandler(ManageProjectPane manageProjectPane,
			GwtUser currentUser, GwtProject gwtProject, ManageProjectServiceAsync mpsa,
			ManageProjectPaneMessages messages) {

		this.manageProjectPane = manageProjectPane;
		this.currentUser = currentUser;
		this.gwtProject = gwtProject;
		this.manageProjectService = mpsa;
		this.messages = messages;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event
	 * .dom.client.ClickEvent)
	 */
	
	public void onClick(ClickEvent event) {
		final DeleteUserFromProjectDialog dialogBox = new DeleteUserFromProjectDialog(
				manageProjectPane, currentUser, gwtProject, manageProjectService);
		dialogBox.setText(messages.editUserDialogTitle());
		dialogBox.setAnimationEnabled(true);

		dialogBox.center();
		dialogBox.show();

	}

}
