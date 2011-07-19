package edu.cmu.square.client.remoteService.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRole;
import edu.cmu.square.client.model.GwtStep;
import edu.cmu.square.client.model.GwtUser;

public interface ManageProjectServiceAsync
{

	/**
	 * Create a project this should only be done by the administrator This
	 * returns the new project GwtProject. Internally it associate the lead
	 * requirement engineer and create the steps depending on the project type
	 * 
	 * @param project
	 *            The project that the user selected.
	 * @return The steps associated with this project.
	 * @throws SquareException
	 */
	void createProject(GwtProject project, AsyncCallback<GwtProject> result);

	/**
	 * Delete a project - This should only be done by the administrator
	 * 
	 * @param project
	 *            The project that the user selected.
	 * @return The steps associated with this project.
	 * @throws SquareException
	 */
	void deleteProject(int projectId, AsyncCallback<Void> result);
	
	
	void getAllProjects(AsyncCallback<List<GwtProject>> result);
	
	/**
	 * This returns all the steps for this project. The steps may vary based on
	 * the project type(SQUARE lite/full), and project
	 * focus(security/privacy/both).
	 * 
	 * @param project
	 *            The project that the user selected.
	 * @return The steps associated with this project.
	 */
	void getSteps(GwtProject project, AsyncCallback<List<GwtStep>> result);

	/**
	 * Gets all users associated with the project that's passed. We use the
	 * user's name, username, and role. The Gwt class will decide whether this
	 * is editable or not.
	 * 
	 * @param project
	 *            The project that's being managed.
	 * @return The list of users for the project.
	 */
	void getUserList(GwtProject project, AsyncCallback<List<GwtUser>> result);

	/**
	 * Adds the user selected by the lead requirements engineer to the passed
	 * project.
	 * 
	 * @param project
	 *            The project being added to.
	 * @param user
	 *            The user selected. @ This may indicate database errors, and/or
	 *            business logic errors (for example duplicate user)
	 */
	void addUserToProject(GwtProject gwtProject, GwtUser gwtUser, GwtRole gwtRole, AsyncCallback<Void> result);

	/**
	 * Removes the passed user from the given project, not from the site.
	 * 
	 * @param project
	 *            The project being managed.
	 * @param user
	 *            The user being removed. @ This may indicate database errors,
	 *            and/or business logic errors (for example user already
	 *            removed)
	 */
	void removeUserFromProject(GwtProject project, GwtUser user, AsyncCallback<Void> result);

	/**
	 * This updates the user's role for the given project.
	 * 
	 * @param user
	 *            The updated user object
	 * @This may indicate database errors, and/or business logic errors (for
	 *       example user already removed)
	 */
	void editRole(GwtUser user, GwtProject project, AsyncCallback<Void> result);

	/**
	 * Returns all the roles except the Lead Requirements engineer.
	 * 
	 * @return all roles except for the lead requirements engineer. @ This may
	 *         indicate database errors, and/or business logic errors (for
	 *         example user already removed)
	 */
	void getAllRoles(AsyncCallback<List<GwtRole>> result);

	
	void updateProjectName(GwtProject project, AsyncCallback<Void> result);
	
	void updateProject(int projectId, int leadRequiremntId,  String projectName, AsyncCallback<GwtProject> result);

	void getProject(Integer projectId, AsyncCallback<GwtProject> asyncCallback);

//	void updateProjectTechnique(Integer projectId, Integer techniqueID, String rationale, AsyncCallback<Void> result);
//
//	void updateProjectInspectionTechnique(Integer projectId, Integer inspectionTechniqueId, AsyncCallback<Void> result);
//
//	void updateProjectInspectionStatus(Integer projectId, String inspectionTechniqueStatus, AsyncCallback<Void> asyncCallback);

}
