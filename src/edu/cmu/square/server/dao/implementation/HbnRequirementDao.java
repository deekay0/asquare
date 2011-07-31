/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.hibernate.EntityMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.RequirementDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Requirement;

/**
 * @author kaalpurush
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class HbnRequirementDao extends HbnAbstractDao<Requirement, Integer> implements RequirementDao
{
	private static Logger logger = Logger.getLogger(HbnRequirementDao.class);
	
	public List<Requirement> findByCategoryId(Integer categoryFromId)
	{
		String query = "Select r from Requirement r inner join r.categories c where c.id=:categoryFromId";
		Query q = getSession().createQuery(query);
		q.setParameter("categoryFromId", categoryFromId);
		return q.list();
	}

	@Override
	public void updatePriority(Requirement r)
	{
		String query = "Update Requirement r set r.priority=:priority where r.id=:requirementId";
		Query q = getSession().createQuery(query);
		q.setParameter("requirementId", r.getId());
		q.setParameter("priority", r.getPriority());
		q.executeUpdate();
		
	}

	@Override
	public void zeroOutPriorities(Integer projectId)
	{
		String query = "Update Requirement r set r.priority=0 where r.project.id= :projectId)";
		Query q = getSession().createQuery(query);
		q.setParameter("projectId", projectId);
		q.executeUpdate();
		
	}

	@Override
	public List<Element> exportToXML(Integer projectId)
	{
		Session xmlSession = getSession().getSession(EntityMode.DOM4J);
		Query q = xmlSession.createQuery("Select r from Requirement r where r.project.id=:projectId");
		q.setParameter("projectId", projectId);
		return q.list();
	}
	
	
	@Override
	public List<Requirement> getRequirementByProject(Project project)
	{
		Session session = super.getSession();
		String query = "Select p.requirements from Project p where p=:myProject";
		Query q = session.createQuery(query);
		q.setParameter("myProject", project);
		return q.list();
	}
	
	@Override
	public void changeStatusToApproved(Requirement r)
	{
		String query = "Update Requirement r set r.status='Approved' where r.id=:requirementId";
		Query q = getSession().createQuery(query);
		q.setParameter("requirementId", r.getId());
		q.executeUpdate();
		
	}
	
	@Override
	public void changeStatusToRequestRevision(Requirement r)
	{
		String query = "Update Requirement r set r.status='Request revision' where r.id=:requirementId";
		Query q = getSession().createQuery(query);
		q.setParameter("requirementId", r.getId());
		q.executeUpdate();
	}

}
