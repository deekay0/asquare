/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtBusinessGoal;
import edu.cmu.square.client.model.GwtSubGoal;


public interface IdentifyGoalsAssetsBusiness extends StepBusinessInterface
{
	
	/**
	 * This returns all the elicitation techniques for this project.	 * 
	 * @param gwtProject The project that the user selected.
	 * @param stepStatus This parameter is for returning the step status of "Select Elicitation Techniques" step from the project.
	 * @return The Elicitation Techniques associated with this project.
	 * @throws SquareException  This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	/**
	 * @param projectId The project that the user selected.
	 * @return GwtBusinessGoal which has the 1 Business Goal, the Sub Goals and All the assets of a project.
	 * @throws SquareException  This may indicate database errors, and/or business logic errors
	 * 
	 */
	GwtBusinessGoal getBusinessGoalInformation(int projectId) throws SquareException;
	/**
	 * Associate a business goal to a project			
	 * @param projectId  The SQUARE project	
	 * @param businessGoal The business goal
	 */
	void setBusinessGoal(int projectId, GwtBusinessGoal businessGoal) throws SquareException;
	/**
	 * Associate a subgoal to a project. This works because each project has only ONE business goal
	 * @param projectId The SQUARE project
	 * @param subgoal The sub goal
	 */
	void addSubGoal(int projectId, GwtSubGoal subgoal) throws SquareException;
	/**
	 * Remove a subgoal from a project. This works because each project has only ONE business goal
	 * @param projectId The SQUARE project
	 * @param subgoal The sub goal
	 */
	void removeSubGoal(int projectId, GwtSubGoal subgoal) throws SquareException;
	/**
	 * Edit a subgoal to a project. This works because each project has only ONE business goal
	 * @param projectId The SQUARE project
	 * @param subgoal The sub goal
	 */
	void updateSubGoal(int projectId, GwtSubGoal subgoal) throws SquareException;
	/**
	 * Add an asset to a project. This is not associated with any sub goals
	 * @param projectId The SQUARE project
	 * @param asset the asset to be associated
	 */
	void addAsset(int projectId, GwtAsset asset) throws SquareException;
	/**
	 * Remove an asset from a project. This is not associated with any sub goals
	 * @param projectId The SQUARE project
	 * @param asset the asset to be removed
	 */
	void removeAsset(int projectId, GwtAsset asset) throws SquareException;
	/**
	 * Update an asset associated with a project. This might be associated with any sub goals
	 * @param projectId The SQUARE project
	 * @param asset the asset to be edited
	 */
	void updateAsset(int projectId, GwtAsset asset) throws SquareException;
	/**
	 * Associate an asset with a subgoal
	 * @param subgoal the sub-goal
	 * @param asset the asset to be associated with a sub-goal
	 */
	void associateSubGoalAndAsset(GwtSubGoal subgoal, GwtAsset asset) throws SquareException;
	
	/**
	 * Remove association of an asset with a subgoal
	 * @param subgoal the sub-goal
	 * @param asset the asset to be associated with a sub-goal
	 */
	void removeAssociationSubGoalAndAsset(GwtSubGoal subgoal, GwtAsset asset) throws SquareException;
	


	
	
	
	

}
