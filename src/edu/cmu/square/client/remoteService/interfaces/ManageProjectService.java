/**
 * 
 */
package edu.cmu.square.client.remoteService.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRole;
import edu.cmu.square.client.model.GwtStep;
import edu.cmu.square.client.model.GwtUser;

/**
 * Purpose: This class provides the asynchronous services for getting steps,  
 * adding, removing, and editing users, and updating project settings.
 */
@RemoteServiceRelativePath("manageProject.rpc")
public interface ManageProjectService extends RemoteService {
	
	
	/**
	 * This returns the new project GwtProject. Internally it associate the lead requirement engineer and create the steps depending on the project type
	 * @param project The project that the user selected.
	 * @return The steps associated with this project.
	 * @throws SquareException TODO
	 */
	GwtProject createProject(GwtProject project) throws SquareException;
	
	GwtProject copyProject(GwtProject project) throws SquareException;
	
	
	/**
	 * This returns the new project GwtProject. Internally it associate the lead requirement engineer and create the steps depending on the project type
	 * @param project The project that the user selected.
	 * @return The steps associated with this project.
	 * @throws SquareException TODO
	 */
	void deleteProject(int projectId) throws SquareException;
	
	/**
	 * This returns the new project GwtProject. Internally it associate the lead requirement engineer and create the steps depending on the project type
	 * @param project The project that the user selected.
	 * @return The steps associated with this project.
	 * @throws SquareException TODO
	 */
	List<GwtProject> getAllProjects() throws SquareException;
	
	/**
	 * This returns all the steps for this project. The steps may vary based on the project 
	 * type(SQUARE lite/full), and project focus(security/privacy/both).
	 * @param project The project that the user selected.
	 * @return The steps associated with this project.
	 * @throws SquareException TODO
	 */
	List<GwtStep> getSteps(GwtProject project) throws SquareException;
	
	/**
	 * Gets all users associated with the project that's passed. We use the user's name, username, 
	 * and role. The Gwt class will decide whether this is editable or not.
	 * @param project The project that's being managed. 
	 * @return The list of users for the project.
	 * @throws SquareException TODO
	 */
	List<GwtUser> getUserList(GwtProject project) throws SquareException;
	
	
	/**
	 * Adds the user selected by the lead requirements engineer to the passed project.
	 * @param gwtProject The project being added to.
	 * @param gwtUser The user selected.
	 * @param gwtRole TODO
	 * @throws SquareException TODO
	 */
	void addUserToProject(GwtProject gwtProject, GwtUser gwtUser, GwtRole gwtRole) throws SquareException;
	
	/**
	 * Removes the passed user from the given project, not from the site.  
	 * @param project The project being managed.
	 * @param user The user being removed.
	 * @throws SquareException TODO
	 */
	void removeUserFromProject(GwtProject project, GwtUser user) throws SquareException;
	
	/**
	 * This updates the user's role for the given project. 
	 * @param user The updated user object
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	void editRole(GwtUser gwtUser, GwtProject gwtProject) throws SquareException;
	
	/**
	 * Returns all the roles except the Lead Requirements engineer.
	 * @return all roles except for the lead requirements engineer. 
	 * @throws SquareException TODO
	 */
	List<GwtRole> getAllRoles() throws SquareException;
	

	
	void updateProjectName(GwtProject project) throws SquareException;
	
	
	GwtProject updateProject(int projectId, int leadRequiremntId,  String projectName) throws SquareException;
	
	
	GwtProject getProject(Integer projectId) throws SquareException;

	

	/**
	 * This updates  the securityElicitationTechnique
	 * @return The string statuses of the steps.
	 */
//	void updateProjectTechnique(Integer projectId, Integer techniqueID, String rationale) throws SquareException;
//	/**
//	 * This updates  the security inpection technique
//	 * @return The string statuses of the steps.
//	 */
//	void updateProjectInspectionTechnique(Integer projectId, Integer inspectionTechniqueId) throws SquareException;
//
//	/**
//	 * This updates  the security inpection technique status
//	 * @return The string statuses of the steps.
//	 */
//	void updateProjectInspectionStatus(Integer projectId, String inspectionTechniqueStatus) throws SquareException;


}
