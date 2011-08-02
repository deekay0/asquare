package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.TechniqueDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Technique;


@Repository
@SuppressWarnings("unchecked")
public class HbnTechniqueDao extends HbnAbstractDao<Technique, Integer> implements TechniqueDao
{
	
	


	
	
	public List<Technique> getTechniquesByProject(Project currentProject)
	{
		Session session = super.getSession();
		String query = "Select p.techniques from Project p where p=:myProject";
		Query q = session.createQuery(query);
		q.setParameter("myProject", currentProject);
		return q.list();
	}

	
	public void addTechniqueToProject(Project project, Technique technique)
	{
		technique.setProject(project);
		super.create(technique);
	}

	
	public List<Technique> getTechniquesByNameAndProject(String name, Integer projectId) {
		Session session = getSession();
		String query = "Select t from Technique t where t.name=:techniqueName and t.project.id=:projectId";
		Query q = session.createQuery(query);
		q.setParameter("techniqueName", name);
		q.setParameter("projectId", projectId);
		
		return q.list();
	}
	
	

}
