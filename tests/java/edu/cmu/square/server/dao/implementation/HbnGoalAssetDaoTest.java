/**
 * 
 */
package edu.cmu.square.server.dao.implementation;


import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.AssetDao;
import edu.cmu.square.server.dao.interfaces.GoalDao;
import edu.cmu.square.server.dao.model.Asset;
import edu.cmu.square.server.dao.model.Goal;
import edu.cmu.square.server.dao.model.GoalType;
import edu.cmu.square.server.dao.model.Project;


public class HbnGoalAssetDaoTest extends AbstractSpringBase {

	Project testProject;
	Goal testBusinessGoal;
	Goal testSubGoal1 ;
	Asset testAsset1;
	Asset testAsset2;
	

	@Resource
	private GoalDao goalDao;
	@Resource
	private AssetDao assetDao;
	
	@Before
	public void setUp()
	{
		
		 testProject= new Project();
		 testBusinessGoal= new Goal();
		 testSubGoal1 = new Goal() ;
		 testAsset1= new Asset();;
		 testAsset2 = new Asset();
		
		GoalType businessGoalType=new GoalType();
		businessGoalType.setId(1);
		GoalType subGoalType=new GoalType();
		subGoalType.setId(2);
		
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = (Project)testMap.get("project");
		

		this.testBusinessGoal.setProject(testProject);
		this.testBusinessGoal.setDescription("Business Goal1");
		this.testBusinessGoal.setGoalType(businessGoalType);
		this.testBusinessGoal.setPriority(1);
		this.goalDao.create(testBusinessGoal);
		
		this.testSubGoal1.setProject(testProject);
		this.testSubGoal1.setGoalType(subGoalType);
		this.testSubGoal1.setDescription("Sub Goal 1");
		this.goalDao.create(testSubGoal1);
		
		this.testAsset1.setProject(testProject);
		this.testAsset1.setDescription("Asset1");
		this.assetDao.create(testAsset1);
		
		this.testAsset2.setProject(testProject);
		this.testAsset2.setDescription("Asset2");
		this.assetDao.create(testAsset2);
		
		
	}
	
	@After
	public void tearDown()
	{
		this.testProject = null;
	}
	

	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnAbstractDao#fetchAll()}.
	 */
	@Test
	public void testAssociateAssetToGoal() {
		
		this.testSubGoal1.getAssets().add(testAsset1);
		this.testSubGoal1.getAssets().add(testAsset2);
	
		Goal subGoal  = this.goalDao.fetch(this.testSubGoal1.getId());
		
		assertTrue(subGoal.getAssets().size() == 2);
	}
	
	@Test
	public void testRemoveAssociateAssetToGoal() {
		
		this.testSubGoal1.getAssets().add(testAsset1);
		this.testSubGoal1.getAssets().add(testAsset2);
	
		Goal subGoal  = this.goalDao.fetch(this.testSubGoal1.getId());
		
		assertTrue(subGoal.getAssets().size() == 2);
		
	
		subGoal.getAssets().remove(this.testAsset1);
		subGoal = this.goalDao.fetch(this.testSubGoal1.getId());
		assertTrue(subGoal.getAssets().size() == 1);

	}

	
}

