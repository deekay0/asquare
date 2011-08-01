package edu.cmu.square.server.remoteService.implementations;

import javax.annotation.Resource;

import org.junit.Test;

import edu.cmu.square.client.remoteService.interfaces.LogInService;
import edu.cmu.square.server.base.AbstractSpringBase;

public class LogInServiceImplTest extends AbstractSpringBase{
	@Resource
	LogInService logIn;

//	@Test
//	public void testLogIn() 
//	{
//			GWTLogIn login;
//			try 
//			{
//				login = logIn.logIn("admin", "yessir");
//			    assertTrue(login.isAuthenticated());
//			    login = logIn.logIn("admin", "nosir");
//			    assertFalse(login.isAuthenticated());
//			}
//			catch (ServiceException e) {
//				
//				fail("Multiple same user names" + e.getMessage());
//			}
//	}

	@Test
	public void testLogOut() {
		 // TODO
	}

	@Test
	public void testLockAccount() {
		// TODO
	}

	@Test
	public void testUnlockAccount() {
		 // TODO
	}

	@Test
	public void testResetCounter() {
		// TODO
	}

	@Test
	public void testCountFailedLoginAttempts() {
		// TODO
	}

	@Test
	public void testIsAuthenticated() {
		// TODO
	}

	@Test
	public void testIsLocked() {
		 // TODO
	}

	@Test
	public void testIsSessionExpired() {
		 // TODO
	}

	@Test
	public void testStoreSessionID() {
		 // TODO
	}

}
