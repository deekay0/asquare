/**
 * 
 */
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
import edu.cmu.square.server.dao.interfaces.TechniqueDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Technique;

/**
 * @author yirul
 *
 */
public class HbnTechniqueDaoTest extends AbstractSpringBase {

	@Resource
	private TechniqueDao techniqueDao;

	@Resource
	private ProjectDao projectDao;

	private Project testProject;
	private Technique testTechnique1;
	private Technique testTechnique2;
	
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
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = (Project)testMap.get("project");
		
		testTechnique1 = new Technique();
		testTechnique1.setName(Data1.name);
		testTechnique1.setDescription(Data1.description);
		testTechnique1.setType(Data1.type);
	
		
		
		testTechnique2 = new Technique();
		testTechnique2.setName(Data2.name);
		testTechnique2.setDescription(Data2.description);
		testTechnique2.setType(Data2.type);
	
		
	}
	
	@After
	public void tearDown()
	{
		this.testProject = null;
		this.testTechnique1 = null;
		this.testTechnique2 = null;
	}
	
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnTechniqueDao#removeTechniqueFromProject(edu.cmu.square.server.dao.model.Technique)}.
	 */
	@Test
	public void testRemoveTechniqueFromProject()
	{
//		//Add the technique to the DB
//		this.techniqueDao.addTechniqueToProject(this.testProject, this.testTechnique1);
//		
//		//check that the technique was added
//		List<Technique> techniques = this.techniqueDao.getTechniquesByProject(testProject);
//		
//		//delete the technique from the project
//		this.techniqueDao.removeTechniqueFromProject(this.testProject, this.testTechnique1);
//		
//		//check that the technique was deleted
//		techniques.clear();
//		techniques = null;
//		techniques = this.techniqueDao.getTechniquesByProject(testProject);
//		Assert.assertEquals(0, techniques.size());	
	}
	
	@Test
	public void testGetTechniquesByProject()
	{
		// create two projects
		
		Project testProject2 = new Project();
		testProject2.setName("TestManageProject1");
		testProject2.setLite(false);
		testProject2.setPrivacy(false);
		testProject2.setSecurity(true);
//		testProject2.setPrivacyTechniqueRationale("None");
//		testProject2.setSecurityTechniqueRationale("None");
		testProject2.setAcquisitionOrganizationEngineer(this.testUser);
		projectDao.create(testProject2);
		
		
		
		
	
		// add one technique to one project
		this.techniqueDao.addTechniqueToProject(this.testProject, this.testTechnique1);
	
		// add the other technique to the other project
		this.techniqueDao.addTechniqueToProject(testProject2, this.testTechnique2);
		
		
		// do the test to make sure you only get the right technique from the right project
		List<Technique> techniques1 = this.techniqueDao.getTechniquesByProject(testProject);
		Assert.assertEquals(1, techniques1.size());
		Assert.assertEquals(Data1.name, techniques1.get(0).getName());
		Assert.assertEquals(Data1.description, techniques1.get(0).getDescription());
		Assert.assertEquals(Data1.type, techniques1.get(0).getType());
		
		List<Technique> techniques2 = this.techniqueDao.getTechniquesByProject(testProject2);
		Assert.assertEquals(1, techniques2.size());
		Assert.assertEquals(Data2.name, techniques2.get(0).getName());
		Assert.assertEquals(Data2.description, techniques2.get(0).getDescription());
		Assert.assertEquals(Data2.type, techniques2.get(0).getType());
		
		
	}
	
	@Test
	public void testAddTechniqueToProject()
	{
		//Add the technique to the DB
		this.techniqueDao.addTechniqueToProject(this.testProject, this.testTechnique1);
		
		//check that the technique was added
		List<Technique> techniques = this.techniqueDao.getTechniquesByProject(testProject);
		Assert.assertEquals(1, techniques.size());
		Assert.assertEquals(Data1.name, techniques.get(0).getName());
		Assert.assertEquals(Data1.description, techniques.get(0).getDescription());
		Assert.assertEquals(Data1.type, techniques.get(0).getType());
	}
	


}
