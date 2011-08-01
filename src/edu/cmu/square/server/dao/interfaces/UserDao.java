/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Role;
import edu.cmu.square.server.dao.model.User;
/**
 * This interface lists out the data access operations on the User table
 *
 */

public interface UserDao extends AbstractDao<User, Integer> {

	/**
	 * This returns a list of users for the given project.
	 * @param project A project(project id cannot be null)
	 * @return A list of users
	 */
	List<User> getUserListForProject(Project project);
	
	
	/**
	 * This links the given user to the project. This will throw a runtime exception 
	 * if the user already exits.
	 * @param user Provided user
	 * @param project The project the user is linked to.
	 * @param role The role the user is filling.
	 */
	void addUserToProject(User user, Project project, Role role);
	
	/**
	 * This will remote the given user from the project. This will throw a runtime
	 * exception if the user does not exist.
	 * @param user The user does not exist.
	 * @param project The project to be removed from.
	 */
	void removeUserFromProject(User user, Project project);
	
	/**
	 * Permanently deletes the user from the database.
	 */
	int removeUserFromDatabase(User user);
	
	/**
	 * This updates the user's role for the given project. 
	 * @param user The updated user object
	 * @param project TODO
	 * @param project The project this user's role is being updated.
	 * @throws RuntimeException  This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	void editRole(User user, Role role, Project project);
	

/**
 * Returns the list of users with the user name.
 * @param userName
 * @return a list of users with the given userName.
 */
	List<User> getUsersbyUsername(String userName);
	
	void createUserProjectRole(User user, Project project, Role role);
	
	/**
	 * Lock the user account after 5 unsuccessful attempts
	 * @param user a user name whose account will be locked
	 */
	void setLockOnAccount(String userName);

	Role getRoleForUserInProject(Integer userId, Integer projectId);

	int getCountOfUsersByRole(String roleName, Integer projectId);


	List<User> getUsersbyEmail(String emailAddress);

	

}
