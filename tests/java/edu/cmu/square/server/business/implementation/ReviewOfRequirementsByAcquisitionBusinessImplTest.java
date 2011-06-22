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
import edu.cmu.square.client.model.GwtTerm;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.step.interfaces.ReviewOfRequirementsByAcquisitionBusiness;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Requirement;

public class ReviewOfRequirementsByAcquisitionBusinessImplTest extends AbstractSpringBase
{
	private GwtProject testProject;
	
	@Resource
	private ReviewOfRequirementsByAcquisitionBusiness reviewOfRequirementsByAcquisitionBusiness;
	
	@Before
	public void setupTest()
	{
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = ((Project) testMap.get("project")).createGwtProject();
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
	public void testUpdateRequirement(){
		try
		{
			GwtRequirement gwtRequirement = new GwtRequirement();
			gwtRequirement.setTitle("req test title");
			gwtRequirement.setDescription("req test description.");

			GwtProject gwtProject = testProject;

			gwtRequirement = reviewOfRequirementsByAcquisitionBusiness.addRequirement(gwtProject, gwtRequirement);
			gwtRequirement.setTitle("req test updated title");
			gwtRequirement.setDescription("req test updated description");
			reviewOfRequirementsByAcquisitionBusiness.updateRequirement(gwtProject, gwtRequirement);

			List<GwtRequirement> reqs = reviewOfRequirementsByAcquisitionBusiness.getRequirements(gwtProject);
			reqs = reviewOfRequirementsByAcquisitionBusiness.getRequirements(gwtProject);
			assertTrue(reqs.get(0).getTitle().equals(gwtRequirement.getTitle()));

		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemoveRequirement(){
	
		try{
			GwtRequirement gwtRequirement = new GwtRequirement();
			gwtRequirement.setTitle("req test title");
			gwtRequirement.setDescription("req test description");
	
			GwtRequirement gwtRequirement2 = new GwtRequirement();
			gwtRequirement2.setTitle("req 2 test title");
			gwtRequirement2.setDescription("req2 test description");
	
			GwtProject gwtProject = testProject;
	
			gwtRequirement = reviewOfRequirementsByAcquisitionBusiness.addRequirement(gwtProject, gwtRequirement);
			gwtRequirement2 = reviewOfRequirementsByAcquisitionBusiness.addRequirement(gwtProject, gwtRequirement2);
			List<GwtRequirement> reqs = reviewOfRequirementsByAcquisitionBusiness.getRequirements(gwtProject);
			assertTrue(reqs.size() == 2);
	
			reviewOfRequirementsByAcquisitionBusiness.removeRequirement(gwtRequirement);
	
			reqs = reviewOfRequirementsByAcquisitionBusiness.getRequirements(gwtProject);
			assertTrue(reqs.size() == 1);
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
		
	}
	
	@Test
	public void testAddRequirementToProject(){
		
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

	
	
}
