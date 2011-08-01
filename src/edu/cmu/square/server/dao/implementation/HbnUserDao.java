/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.UserDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Role;
import edu.cmu.square.server.dao.model.User;

/**
 * @author yirul
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class HbnUserDao extends HbnAbstractDao<User, Integer> implements UserDao{
   
	
	public void addUserToProject(User user, Project project, Role role) {
		
		this.createUserProjectRole(user, project, role);
	}

	
	public void editRole(User user, Role role, Project project) {
		Session session = getSession(); // get the hibernate session;
		String query = "update user_project_role upr set upr.role_id = :roleId where upr.user_id=:userId and upr.project_id =:projectId";
		Query q =session.createSQLQuery(query);
		
		q.setParameter("roleId", role.getId());
		q.setParameter("userId", user.getId());
		q.setParameter("projectId", project.getId());
		q.executeUpdate();	
		
		
	}

	
	public List<User> getUserListForProject(Project project) {
		String query = "Select upr.user from UserProjectRole upr where upr.project = :project";
		Query q = getSession().createQuery(query);
		q.setParameter("project",project);
		return q.list();
		
	}

	

	public void removeUserFromProject(User user, Project project) {
		String query = "Delete from UserProjectRole upr where upr.project = :project and upr.user=:user";
		Query q = getSession().createQuery(query);
		q.setParameter("user", user);
		q.setParameter("project", project);
		q.executeUpdate();
	}

	/**
	 * Deletes the user permanently from the database.
	 * We assume the database is set to cascade the delete so that user's id will be removed from
	 * all the related tables. 
	 * 
	 * @return The number of rows deleted by this query.  The correct value should be 1.
	 */
	
	public int removeUserFromDatabase(User user)
	{
		Query q = getSession().createQuery("delete from User u where u = :user");
		q.setParameter("user", user);

		return q.executeUpdate();  //should return 1 since we only deleted one user.
	}


	public List<User> getUsersbyUsername(String userName) {
		Session session = getSession();
		String query = "Select u from User u where u.userName=:userName";
		
		Query q = session.createQuery(query);
		q.setParameter("userName", userName);
		return q.list();
	}


	
	public void createUserProjectRole(User user, Project project, Role role) {
		Session session = getSession();
		String query = "insert into user_project_role (user_id, project_id, role_id) values (:user_id, :project_id, :role_id)"; 
//				"values(" + user.getId()
//		+ "," + project.getId() + "," + role.getId();
		Query q = session.createSQLQuery(query);
		q.setParameter("user_id", user.getId());
		q.setParameter("project_id", project.getId());
		q.setParameter("role_id", role.getId());
		q.executeUpdate();
		
	}

	/**
	 * Lock the user account after 5 un-successive attempts
	 * @param userName the user whose account will be locked
	 */
	public void setLockOnAccount(String userName) 
	{
		Session session = getSession(); // get the hibernate session;
		String query = "update user set locked = 1 where userName = :user_id";
		Query q =session.createSQLQuery(query);
		q.setParameter("user_id", userName);
		q.executeUpdate();	
	}

	/**
	 * Lock the user account after 5 un-successive attempts
	 * @param userName the user whose account will be locked
	 */
	public void editRole(User user, String role) 
	{
		Session session = getSession(); // get the hibernate session;
		String query = "update user u set u.role = r (select r1 from role as r1 where r1.name = :role) where u= :user";
		Query q =session.createSQLQuery(query);
		
		q.setParameter("role", role);
		q.setParameter("user", user);
		q.executeUpdate();	
	}

	
	public Role getRoleForUserInProject(Integer userId, Integer projectId) {
		Session session = getSession();
		String query = "Select upr.role from UserProjectRole upr where upr.user.id=:userId and upr.project.id=:projectId";
		Query q = session.createQuery(query);
		q.setParameter("userId", userId);
		q.setParameter("projectId", projectId);
		List result = q.list();
		if (result.isEmpty())
		{
			return null;
		}
		return (Role)result.get(0);
		
	}


	@Override
	public int getCountOfUsersByRole(String roleName, Integer projectId)
	{
		String query = "Select count(upr.user.id) from UserProjectRole upr" +
				" where upr.role.name=:roleName and upr.project.id=:projectId";
		Query q = getSession().createQuery(query);
		q.setParameter("roleName", roleName);
		q.setParameter("projectId", projectId);
		Long result = (Long)q.uniqueResult();
		return result.intValue();
	}


	@Override
	public List<User> getUsersbyEmail(String emailAddress)
	{
		String query = "Select u from User u where u.email=:emailAddress";
		Query q = getSession().createQuery(query);
		q.setParameter("emailAddress", emailAddress);
		return q.list();
	}

}
