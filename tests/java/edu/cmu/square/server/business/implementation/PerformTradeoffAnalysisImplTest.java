package edu.cmu.square.server.business.implementation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.server.authorization.AuthorizationAspect;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.step.interfaces.PerformTradeoffAnalysisBusiness;
import edu.cmu.square.server.dao.model.Project;

public class PerformTradeoffAnalysisImplTest extends AbstractSpringBase
{
	private GwtProject testProject;
	private GwtProject testProject2;
	
	@Resource
	private PerformTradeoffAnalysisBusiness performTradeoffAnalysisImpl;
	
	@Before
	public void setupTest()
	{
		performTradeoffAnalysisImpl.setUserName("security");
		performTradeoffAnalysisImpl.setProjectName("Aaa3");
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = ((Project) testMap.get("project")).createGwtProject();
		testProject2 = ((Project) testMap.get("testProject2")).createGwtProject();
	}
	
	@Test
	public void testGetRequirementRateValues()
	{
		try
		{
			List<GwtRequirementRating> list = performTradeoffAnalysisImpl.getRequirementRateValues(1047);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testGetTradeoffReasons()
	{
		try
		{
			List<GwtTradeoffReason> list = performTradeoffAnalysisImpl.getTradeoffReasons(1047);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddTradeoffReason()
	{
		try
		{
			performTradeoffAnalysisImpl.addTradeoffReason(1047, 36, "test tradeoff reason");
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testUpdateTradeoffReason()
	{
		try
		{
			performTradeoffAnalysisImpl.updateTradeoffReason(1047, 36, "test updating tradeoff reason");
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdatePriority()
	{
		try
		{
			performTradeoffAnalysisImpl.updatePriority(1047, 36, 1);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetRequirementRateValue()
	{
		try
		{
			int rate = performTradeoffAnalysisImpl.getRequirementRateValue(1047, 36, 35948);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetRequirementRateValue()
	{
		try
		{
			performTradeoffAnalysisImpl.setRequirementRateValue(1047, 36, 35948, 2);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void verifyStep()
	{
		try
		{
			GwtStepVerficationResult result = performTradeoffAnalysisImpl.verifyStep(new Project(testProject2));
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
