package edu.cmu.square.server.business.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.interfaces.ChooseProjectBusiness;
import edu.cmu.square.server.business.interfaces.StepBusiness;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.StepDao;
import edu.cmu.square.server.dao.model.Step;

public class ChooseProjectBusinessImplTest extends AbstractSpringBase
{
	@Resource
	private ChooseProjectBusiness chooseBusiness;
	
	@Resource
	private StepBusiness stepBusiness;
	
	@Resource
	private StepDao stepDao;
	
	@Resource
	private ProjectDao projectDao;
	@Before
	public void doItFirst()
	{
		super.createUserProjectforRole();
		userDao.addUserToProject(testUser, testProject, testRole);
		List<Step> steps = stepDao.getSecuritySteps();
		testProject.getSteps().addAll(steps);
		projectDao.update(testProject);
	}
/*	added by Nan
 * @Test
	public void testGetProjectsForUser()
	{
		try
		{
			List<GwtProject>[] projects = chooseBusiness.getProjectsForUser(testUser.getId());
			assertTrue(projects[0].size()==1);
			assertTrue(projects[1].size()==0);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Error " + e);
		}
	}*/ 

	
//	@Test
//	public void testGetProjectsForUser1()
//	{
//		try
//		{
//			
//			for (Step s: testProject.getSteps())
//			{
//				stepBusiness.updateStepStatus(testProject.getId(), s.getId(), StepStatus.InProgress.getLabel());
//			}
//			
//			List<GwtProject>[] projects = chooseBusiness.getProjectsForUser(testUser.getId());
//			assertTrue(projects[0].size()==9);
//			assertTrue(projects[1].size()==0);
//		}
//		catch (SquareException e)
//		{
//			e.printStackTrace();
//			fail("Error " + e);
//		}
//	}
}
