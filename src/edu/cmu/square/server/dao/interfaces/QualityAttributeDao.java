/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageAttributeRating;
import edu.cmu.square.server.dao.model.QualityAttribute;

/**
 * @author deekay
 *
 */
public interface QualityAttributeDao extends AbstractDao<QualityAttribute, Integer> 
{	
	List<QualityAttribute> getProjectAttributes(Project project);
	List<QualityAttribute> getAll();
	List<ProjectPackageAttributeRating> getAll2();
}
