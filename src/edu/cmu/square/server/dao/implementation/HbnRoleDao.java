package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.server.dao.interfaces.RoleDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Role;
import edu.cmu.square.server.dao.model.User;
@Repository
@SuppressWarnings("unchecked")
public class HbnRoleDao extends HbnAbstractDao<Role, Integer> implements RoleDao {

	
	public Role findByName(String roleName) {
		if(roleName==null) {
			throw new NullPointerException("roleName cannot be null");
		}
		Session session = getSession();
		String query = "Select r from Role r where r.name = :roleName";
		Query q = session.createQuery(query);
		q.setParameter("roleName", roleName);
		List<Role> roles = q.list();
		return roles.get(0);
	}

	
	public List<Role> getAllRolesExceptLeadRequirementsEngineer() {
		Session session = getSession();
		String query = "Select r from Role r where r.name != :roleName";
		Query q = session.createQuery(query);
		q.setParameter("roleName", ProjectRole.Lead_Requirements_Engineer.getLabel());
		return q.list();
	}
	
	
	public List<Role> getRoleByUsernameProject(User user, Project project) {
		Session session = getSession();
		String query = "Select upr.role from UserProjectRole upr where upr.user.userName=:userName and upr.project=:project";
		Query q = session.createQuery(query);
		q.setParameter("userName", user.getUserName());
		q.setParameter("project", project);
		return q.list();
	}

	
	public List<Role> getRoleByUsernameProjectName(String userName,
			String projectName) {
		Session session = getSession();
		String query = "Select upr.role from UserProjectRole upr where upr.user.userName=:userName and upr.project.name=:projectName";
		Query q = session.createQuery(query);
		q.setParameter("userName", userName);
		q.setParameter("projectName", projectName);
		return q.list();
	}


}
