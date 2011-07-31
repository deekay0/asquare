package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.model.Requirement;

public class HbnRequirementDaoTest extends AbstractSpringBase
{
	
	@Before
	public void setupTest() 
	{
		super.createRequirementsWithCategories();
	}
	
	@Test
	public void testFindByCategoryId()
	{
		List<Requirement> requirements = requirementDao.fetchAll();
		assertTrue(requirements.size()>0);
	}
	@Test
	public void testRequirementExport()
	{
		
		requirementDao.exportToXML(null);
	}
	/*
	@Test
	public void testGetRequirementByProject()
	{
		List<Requirement> requirements3 = requirementDao.getRequirementByProject(testProject);
		System.out.println("testGetReq->"+requirements3);
		assertEquals(requirements3, testProject.getRequirements()); 
		
	}
	*/
	/*
	@Test
	public void testChangeStatusToApproved()
	{
		//Requirement req = requirementDao.changeStatusToApproved(testRequirement);
		String approvedStatus = "Approved";
		
		requirementDao.changeStatusToApproved(testRequirement);
		System.out.println(testRequirement.getStatus());
		assertEquals(testRequirement.getStatus(), approvedStatus);
	}
	@Test
	public void testChangeStatusToRequestRevision()
	{
		String requeststatus = "Request revision";
		
		requirementDao.changeStatusToApproved(testRequirement2);
		assertEquals(testRequirement2.getStatus(), requeststatus);
	}
*/
}
