/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.server.dao.interfaces.ProjectPackageRequirementRatingDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageRequirementRating;
import edu.cmu.square.server.dao.model.QualityAttribute;
import edu.cmu.square.server.dao.model.Requirement;
import edu.cmu.square.server.dao.model.SoftwarePackage;

/**
 * @author Nan
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class HbnProjectPackageRequirementRatingDao extends HbnAbstractDao<ProjectPackageRequirementRating, Integer> implements ProjectPackageRequirementRatingDao 
{
	/*
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
	}*/
	
	@Override
	public void setRating(int projectID, int packageID, int requirementID, int rating)
	{
		String query = "replace into project_package_requirement_rating values(:projectId, :packageId, :requirementId,:value)";
		
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
		String query = "Select s from ProjectPackageRequirementRating s where s.project.id=:projectId and s.softwarePackage.id=:packageId and s.requirement.id=:requirementId)";
		
		Query q = getSession().createQuery(query);
		
		q.setParameter("projectId", projectID);
		q.setParameter("packageId", packageID);
		q.setParameter("requirementId", requirementID);
		
		List<ProjectPackageRequirementRating> list = q.list(); 
		if(list.size() == 1)
			rating = list.get(0).getRating();
			
		return rating;
	}

	@Override
	public void updateRating(int projectID, int packageID, int requirementID, int rating)
	{
		String query = "replace into project_package_requirement_rating values(:projectId,:packageId,:requirementId,:rating)";
		
		Query q = getSession().createSQLQuery(query);
		
		q.setParameter("projectId", projectID);
		q.setParameter("packageId", packageID);
		q.setParameter("requirementId", requirementID);
		q.setParameter("rating", rating);
			
		q.executeUpdate();
	}

	@Override
	public List<GwtRequirementRating> getAllRatings(Project project)
	{

		List<ProjectPackageRequirementRating> lines = null;
		
		String query = "select s from ProjectPackageRequirementRating s where s.project.id=:project";
		
		Query q = getSession().createQuery(query);
		q.setParameter("project", project.getId());
		lines = (List<ProjectPackageRequirementRating>)q.list();
		
		List<GwtRequirementRating> result = new ArrayList<GwtRequirementRating>();
		GwtRequirementRating current = null;
		for(int i=0 ;i<lines.size(); ++i)
		{
			current = new GwtRequirementRating();
			current.setRequirementId(lines.get(i).getRequirement().getId());
			current.setPackageId(lines.get(i).getSoftwarePackage().getId());
			current.setValue(lines.get(i).getRating());
			result.add(current);
		}
			
		return result;

	}
	
	@Override
	public List<ProjectPackageRequirementRating> getAllRatingsNoGwt(Project project)
	{

		List<ProjectPackageRequirementRating> lines = null;
		
		String query = "select s from ProjectPackageRequirementRating s where s.project.id=:project";
		
		Query q = getSession().createQuery(query);
		q.setParameter("project", project.getId());
		
		lines = (List<ProjectPackageRequirementRating>)q.list();
		
		List<ProjectPackageRequirementRating> result = new ArrayList<ProjectPackageRequirementRating>();
		ProjectPackageRequirementRating current = null;
		
		SoftwarePackage sp;
		Requirement req;
		
		for(int i=0 ;i<lines.size(); ++i)
		{
			current = new ProjectPackageRequirementRating();
			current.setIdInt(project.getId(), lines.get(i).getSoftwarePackage().getId(), lines.get(i).getRequirement().getId());
			
			req = new Requirement();
			req.setId(lines.get(i).getRequirement().getId());
			req.setTitle((lines.get(i).getRequirement().getTitle()));
			req.setSecurity(lines.get(i).getRequirement().isSecurity());
			req.setPrivacy(lines.get(i).getRequirement().isPrivacy());
			req.setDescription((lines.get(i).getRequirement().getDescription()));
			req.setPriority((lines.get(i).getRequirement().getPriority()));
			req.setStatus((lines.get(i).getRequirement().getStatus()));
			req.setProject(project);
			
			sp = new SoftwarePackage();
			sp.setDescription(lines.get(i).getSoftwarePackage().getDescription());
			sp.setName(lines.get(i).getSoftwarePackage().getName());
			sp.setId(lines.get(i).getSoftwarePackage().getId());
			
			current.setRequirement(req);
			current.setSoftwarePackage(sp);
			current.setRating((lines.get(i).getRating()));
			result.add(current);
		}
			
		return result;

	}


}
