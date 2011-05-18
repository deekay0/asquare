package edu.cmu.square.server.dao.implementation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.ArtifactDao;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.model.Artifact;
import edu.cmu.square.server.dao.model.Project;

public class HbnArtifactDaoTest extends AbstractSpringBase
{

	@Resource
	ArtifactDao artifactDao;

	@Resource
	ProjectDao projectDao;

	private Project testProject;
	private Artifact testArtifact1;
	private Artifact testArtifact2;

	
	private static class ArtifactData1
	{
		static public String name = "The Best Misuse case";
		static public String description = "Misuse case of how to break this tool.  This might " +
				             "include more meta inforamtion about the documentat htat is stored in the repository.";
		static public String revision ="1.3";
		static public String link = "http://blah.com/blah.docx";
	}
	
	private static class ArtifactData2
	{
		static public String name = "A Use Case";
		static public String description = "This would be more meta inforamiton about the use case.";
		static public String revision ="Latest and Greatest";
		static public String link = "\\\\Shared\\Server\\uber.docx";
	}

	
	
	
	
	@Before
	public void setUp()
	{
		Map<String, Object> testMap = super.createUserProjectforRole();
		this.testProject = (Project) testMap.get("project");
		this.projectDao.create(testProject);
		
		this.testArtifact1 = new Artifact(this.testProject, ArtifactData1.name, ArtifactData1.description, ArtifactData1.link, ArtifactData1.revision);
		this.testArtifact2 = new Artifact(this.testProject, ArtifactData2.name, ArtifactData2.description, ArtifactData2.link, ArtifactData2.revision);
	}

	@After
	public void tearDown()
	{
		this.testProject = null;
		this.testArtifact1 = null;
		this.testArtifact2 = null;
	}

	@Test
	public void testAddArtifact()
	{
		this.artifactDao.create(this.testArtifact1);

		Artifact result = this.artifactDao.fetch(this.testArtifact1.getId());
		Assert.assertEquals(this.testArtifact1.getName(), result.getName());
		Assert.assertEquals(this.testArtifact1.getDescription(), result.getDescription());
		Assert.assertEquals(this.testArtifact1.getRevision(), result.getRevision());
		Assert.assertEquals(this.testArtifact1.getLink(), result.getLink());
		
		this.artifactDao.create(this.testArtifact2);
		
		Artifact result2 = this.artifactDao.fetch(this.testArtifact1.getId());
		Assert.assertEquals(this.testArtifact1.getName(), result2.getName());
		Assert.assertEquals(this.testArtifact1.getDescription(), result2.getDescription());
		Assert.assertEquals(this.testArtifact1.getRevision(), result2.getRevision());
		Assert.assertEquals(this.testArtifact1.getLink(), result2.getLink());
		
		Artifact result3 = this.artifactDao.fetch(this.testArtifact2.getId());
		Assert.assertEquals(this.testArtifact2.getName(), result3.getName());
		Assert.assertEquals(this.testArtifact2.getDescription(), result3.getDescription());
		Assert.assertEquals(this.testArtifact2.getRevision(), result3.getRevision());
		Assert.assertEquals(this.testArtifact2.getLink(), result3.getLink());
	}
	
	
	@Test
	public void testGetArtifactsByProject()
	{
		this.artifactDao.create(this.testArtifact1);
		this.artifactDao.create(this.testArtifact2);
		
		List<Artifact> artifacts = this.artifactDao.getArtifactsByProject(this.testProject);
		
		Assert.assertEquals(2, artifacts.size());
		Assert.assertTrue(artifacts.contains(this.testArtifact1));
		Assert.assertTrue(artifacts.contains(this.testArtifact2));
	}
	
	@Test
	public void testRemoveArtifact()
	{
		this.artifactDao.create(this.testArtifact1);
		this.artifactDao.create(this.testArtifact2);
		
		this.artifactDao.deleteById(this.testArtifact1.getId());
		
		List<Artifact> artifacts = this.artifactDao.getArtifactsByProject(this.testProject);
		Assert.assertEquals(1, artifacts.size());
		Assert.assertFalse(artifacts.contains(this.testArtifact1));
		Assert.assertTrue(artifacts.contains(this.testArtifact2));
	
		
		this.artifactDao.deleteById(this.testArtifact2.getId());
		
		artifacts = this.artifactDao.getArtifactsByProject(this.testProject);
		Assert.assertEquals(0, artifacts.size());
		Assert.assertFalse(artifacts.contains(this.testArtifact1));
		Assert.assertFalse(artifacts.contains(this.testArtifact2));
	}
	
	@Test
	public void testRemoveArtifactByObject()
	{
		this.artifactDao.create(this.testArtifact1);
		this.artifactDao.create(this.testArtifact2);
		
		this.artifactDao.deleteEntity(this.testArtifact1);
		
		List<Artifact> artifacts = this.artifactDao.getArtifactsByProject(this.testProject);
		Assert.assertEquals(1, artifacts.size());
		Assert.assertFalse(artifacts.contains(this.testArtifact1));
		Assert.assertTrue(artifacts.contains(this.testArtifact2));
	
		
		this.artifactDao.deleteEntity(this.testArtifact2);
		
		artifacts = this.artifactDao.getArtifactsByProject(this.testProject);
		Assert.assertEquals(0, artifacts.size());
		Assert.assertFalse(artifacts.contains(this.testArtifact1));
		Assert.assertFalse(artifacts.contains(this.testArtifact2));
	}
	
	@Test
	public void testUpdate()
	{
		this.artifactDao.create(this.testArtifact1);
		this.testArtifact1.setName(ArtifactData2.name);
		this.testArtifact1.setDescription(ArtifactData2.description);
		this.testArtifact1.setRevision(ArtifactData2.revision);
		this.testArtifact1.setLink(ArtifactData2.link);
		
		this.artifactDao.update(this.testArtifact1);
		
		Artifact result = this.artifactDao.fetch(this.testArtifact1.getId());
		Assert.assertEquals(ArtifactData2.name, result.getName());
		Assert.assertEquals(ArtifactData2.description, result.getDescription());
		Assert.assertEquals(ArtifactData2.revision, result.getRevision());
		Assert.assertEquals(ArtifactData2.link, result.getLink());
	}	
	
}
