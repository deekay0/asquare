package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.TechniqueEvaluationCriteriaDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.TechniqueEvaluationCriteria;
import edu.cmu.square.server.dao.model.TechniqueTechniqueEvaluationId;
 

@Repository
@SuppressWarnings("unchecked")
public class HbnTechniqueEvaluationCriteriaDao extends HbnAbstractDao<TechniqueEvaluationCriteria, TechniqueTechniqueEvaluationId> implements TechniqueEvaluationCriteriaDao
{

	public List<TechniqueEvaluationCriteria> getAllValues(Project project) {
		Session session = getSession();
		String query = "Select r from  TechniqueEvaluationCriteria r where r.project = :project";
		Query q = session.createQuery(query);
		q.setParameter("project",project);
		List<TechniqueEvaluationCriteria> pList = q.list();
		return pList;
	}


	

}
