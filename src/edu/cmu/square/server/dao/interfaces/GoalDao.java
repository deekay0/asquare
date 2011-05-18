/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Goal;
import edu.cmu.square.server.dao.model.Project;
/**
 * This interface lists out the data access operations on the User table
 *
 */

public interface GoalDao extends AbstractDao<Goal, Integer> {

	List<Goal> getBusinessGoalByProject(Project project);
	
	List<Goal> getSubGoalByProject(Project project);

	List<Goal> getSubGoalByDescription(String goalDescription);

	

}
