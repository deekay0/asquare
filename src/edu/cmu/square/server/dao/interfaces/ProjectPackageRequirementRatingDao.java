/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import org.hibernate.Query;

import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageRequirementRating;;

/**
 * @author Nan
 *
 */
public interface ProjectPackageRequirementRatingDao extends AbstractDao<ProjectPackageRequirementRating, Integer> 
{	
	
	List<GwtRequirementRating> getAllRatings(Project project);
	void setRating(int projectID, int packageId, int requirementID, int rating);
	int getRating(int projectID, int packageId, int requirementID);
	void updateRating(int projectID, int packageId, int requirementID, int rating);
}
