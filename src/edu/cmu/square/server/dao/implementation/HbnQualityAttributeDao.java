/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.QualityAttributeDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageAttributeRating;
import edu.cmu.square.server.dao.model.QualityAttribute;

/**
 * @author deekay
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class HbnQualityAttributeDao extends HbnAbstractDao<QualityAttribute, Integer> implements QualityAttributeDao 
{
	
	public List<QualityAttribute> getProjectAttributes(Project project)
	{
		if(project == null)
			return null;
		
		List<QualityAttribute> attributes = null;

		String query = "select distinct s from QualityAttribute s, ProjectPackageAttributeRating ps where ps.id.attributeId=s.id and ps.id.projectId=38";
		
		Query q = getSession().createQuery(query);
		//q.setParameter("project", project);
		
		attributes = (List<QualityAttribute>)q.list();
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
	
	public List<ProjectPackageAttributeRating> getAll2()
	{
		List<ProjectPackageAttributeRating> attributes = null;
		
		String query = "select s from ProjectPackageAttributeRating s";
		
		Query q = getSession().createQuery(query);
		attributes = (List<ProjectPackageAttributeRating>)q.list();
		return attributes;
	}
	
}
