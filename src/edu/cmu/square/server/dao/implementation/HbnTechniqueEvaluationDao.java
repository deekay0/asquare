package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.TechniqueEvaluationDao;
import edu.cmu.square.server.dao.model.EvaluationCriteria;
import edu.cmu.square.server.dao.model.Project;


@Repository
@SuppressWarnings("unchecked")
public class HbnTechniqueEvaluationDao extends HbnAbstractDao<EvaluationCriteria, Integer> implements TechniqueEvaluationDao
{

	
	public List<EvaluationCriteria> getEvaluationsByProject(Project testProject)
	{
		Session session = getSession();
		String query = "Select te from Project p inner join  p.techniqueEvaluations as te where p=:project";
		Query q = session.createQuery(query);
		q.setParameter("project", testProject);
		
		return q.list();
	}

	
	public List<EvaluationCriteria> getEvaluationsByNameAndProject(String name, Integer projectId) {
		Session session = getSession();
		String query = "Select e from EvaluationCriteria e where e.name=:evaluationName and e.project.id=:projectId";
		Query q = session.createQuery(query);
		q.setParameter("evaluationName", name);
		q.setParameter("projectId", projectId);
		
		return q.list();
		
	}

	
}
