package edu.cmu.square.server.business.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.step.interfaces.ElicitRequirementsBusiness;
import edu.cmu.square.server.dao.model.Requirement;

public class ElicitRequirementsBusinessImplTest extends AbstractSpringBase
{

	@Resource
	private ElicitRequirementsBusiness elicitRequirementBusiness;
	
	@Before
	public void setupTest() 
	{
		super.createRequirementsWithCategories();


		elicitRequirementBusiness.setProjectName(testProject.getName());
		elicitRequirementBusiness.setUserName(testUser.getUserName());
	}
	
	@After
	public void cleanup() 
	{
		testProject = null;
		testRequirement = null;
	}
	@Test
	public void testAddRequirementToProject()
	{
		try
		{
			int testRequirementId = elicitRequirementBusiness.addRequirementToProject(testProject.getId(), testRequirement.createGwtRequirement());
			testRequirement.setId(testRequirementId);
			Requirement req = requirementDao.fetch(testRequirement.getId());
			assertEquals(req.getDescription(), testRequirement.getDescription());
			assertEquals(req.getTitle(), testRequirement.getTitle());
			assertEquals(req.getProject(), testProject);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteRequirement()
	{
		try
		{
			elicitRequirementBusiness.addRequirementToProject(testProject.getId(), testRequirement.createGwtRequirement());

			Requirement req = requirementDao.fetch(testRequirement.getId());
			assertEquals(req.getDescription(), testRequirement.getDescription());
			assertEquals(req.getTitle(), testRequirement.getTitle());
			assertEquals(req.getProject(), testProject);
			
			elicitRequirementBusiness.deleteRequirement(testRequirement.getId(), testProject.getId());
			
			Requirement req1 = requirementDao.fetch(testRequirement.getId());
			assertTrue(req1==null);
		}
		catch (SquareException e)
		{
			
			e.printStackTrace();
			fail("error " + Arrays.toString(e.getStackTrace()));
		}
		
		
	}

	@Test
	public void testUpdateRequirement()
	{
		try
		{
			elicitRequirementBusiness.addRequirementToProject(testProject.getId(), testRequirement.createGwtRequirement());
			
			Requirement req = requirementDao.fetch(testRequirement.getId());
			assertEquals(req.getDescription(), testRequirement.getDescription());
			assertEquals(req.getTitle(), testRequirement.getTitle());
			assertEquals(req.getProject(), testProject);
			//assertTrue(req.getRisks().size() == 2);
			
			GwtRequirement newTestRequirement = req.createGwtRequirement();
			newTestRequirement.setDescription("updated description");
			
			assertNotSame(req.getDescription(), newTestRequirement.getDescription());
			elicitRequirementBusiness.updateRequirement(newTestRequirement);
			
			Requirement req1 = requirementDao.fetch(newTestRequirement.getId());
			assertEquals(req1.getDescription(), newTestRequirement.getDescription());
		}
		catch (SquareException e)
		{
			
			e.printStackTrace();
			fail("error " + Arrays.toString(e.getStackTrace()));
		}
	}

	@Test
	public void testGetRequirementsFromProject()
	{
		
		try
		{
			int testRequirementId = elicitRequirementBusiness.addRequirementToProject(testProject.getId(), testRequirement.createGwtRequirement());
			testRequirement.setId(testRequirementId);
			List<GwtRequirement> gwtRequirements = elicitRequirementBusiness.getRequirementsFromProject(testProject.getId());
			assertTrue(gwtRequirements.size()==1);
			
			Requirement req = new Requirement(gwtRequirements.get(0));
			assertEquals(req.getDescription(), testRequirement.getDescription());
			assertEquals(req.getTitle(), testRequirement.getTitle());
			assertEquals(req.getProject(), testProject);
			
			assertEquals(req.getGoals().size(),1);
			//assertTrue(req.getRisks().size() == 2);
			
		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("error " + Arrays.toString(e.getStackTrace()));
		}
		
		
	}

}
