/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Role;
import edu.cmu.square.server.dao.model.User;

/**
 * @author yirul
 *
 */
public interface RoleDao extends AbstractDao<Role, Integer> {

	Role findByName(String string);

	List<Role> getAllRolesExceptLeadRequirementsEngineer();
 
	List<Role> getRoleByUsernameProject(User user, Project project);

	List<Role> getRoleByUsernameProjectName(String userName, String projectName);
}
