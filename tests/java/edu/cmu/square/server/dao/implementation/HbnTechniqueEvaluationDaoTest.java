package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.TechniqueEvaluationDao;
import edu.cmu.square.server.dao.model.EvaluationCriteria;
import edu.cmu.square.server.dao.model.Project;

public class HbnTechniqueEvaluationDaoTest extends AbstractSpringBase {
	@Resource
	private TechniqueEvaluationDao techniqueEvaluationDao;
	
	@Test
	public void testGetEvaluationsByProject() {
		Map<String, Object> map = super.createUserProjectforRole();
		Project p = (Project)map.get("project");
		
		EvaluationCriteria t = new EvaluationCriteria();
		t.setName("Great new technique");
		t.setDescription("Great description");
		t.setProject(p);
		techniqueEvaluationDao.create(t);
		List<EvaluationCriteria> evaluations = techniqueEvaluationDao.getEvaluationsByProject(p);
		assertNotNull(evaluations);
		assertTrue(evaluations.size()==1);
	}

}
