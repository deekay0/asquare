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
	List<SoftwarePackage> getProjectPackages(Project project);
	List<SoftwarePackage> getAll();
}
