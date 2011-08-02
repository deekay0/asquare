/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.SoftwarePackageDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.QualityAttribute;
import edu.cmu.square.server.dao.model.SoftwarePackage;

/**
 * @author deekay
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class HbnSoftwarePackageDao extends HbnAbstractDao<SoftwarePackage, Integer> implements SoftwarePackageDao 
{
	
	public List<SoftwarePackage> getSoftwarePackagesByProject(Project project)
	{
		if(project == null)
			return null;
		
		List<SoftwarePackage> packages = null;
		
		String query = "select distinct s from SoftwarePackage s, ProjectPackageAttributeRating ps where ps.softwarePackage.id=s.id and ps.project.id =:project";
		
		Query q = getSession().createQuery(query);
		q.setParameter("project", project.getId());
		packages = (List<SoftwarePackage>)q.list();
		
		System.out.println("HBN: software packages: "+packages.size());
		return packages;
	}
	

	public List<SoftwarePackage> getSoftwarePackagesByNameAndProject(String name, Integer projectId) {
		Session session = getSession();
		String query = "Select s from SoftwarePackage s, ProjectPackageAttributeRating ps where ps.softwarePackage.id = s.id and ps.project.id=:projectId and s.name=:evaluationName";
		Query q = session.createQuery(query);
		q.setParameter("evaluationName", name);
		q.setParameter("projectId", projectId);
		System.out.println("HBN2: software packages: "+q.list().size());
		return q.list();
		
	}
	
	public void addSoftwarePackageToProject(Project project, SoftwarePackage softwarePackage)
	{
		List<QualityAttribute> QAs = new HbnQualityAttributeDao().getAllForProject(project);
		String query = null;
		for(int i=0; i<QAs.size(); ++i)
		{	
			
//			query = "Replace into software_package sp values(:id, :name, :desc, curdate(), curdate())";
//			Query q = getSession().createSQLQuery(query);
//			q.setParameter("id", 0);
//			q.setParameter("name", softwarePackage.getName());
//			q.setParameter("desc", softwarePackage.getDescription());
//			q.executeUpdate();
//			
			query = "Replace into project_package_attribute_rating ps values(:projectId, :packageId, :attributeId, 1)";
			Query q = getSession().createSQLQuery(query);
			q.setParameter("projectId", project.getId());
			q.setParameter("packageId", softwarePackage.getId());
			q.setParameter("attributeId", QAs.get(i).getId());

			q.executeUpdate();
			
			
		}
	}
	
	public List<SoftwarePackage> getAll()
	{
		List<SoftwarePackage> packages = null;
		
		String query = "select s from SoftwarePackage s";
		
		Query q = getSession().createQuery(query);
		packages = (List<SoftwarePackage>)q.list();
		return packages;
	}
}
