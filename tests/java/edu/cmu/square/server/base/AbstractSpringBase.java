package edu.cmu.square.server.base;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtInspectionTechnique;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.model.GwtTechnique;
import edu.cmu.square.client.model.GwtTerm;
import edu.cmu.square.server.dao.interfaces.ArtifactDao;
import edu.cmu.square.server.dao.interfaces.AsquareCaseDao;
import edu.cmu.square.server.dao.interfaces.CategoryDao;
import edu.cmu.square.server.dao.interfaces.GoalDao;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.RequirementDao;
import edu.cmu.square.server.dao.interfaces.RiskDao;
import edu.cmu.square.server.dao.interfaces.RoleDao;
import edu.cmu.square.server.dao.interfaces.UserDao;
import edu.cmu.square.server.dao.model.Artifact;
import edu.cmu.square.server.dao.model.AsquareCase;
import edu.cmu.square.server.dao.model.Category;
import edu.cmu.square.server.dao.model.Goal;
import edu.cmu.square.server.dao.model.GoalType;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Requirement;
import edu.cmu.square.server.dao.model.Risk;
import edu.cmu.square.server.dao.model.Role;
import edu.cmu.square.server.dao.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext*.xml"})
@Transactional
public class AbstractSpringBase {
	
	
	protected AsquareCase asquareCase;
	
	protected Project testProject;
	//protected Project testProject1;
	
	
	protected User testUser;
	
	protected User testUser1;
	
	protected Role testRole;
	
	protected Requirement testRequirement;
	//protected Requirement testRequirement1;
	protected Requirement testRequirement2;
	protected Category testCategory1;
	protected Category testCategory2;
	
	@Resource
	protected AsquareCaseDao caseDao;
	@Resource
	protected GoalDao goalDao;
	@Resource
	protected RequirementDao requirementDao;
	@Resource
	protected CategoryDao categoryDao;
	
	@Resource
	protected RiskDao riskDao;
	@Resource
	protected ProjectDao projectDao;
	@Resource
	protected RoleDao roleDao;
	@Resource
	protected UserDao userDao;
	
	
	@Resource
	protected ArtifactDao artifactDao;
	
	private Random r = new Random();
	/**
	 * Creates a dummy project, user, (with a  role) for testing purposes
	 * @return a Map that contains the "project"
	 */
	public Map<String,Object> createUserProjectforRole() {
		Map<String, Object> map = new Hashtable<String, Object>();
		
		//create user
		testUser = new User();
		testUser.setDepartment("Computer Science");
		testUser.setEmail("loomi@blah.com");
		testUser.setFullName("Loomi Liao22");
		testUser.setLocation("Taipei");
		testUser.setOrganization("Search Engines");
		testUser.setPassword("password");
		testUser.setUserName("coconut");
		testUser.setPhone("1231418");
		testUser.setisAdmin(true);
		userDao.create(testUser);
		
		map.put("user", testUser);
		
		
		asquareCase = caseDao.fetch(1);
		map.put("asquareCase", asquareCase);
		
		
		//create project
		testProject = new Project();
		testProject.setName("TestManageProject" + r.nextDouble());
		testProject.setLite(false);
		testProject.setPrivacy(false);
		testProject.setSecurity(true);
		testProject.setPrivacyTechniqueRationale("None");
		testProject.setSecurityTechniqueRationale("None");
		testProject.setLeadRequirementEngineer(testUser);
		testProject.setCases(asquareCase);
	
		
		projectDao.create(testProject);
		map.put("project", testProject);
		
		
		
		testUser1 = new User();
		testUser1.setDepartment("Computer Science");
		testUser1.setEmail("loomi2@blah.com");
		testUser1.setFullName("Loomi Liao22");
		testUser1.setLocation("Taipei");
		testUser1.setOrganization("Search Engines");
		testUser1.setPassword("password");
		testUser1.setUserName("coconut1");
		testUser1.setPhone("1231418");
		userDao.create(testUser1);
		map.put("user1", testUser1);
		
		testRole = roleDao.findByName("Lead Requirements Engineer");
		map.put("role", testRole);
		return map;
	}
	
	
	public void createRequirementsWithCategories() {
		
		
		createUserProjectforRole();
		
		Role role = roleDao.findByName("Stakeholder");
		userDao.addUserToProject(testUser, testProject, testRole);
		userDao.addUserToProject(testUser1, testProject, role);
		
		testRequirement = new Requirement();
		testRequirement.setDescription("This is a test requirement");
		testRequirement.setPriority(1);
		testRequirement.setPrivacy(false);
		testRequirement.setSecurity(true);
		testRequirement.setStatus("Pending");
		testRequirement.setProject(testProject);
		//testRequirement.setStatus(status);
		testRequirement.setTitle("Test requirement title");
		
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
		testRequirement.getRisks().add(risk1);
		
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
		testRequirement.getRisks().add(risk2);
		
		Artifact artifact1 = new Artifact();
		artifact1.setRevision("verison1");
		artifact1.setDescription("artifactDesc");
		artifact1.setLink("artifactLink");
		artifact1.setProject(testProject);
		artifact1.setName("Bite me!");
		artifactDao.create(artifact1);
		testRequirement.getArtifacts().add(artifact1);
		
		Artifact artifact2 = new Artifact();
		artifact2.setRevision("verison1");
		artifact2.setDescription("artifactDesc");
		artifact2.setLink("artifactLink");
		artifact2.setProject(testProject);
		artifact2.setName("LOLZ!");
		artifactDao.create(artifact2);
		testRequirement.getArtifacts().add(artifact2);
		Goal subGoal = new Goal();
		
		subGoal.setDescription("this is a test subgoal");
		subGoal.setGoalType(new GoalType(2));
		subGoal.setPriority(1);
		subGoal.setProject(testProject);
		
		goalDao.create(subGoal);
		testRequirement.getGoals().add(subGoal);

		//add a category
		testCategory1 = new Category();
		testCategory1.setLabel("Test Category");
		testCategory1.setProject(testProject);
		
		categoryDao.create(testCategory1);
		
		testRequirement.getCategories().add(testCategory1);
		requirementDao.create(testRequirement);

		
		//second requirement
		testRequirement2 = new Requirement();
		testRequirement2.setDescription("This is a test requirement");
		testRequirement2.setPriority(1);
		testRequirement2.setPrivacy(false);
		testRequirement2.setSecurity(true);
		testRequirement2.setStatus("Pending");
		testRequirement2.setProject(testProject);
		testRequirement2.setTitle("Test requirement title");
		
		Risk risk12 = new Risk();
		risk12.setCurrentMeasures("currentMeasures");
		risk12.setImpact(1);
		risk12.setLikelihood(2);
		risk12.setPlannedMeasures("plannedMeasures");
		risk12.setRiskTitle("risky risk title");
		risk12.setThreatAction("my threat action");
		risk12.setThreatSource("my threat source");
		risk12.setVulnerability("this is vulnerable");
		risk12.setProject(testProject);
		riskDao.create(risk12);
		testRequirement2.getRisks().add(risk12);
		
		Risk risk22 = new Risk();
		risk22.setCurrentMeasures("currentMeasures2");
		risk22.setImpact(12);
		risk22.setLikelihood(22);
		risk22.setPlannedMeasures("plannedMeasures2");
		risk22.setRiskTitle("risky risk title2");
		risk22.setThreatAction("my threat action2");
		risk22.setThreatSource("my threat source2");
		risk22.setVulnerability("this is vulnerable2");
		risk22.setProject(testProject);
		riskDao.create(risk22);
		testRequirement2.getRisks().add(risk22);
		
		
		Goal subGoal2 = new Goal();
		subGoal2.setDescription("this is a test subgoal");
		subGoal2.setGoalType(new GoalType(2));
		subGoal2.setPriority(1);
		subGoal2.setProject(testProject);
		goalDao.create(subGoal2);
		
		testRequirement2.getGoals().add(subGoal2);

		Artifact artifact3 = new Artifact();
		artifact3.setRevision("verison1");
		artifact3.setDescription("artifactDesc");
		artifact3.setLink("artifactLink");
		artifact3.setProject(testProject);
		artifact3.setName("Que Horrible!");
		artifactDao.create(artifact3);
		testRequirement2.getArtifacts().add(artifact3);
		
		
		//add a category
		testCategory2 = new Category();
		testCategory2.setLabel("Test Category");
		testCategory2.setProject(testProject);
		
		categoryDao.create(testCategory2);
		
		testRequirement2.getCategories().add(testCategory2);
		requirementDao.create(testRequirement2);
		
		
	}

	public Project getTestProject()
	{
		return testProject;
	}

	public void setTestProject(Project testProject)
	{
		this.testProject = testProject;
	}

	public User getTestUser()
	{
		return testUser;
	}

	public void setTestUser(User testUser)
	{
		this.testUser = testUser;
	}

	public Role getTestRole()
	{
		return testRole;
	}

	public void setTestRole(Role testRole)
	{
		this.testRole = testRole;
	}
	
	
	
	
	public List<GwtTerm> createDefaultTerms()
	{
		List<GwtTerm> terms = new ArrayList<GwtTerm>();
		
		for (int i = 0; i < 5; i++)
		{
			GwtTerm term = new GwtTerm();
			term.setDefinition("definition " + i);
			term.setTerm("term " + i);
			
			terms.add(term);
		}
		
		return terms;
	}
	
	public List<GwtRequirement> createDefaultRequirements()
	{
		List<GwtRequirement> reqs = new ArrayList<GwtRequirement>();
		
		for (int i = 0; i < 5; i++)
		{
			GwtRequirement req = new GwtRequirement();
			req.setDescription("req test description " + i);
			req.setTitle("req test title " + i);
			
			reqs.add(req);
		}
		
		return reqs;
	}
	
	public List<GwtTechnique> createDefaultTechnique()
	{
		List<GwtTechnique> techniques = new ArrayList<GwtTechnique>();
		
		for (int i = 0; i < 5; i++)
		{
			GwtTechnique technique = new GwtTechnique();
			technique.setDescription("description " + i);
			technique.setTitle("technique " + i);
			
			techniques.add(technique);
		}
		
		return techniques;
	}
	
	public List<GwtEvaluation> createDefaultEvaluation()
	{
		List<GwtEvaluation> evaluations = new ArrayList<GwtEvaluation>();
		
		for (int i = 0; i < 5; i++)
		{
			GwtEvaluation evaluation = new GwtEvaluation();
			evaluation.setDescription("Description " + i);
			evaluation.setTitle("evaluation " + i);
			
			evaluations.add(evaluation);
		}
		
		return evaluations;
	}
	
	public List<GwtInspectionTechnique> createDefaultInspections()
	{
		List<GwtInspectionTechnique> inspections = new ArrayList<GwtInspectionTechnique>();
		
		for (int i = 0; i < 5; i++)
		{
			GwtInspectionTechnique inspection = new GwtInspectionTechnique();
			inspection.setDescription("Description " + i);
			inspection.setTitle("inspection " + i);
			
			inspections.add(inspection);
		}
		
		return inspections;
	}
	
	
}
