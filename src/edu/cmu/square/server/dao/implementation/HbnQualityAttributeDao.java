/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtRating;
import edu.cmu.square.server.dao.interfaces.QualityAttributeDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageAttributeRating;
import edu.cmu.square.server.dao.model.QualityAttribute;
import edu.cmu.square.server.dao.model.SoftwarePackage;

/**
 * @author deekay
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class HbnQualityAttributeDao extends HbnAbstractDao<QualityAttribute, Integer> implements QualityAttributeDao 
{
	
	public List<QualityAttribute> getQualityAttributesByProject(Project project)
	{
		if(project == null) 
			return null;
		
		List<QualityAttribute> attributes = null;

		String query = "select distinct s from QualityAttribute s, ProjectPackageAttributeRating ps where ps.qualityAttribute.id=s.id and ps.project.id=:project";
		
		Query q = getSession().createQuery(query);
		q.setParameter("project", project.getId());
		
		attributes = (List<QualityAttribute>)q.list();
		
		System.out.println("HBN: quality attributes: "+attributes.size());
		
		return attributes;
	}
	
	public List<QualityAttribute> getAll()
	{
		List<QualityAttribute> attributes = null;
		
		String query = "select s from QualityAttribute s";
		
		Query q = getSession().createQuery(query);
		attributes = (List<QualityAttribute>)q.list();
		return attributes;
	}
	
	public List<GwtRating> getAllRatings(Project p)
	{
		List<ProjectPackageAttributeRating> lines = null;
		
		String query = "select s from ProjectPackageAttributeRating s where s.project.id=:project";
		
		Query q = getSession().createQuery(query);
		q.setParameter("project", p.getId());
		lines = (List<ProjectPackageAttributeRating>)q.list();
		
		List<GwtRating> result = new ArrayList<GwtRating>();
		GwtRating current = null;
		for(int i=0 ;i<lines.size(); ++i)
		{
			current = new GwtRating();
			current.setAttributeId(lines.get(i).getQualityAttribute().getId());
			current.setPackageId(lines.get(i).getSoftwarePackage().getId());
			current.setValue(lines.get(i).getRating());
			result.add(current);
		}
			
		return result;
	}
	
	public void setRating(int projectID, int packageId, int attributeId, int value)
	{		
		String query = "replace into project_package_attribute_rating values(:projectId,:packageId,:attributeId,:value)";
		
		Query q = getSession().createSQLQuery(query);
		
		q.setParameter("packageId", packageId);
		q.setParameter("projectId", projectID);
		q.setParameter("value", value);
		q.setParameter("attributeId", attributeId);
			
		q.executeUpdate();
	}
	
	public void updateRating(int projectID, int packageId, int attributeId, int value)
	{		
		String query = "replace into project_package_attribute_rating values(:projectId,:packageId,:attributeId,:rating)";
		
		Query q = getSession().createSQLQuery(query);
		
		q.setParameter("packageId", packageId);
		q.setParameter("projectId", projectID);
		q.setParameter("attributeId", attributeId);
		q.setParameter("rating", value);
			
		q.executeUpdate();
	}
	
	public List<QualityAttribute> getQualityAttributesByNameAndProject(String name, Integer projectId) {
		Session session = getSession();
		String query = "Select s from QualityAttribute s, ProjectPackageAttributeRating ps where ps.qualityAttribute.id = s.id and ps.project.id=:projectId and s.name=:evaluationName";
		Query q = session.createQuery(query);
		q.setParameter("evaluationName", name);
		q.setParameter("projectId", projectId);
		
		System.out.println("HBN2: quality attributes: "+q.list().size());
		
		return q.list();	
	}
	
	public int getRating(int projectID, int packageId, int attributeId)
	{
		int rating = -1;
		String query = "Select s from ProjectPackageAttributeRating s where s.project.id=:projectId and s.softwarePackage.id=:packageId and s.qualityAttribute.id=:attributeId)";
		
		Query q = getSession().createQuery(query);
		
		q.setParameter("packageId", packageId);
		q.setParameter("projectId", projectID);
		q.setParameter("attributeId", attributeId);
		
		List<ProjectPackageAttributeRating> list = q.list(); 
		if(list.size() == 1)
			rating = list.get(0).getRating();
		
		
		return rating;
	}

	public List<QualityAttribute> getAllForProject(Project project)
	{
		List<QualityAttribute> attributes = null;
		
		String query = "select distinct s from QualityAttribute s, ProjectPackageAttributeRating ps where s.id=ps.qualityAttribute.id and ps.project.id=:projectId";
		
		Query q = getSession().createQuery(query);
		q.setParameter("projectId", project.getId());
		
		attributes = (List<QualityAttribute>)q.list();
		return attributes;
	}
	
}
