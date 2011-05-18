package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.TechniqueDao;
import edu.cmu.square.server.dao.interfaces.TechniqueEvaluationCriteriaDao;
import edu.cmu.square.server.dao.interfaces.TechniqueEvaluationDao;
import edu.cmu.square.server.dao.model.EvaluationCriteria;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Technique;
import edu.cmu.square.server.dao.model.TechniqueEvaluationCriteria;
import edu.cmu.square.server.dao.model.TechniqueTechniqueEvaluationId;

public class HbnTechniqueEvaluationCriteriaDaoTest extends AbstractSpringBase {
	@Resource
	private TechniqueEvaluationCriteriaDao techniqueEvaluationCriteriaDao;
	
	@Resource
	private TechniqueDao techniqueDao;
	
	@Resource
	private TechniqueEvaluationDao techniqueEvaluationDao;
	
	
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
	
	Project testProject;
	EvaluationCriteria evaluationTech1;
	EvaluationCriteria evaluationTech2;
	Technique testTechnique1;
	Technique testTechnique2;
	
	
	@Before
	public void setUp()
	{
		
		Map<String, Object> map = super.createUserProjectforRole();
		testProject = (Project)map.get("project");
		
		evaluationTech1 = new EvaluationCriteria();
		evaluationTech1.setName("Great new technique1");
		evaluationTech1.setDescription("Great description1");
		evaluationTech1.setProject(testProject);
		techniqueEvaluationDao.create(evaluationTech1);
		
		evaluationTech2 = new EvaluationCriteria();
		evaluationTech2.setName("Great new technique2");
		evaluationTech2.setDescription("Great description2");
		evaluationTech2.setProject(testProject);
		techniqueEvaluationDao.create(evaluationTech2);
		
		testTechnique1 = new Technique();
		testTechnique1.setName(Data1.name);
		testTechnique1.setDescription(Data1.description);
		testTechnique1.setType(Data1.type);
		testTechnique1.setProject(testProject);
		techniqueDao.create(testTechnique1);
		
		 testTechnique2 = new Technique();
		testTechnique2.setName(Data2.name);
		testTechnique2.setDescription(Data2.description);
		testTechnique2.setType(Data2.type);
		testTechnique2.setProject(testProject);
		techniqueDao.create(testTechnique2);
	}
	
	@Test
	public void testSetRates() {
	
		// do the test to make sure you only get the right technique from the right project
		List<Technique> techniques1 = this.techniqueDao.getTechniquesByProject(testProject);
		Assert.assertEquals(2, techniques1.size());

		
		List<EvaluationCriteria> evaluations = techniqueEvaluationDao.getEvaluationsByProject(testProject);
		assertTrue(evaluations.size()==2);
		
		TechniqueEvaluationCriteria value1 =setValuesTechniqueValues(this.testTechnique1,this.evaluationTech1,1);
		TechniqueEvaluationCriteria value2 =setValuesTechniqueValues(this.testTechnique1,this.evaluationTech2,2);
		TechniqueEvaluationCriteria value3 =setValuesTechniqueValues(this.testTechnique2,this.evaluationTech1,3);
		TechniqueEvaluationCriteria value4 =setValuesTechniqueValues(this.testTechnique2,this.evaluationTech2,1);
		
		techniqueEvaluationCriteriaDao.create(value1);
		techniqueEvaluationCriteriaDao.create(value2);
		techniqueEvaluationCriteriaDao.create(value3);
		techniqueEvaluationCriteriaDao.create(value4);
		
	    TechniqueEvaluationCriteria tecOut =techniqueEvaluationCriteriaDao.fetch(value1.getId());
	    assertTrue(tecOut.getValue()==1);
	     tecOut =techniqueEvaluationCriteriaDao.fetch(value2.getId());
	    assertTrue(tecOut.getValue()==2);
	     tecOut =techniqueEvaluationCriteriaDao.fetch(value3.getId());
	    assertTrue(tecOut.getValue()==3);
	     tecOut =techniqueEvaluationCriteriaDao.fetch(value4.getId());
	    assertTrue(tecOut.getValue()==1);	
	    

	   List<TechniqueEvaluationCriteria> list=  techniqueEvaluationCriteriaDao.getAllValues(testProject);//getAllValues(testProject);
	   assertTrue(list.size()==4);

	}
	
	public TechniqueEvaluationCriteria setValuesTechniqueValues(Technique tecnique, EvaluationCriteria evaluationCriteria, int value)
	{

		TechniqueEvaluationCriteria tec=new TechniqueEvaluationCriteria();
		TechniqueTechniqueEvaluationId id=new TechniqueTechniqueEvaluationId();
		
		id.setTid(tecnique.getId());
		id.setEid(evaluationCriteria.getId());
		id.setPid(testProject.getId());
		tec.setValue(value);
		tec.setId(id);	
		//tec.setTechnique(tecnique);	
		//tec.setTechniqueEvaluation(evaluationCriteria);	
		tec.setId(id);	
		return tec;
	}
	
	
	public TechniqueTechniqueEvaluationId getTechniqueValueID(Technique tecnique, EvaluationCriteria evaluationCriteria)
	{
		TechniqueTechniqueEvaluationId id=new TechniqueTechniqueEvaluationId();
		id.setTid(tecnique.getId());
		id.setEid(evaluationCriteria.getId());
		return id;
	}
	
	
	@Test
	public void testUpdateRates() {
	
		

	}
	
	


}
