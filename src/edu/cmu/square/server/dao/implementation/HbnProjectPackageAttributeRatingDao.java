/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.cmu.square.client.model.GwtProjectPackageAttributeRating;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.server.dao.interfaces.ProjectPackageAttributeRatingDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageAttributeRating;

/**
 * @author DK
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class HbnProjectPackageAttributeRatingDao extends HbnAbstractDao<ProjectPackageAttributeRating, Integer> implements ProjectPackageAttributeRatingDao 
{
	@Override
	public void setRating(int projectID, int packageID, int requirementID, int rating)
	{
		String query = "replace into project_package_attribute_rating values(:projectId, :packageId, :requirementId,:value)";
		
		Query q = getSession().createSQLQuery(query);
		
		q.setParameter("projectId", projectID);
		q.setParameter("packageId", packageID);
		q.setParameter("requirementId", requirementID);
		q.setParameter("value", rating);
			
		q.executeUpdate();
	}

	@Override
	public int getRating(int projectID, int packageID, int requirementID)
	{
		int rating = -1;
		String query = "Select s from ProjectPackageAttributeRating s where s.project.id=:projectId and s.softwarePackage.id=:packageId and s.requirement.id=:requirementId)";
		
		Query q = getSession().createQuery(query);
		
		q.setParameter("projectId", projectID);
		q.setParameter("packageId", packageID);
		q.setParameter("requirementId", requirementID);
		
		List<ProjectPackageAttributeRating> list = q.list(); 
		if(list.size() == 1)
			rating = list.get(0).getRating();
			
		return rating;
	}

	@Override
	public void updateRating(int projectID, int packageID, int requirementID, int rating)
	{
		String query = "replace into project_package_attribute_rating values(:projectId,:packageId,:requirementId,:rating)";
		
		Query q = getSession().createSQLQuery(query);
		
		q.setParameter("projectId", projectID);
		q.setParameter("packageId", packageID);
		q.setParameter("requirementId", requirementID);
		q.setParameter("rating", rating);
			
		q.executeUpdate();
	}

	@Override
	public List<GwtProjectPackageAttributeRating> getAllRatingsForProject(Project project)
	{

		List<ProjectPackageAttributeRating> lines = null;
		
		String query = "select s from ProjectPackageAttributeRating s where s.project.id=:project";
		
		Query q = getSession().createQuery(query);
		q.setParameter("project", project.getId());
		lines = (List<ProjectPackageAttributeRating>)q.list();
		
		List<GwtProjectPackageAttributeRating> result = new ArrayList<GwtProjectPackageAttributeRating>();
		GwtProjectPackageAttributeRating current = null;
		GwtQualityAttribute qa;
		GwtSoftwarePackage sp;
		
		for(int i=0 ;i<lines.size(); ++i)
		{
			
			current = new GwtProjectPackageAttributeRating();
			qa = new GwtQualityAttribute();
			qa.setDescription(lines.get(i).getQualityAttribute().getDescription());
			qa.setName(lines.get(i).getQualityAttribute().getName());
			qa.setId(lines.get(i).getQualityAttribute().getId());
			
			sp = new GwtSoftwarePackage();
			sp.setDescription(lines.get(i).getSoftwarePackage().getDescription());
			sp.setName(lines.get(i).getSoftwarePackage().getName());
			sp.setId(lines.get(i).getSoftwarePackage().getId());
			current.setAttribute(qa);
			current.setPackage(sp);
			current.setValue(lines.get(i).getRating());
			result.add(current);
		}
			
		return result;

	}
	
	@Override
	public List<GwtProjectPackageAttributeRating> getAllRatings(Project project)
	{

		List<ProjectPackageAttributeRating> lines = null;
		
		String query = "select s from ProjectPackageAttributeRating s";
		
		Query q = getSession().createQuery(query);
		lines = (List<ProjectPackageAttributeRating>)q.list();
		
		List<GwtProjectPackageAttributeRating> result = new ArrayList<GwtProjectPackageAttributeRating>();
		GwtProjectPackageAttributeRating current = null;
		GwtQualityAttribute qa;
		GwtSoftwarePackage sp;
		
		for(int i=0 ;i<lines.size(); ++i)
		{
			current = new GwtProjectPackageAttributeRating();
			qa = new GwtQualityAttribute();
			qa.setDescription(lines.get(i).getQualityAttribute().getDescription());
			qa.setName(lines.get(i).getQualityAttribute().getName());
			qa.setId(lines.get(i).getQualityAttribute().getId());
			
			sp = new GwtSoftwarePackage();
			sp.setDescription(lines.get(i).getSoftwarePackage().getDescription());
			sp.setName(lines.get(i).getSoftwarePackage().getName());
			sp.setId(lines.get(i).getSoftwarePackage().getId());
			current.setAttribute(qa);
			current.setPackage(sp);
			current.setValue(lines.get(i).getRating());
			result.add(current);
		}
			
		return result;

	}


}
