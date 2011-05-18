package edu.cmu.square.server.business.implementation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GWTAuthorization;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.interfaces.LogInBusiness;
import edu.cmu.square.server.dao.interfaces.UserDao;
import edu.cmu.square.server.dao.model.User;

public class LogInBusinessImplTest extends AbstractSpringBase
{

	@Resource
	private LogInBusiness mps;
	@Resource
	private UserDao userDao;
	@Test
	
	public void testLockAccount() 
	{
		User user = new User();
		user.setDepartment("Computer Science");
		user.setEmail("loomi@gmail.com");
		user.setFullName("Sneader");
		user.setLocation("Taipei");
		user.setOrganization("Search Engines");
		user.setPassword("passtest");
		user.setUserName("vegetable");
		user.setPhone("1231418");
		userDao.create(user);
		
		try
		{
			GwtUser gwtUser = mps.lockAccount("Sneader");
			assert(gwtUser.isLocked());
		}
		catch (SquareException e) 
		{
			fail("Multiple users with the same name" + e.getMessage());
		}
	}

	

	@Test
	public void testLogIn()
	{	
		try
		{
			User user = new User();
			user.setDepartment("Computer Science");
			user.setEmail("loomi@gmail.com");
			user.setFullName("Sneader");
			user.setLocation("Taipei");
			user.setOrganization("Search Engines");
			user.setPassword("passtest");
			user.setUserName("vegetable");
			user.setPhone("1231418");
			userDao.create(user);
			
			GwtUser gwtUser = mps.logIn("Sneader", "pass1test");
			assertFalse(gwtUser.isAuthenticated());
			user.setLocked(false);
		}
		catch (SquareException e)
		{
			fail("Multiple users with the same name" + e.getMessage());
		}
	}


	@Test
	public void testgetRoles() 
	{
		GwtUser user = new GwtUser();
		user.setUserName("admin");
		user.setUserId(53);
		
		GwtProject project = new GwtProject();
		project.setId(38);
		project.setName("Loomi's test project");
		GWTAuthorization auth = mps.getRoles(user, 38);
		assert(auth.getRoles().size() == 1);
	}


}
