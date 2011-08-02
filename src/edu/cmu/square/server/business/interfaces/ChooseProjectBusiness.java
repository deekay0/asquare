package edu.cmu.square.server.business.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;

public interface ChooseProjectBusiness extends BaseBusinessInterface
{
	/**
	 * This would retrieve the list of Projects that this user is a member of. It will get 
	 * the projects in two sets: completed and incomplete. Completed projects are those whose 
	 * all steps are marked as complete. Incomplete project would have some steps in progress.
	 * For the incomplete projects, it would also have a list of steps that are currently in progress
	 * All the projects would indicate whether they are security/privacy or both. 
	 * @param userId The User whose list of projects are retrieved.
	 * @return A list of projects and additional informaiton for the user.
	 */
	List<GwtProject>[] getProjectsForUser(Integer userId, Integer caseId) throws SquareException;
}
