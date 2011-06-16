/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.SoftwarePackageDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.SoftwarePackage;

/**
 * @author deekay
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class HbnSoftwarePackageDao extends HbnAbstractDao<SoftwarePackage, Integer> implements SoftwarePackageDao 
{
	
	public List<SoftwarePackage> getProjectPackages(Project project)
	{
		if(project == null)
			return null;
		
		List<SoftwarePackage> packages = null;
		
		String query = "select s from SoftwarePackage s, ProjectPackageAttributeRating ps where ps.package_id=s.id and ps.project =:project";
		
		Query q = getSession().createQuery(query);
		q.setParameter("project", project);
		packages = (List<SoftwarePackage>)q.list();
		return packages;
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
