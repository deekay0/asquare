package edu.cmu.square.server.dao.implementation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.InspectionTechniqueDao;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.model.InspectionTechnique;
import edu.cmu.square.server.dao.model.Project;


public class HbnInspectionTechniqueDaoTest extends AbstractSpringBase
{

	@Resource
	private InspectionTechniqueDao dao;

	@Resource
	private ProjectDao projectDao;

	private Project testProject;
	private InspectionTechnique testTechnique1;
	private InspectionTechnique testTechnique2;

	private static class Data1
	{
		static public String name = "Test Inspection Technique!";
		static public String description = "It's a fagan inspection";
	}

	private static class Data2
	{
		static public String name = "Peer Review";
		static public String description = "This is an informal Peer Review";
	}

	@Before
	public void setUp()
	{
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = (Project) testMap.get("project");

		testTechnique1 = new InspectionTechnique();
		testTechnique1.setName(Data1.name);
		testTechnique1.setDescription(Data1.description);

		testTechnique2 = new InspectionTechnique();
		testTechnique2.setName(Data2.name);
		testTechnique2.setDescription(Data2.description);
	}

	@After
	public void tearDown()
	{
		this.testProject = null;
		this.testTechnique1 = null;
		this.testTechnique2 = null;
	}


	@Test
	public void testGetInspectionTechniquesByProject()
	{
		// create another project for testing
		Project testProject2 = createSecondProject();

		// add one technique to one project
		this.testTechnique1.setProject(this.testProject);
		this.dao.create(testTechnique1);

		// add the other technique to the other project
		this.testTechnique2.setProject(testProject2);
		this.dao.create(testTechnique2);

		// do the test to make sure you only get the right technique from the
		// right project
		List<InspectionTechnique> techniques1 = this.dao.getInspectionTechniquesByProject(testProject);
		Assert.assertEquals(1, techniques1.size());
		Assert.assertEquals(Data1.name, techniques1.get(0).getName());
		Assert.assertEquals(Data1.description, techniques1.get(0).getDescription());

		List<InspectionTechnique> techniques2 = this.dao.getInspectionTechniquesByProject(testProject2);
		Assert.assertEquals(1, techniques2.size());
		Assert.assertEquals(Data2.name, techniques2.get(0).getName());
		Assert.assertEquals(Data2.description, techniques2.get(0).getDescription());
	}
	
	@Test
	public void testAddInspectionTechnique()
	{
		this.testTechnique1.setProject(this.testProject);
		this.dao.create(testTechnique1);
		
		List<InspectionTechnique> techniques = this.dao.getInspectionTechniquesByProject(testProject);
		Assert.assertEquals(1, techniques.size());
		Assert.assertEquals(Data1.name, techniques.get(0).getName());
		Assert.assertEquals(Data1.description, techniques.get(0).getDescription());
	}
	
	@Test
	public void testHasInspectionTechnique()
	{
		this.testTechnique1.setProject(this.testProject);
		this.dao.create(testTechnique1);
		
		Assert.assertEquals(this.testTechnique1.getId().intValue(), this.dao.hasTechnique(this.testTechnique1.getName(), this.testTechnique1.getProject()));
		Assert.assertEquals(-1, this.dao.hasTechnique(this.testTechnique2.getName(), this.testProject));
	}


	
	
	private Project createSecondProject()
	{
		Project newProject = new Project();
		newProject.setName("TestManageProject1");
		newProject.setLite(false);
		newProject.setPrivacy(false);
		newProject.setSecurity(true);
		newProject.setPrivacyTechniqueRationale("None");
		newProject.setSecurityTechniqueRationale("None");
		newProject.setLeadRequirementEngineer(this.testUser);
		projectDao.create(newProject);
		
		return newProject;
	}

}
