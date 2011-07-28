package edu.cmu.square.server.business.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtRating;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.server.authorization.AuthorizationAspect;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.step.interfaces.ReviewPackagesBusiness;
import edu.cmu.square.server.dao.model.Project;

public class ReviewPackagesBusinessImplTest extends AbstractSpringBase
{
	private GwtProject testProject;
	private GwtProject testProject2;
	
	@Resource
	private ReviewPackagesBusiness reviewPackagesBusiness;
	
	@Before
	public void setupTest()
	{
		reviewPackagesBusiness.setUserName("security");
		reviewPackagesBusiness.setProjectName("Aaa3");
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = ((Project) testMap.get("project")).createGwtProject();
		testProject2 = ((Project) testMap.get("testProject2")).createGwtProject();
	}
	
	@Test
	public void testAddQualityAttribute()
	{
		try
		{
			reviewPackagesBusiness.addQualityAttribute(testProject2, new GwtQualityAttribute(100));
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testAddSoftwarePackage()
	{
		try
		{
			reviewPackagesBusiness.addSoftwarePackage(testProject2, new GwtSoftwarePackage(100));
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetQualityAttributes()
	{
		try
		{
			List<GwtQualityAttribute> list = reviewPackagesBusiness.getQualityAttributes(testProject2, StepStatus.InProgress);
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testGetSoftwarePackages()
	{
		try
		{
			List<GwtSoftwarePackage> list = reviewPackagesBusiness.getSoftwarePackages(testProject2, StepStatus.InProgress);
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemoveSoftwarePackage()
	{
		try
		{
			reviewPackagesBusiness.removeSoftwarePackage(new GwtSoftwarePackage(36), testProject2);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemoveQualityAttribute()
	{
		try
		{
			reviewPackagesBusiness.removeQualityAttribute(new GwtQualityAttribute(42), testProject2);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateQualityAttribute()
	{
		try
		{
			reviewPackagesBusiness.updateQualityAttribute(new GwtQualityAttribute(), testProject2);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateSoftwarePackage()
	{
		try
		{
			reviewPackagesBusiness.updateSoftwarePackage(new GwtSoftwarePackage(), testProject2);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetRateValues()
	{
		try
		{
			List<GwtRating> list = reviewPackagesBusiness.getRateValues(1047);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetRateValue()
	{
		try
		{
			reviewPackagesBusiness.setRateValue(1047, 36, 42, 1);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetRateValue()
	{
		try
		{
			int rate = reviewPackagesBusiness.getRateValue(1047, 36, 42);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoadDefaultTechniques()
	{
		try
		{
			reviewPackagesBusiness.loadDefaultTechniques(1047, reviewPackagesBusiness.getSoftwarePackages(testProject2, StepStatus.InProgress));
		}
		catch (SquareException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoadDefaultEvaluations()
	{
		try
		{
			reviewPackagesBusiness.loadDefaultEvaluations(1047, reviewPackagesBusiness.getQualityAttributes(testProject2, StepStatus.InProgress));
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
			GwtStepVerficationResult result = reviewPackagesBusiness.verifyStep(new Project(testProject2));
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
