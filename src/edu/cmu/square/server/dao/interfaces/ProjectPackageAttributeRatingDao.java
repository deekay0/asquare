/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.client.model.GwtProjectPackageAttributeRating;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageAttributeRating;

/**
 * @author DK
 *
 */
public interface ProjectPackageAttributeRatingDao extends AbstractDao<ProjectPackageAttributeRating, Integer> 
{
	List<GwtProjectPackageAttributeRating> getAllRatingsForProject(Project project);
	void setRating(int projectID, int packageId, int requirementID, int rating);
	int getRating(int projectID, int packageId, int requirementID);
	void updateRating(int projectID, int packageId, int requirementID, int rating);
	List<GwtProjectPackageAttributeRating> getAllRatings(Project project);
}
