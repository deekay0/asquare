package edu.cmu.square.server.business.implementation;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.GwtTerm;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.step.interfaces.ReviewOfRequirementsByAcquisitionBusiness;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Requirement;

public class ReviewOfRequirementsByAcquisitionBusinessImplTest extends AbstractSpringBase
{
	private GwtProject testProject;
	private GwtProject testProject2;
	
	@Resource
	private ReviewOfRequirementsByAcquisitionBusiness reviewOfRequirementsByAcquisitionBusiness;
	
	@Before
	public void setupTest()
	{
		reviewOfRequirementsByAcquisitionBusiness.setUserName("aoe");
		reviewOfRequirementsByAcquisitionBusiness.setProjectName("Democase1");
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = ((Project) testMap.get("project")).createGwtProject();
		
		testProject2 = ((Project) testMap.get("testProject2")).createGwtProject();
	}
		
	
	@Test
	public void testGetRequirements(){
		try
		{
			List<GwtRequirement> list = reviewOfRequirementsByAcquisitionBusiness.getRequirements(new GwtProject(1153));
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testAddRequirement(){
		try
		{
			GwtRequirement gwtRequirement = new GwtRequirement();
			gwtRequirement.setTitle("req test title");
			gwtRequirement.setDescription("req test description");
			GwtProject gwtProject = testProject;
			reviewOfRequirementsByAcquisitionBusiness.addRequirement(gwtProject, gwtRequirement);

			List<GwtRequirement> reqs = reviewOfRequirementsByAcquisitionBusiness.getRequirements(gwtProject);
			assertTrue(reqs.size() == 1);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void addRequirementToProject()
	{
		try
		{
			GwtRequirement gwtRequirement = new GwtRequirement();
			gwtRequirement.setTitle("req test title");
			gwtRequirement.setDescription("req test description");
			GwtProject gwtProject = testProject;
			reviewOfRequirementsByAcquisitionBusiness.addRequirementToProject(gwtProject.getId(), gwtRequirement);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateRequirement(){
		try
		{
			reviewOfRequirementsByAcquisitionBusiness.updateRequirement(testProject2, new GwtRequirement());
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemoveRequirement(){	
		try{
			reviewOfRequirementsByAcquisitionBusiness.deleteRequirement(35955, 1153);
		}catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoadDefaultRequirements(){
		try
		{
			reviewOfRequirementsByAcquisitionBusiness.loadDefaultRequirements(testProject.getId(), createDefaultRequirements());
			
			List<GwtRequirement> reqs = reviewOfRequirementsByAcquisitionBusiness.getRequirements(new GwtProject(testProject.getId()));
			
			Assert.assertEquals(5, reqs.size()); //This is the amount of terms in the XML file.

		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteRequirement(){
		
	}

	@Test
	public void testGetRequirementsFromProject(){
		try
		{
			List<GwtRequirement> reqs = reviewOfRequirementsByAcquisitionBusiness.getRequirementsFromProject(1153);
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testAddRequirementToProject(){
		try
		{
			reviewOfRequirementsByAcquisitionBusiness.addRequirementToProject(1153, new GwtRequirement());
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testChangeStatusToApproveRequirement(){
		String approvedStatus = "Approved";
			
		try
		{
			GwtRequirement gwtRequirement = new GwtRequirement();
			gwtRequirement.setTitle("req test title");
			gwtRequirement.setDescription("req test description.");
			gwtRequirement.setStatus("Pending");
			GwtProject gwtProject = testProject;

			gwtRequirement = reviewOfRequirementsByAcquisitionBusiness.addRequirement(gwtProject, gwtRequirement);
			reviewOfRequirementsByAcquisitionBusiness.updateRequirement(gwtProject, gwtRequirement);

			List<GwtRequirement> reqs = reviewOfRequirementsByAcquisitionBusiness.getRequirements(gwtProject);
			reqs = reviewOfRequirementsByAcquisitionBusiness.getRequirements(gwtProject);
			assertTrue(reqs.get(0).getStatus().equals(approvedStatus));

		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
		
	}

	@Test
	public void testChangeStatusToRequestRevisionRequirement(){
		
	}
	
	
	@Test
	public void verifyStep()
	{
		try
		{
			GwtStepVerficationResult result = reviewOfRequirementsByAcquisitionBusiness.verifyStep(new Project(testProject2));
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
