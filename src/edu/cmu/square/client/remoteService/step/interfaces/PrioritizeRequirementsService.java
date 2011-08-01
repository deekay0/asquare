package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtPrioritization;
import edu.cmu.square.client.model.GwtPrioritizationStatus;
import edu.cmu.square.client.model.GwtPrioritizedRequirement;
import edu.cmu.square.client.model.GwtUser;

@RemoteServiceRelativePath("prioritizeRequirements.rpc")
public interface PrioritizeRequirementsService extends RemoteService
{
	
	void batchUpdatePrioritizations(List<GwtPrioritizedRequirement> prioriries) throws SquareException;
	/**
	 * Returns the total comparisons, the current comparisons completed, and whether
	 * this user has completed all their prioritizations.
	 * @param user The current user
	 * @param projectId The current project
	 * @return A GwtPrioritizationStatus object
	 * @throws SquareException 
	 */
	GwtPrioritizationStatus getPriotizationStatus(GwtUser user, Integer projectId) throws SquareException;
	
	/**
	 * Resets the comparisons to 0 (both cost and value). Returns the total comparisons, 
	 * current comparisons, and comparison status. 
	 * @param user The current user
	 * @param projectId The current project
	 * @return A GwtPrioritizationStatus object
	 * @throws SquareException
	 */
	void resetPrioritizationStatus(GwtUser user, Integer projectId) throws SquareException;
	
	/**
	 * Returns a list of unique pair of requirements that need to be compared. The cost and 
	 * value of the requirements are also returned
	 * @param user The current user
	 * @param projectId The current project
	 * @return A list of GwtPrioritization object
	 * @throws SquareException
	 */
	List<GwtPrioritization> getRequirementsToCompare(GwtUser user, Integer projectId) throws SquareException;
	/**
	 * Saves the current comparison with the given pair, cost, and value. Then it returns the current count and completion status.
	 * @param currentPrioritization The unique pair of requirements with updated cost and value
	 * @param user The current user
	 * @param projectId The current project
	 * @throws SquareException
	 */
	void updateRequirementComparison(GwtPrioritization currentPrioritization, GwtUser user, Integer projectId) throws SquareException;
	
	/**
	 * Retrieve the list of prioritized requirements with the requirement object, group rank, individual rank, and lead requirements engineer rank.
	 * @param user The current user.
	 * @param projectId The current project 
	 * @return The GwtPrioritizedRequirements object. 
	 * @throws SquareException
	 */
	List<GwtPrioritizedRequirement> getPrioritizedRequirements(GwtUser user, Integer projectId) throws SquareException;
	
	/**
	 * Retrieves the total number of users of the project and the number of users who have prioritized requirements.
	 * @param projectId Current project
	 * @return A GwtPrioritizationStatus
	 * @throws SquareException
	 */
	GwtPrioritizationStatus getUserPrioritizations(Integer projectId) throws SquareException;
	
	List<GwtPrioritizedRequirement> getPrioritizedRequirementsIndividual(GwtUser user, Integer projectId) throws SquareException;
}
