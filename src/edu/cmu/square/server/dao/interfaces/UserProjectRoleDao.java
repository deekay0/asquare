/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.User;
import edu.cmu.square.server.dao.model.UserProjectRole;
import edu.cmu.square.server.dao.model.UserProjectRoleId;

/**
 * @author kaalpurush
 *
 */
public interface UserProjectRoleDao extends AbstractDao<UserProjectRole, UserProjectRoleId> {
	List<UserProjectRole> getByUserProject(User user, Project project);
}
