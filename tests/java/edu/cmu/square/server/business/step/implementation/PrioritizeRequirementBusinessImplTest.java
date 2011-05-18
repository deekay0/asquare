package edu.cmu.square.server.business.step.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtPrioritization;
import edu.cmu.square.client.model.GwtPrioritizationStatus;
import edu.cmu.square.client.model.GwtPrioritizedRequirement;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.step.interfaces.ElicitRequirementsBusiness;
import edu.cmu.square.server.business.step.interfaces.PrioritizeRequirementBusiness;
import edu.cmu.square.server.dao.interfaces.UserAhpDao;
import edu.cmu.square.server.dao.model.Artifact;
import edu.cmu.square.server.dao.model.Category;
import edu.cmu.square.server.dao.model.Goal;
import edu.cmu.square.server.dao.model.GoalType;
import edu.cmu.square.server.dao.model.Requirement;
import edu.cmu.square.server.dao.model.Risk;
import edu.cmu.square.server.dao.model.UserAhp;

public class PrioritizeRequirementBusinessImplTest extends AbstractSpringBase
{
	private static Logger logger = Logger.getLogger(PrioritizeRequirementBusinessImplTest.class);
	@Resource
	private PrioritizeRequirementBusiness prioritizeRequirementBusiness;
	@Resource
	private ElicitRequirementsBusiness elicitRequirementBusiness;
	@Resource
	private UserAhpDao userAhpDao;

	@Before
	public void setupTest()
	{
		super.createRequirementsWithCategories();
		elicitRequirementBusiness.setProjectName(testProject.getName());
		elicitRequirementBusiness.setUserName(testUser.getUserName());
		try
		{
			elicitRequirementBusiness.addRequirementToProject(testProject.getId(), testRequirement.createGwtRequirement());
			elicitRequirementBusiness.addRequirementToProject(testProject.getId(), testRequirement2.createGwtRequirement());

			// create another requirement and add to project
			Requirement testRequirement4 = new Requirement();
			testRequirement4.setDescription("This is a test requirement");
			testRequirement4.setPriority(1);
			testRequirement4.setPrivacy(false);
			testRequirement4.setSecurity(true);
			testRequirement4.setProject(testProject);
			testRequirement4.setTitle("Test requirement title");

			Risk risk1 = new Risk();
			risk1.setCurrentMeasures("currentMeasures");
			risk1.setImpact(1);
			risk1.setLikelihood(2);
			risk1.setPlannedMeasures("plannedMeasures");
			risk1.setRiskTitle("risky risk title");
			risk1.setThreatAction("my threat action");
			risk1.setThreatSource("my threat source");
			risk1.setVulnerability("this is vulnerable");
			risk1.setProject(testProject);
			riskDao.create(risk1);
			testRequirement4.getRisks().add(risk1);

			Risk risk2 = new Risk();
			risk2.setCurrentMeasures("currentMeasures2");
			risk2.setImpact(12);
			risk2.setLikelihood(22);
			risk2.setPlannedMeasures("plannedMeasures2");
			risk2.setRiskTitle("risky risk title2");
			risk2.setThreatAction("my threat action2");
			risk2.setThreatSource("my threat source2");
			risk2.setVulnerability("this is vulnerable2");
			risk2.setProject(testProject);
			riskDao.create(risk2);
			testRequirement4.getRisks().add(risk2);

			Artifact artifact1 = new Artifact();
			artifact1.setRevision("verison1");
			artifact1.setDescription("artifactDesc");
			artifact1.setLink("artifactLink");
			artifact1.setName("name1");
			artifact1.setProject(testProject);
			artifactDao.create(artifact1);
			testRequirement4.getArtifacts().add(artifact1);

			Artifact artifact2 = new Artifact();
			artifact2.setRevision("verison1");
			artifact2.setDescription("artifactDesc");
			artifact2.setLink("artifactLink");
			artifact2.setName("name2");
			artifact2.setProject(testProject);
			artifactDao.create(artifact2);
			testRequirement4.getArtifacts().add(artifact2);
			Goal subGoal = new Goal();

			subGoal.setDescription("this is a test subgoal");
			subGoal.setGoalType(new GoalType(2));
			subGoal.setPriority(1);
			subGoal.setProject(testProject);

			goalDao.create(subGoal);
			testRequirement4.getGoals().add(subGoal);

			// add a category
			testCategory1 = new Category();
			testCategory1.setLabel("Test Category");
			testCategory1.setProject(testProject);

			categoryDao.create(testCategory1);

			testRequirement4.getCategories().add(testCategory1);
			elicitRequirementBusiness.addRequirementToProject(testProject.getId(), testRequirement4.createGwtRequirement());

		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prioritizeRequirementBusiness.setProjectName(testProject.getName());
		prioritizeRequirementBusiness.setUserName(testUser.getUserName());

	}

	@Test
	public void testBatchUpdateRequirementsComparisons()
	{

	}

	@Test
	public void testCreateComparisonMatrix()
	{
		GwtUser user = testUser.createGwtUser();

		try
		{

			prioritizeRequirementBusiness.createComparisonMatrix(user, testProject.getId());

			List<UserAhp> prioritizations = userAhpDao.getAllPrioritizationsForUser(testUser.getId());
			logger.info("prioritizations " + prioritizations);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Error in creating comparison " + e);
		}

	}

	@Test
	public void testGetPrioritizedRequirementsForUser()
	{
		GwtUser user = testUser.createGwtUser();
		double cost = 0;
		double value = 0;
		Random r = new Random();
		try
		{

			prioritizeRequirementBusiness.createComparisonMatrix(user, testProject.getId());
			List<GwtPrioritization> requirementComparisons = prioritizeRequirementBusiness.getRequirementsToCompare(user, testProject.getId());
			assertTrue(requirementComparisons.size() == 3);

			for (GwtPrioritization gp : requirementComparisons)
			{

				cost = r.nextInt(5) + .5;
				value = r.nextInt(5) + .5;
				gp.setCostA(cost);
				gp.setCostB(1 / cost);
				gp.setValueA(value);
				gp.setValueB(1 / value);

				prioritizeRequirementBusiness.updateRequirementComparison(gp, user, testProject.getId());
			}
			List<GwtPrioritizedRequirement> requirements = prioritizeRequirementBusiness.getPrioritizedRequirementsForUser(user, testProject.getId());
			assertTrue(requirements.size() == 3);
			logger.info("requirements: " + requirements);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Error in creating comparison " + e);
		}
	}

	@Test
	public void testGetPrioritizedRequirementsList()
	{
		GwtUser user = testUser.createGwtUser();
		GwtUser user1 = testUser1.createGwtUser();
		double cost = 0;
		double value = 0;
		Random r = new Random();
		try
		{
			// prioritize for user
			prioritizeRequirementBusiness.createComparisonMatrix(user, testProject.getId());
			List<GwtPrioritization> requirementComparisons = prioritizeRequirementBusiness.getRequirementsToCompare(user, testProject.getId());
			assertTrue(requirementComparisons.size() == 3);

			for (GwtPrioritization gp : requirementComparisons)
			{

				cost = r.nextInt(5) + .5;
				value = r.nextInt(5) + .5;
				gp.setCostA(cost);
				gp.setCostB(1 / cost);
				gp.setValueA(value);
				gp.setValueB(1 / value);

				prioritizeRequirementBusiness.updateRequirementComparison(gp, user, testProject.getId());
			}

			// prioritize for user1
			prioritizeRequirementBusiness.createComparisonMatrix(user1, testProject.getId());
			List<GwtPrioritization> requirementComparisons1 = prioritizeRequirementBusiness.getRequirementsToCompare(user1, testProject.getId());
			assertTrue(requirementComparisons1.size() == 3);

			for (GwtPrioritization gp : requirementComparisons1)
			{

				cost = r.nextInt(5) + .5;
				value = r.nextInt(5) + .5;
				gp.setCostA(cost);
				gp.setCostB(1 / cost);
				gp.setValueA(value);
				gp.setValueB(1 / value);

				prioritizeRequirementBusiness.updateRequirementComparison(gp, user1, testProject.getId());
			}

			List<GwtPrioritizedRequirement> requirements = prioritizeRequirementBusiness.getPrioritizedRequirements(testProject.getId());
			assertTrue(requirements.size() == 3);
			logger.info("requirements: " + requirements);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Error in creating comparison " + e);
		}
	}

	@Test
	public void testGetPriotizationStatus()
	{
		GwtUser user = testUser.createGwtUser();
		try
		{
			prioritizeRequirementBusiness.createComparisonMatrix(user, testProject.getId());
			GwtPrioritizationStatus status = prioritizeRequirementBusiness.getPriotizationStatus(user, testProject.getId());
			assertTrue(status.getTotalComparisons() == 3);
			assertTrue(status.getPrioritizationsDone() == 0);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Error in creating comparison " + e);
		}
	}

	@Test
	public void testGetRequirementsToCompare()
	{
		GwtUser user = testUser.createGwtUser();
		try
		{
			prioritizeRequirementBusiness.createComparisonMatrix(user, testProject.getId());
			List<GwtPrioritization> requirementComparisons = prioritizeRequirementBusiness.getRequirementsToCompare(user, testProject.getId());
			assertTrue(requirementComparisons.size() == 3);

		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Error in creating comparison " + e);
		}
	}

//	@Test
//	public void testBatchUpdatePriorities()
//	{
//		GwtUser user = testUser.createGwtUser();
//		double cost = 0;
//		double value = 0;
//		Random r = new Random();
//		try
//		{
//			prioritizeRequirementBusiness.createComparisonMatrix(user, testProject.getId());
//			List<GwtPrioritization> requirementComparisons = prioritizeRequirementBusiness.getRequirementsToCompare(user, testProject.getId());
//			assertTrue(requirementComparisons.size() == 3);
//
//			for (GwtPrioritization gp : requirementComparisons)
//			{
//
//				cost = r.nextInt(5) + .5;
//				value = r.nextInt(5) + .5;
//				gp.setCostA(cost);
//				gp.setCostB(1 / cost);
//				gp.setValueA(value);
//				gp.setValueB(1 / value);
//
//				prioritizeRequirementBusiness.updateRequirementComparison(gp, user, testProject.getId());
//			}
//			List<GwtPrioritizedRequirement> requirements = prioritizeRequirementBusiness.getPrioritizedRequirementsForUser(user, testProject.getId());
//			assertTrue(requirements.size() == 3);
//			for (GwtPrioritizedRequirement gpr: requirements )
//			{
//				gpr.getRequirement().setPriority(33);
//			}
//			prioritizeRequirementBusiness.batchUpdatePriorities(requirements);
//			List<GwtPrioritizedRequirement> requirementsFromDb = prioritizeRequirementBusiness.getPrioritizedRequirementsForUser(user, testProject.getId());
//			assertEquals(33,requirementsFromDb.get(0).getRequirement().getPriority());
//		}
//		catch (SquareException e)
//		{
//			e.printStackTrace();
//			fail("Error in creating comparison " + e);
//		}
//	}

	@Test
	public void testResetPrioritizationStatus()
	{

	}
	
	@Test
	public void testGetUserPrioritizations()
	{
		GwtUser user = testUser.createGwtUser();
		GwtUser user1 = testUser1.createGwtUser();
		double cost = 0;
		double value = 0;
		Random r = new Random();
		try
		{
			// prioritize for user
			prioritizeRequirementBusiness.createComparisonMatrix(user, testProject.getId());
			List<GwtPrioritization> requirementComparisons = prioritizeRequirementBusiness.getRequirementsToCompare(user, testProject.getId());
			assertTrue(requirementComparisons.size() == 3);

			for (GwtPrioritization gp : requirementComparisons)
			{

				cost = r.nextInt(5) + .5;
				value = r.nextInt(5) + .5;
				gp.setCostA(cost);
				gp.setCostB(1 / cost);
				gp.setValueA(value);
				gp.setValueB(1 / value);

				prioritizeRequirementBusiness.updateRequirementComparison(gp, user, testProject.getId());
			}

			// prioritize for user1
			prioritizeRequirementBusiness.createComparisonMatrix(user1, testProject.getId());
			List<GwtPrioritization> requirementComparisons1 = prioritizeRequirementBusiness.getRequirementsToCompare(user1, testProject.getId());
			assertTrue(requirementComparisons1.size() == 3);

			for (GwtPrioritization gp : requirementComparisons1)
			{
				cost = r.nextInt(5) + .5;
				value = r.nextInt(5) + .5;
				gp.setCostA(cost);
				gp.setCostB(1 / cost);
				gp.setValueA(value);
				gp.setValueB(1 / value);

				prioritizeRequirementBusiness.updateRequirementComparison(gp, user1, testProject.getId());
			}

			GwtPrioritizationStatus status = prioritizeRequirementBusiness.getUserPrioritizations(testProject.getId());
			assertTrue(status.getCompletedUsers()==2);
			
		}
			catch (SquareException e)
			{
				e.printStackTrace();
				fail("Error in creating comparison " + e);
			}
	}

	@Test
	public void testUpdateRequirementComparison()
	{
		GwtUser user = testUser.createGwtUser();
		try
		{
			prioritizeRequirementBusiness.createComparisonMatrix(user, testProject.getId());
			List<GwtPrioritization> requirementComparisons = prioritizeRequirementBusiness.getRequirementsToCompare(user, testProject.getId());
			assertTrue(requirementComparisons.size() == 3);

			GwtPrioritization gp = requirementComparisons.get(0);
			gp.setCostA(200);
			gp.setValueA(300);

			prioritizeRequirementBusiness.updateRequirementComparison(gp, user, testProject.getId());

			List<GwtPrioritization> requirementComparisons1 = prioritizeRequirementBusiness.getRequirementsToCompare(user, testProject.getId());
			assertTrue(requirementComparisons1.size() == 2);

		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Error in creating comparison " + e);
		}
	}

}
