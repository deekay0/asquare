/**
 * 
 */
package edu.cmu.square.server.business.interfaces;

import java.util.List;

import org.w3c.dom.Document;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtInspectionTechnique;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRole;
import edu.cmu.square.client.model.GwtStep;
import edu.cmu.square.client.model.GwtTechnique;
import edu.cmu.square.client.model.GwtTerm;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.server.dao.model.Role;

/**
 * Purpose: This class provides the asynchronous services for getting steps,  
 * adding, removing, and editing users, and updating project settings.
 */
public interface ManageProjectBusiness  extends BaseBusinessInterface {
	
	
	/**
	 * returns GwtProject this is the new project 
	 * type(SQUARE lite/full), and project focus(security/privacy/both).
	 * @param p The project that the user selected.
	 * @return The steps associated with this project.
	 */

	
	 GwtProject createProject(GwtProject newProject, List<GwtTerm> terms)throws SquareException;
	/**
	 * returns GwtProject this is the new project 
	 * type(SQUARE lite/full), and project focus(security/privacy/both).
	 * @param p The project that the user selected.
	 * @return The steps associated with this project.
	 */
	

	 
	void deleteProject(int projectId) throws SquareException;
	
	
	List<GwtProject> getAllProjects()  throws SquareException;
	
	/**
	 * This returns all the steps for this project. The steps may vary based on the project 
	 * type(SQUARE lite/full), and project focus(security/privacy/both).
	 * @param p The project that the user selected.
	 * @return The steps associated with this project.
	 */
	
	List<GwtStep> getSteps(GwtProject gwtProject) throws SquareException;
	
	
	/**
	 * Gets all users associated with the project that's passed. We use the user's name, username, 
	 * and role. The Gwt class will decide whether this is editable or not.
	 * @param gwtProject The project that's being managed. 
	 * @return The list of users for the project.
	 */
	List<GwtUser> getUserList(GwtProject gwtProject) throws SquareException;
	
	
	/**
	 * Adds the user selected by the lead requirements engineer to the passed project.
	 * @param gwtProject The project being added to.
	 * @param gwtUser The user selected.
	 * @param gwtRole TODO
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example duplicate user)
	 */
	void addUserToProject(GwtProject gwtProject, GwtUser gwtUser, GwtRole gwtRole) throws SquareException;
	
	/**
	 * Removes the passed user from the given project, not from the site.  
	 * @param project The project being managed.
	 * @param user The user being removed.
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	void removeUserFromProject(GwtProject project, GwtUser user) throws SquareException;
	
	/**
	 * This updates the user's role for the given project. 
	 * @param user The updated user object
	 * @param project TODO
	 * @throws SquareExceptionThis may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	void editRole(GwtUser user, GwtProject project) throws SquareException;
	
	/**
	 * Returns all the roles except the Lead Requirements engineer.
	 * @return all roles except for the lead requirements engineer. 
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example user already removed) 
	 */
	List<Role> getAllRoles() throws SquareException;
	
	/**
	 * Updates the project with the details. This may potentially change the project name,
	 * the step status, project type, and focus. This also validates that only one step is open.
	 * @param project The project that's being updated.
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example user already removed) 
	 */
	void updateProjectName(GwtProject project) throws SquareException;
	
	GwtProject updateProject(int projectId, int leadRequiremntId,  String projectName) throws SquareException;


	GwtProject getProject(Integer projectId) throws SquareException;
	
	 List<GwtTerm> findDefaultTerms(Document defaultValuesProjectXMLDocument);
	
	 //List<GwtTechnique> findDefaultElicitationTechniques(Document defaultValuesProjectXMLDocument);
	 
	 //List<GwtEvaluation> findDefaultEvaluationCriteria(Document defaultValuesProjectXMLDocument);
	 
	 //List<GwtInspectionTechnique> findDefaultInspectionTechniques(Document defaultValuesProjectXMLDocument);
	
	GwtProject copyProject(GwtProject originalProject) throws SquareException;
	
}
