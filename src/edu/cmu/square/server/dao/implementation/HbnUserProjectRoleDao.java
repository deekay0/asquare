/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.UserProjectRoleDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.User;
import edu.cmu.square.server.dao.model.UserProjectRole;
import edu.cmu.square.server.dao.model.UserProjectRoleId;

/**
 * @author kaalpurush
 *
 */
@Repository
public class HbnUserProjectRoleDao extends HbnAbstractDao<UserProjectRole, UserProjectRoleId> implements UserProjectRoleDao{

	@SuppressWarnings("unchecked")
	
	public List<UserProjectRole> getByUserProject(User user, Project project) {
		Session session = getSession();
		String query = "Select upr from UserProjectRole upr where upr.user=:user and upr.project=:project";
		Query q = session.createQuery(query);
		q.setParameter("user", user);
		q.setParameter("project", project);
		return q.list();
	}
}
