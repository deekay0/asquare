package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.InspectionTechniqueDao;
import edu.cmu.square.server.dao.model.InspectionTechnique;
import edu.cmu.square.server.dao.model.Project;


@Repository
@SuppressWarnings("unchecked")
public class HbnInspectionTechniqueDao extends HbnAbstractDao<InspectionTechnique, Integer> implements InspectionTechniqueDao
{
	public List<InspectionTechnique> getInspectionTechniquesByProject(Project currentProject)
	{
		Session session = super.getSession();
		String query = "Select p.inspectionTechniques from Project p where p=:myProject";
		Query q = session.createQuery(query);
		q.setParameter("myProject", currentProject);
		return q.list();
	}

	
	@Override
	public int hasTechnique(String name, Project project)
	{
		Session session = super.getSession();
		String query = "Select t from InspectionTechnique t where t.name=:name and t.project=:project";
		Query q = session.createQuery(query);
		q.setParameter("name", name);
		q.setParameter("project", project);
		
		if (q.list().size() == 1)
		{
			InspectionTechnique technique = (InspectionTechnique) q.list().get(0);
			return technique.getId();
		}
		
		return -1;
	}


	@Override
	public InspectionTechnique getInspectionTechniquesByNameAndProject(String title, int projectId)
	{
		Session session = super.getSession();
		String query = "Select t from InspectionTechnique t where t.name=:name and t.project.id=:projectId";
		Query q = session.createQuery(query);
		q.setParameter("name", title);
		q.setParameter("projectId", projectId);
		InspectionTechnique i = null;
		try {
			i = (InspectionTechnique)q.uniqueResult();
		} 
		catch (Throwable t)
		{
			
		}
		return i;
	}

}
