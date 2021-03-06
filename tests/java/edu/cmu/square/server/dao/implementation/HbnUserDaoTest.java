/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.RoleDao;
import edu.cmu.square.server.dao.interfaces.UserDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Role;
import edu.cmu.square.server.dao.model.User;

/**
 * @author yirul
 *
 */
public class HbnUserDaoTest extends AbstractSpringBase {

	@Resource
	private UserDao userDao;
	@Resource
	private ProjectDao projectDao;
	@Resource
	private RoleDao roleDao;
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnUserDao#addUserToProject(edu.cmu.square.server.dao.model.User, edu.cmu.square.server.dao.model.Project, Role)}.
	 */
	@Test
	public void testAddUserToProject()
	{	
		Role role = roleDao.findByName("Lead Requirements Engineer");
//		userProjectRole.setRole(role);
		User user = new User();
		user.setDepartment("Computer Science");
		user.setEmail("loomi@gmail.com");
		user.setFullName("Loomi Liao22");
		user.setLocation("Taipei");
		user.setOrganization("Search Engines");
		user.setPassword("password");
		user.setUserName("fruit");
		user.setPhone("1231418");
		
		Project project = new Project();
		project.setName("TestManageProject");
		project.setLite(false);
		project.setPrivacy(false);
		project.setSecurity(true);
	
//		project.setPrivacyTechniqueRationale("None");
//		project.setSecurityTechniqueRationale("None");
//		
		project.setAcquisitionOrganizationEngineer(user);
	
		userDao.create(user);
		projectDao.create(project);
		userDao.addUserToProject(user, project, role);
		User u = userDao.fetch(user.getId());
		assertTrue(u.equals(user));
		
	}

	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnUserDao#editRole(edu.cmu.square.server.dao.model.User, edu.cmu.square.server.dao.model.Project, Project)}.
	 */
	@Test
	public void testEditRole() {
		
//		userDao.editRole(user, role);
	}

	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnUserDao#getAllRoles()}.
	 */
	@Test
	public void testGetAllRoles() {
		
	}

	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnUserDao#getUserListForProject(edu.cmu.square.server.dao.model.Project)}.
	 */
	@Test
	public void testGetUserListForProject() {
		//create a user
		User user = makeUser();
		
		Project project = new Project();
		project.setName("TestManageProject");
		project.setLite(false);
		project.setPrivacy(false);
		project.setSecurity(true);
//		project.setPrivacyTechniqueRationale("None");
//		project.setSecurityTechniqueRationale("None");
		project.setAcquisitionOrganizationEngineer(user);
		
		Role role = roleDao.findByName("Acquisition Organization Engineer");
		
		userDao.create(user);
		projectDao.create(project);
		userDao.createUserProjectRole(user, project, role);
		
		List<User> userList = userDao.getUserListForProject(project);
		assertNotNull(userList);
		assertTrue(userList.size()>0);
	}

	@Test
	public void testGwtUserListFromProjectWithStaticData() {
		Project project = new Project();
		project.setId(38);
	}
	
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnUserDao#getUsersForAutoComplete(java.lang.String, edu.cmu.square.server.dao.model.Project)}.
	 */
	@Test
	public void testGetUsersForAutoComplete() {
		
	}

	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnUserDao#removeUserFromProject(edu.cmu.square.server.dao.model.User, edu.cmu.square.server.dao.model.Project)}.
	 */
	@Test
	public void testRemoveUserFromProject() {
		
	}
	
	/**
	 * 
	 */
	@Test
	public void testGetUsersbyUsername()
	{
		List<User> users= userDao.getUsersbyUsername("admin");
		assertNotNull(users);
		
	}
	
	@Test
	public void testSetLockOnAccount()
	{
		User user = new User();
		user.setDepartment("Computer Science");
		user.setEmail("loomi@gmail.com");
		user.setFullName("ARPH");
		user.setLocation("Taipei");
		user.setOrganization("Search Engines");
		user.setPassword("password");
		user.setUserName("ARPH");
		user.setPhone("1231418");
		userDao.create(user);
		User u = userDao.fetch(user.getId());
		assertTrue(u.equals(user));
		
		userDao.setLockOnAccount("ARPH");
		List<User> users= userDao.getUsersbyUsername("ARPH");
		Assert.assertEquals(1, this.userDao.getUsersbyUsername("ARPH").size());
		assertTrue(users.get(0).getUserName().equalsIgnoreCase("ARPH"));
		
	}

	
	@Test
	public void removeUserFromDatabase()
	{
		User testUser = makeUser();
		
		//Add the user to the DB
		this.userDao.create(testUser);
		
		//Check that he's there?
		Assert.assertEquals(1, this.userDao.getUsersbyUsername(testUser.getUserName()).size());
		
		//Remove the user from the DB
		int value = this.userDao.removeUserFromDatabase(testUser);
		Assert.assertEquals(1, value);
		
		//Check that he's gone
		Assert.assertEquals(0, this.userDao.getUsersbyUsername(testUser.getUserName()).size());
	}
	
	
	
	private User makeUser()
	{
		User user = new User();
		user.setDepartment("Computer Science");
		user.setEmail("loomi@gmail.com");
		user.setFullName("Loomi Liao22");
		user.setLocation("Taipei");
		user.setOrganization("Search Engines");
		user.setPassword("password");
		user.setUserName("vegetable");
		user.setPhone("1231418");
		
		return user;
	}
	
	
	
}
