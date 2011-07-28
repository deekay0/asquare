package edu.cmu.square.server.business.implementation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRationale;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.server.authorization.AuthorizationAspect;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.step.interfaces.FinalProductSelectionBusiness;
import edu.cmu.square.server.dao.model.Project;

public class FinalProductSelectionImplTest extends AbstractSpringBase
{
	private GwtProject testProject;
	private GwtProject testProject2;
	
	@Resource
	private FinalProductSelectionBusiness finalProductSelectionBusiness;
	
	@Before
	public void setupTest()
	{
		finalProductSelectionBusiness.setUserName("aoe");
		finalProductSelectionBusiness.setProjectName("Aaa3");
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = ((Project) testMap.get("project")).createGwtProject();
		testProject2 = ((Project) testMap.get("testProject2")).createGwtProject();
	}
	
	@Test
	public void testGetRationale()
	{
		try
		{
			GwtRationale rationale = finalProductSelectionBusiness.getRationale(testProject2);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testSetRationale()
	{
		try
		{
			finalProductSelectionBusiness.setRationale(new GwtRationale());
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testUpdateTradeoffReason()
	{
		try
		{
			finalProductSelectionBusiness.updateTradeoffReason(1047, 36, "test updating tradeoff reason");
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTradeoffReason()
	{
		try
		{
			List<GwtTradeoffReason> list = finalProductSelectionBusiness.getTradeoffReasons(1047);
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetRequirementRateValue()
	{
		try
		{
			int rate = finalProductSelectionBusiness.getRequirementRateValue(1047, 36, 35948);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetRequirementRateValues()
	{
		try
		{
			List<GwtRequirementRating> list = finalProductSelectionBusiness.getRequirementRateValues(1047);
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
			GwtStepVerficationResult result = finalProductSelectionBusiness.verifyStep(new Project(testProject2));
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
