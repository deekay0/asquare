package edu.cmu.square.server.remoteService.implementations;

import static org.junit.Assert.fail;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtTechnique;
import edu.cmu.square.client.remoteService.step.interfaces.ElicitationTechniqueService;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.TechniqueDao;
import edu.cmu.square.server.dao.model.Project;

public class ElicitationTechniqueServiceImplTest extends AbstractSpringBase {

	
	@Resource
	@SuppressWarnings("unused")
	private ElicitationTechniqueService elicitationTechniqueService;
	@Resource
	@SuppressWarnings("unused")
	private TechniqueDao techniqueDao;
	@SuppressWarnings("unused")
	private GwtProject testProject;
	private GwtTechnique testTechnique1;

	private static class Data1 {
		static public String name = "ARM";
		static public String description = "The best requirements technique ever!";
		static public boolean type = true;
	}

	@Before
	public void setUp() {
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = ((Project) testMap.get("project")).createGwtProject();

		testTechnique1 = new GwtTechnique();
		testTechnique1.setTitle(Data1.name);
		testTechnique1.setDescription(Data1.description);
		if (Data1.type) {
			testTechnique1.setToSecurity();
		}

	}

	@After
	public void tearDown() {
		this.testProject = null;
		this.testTechnique1 = null;
	}

	@Test
	public void testAddTechnique() {
		try {

//			elicitationTechniqueService.addTechnique(testProject,
//					testTechnique1);
//
//			List<Technique> techniques = techniqueDao
//					.getTechniquesByProject(new Project(testProject));
//
//			Assert.assertEquals(1, techniques.size());
//			Assert.assertEquals(Data1.name, techniques.get(0).getName());
//			Assert.assertEquals(Data1.description, techniques.get(0)
//					.getDescription());
//			Assert.assertEquals(Data1.type, techniques.get(0).getType());
		} catch (Exception e) {

			e.printStackTrace();
			fail("Exception in test " + e.getMessage());
		}
	}

}
