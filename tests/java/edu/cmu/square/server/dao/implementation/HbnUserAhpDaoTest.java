package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.UserAhpDao;
import edu.cmu.square.server.dao.model.User;

public class HbnUserAhpDaoTest extends AbstractSpringBase
{

	@Resource
	private UserAhpDao userAhpDao;
	@Before
	public void setupTest()
	{
		super.createRequirementsWithCategories();
	}
	@Test
	public void testGetUsersInComplete()
	{
		List<User> obj = userAhpDao.getUsersCompleted(testProject.getId(), 3l);
		assertTrue(obj.size() == 0);
	}

}
