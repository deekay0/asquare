/**
 * 
 */
package edu.cmu.square.server.dao.implementation;


import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.GoalDao;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.model.Goal;
import edu.cmu.square.server.dao.model.GoalType;
import edu.cmu.square.server.dao.model.Project;


public class HbnGoalDaoTest extends AbstractSpringBase {

	Project testProject;
	
	@Resource
	private ProjectDao projectDao;
	
	@Before
	public void setUp()
	{
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = (Project)testMap.get("project");
		this.projectDao.create(testProject);
	}
	
	@After
	public void tearDown()
	{
		this.testProject = null;
	}
	
	@Resource
	GoalDao goalDao;
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnAbstractDao#fetchAll()}.
	 */
	@Test
	public void testAddGoal() {
		

		String goalDescription="First Goal";

		Project project = testProject;
		GoalType goalType=new GoalType();
		Goal goal = new Goal();
		goalType.setId(1);
		
		goal.setGoalType(goalType);
		goal.setProject(project);
		goal.setDescription(goalDescription);
		goal.setPriority(1);
		
		goalDao.create(goal);
		Goal goalOut= goalDao.fetch(goal.getId());
		
		assertTrue(goalOut.getDescription().equalsIgnoreCase(goalDescription));
		assertTrue(goalOut.getProject().getId()==project.getId());	
	}

	@Test
	public void testUpdateGoal() {
		
		
		String goalDescription="First Goal";
		Project project = testProject;
		GoalType goalType=new GoalType();
		Goal goal = new Goal();
		
		project.setId(6);
		goalType.setId(1);
		
		goal.setGoalType(goalType);
		goal.setProject(project);
		goal.setDescription(goalDescription);
		goal.setPriority(1);
		
		goalDao.create(goal);
		Goal goalOut= goalDao.fetch(goal.getId());
		
		goalOut.setDescription("Party");
		
		goalDao.update(goalOut);
		
		Goal goalOut2= goalDao.fetch(goal.getId());
		
		assertTrue(goalOut2.getDescription().equalsIgnoreCase("Party"));
		assertTrue(goalOut2.getProject().getId()==6);	
	

	}
	
	@Test
	public void testDeleteGoal() {
		
		
		String goalDescription="First Goal";
		Project project = testProject;
		GoalType goalType=new GoalType();
		Goal goal = new Goal();
		
		project.setId(6);
		goalType.setId(1);
		
		goal.setGoalType(goalType);
		goal.setProject(project);
		goal.setDescription(goalDescription);
		goal.setPriority(1);
		
		goalDao.create(goal);
	
		goalDao.deleteById(goal.getId());
		Goal goalOut2 = null;
		try
		{
			goalOut2= goalDao.fetch(goal.getId());
		}
		catch(Exception e)
		{
			goalOut2 = null;
		}
		
		assertTrue(goalOut2 == null);
		
	}
	
	@Test
	public void testDeleteEntityGoal() {
		
		
		String goalDescription="First Goal";
		Project project = testProject;
		GoalType goalType=new GoalType();
		Goal goal = new Goal();
		
		goalType.setId(1);
		
		goal.setGoalType(goalType);
		goal.setProject(project);
		goal.setDescription(goalDescription);
		goal.setPriority(1);
		
		goalDao.create(goal);

		goalDao.deleteEntity(goal);
		Goal goalOut2 = null;
		try
		{
			goalOut2= goalDao.fetch(goal.getId());
		}
		catch(Exception e)
		{
			goalOut2 = null;
		}
		
		assertTrue(goalOut2 == null);
		
	}
	

	@Test
	public void testgetBusinessGoalByProject() {
		
		
		String goalDescription="First Goal";

		Project project = testProject;
		GoalType goalType=new GoalType();
		Goal goal = new Goal();
		
		
	
		goalType.setId(1);

		goal.setGoalType(goalType);
		goal.setProject(project);
		goal.setDescription(goalDescription);
		goal.setPriority(1);
		
		goalDao.create(goal);
		List<Goal> goalOut= goalDao.getBusinessGoalByProject(project);
		assertTrue(goalOut.size()==1);

	}
	
	
@Test	
public void testgetBusinessGoalByProject1() {
		
		//Test a project without a business goal

		Project project = testProject;
	
		List<Goal> goalOut= goalDao.getBusinessGoalByProject(project);
		assertTrue(goalOut.size()==0);

	}
	
@Test
public void testgetSubGoalByProject() {

	Project project =testProject;
	GoalType businessGoalType=new GoalType();
	GoalType securityGoalType=new GoalType();
	businessGoalType.setId(1);
	securityGoalType.setId(2);
	
	Goal businessgoal = new Goal();
	Goal subGoal1 = new Goal();
	Goal subGoal2 = new Goal();
	
	businessgoal.setProject(project);
	businessgoal.setGoalType(businessGoalType);
	businessgoal.setDescription("Business Goal Description");
	businessgoal.setPriority(1);
	
	subGoal1.setProject(project);
	subGoal1.setGoalType(securityGoalType);
	subGoal1.setDescription("Sub Goal Description1");
	subGoal1.setPriority(1);
	
	subGoal2.setProject(project);
	subGoal2.setGoalType(securityGoalType);
	subGoal2.setDescription("Sub Goal Description2");
	subGoal2.setPriority(1);
	
	
	goalDao.create(businessgoal);
	goalDao.create(subGoal1);
	goalDao.create(subGoal2);
	
	Goal goalOut= goalDao.fetch(businessgoal.getId());
	Goal subGoalOut1= goalDao.fetch(subGoal1.getId());
	Goal subGoalOut2= goalDao.fetch(subGoal2.getId());
	
	
	assertTrue(goalOut.getDescription().equalsIgnoreCase("Business Goal Description"));
	assertTrue(goalOut.getProject().getId()==project.getId());
	assertTrue(goalOut.getId() ==businessgoal.getId() );
	
	assertTrue(subGoalOut1.getDescription().equalsIgnoreCase("Sub Goal Description1"));
	assertTrue(subGoalOut1.getProject().getId()==project.getId());
	assertTrue(subGoalOut1.getId() ==subGoal1.getId() );
	
	assertTrue(subGoalOut2.getDescription().equalsIgnoreCase("Sub Goal Description2"));
	assertTrue(subGoalOut2.getProject().getId()==project.getId());
	assertTrue(subGoalOut2.getId() ==subGoal2.getId() );
	
 List<Goal> goalList=	 goalDao.getSubGoalByProject(project);
 assertTrue(goalList.size()==2);


}



}

