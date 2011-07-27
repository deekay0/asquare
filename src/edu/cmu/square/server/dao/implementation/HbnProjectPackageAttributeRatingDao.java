/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.cmu.square.client.model.GwtProjectPackageAttributeRating;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.server.dao.interfaces.ProjectPackageAttributeRatingDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageAttributeRating;
import edu.cmu.square.server.dao.model.QualityAttribute;
import edu.cmu.square.server.dao.model.SoftwarePackage;

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
		System.out.println("project: "+projectID+" package: "+packageID+" attribute: "+requirementID+" rating: "+rating);
		
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
		System.out.println("project: "+projectID+" package: "+packageID+" attribute: "+requirementID+" rating: "+rating);
		
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
	public List<ProjectPackageAttributeRating> getAllRatingsForProjectNoGwt(Project project)
	{
System.out.println("in Hbn, getallRatingforprojectNoGWT");
		List<ProjectPackageAttributeRating> lines = null;
		
		String query = "select s from ProjectPackageAttributeRating s where s.project.id=:project";
		
		Query q = getSession().createQuery(query);
		q.setParameter("project", project.getId());

System.out.println(project.getId());
		
		lines = (List<ProjectPackageAttributeRating>)q.list();

System.out.println(lines);

		List<ProjectPackageAttributeRating> result = new ArrayList<ProjectPackageAttributeRating>();
		ProjectPackageAttributeRating current = null;
		QualityAttribute qa;
		SoftwarePackage sp;
		
		for(int i=0 ;i<lines.size(); ++i)
		{
			
			current = new ProjectPackageAttributeRating();
			current.setIdInt(project.getId(), lines.get(i).getSoftwarePackage().getId(), lines.get(i).getQualityAttribute().getId());
			
			qa = new QualityAttribute();
			qa.setDescription(lines.get(i).getQualityAttribute().getDescription());
			qa.setName(lines.get(i).getQualityAttribute().getName());
			qa.setId(lines.get(i).getQualityAttribute().getId());
			
			sp = new SoftwarePackage();
			sp.setDescription(lines.get(i).getSoftwarePackage().getDescription());
			sp.setName(lines.get(i).getSoftwarePackage().getName());
			sp.setId(lines.get(i).getSoftwarePackage().getId());
			
			
			current.setQualityAttribute(qa);
			current.setSoftwarePackage(sp);
			current.setRating(lines.get(i).getRating());
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
/*
	@Override
	public void create(GwtProjectPackageAttributeRating newPpar)
	{
		if (!persistentClass.isInstance(newPpar))
			throw new IllegalArgumentException(
					"Object class does not match dao type.");
		//handleCreatedModifiedDate(newPpar);
		getSession().save(newPpar);
		
	}
	
	private void handleCreatedModifiedDate(GwtProjectPackageAttributeRating newPpar) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(persistentClass);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();

			for (PropertyDescriptor p : propertyDescriptors) {
				if (p.getName().equals("dateCreated")||p.getName().equals("dateModified")) {
					if (p.getReadMethod().invoke(newPpar)==null) {
						p.getWriteMethod().invoke(newPpar, new Date());
					}
				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}

	}
*/


}
