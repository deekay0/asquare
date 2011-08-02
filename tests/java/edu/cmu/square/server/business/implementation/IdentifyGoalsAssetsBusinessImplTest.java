package edu.cmu.square.server.business.implementation;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtBusinessGoal;
import edu.cmu.square.client.model.GwtSubGoal;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.step.interfaces.IdentifyGoalsAssetsBusiness;
import edu.cmu.square.server.dao.model.Project;

public class IdentifyGoalsAssetsBusinessImplTest extends AbstractSpringBase
{

	@Resource
	private IdentifyGoalsAssetsBusiness identifyAssetGoals; 

	private int testProjectId;	

	@Before
	public void setUp()
	{
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProjectId = ((Project)testMap.get("project")).createGwtProject().getId();
	}
	
	@After
	public void tearDown()
	{
		this.testProjectId = 0;
	}
	
	@Test
	public void testsetBusinessGoal()
	{
		GwtBusinessGoal businessGoal = new GwtBusinessGoal();
		businessGoal.setDescription("The goal is to finish");
		
		
		GwtBusinessGoal businessGoalOut = null;
		try
		{
			identifyAssetGoals.setBusinessGoal(testProjectId, businessGoal);
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
		assert( businessGoalOut.getDescription().equalsIgnoreCase("The goal is to finish"));
	}

	
	@Test
	public void testsetUpdateBusinessGoal1()
	{
		try
		{
			GwtBusinessGoal businessGoal = new GwtBusinessGoal();
			businessGoal.setDescription("The goal is to finish");
			identifyAssetGoals.setBusinessGoal(testProjectId, businessGoal);
		
			//Create a business Goal for the demo project
			GwtBusinessGoal businessGoalOut = null;
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
			assertTrue( businessGoalOut.getDescription().equalsIgnoreCase("The goal is to finish"));
		
			//Update the Business Goal for the demo project
			businessGoal.setDescription("Description Updated");
			identifyAssetGoals.setBusinessGoal(testProjectId, businessGoal);
		
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
			assertTrue( businessGoalOut.getDescription().equalsIgnoreCase("Description Updated"));
			
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testaddSubGoal()
	{
		try
		{
			GwtBusinessGoal businessGoal = new GwtBusinessGoal();
			businessGoal.setDescription("The goal is to finish");
			identifyAssetGoals.setBusinessGoal(testProjectId, businessGoal);
		
			GwtBusinessGoal businessGoalOut = null;
		
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
	
			assertTrue( businessGoalOut.getDescription().equalsIgnoreCase("The goal is to finish"));
		
			GwtSubGoal subGoal1 = new GwtSubGoal();
			GwtSubGoal subGoal2 = new GwtSubGoal();
		
			subGoal1.setDescription("Sub Goal1");
			subGoal1.setPriority(1);;
	
			subGoal2.setDescription("Sub Goal2");
			subGoal2.setPriority(2);
		
			assertTrue( businessGoalOut.getSubGoals().size()==0);
			identifyAssetGoals.addSubGoal(testProjectId, subGoal1);
			identifyAssetGoals.addSubGoal(testProjectId, subGoal2);
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		
			assertTrue( businessGoalOut.getSubGoals().size()==2);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testupdateSubGoal()
	{
		try
		{
			GwtBusinessGoal businessGoal = new GwtBusinessGoal();
			businessGoal.setDescription("The goal is to finish");
			identifyAssetGoals.setBusinessGoal(testProjectId, businessGoal);
		
			GwtBusinessGoal businessGoalOut = null;
		
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
	
			assertTrue( businessGoalOut.getDescription().equalsIgnoreCase("The goal is to finish"));
		
			GwtSubGoal subGoal1 = new GwtSubGoal();
			GwtSubGoal subGoal2 = new GwtSubGoal();
		
			subGoal1.setDescription("Sub Goal1");
			subGoal1.setPriority(1);;
	
			subGoal2.setDescription("Sub Goal2");
			subGoal2.setPriority(2);
		
			assertTrue( businessGoalOut.getSubGoals().size()==0);
			identifyAssetGoals.addSubGoal(testProjectId, subGoal1);
			identifyAssetGoals.addSubGoal(testProjectId, subGoal2);
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		
			//Test that it retrieves two subgoals
			assertTrue( businessGoalOut.getSubGoals().size()==2);
		
			GwtSubGoal currentSubGoal= new GwtSubGoal();
		
			currentSubGoal.setId( businessGoalOut.getSubGoals().get(0).getId());
			currentSubGoal.setDescription( businessGoalOut.getSubGoals().get(0).getDescription());
			currentSubGoal.setPriority( businessGoalOut.getSubGoals().get(0).getPriority());
		
			currentSubGoal.setDescription("Modified sub goal1");
			currentSubGoal.setPriority(5);
		
			identifyAssetGoals.updateSubGoal(testProjectId, currentSubGoal);
		
			assertTrue(!businessGoalOut.getSubGoals().get(0).getDescription().equalsIgnoreCase("Modified sub goal1"));
	  
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		
			assertTrue(businessGoalOut.getSubGoals().get(0).getDescription().equalsIgnoreCase("Modified sub goal1"));
			assertTrue(businessGoalOut.getSubGoals().get(0).getPriority()==5);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testremoveSubGoal()
	{
		try
		{
			GwtBusinessGoal businessGoal = new GwtBusinessGoal();
			businessGoal.setDescription("The goal is to finish");
			identifyAssetGoals.setBusinessGoal(testProjectId, businessGoal);
		
			GwtBusinessGoal businessGoalOut = null;
		
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
	
			assertTrue( businessGoalOut.getDescription().equalsIgnoreCase("The goal is to finish"));
		
			GwtSubGoal subGoal1 = new GwtSubGoal();
			GwtSubGoal subGoal2 = new GwtSubGoal();
		
			subGoal1.setDescription("Sub Goal1");
			subGoal1.setPriority(1);;
	
			subGoal2.setDescription("Sub Goal2");
			subGoal2.setPriority(2);
		
			assertTrue( businessGoalOut.getSubGoals().size()==0);
			identifyAssetGoals.addSubGoal(testProjectId, subGoal1);
			identifyAssetGoals.addSubGoal(testProjectId, subGoal2);
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		
			assertTrue(businessGoalOut.getSubGoals().size() == 2);
		
			identifyAssetGoals.removeSubGoal(testProjectId, subGoal1);
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
			assertTrue( businessGoalOut.getSubGoals().size()==1);
		
			identifyAssetGoals.removeSubGoal(testProjectId, subGoal2);
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
			assertTrue( businessGoalOut.getSubGoals().size() == 0);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testaddAsset()
	{
		
		try
		{
		GwtBusinessGoal businessGoal = new GwtBusinessGoal();
		businessGoal.setDescription("The goal is to finish");
		identifyAssetGoals.setBusinessGoal(testProjectId, businessGoal);
		
		GwtBusinessGoal businessGoalOut = null;
		
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
	
			assertTrue( businessGoalOut.getDescription().equalsIgnoreCase("The goal is to finish"));
		
		GwtAsset gwtAsset1 = new GwtAsset();
		GwtAsset gwtAsset2 = new GwtAsset();
		
		gwtAsset1.setDescription("Asset1");
		gwtAsset2.setDescription("Asset2");
		
		
		assertTrue( businessGoalOut.getAssets().size()==0);
		
		identifyAssetGoals.addAsset(testProjectId,gwtAsset1);
		identifyAssetGoals.addAsset(testProjectId,gwtAsset2);
	
		businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		
		assertTrue(businessGoalOut.getAssets().size()==2);
	}
		 catch (SquareException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testupdateAsset()
	{
		
		try
		{
		GwtBusinessGoal businessGoal = new GwtBusinessGoal();
		businessGoal.setDescription("The goal is to finish");
		identifyAssetGoals.setBusinessGoal(testProjectId, businessGoal);
		
		GwtBusinessGoal businessGoalOut = null;
		
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
	
			assertTrue( businessGoalOut.getDescription().equalsIgnoreCase("The goal is to finish"));
		
		GwtAsset gwtAsset1 = new GwtAsset();
		GwtAsset gwtAsset2 = new GwtAsset();
		
		gwtAsset1.setDescription("Asset1");
		gwtAsset2.setDescription("Asset2");
		
		
		assertTrue( businessGoalOut.getAssets().size()==0);
		
		identifyAssetGoals.addAsset(testProjectId,gwtAsset1);
		identifyAssetGoals.addAsset(testProjectId,gwtAsset2);
	
		businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		
		assertTrue(businessGoalOut.getAssets().size()==2);
		
		GwtAsset gwtModifiedAsset = new GwtAsset();
		gwtModifiedAsset.setId(gwtAsset2.getId());
		gwtModifiedAsset.setDescription("Modified Asses Text");
		
		identifyAssetGoals.updateAsset(testProjectId, gwtModifiedAsset);
		businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		assertTrue(businessGoalOut.getAssets().get(1).getDescription().equalsIgnoreCase("Modified Asses Text"));
		assertTrue(businessGoalOut.getAssets().get(1).getId()==gwtModifiedAsset.getId());
	}
		 catch (SquareException e) 
		{
			e.printStackTrace();
		}
	}


	@Test
	public void testremoveAsset()
	{
		
		try
		{
		GwtBusinessGoal businessGoal = new GwtBusinessGoal();
		businessGoal.setDescription("The goal is to finish");
		identifyAssetGoals.setBusinessGoal(testProjectId, businessGoal);
		
		GwtBusinessGoal businessGoalOut = null;
		
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
	
			assertTrue( businessGoalOut.getDescription().equalsIgnoreCase("The goal is to finish"));
		
		GwtAsset gwtAsset1 = new GwtAsset();
		GwtAsset gwtAsset2 = new GwtAsset();
		
		gwtAsset1.setDescription("Asset1");
		gwtAsset2.setDescription("Asset2");
		
		
		assertTrue( businessGoalOut.getAssets().size()==0);
		
		identifyAssetGoals.addAsset(testProjectId,gwtAsset1);
		identifyAssetGoals.addAsset(testProjectId,gwtAsset2);
		businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		assertTrue(businessGoalOut.getAssets().size()==2);
		
		identifyAssetGoals.removeAsset(testProjectId,gwtAsset2);
		businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		assertTrue(businessGoalOut.getAssets().size()==1);
		
		identifyAssetGoals.removeAsset(testProjectId,gwtAsset1);
		businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		assertTrue(businessGoalOut.getAssets().size()==0);
		
	}
		 catch (SquareException e) 
		 {
			e.printStackTrace();
		
		}
	}

	@Test
	public void testassociateSubGoalAndAsset()
	{
		try {
			GwtBusinessGoal businessGoal = new GwtBusinessGoal();
			businessGoal.setDescription("The goal is to finish");
			identifyAssetGoals.setBusinessGoal(testProjectId, businessGoal);
			
			GwtBusinessGoal businessGoalOut = null;
			
				businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		
				assertTrue( businessGoalOut.getDescription().equalsIgnoreCase("The goal is to finish"));
			
			GwtSubGoal subGoal1 = new GwtSubGoal();
			GwtSubGoal subGoal2 = new GwtSubGoal();
			
			subGoal1.setDescription("Sub Goal1");
			subGoal1.setPriority(1);;
		
			subGoal2.setDescription("Sub Goal2");
			subGoal2.setPriority(2);
			
			assertTrue( businessGoalOut.getSubGoals().size()==0);
			identifyAssetGoals.addSubGoal(testProjectId, subGoal1);
			identifyAssetGoals.addSubGoal(testProjectId, subGoal2);
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
			
			assertTrue( businessGoalOut.getSubGoals().size()==2);
			
			GwtAsset gwtAsset1 = new GwtAsset();
			GwtAsset gwtAsset2 = new GwtAsset();
			
			gwtAsset1.setDescription("Asset1");
			gwtAsset2.setDescription("Asset2");
			
			
			assertTrue( businessGoalOut.getAssets().size()==0);
			
			identifyAssetGoals.addAsset(testProjectId,gwtAsset1);
			identifyAssetGoals.addAsset(testProjectId,gwtAsset2);
		
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
			
			assertTrue(businessGoalOut.getAssets().size()==2);
			assertTrue(businessGoalOut.getSubGoals().get(0).getAssets().size()==0);
			
			identifyAssetGoals.associateSubGoalAndAsset(businessGoalOut.getSubGoals().get(0), businessGoalOut.getAssets().get(0));
			identifyAssetGoals.associateSubGoalAndAsset(businessGoalOut.getSubGoals().get(0), businessGoalOut.getAssets().get(1));
			
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
			assertTrue(businessGoalOut.getSubGoals().get(0).getAssets().size()==2);
		
			} catch (SquareException e) {
				e.printStackTrace();
			}
	}
	
	public void testremoveAssociationSubGoalAndAsset()
	{
		try {
			GwtBusinessGoal businessGoal = new GwtBusinessGoal();
			businessGoal.setDescription("The goal is to finish");
			identifyAssetGoals.setBusinessGoal(testProjectId, businessGoal);
			
			GwtBusinessGoal businessGoalOut = null;
			
				businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
		
				assertTrue( businessGoalOut.getDescription().equalsIgnoreCase("The goal is to finish"));
			
			GwtSubGoal subGoal1 = new GwtSubGoal();
			GwtSubGoal subGoal2 = new GwtSubGoal();
			
			subGoal1.setDescription("Sub Goal1");
			subGoal1.setPriority(1);;
		
			subGoal2.setDescription("Sub Goal2");
			subGoal2.setPriority(2);
			
			assertTrue( businessGoalOut.getSubGoals().size()==0);
			identifyAssetGoals.addSubGoal(testProjectId, subGoal1);
			identifyAssetGoals.addSubGoal(testProjectId, subGoal2);
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
			
			assertTrue( businessGoalOut.getSubGoals().size()==2);
			
			GwtAsset gwtAsset1 = new GwtAsset();
			GwtAsset gwtAsset2 = new GwtAsset();
			
			gwtAsset1.setDescription("Asset1");
			gwtAsset2.setDescription("Asset2");
			
			
			assertTrue( businessGoalOut.getAssets().size()==0);
			
			identifyAssetGoals.addAsset(testProjectId,gwtAsset1);
			identifyAssetGoals.addAsset(testProjectId,gwtAsset2);
		
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
			
			assertTrue(businessGoalOut.getAssets().size()==2);
			assertTrue(businessGoalOut.getSubGoals().get(0).getAssets().size()==0);
			
			identifyAssetGoals.associateSubGoalAndAsset(businessGoalOut.getSubGoals().get(0), businessGoalOut.getAssets().get(0));
			identifyAssetGoals.associateSubGoalAndAsset(businessGoalOut.getSubGoals().get(0), businessGoalOut.getAssets().get(1));
			
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
			assertTrue(businessGoalOut.getSubGoals().get(0).getAssets().size()==2);
			
			identifyAssetGoals.removeAssociationSubGoalAndAsset(businessGoalOut.getSubGoals().get(0), businessGoalOut.getAssets().get(0));
			identifyAssetGoals.removeAssociationSubGoalAndAsset(businessGoalOut.getSubGoals().get(0), businessGoalOut.getAssets().get(1));
			
			businessGoalOut = identifyAssetGoals.getBusinessGoalInformation(testProjectId);
			assertTrue(businessGoalOut.getSubGoals().get(0).getAssets().size()==0);
		
			} catch (SquareException e) {
				e.printStackTrace();
			}
	}
	
}
