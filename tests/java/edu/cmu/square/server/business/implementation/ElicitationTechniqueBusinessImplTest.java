package edu.cmu.square.server.business.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtTechnique;
import edu.cmu.square.client.model.GwtTechniqueEvaluationValue;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.interfaces.ManageProjectBusiness;
import edu.cmu.square.server.business.step.interfaces.ElicitationTechniqueBusiness;
import edu.cmu.square.server.dao.implementation.HbnTechniqueDao;
import edu.cmu.square.server.dao.interfaces.TechniqueDao;
import edu.cmu.square.server.dao.interfaces.TechniqueEvaluationDao;
import edu.cmu.square.server.dao.interfaces.UserDao;
import edu.cmu.square.server.dao.model.EvaluationCriteria;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Role;
import edu.cmu.square.server.dao.model.Technique;
import edu.cmu.square.server.dao.model.User;

public class ElicitationTechniqueBusinessImplTest extends AbstractSpringBase
{
	@Resource
	private ElicitationTechniqueBusiness elicitationTechniqueBusiness;
	@Resource
	private ManageProjectBusiness manageProjectBusiness;
	@Resource
	private TechniqueDao techniqueDao;
	@Resource
	private UserDao userDao;
	@Resource
	private TechniqueEvaluationDao techniqueEvaluationDao;

	private GwtProject testProject;
	private GwtTechnique testTechnique1;
	private GwtEvaluation testEvaluation1;
	private GwtTechnique testTechnique2;
	private GwtEvaluation testEvaluation2;
	private User testUser;

	private static class Data1
	{
		static public String name = "ARM1";
		static public String description = "The best requirements technique ever!";
		static public boolean type = true;
	}
	private static class Data2
	{
		static public String name = "ARM2";
		static public String description = "The best requirements technique ever111!";
		static public boolean type = true;
	}

	@Before
	public void setUp()
	{
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = ((Project) testMap.get("project")).createGwtProject();

		Project project = (Project) testMap.get("project");
		User user = (User) testMap.get("user");
		testUser = (User) testMap.get("user");
		Role role = (Role) testMap.get("role");
		userDao.addUserToProject(user, project, role);
		elicitationTechniqueBusiness.setProjectName(project.getName());
		elicitationTechniqueBusiness.setUserName(user.getUserName());

		testTechnique1 = new GwtTechnique();
		testTechnique1.setTitle(Data1.name);
		testTechnique1.setDescription(Data1.description);
		if (Data1.type)
		{
			testTechnique1.setToSecurity();
		}

		testEvaluation1 = new GwtEvaluation();
		testEvaluation1.setTitle(Data1.name);
		testEvaluation1.setDescription(Data1.description);

		testTechnique2 = new GwtTechnique();
		testTechnique2.setTitle(Data2.name);
		testTechnique2.setDescription(Data2.description);
		if (Data2.type)
		{
			testTechnique2.setToSecurity();
		}

		testEvaluation2 = new GwtEvaluation();
		testEvaluation2.setTitle(Data2.name);
		testEvaluation2.setDescription(Data2.description);

	}

	@After
	public void tearDown()
	{
		this.testProject = null;
		this.testTechnique1 = null;
	}

	@Test
	public void testAddTechnique()
	{
		try
		{

			elicitationTechniqueBusiness.addTechnique(testProject, testTechnique1);

			List<Technique> techniques = techniqueDao.getTechniquesByProject(new Project(testProject));

			Assert.assertEquals(1, techniques.size());
			Assert.assertEquals(Data1.name, techniques.get(0).getName());
			Assert.assertEquals(Data1.description, techniques.get(0).getDescription());
			Assert.assertEquals(Data1.type, techniques.get(0).getType());
		}
		catch (SquareException e)
		{

			e.printStackTrace();
			fail("Exception in test " + e.getMessage());
		}
	}
	@Test
	public void testDeleteTechnique()
	{
		try
		{
			elicitationTechniqueBusiness.addTechnique(testProject, testTechnique1);
			((HbnTechniqueDao) techniqueDao).getSession().flush();
			((HbnTechniqueDao) techniqueDao).getSession().evict(testTechnique1);
			elicitationTechniqueBusiness.removeTechnique(testTechnique1, testProject.getId());
			List<Technique> techniques = techniqueDao.getTechniquesByProject(new Project(testProject));
			Assert.assertEquals(0, techniques.size());

		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Exception in test " + e.getMessage());
		}

	}

	@Test
	public void testUpdateTechnique()
	{
		try
		{
			elicitationTechniqueBusiness.addTechnique(testProject, testTechnique1);
			testTechnique1.setTitle("my New Title");
			elicitationTechniqueBusiness.updateTechnique(testTechnique1, testProject);
			List<Technique> techniques = techniqueDao.getTechniquesByProject(new Project(testProject));

			Assert.assertEquals(1, techniques.size());
			Assert.assertEquals(testTechnique1.getTitle(), techniques.get(0).getName());
			Assert.assertEquals(testTechnique1.getDescription(), techniques.get(0).getDescription());
			Assert.assertEquals(testTechnique1.isSecurityTechnique(), techniques.get(0).getType());

		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Exception in test " + e.getMessage());
		}
	}

	// /////////////////////////////////Evaluation
	// Tests/////////////////////////////////////

	@Test
	public void testAddEvaluation()
	{
		try
		{

			elicitationTechniqueBusiness.addEvaluation(testProject, testEvaluation1);

			List<EvaluationCriteria> evaluations = techniqueEvaluationDao.getEvaluationsByProject(new Project(testProject));

			Assert.assertEquals(1, evaluations.size());
			Assert.assertEquals(Data1.name, evaluations.get(0).getName());
			Assert.assertEquals(Data1.description, evaluations.get(0).getDescription());
		}
		catch (SquareException e)
		{

			e.printStackTrace();
			fail("Exception in test " + e.getMessage());
		}
	}

	@Test
	public void testDeleteEvaluation()
	{
		try
		{
			elicitationTechniqueBusiness.addEvaluation(testProject, testEvaluation1);
			List<EvaluationCriteria> evaluations = techniqueEvaluationDao.getEvaluationsByProject(new Project(testProject));

			Assert.assertEquals(1, evaluations.size());

			((HbnTechniqueDao) techniqueDao).getSession().flush();
			((HbnTechniqueDao) techniqueDao).getSession().evict(testTechnique1);

			elicitationTechniqueBusiness.removeEvaluation(testEvaluation1);

			List<EvaluationCriteria> evaluations1 = techniqueEvaluationDao.getEvaluationsByProject(new Project(testProject));

			Assert.assertEquals(0, evaluations1.size());

		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Exception in test " + e.getMessage());
		}

	}

	@Test
	public void testUpdateEvaluation()
	{
		try
		{
			elicitationTechniqueBusiness.addEvaluation(testProject, testEvaluation1);
			testEvaluation1.setTitle("my New Title");
			List<EvaluationCriteria> evaluations = techniqueEvaluationDao.getEvaluationsByProject(new Project(testProject));

			Assert.assertEquals(1, evaluations.size());
			Assert.assertNotSame(testEvaluation1.getTitle(), evaluations.get(0).getName());

			elicitationTechniqueBusiness.updateEvaluation(testEvaluation1, testProject);

			List<EvaluationCriteria> evaluations1 = techniqueEvaluationDao.getEvaluationsByProject(new Project(testProject));

			Assert.assertEquals(1, evaluations.size());
			Assert.assertEquals(testEvaluation1.getTitle(), evaluations1.get(0).getName());

		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Exception in test " + e.getMessage());
		}
	}
	@Test
	public void testSetValues() throws SquareException
	{

		elicitationTechniqueBusiness.addTechnique(testProject, testTechnique1);
		elicitationTechniqueBusiness.addEvaluation(testProject, testEvaluation1);

		int projectID = testProject.getId();
		int techniqueID = testTechnique1.getTechniqueId();
		int evaluationID = testEvaluation1.getEvaluationId();

		elicitationTechniqueBusiness.setRateValue(projectID, techniqueID, evaluationID, 3);
		int value = elicitationTechniqueBusiness.getRateValue(projectID, techniqueID, evaluationID);
		assertTrue(value == 3);

		elicitationTechniqueBusiness.setRateValue(projectID, techniqueID, evaluationID, 2);
		value = elicitationTechniqueBusiness.getRateValue(projectID, techniqueID, evaluationID);
		assertTrue(value == 2);

	}
	public void setValues(GwtProject p, GwtTechnique t, GwtEvaluation e, int value) throws SquareException
	{
		int projectID = p.getId();
		int techniqueID = t.getTechniqueId();
		int evaluationID = e.getEvaluationId();

		elicitationTechniqueBusiness.setRateValue(projectID, techniqueID, evaluationID, value);
	}
	@Test
	public void testgetAllValues() throws SquareException
	{

		elicitationTechniqueBusiness.addTechnique(testProject, testTechnique1);
		elicitationTechniqueBusiness.addEvaluation(testProject, testEvaluation1);
		elicitationTechniqueBusiness.addTechnique(testProject, testTechnique2);
		elicitationTechniqueBusiness.addEvaluation(testProject, testEvaluation2);

		int projectID = testProject.getId();

		setValues(testProject, testTechnique1, testEvaluation1, 1);
		setValues(testProject, testTechnique1, testEvaluation2, 2);
		setValues(testProject, testTechnique2, testEvaluation1, 3);
		setValues(testProject, testTechnique2, testEvaluation2, 2);

		List<GwtTechniqueEvaluationValue> list = elicitationTechniqueBusiness.getRateValues(projectID);

		assertTrue(list.size() == 4);

		// elicitationTechniqueBusiness.setTechniqueEvaluationValue(testProject.getId(),
		// , evaluationCriteriaID, value)
	}

	@Test
	public void testUpdateProjectTechnique() throws SquareException
	{
		elicitationTechniqueBusiness.addTechnique(testProject, testTechnique1);
		manageProjectBusiness.setUserName(testUser.getUserName());
		manageProjectBusiness.setProjectName(testProject.getName());
		manageProjectBusiness.setTechniqueToProject(testProject.getId(), testTechnique1.getTechniqueId(), "hello");

		GwtProject project = manageProjectBusiness.getProject(testProject.getId());
		assertTrue(testTechnique1.getTechniqueId() == project.getSecurityTechniqueID());

	}

}
