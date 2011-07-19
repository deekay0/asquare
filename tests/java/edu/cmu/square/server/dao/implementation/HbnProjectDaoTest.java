package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import edu.cmu.square.client.model.InspectionStatus;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.InspectionTechniqueDao;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.StepDao;
import edu.cmu.square.server.dao.model.InspectionTechnique;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Step;
import edu.cmu.square.server.dao.model.Technique;

public class HbnProjectDaoTest extends AbstractSpringBase
{

	@Resource
	private StepDao stepDao;
	
	@Resource
	private ProjectDao projectDao;
	
	@Resource
	private InspectionTechniqueDao inspectionDao;
	
	
	
	private Technique testElicitationTechnique1;
	private Technique testEliciationTechnique2;
	private InspectionTechnique testInspectionTechnique;
	
	
	private static class Data1
	{
		static public String name = "ARM";
		static public String description = "The best requirements technique ever!";
		static public boolean type = true;
	}
	
	private static class Data2
	{
		static public String name = "Super Man";
		static public String description = "Weeeeeeee!!!!";
		static public boolean type = false;
	}
	
	
	@Before
	public void setUp()
	{
		
		super.createUserProjectforRole();
		userDao.addUserToProject(testUser, testProject, testRole);
		
//		this.projectDao.create(testProject);
		
		testElicitationTechnique1 = new Technique();
		testElicitationTechnique1.setName(Data1.name);
		testElicitationTechnique1.setDescription(Data1.description);
		testElicitationTechnique1.setType(Data1.type);
		
		testEliciationTechnique2 = new Technique();
		testEliciationTechnique2.setName(Data2.name);
		testEliciationTechnique2.setDescription(Data2.description);
		testEliciationTechnique2.setType(Data2.type);
	
		testInspectionTechnique = new InspectionTechnique();
		testInspectionTechnique.setName("Fagan");
		testInspectionTechnique.setDescription("Need five people");
		
		List<Step> steps = stepDao.getSecuritySteps();
//		testProject.getSteps().clear();
		testProject.getSteps().addAll(steps);
	
	}
	
	@After
	public void tearDown()
	{
		this.testElicitationTechnique1 = null;
		this.testEliciationTechnique2 = null;
	}
	
	@Test
	@Transactional
	public void testCreateProject()
	{
		Project  newProject = new Project();
		
		newProject.setName("TestManageProject1");
		newProject.setLite(false);
		newProject.setPrivacy(false);
		newProject.setSecurity(true);
		newProject.setAcquisitionOrgEngineer(this.testUser);
		
		projectDao.create(newProject);
		
		Project result = projectDao.fetch(newProject.getId());
		
		Assert.assertEquals(newProject.getName(), result.getName());
	}
	
	
	@Test
	@Transactional
	public void testGetProjectSteps()
	{	
		
		
		
		List<Step> steps = null;
		
		try
		{
		 steps = stepDao.getProjectSteps(testProject);
		}
		catch (RuntimeException re)
		{
			fail("Error in test " + re.getMessage());
		}
		
		assertNotNull(steps);
		assertTrue(steps.size() > 0);
	}
	
	
	
	@Test
	@Transactional
	public void testUpdateProjectSteps()
	{	
		Project project = new Project();
		project.setId(38);
		
		
			/*ProjectStepId projectStepId = new ProjectStepId();
			projectStepId.setStepId(1);
			projectStepId.setProjectId(38);
			ProjectStep step= projectStepDao.fetch(projectStepId);
			assertTrue(step.getStatus().equalsIgnoreCase("In Progress"));
			step.setStatus("Complete");
			projectStepDao.update(step);
			step= projectStepDao.fetch(projectStepId); 
			assertTrue(step.getStatus().equalsIgnoreCase("Complete"));*/

	
	}
	
	
	
	
	
	
	
	
	/*added by Nan
	 * @Test
	public void testGetProjectsForUserStatus() 
	{
		Integer userId = testUser.getId();
		
		List<Project> projectsWithStep = projectDao.getIncompleteProjectsForUser(userId);
		assertTrue(projectsWithStep.size() == 1);
		
	}*/
	
}
