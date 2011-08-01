/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.SoftwarePackage;

/**
 * @author deekay
 *
 */
public interface SoftwarePackageDao extends AbstractDao<SoftwarePackage, Integer> 
{	
	List<SoftwarePackage> getSoftwarePackagesByProject(Project project);
	List<SoftwarePackage> getAll();
	List<SoftwarePackage> getSoftwarePackagesByNameAndProject(String name, Integer projectId);
	void addSoftwarePackageToProject(Project project, SoftwarePackage softwarePackage);
}
