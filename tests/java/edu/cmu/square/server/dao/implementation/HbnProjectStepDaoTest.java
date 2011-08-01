package edu.cmu.square.server.dao.implementation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.ProjectStepDao;
import edu.cmu.square.server.dao.interfaces.StepDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectStep;
import edu.cmu.square.server.dao.model.ProjectStepId;
import edu.cmu.square.server.dao.model.Step;




public class HbnProjectStepDaoTest extends AbstractSpringBase
{

	@Resource
	ProjectStepDao projectStepDao;
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnAbstractDao#fetchAll()}.
	 */
	Project testProject;
	
	@Resource
	ProjectDao projectDao;
	
	@Resource 
	StepDao stepDao;
	
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
	

	@Test
	public void createStepForProject()
	{
	
	   List<Step> steps = stepDao.fetchAll();
	   for(Step s: steps)
	   {
		   ProjectStep projectStep = new ProjectStep();
		   ProjectStepId projectStepId = new ProjectStepId();
		   projectStepId.setProjectId(this.testProject.getId());
		   projectStepId.setStepId(s.getId());
		   projectStep.setId(projectStepId);
		   projectStep.setStatus("Not Started");
		   projectStepDao.create(projectStep);
	   }
	   
	   
	   List<Step> stepsOut= stepDao.getProjectSteps(this.testProject);
	   Assert.assertEquals(steps.size(), stepsOut.size());
		
	}
	

}
