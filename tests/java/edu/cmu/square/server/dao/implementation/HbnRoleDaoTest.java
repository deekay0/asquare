/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.RoleDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Role;
import edu.cmu.square.server.dao.model.User;
import edu.cmu.square.server.dao.model.UserProjectRoleId;


public class HbnRoleDaoTest extends AbstractSpringBase {

	@Resource
	RoleDao roleDao;
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnAbstractDao#fetchAll()}.
	 */
	@Test
	public void testFetchAll() {
		List<Role> roleList = roleDao.fetchAll();
		assertNotNull(roleList);
		assertTrue(roleList.size()>0);
		
	}

	
	@Test
	public void testgetAllRolesExceptLeadRequirementsEngineer() {
		List<Role> roleList = roleDao.getAllRolesExceptLeadRequirementsEngineer();
		assertNotNull(roleList);
		assertTrue(roleList.size()>0);
		
	}
	
	@Test
	public void testgetRoleByUsernameProject()
	{
		User user = new User();
		user.setDepartment("Computer Science");
		user.setEmail("loomi@gmail.com");
		user.setFullName("Loomi Liao22");
		user.setLocation("Taipei");
		user.setOrganization("Search Engines");
		user.setPassword("password");
		user.setUserName("admin");
		user.setPhone("1231418");
		user.setId(100);
		
		Project project = new Project();
		project.setId(100);
		project.setName("Project XYZ");
		
		UserProjectRoleId upr = new UserProjectRoleId();
		upr.setProjectId(5678);
		upr.setRoleId(1);
		upr.setUserId(1234);
		
		List<Role> roles = this.roleDao.getRoleByUsernameProject(user,project);
		assertTrue(roles.size()==0);
	}
}

