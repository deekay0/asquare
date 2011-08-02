/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import org.hibernate.Query;

import edu.cmu.square.client.model.GwtRating;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageAttributeRating;
import edu.cmu.square.server.dao.model.QualityAttribute;

/**
 * @author deekay
 *
 */
public interface QualityAttributeDao extends AbstractDao<QualityAttribute, Integer> 
{	
	List<QualityAttribute> getAll();
	List<QualityAttribute> getQualityAttributesByNameAndProject(String name, Integer projectId);
	List<QualityAttribute> getQualityAttributesByProject(Project project);
	List<GwtRating> getAllRatings(Project project);
	void setRating(int projectID, int techniqueID, int evaluationCriteriaID, int value);
	int getRating(int projectID, int packageId, int attributeId);
	void updateRating(int projectID, int packageId, int attributeId, int a);
}
