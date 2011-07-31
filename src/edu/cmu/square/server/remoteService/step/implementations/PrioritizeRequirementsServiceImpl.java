/**
 * 
 */
package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtPrioritization;
import edu.cmu.square.client.model.GwtPrioritizationStatus;
import edu.cmu.square.client.model.GwtPrioritizedRequirement;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.remoteService.step.interfaces.PrioritizeRequirementsService;
import edu.cmu.square.server.business.step.interfaces.PrioritizeRequirementBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

/**
 * @author kaalpurush
 *
 */
@Service
public class PrioritizeRequirementsServiceImpl extends SquareRemoteServiceServlet implements PrioritizeRequirementsService
{

	private static final long serialVersionUID = -1484688151118462506L;
	
	@Resource
	private PrioritizeRequirementBusiness prioritizeRequirementBusiness;

	/* (non-Javadoc)
	 * @see edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet#setValuesForAuthorization()
	 */
	@Override
	public void setValuesForAuthorization()
	{
		setValuesForAuthorization(getLogic(prioritizeRequirementBusiness, PrioritizeRequirementBusiness.class));
	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.client.remoteService.interfaces.PrioritizeRequirementsService#getPrioritizedRequirementsList(edu.cmu.square.client.model.GwtUser, java.lang.Integer)
	 */
	@Override
	public List<GwtPrioritizedRequirement> getPrioritizedRequirements(GwtUser user, Integer projectId) throws SquareException
	{
		
		return getLogic(prioritizeRequirementBusiness, PrioritizeRequirementBusiness.class).getPrioritizedRequirements(projectId);
	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.client.remoteService.interfaces.PrioritizeRequirementsService#getPriotizationStatus(edu.cmu.square.client.model.GwtUser, java.lang.Integer)
	 */
	@Override
	public GwtPrioritizationStatus getPriotizationStatus(GwtUser user, Integer projectId) throws SquareException
	{
		// TODO Auto-generated method stub
		return getLogic(prioritizeRequirementBusiness, PrioritizeRequirementBusiness.class).getPriotizationStatus(user, projectId);
	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.client.remoteService.interfaces.PrioritizeRequirementsService#getRequirementsToCompare(edu.cmu.square.client.model.GwtUser, java.lang.Integer)
	 */
	@Override
	public List<GwtPrioritization> getRequirementsToCompare(GwtUser user, Integer projectId) throws SquareException
	{
		// TODO Auto-generated method stub
		return getLogic(prioritizeRequirementBusiness, PrioritizeRequirementBusiness.class).getRequirementsToCompare(user, projectId);
	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.client.remoteService.interfaces.PrioritizeRequirementsService#getUserPrioritizations(java.lang.Integer)
	 */
	@Override
	public GwtPrioritizationStatus getUserPrioritizations(Integer projectId) throws SquareException
	{
		// TODO Auto-generated method stub
		return getLogic(prioritizeRequirementBusiness, PrioritizeRequirementBusiness.class).getUserPrioritizations(projectId);
	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.client.remoteService.interfaces.PrioritizeRequirementsService#resetPrioritizationStatus(edu.cmu.square.client.model.GwtUser, java.lang.Integer)
	 */
	@Override
	public void resetPrioritizationStatus(GwtUser user, Integer projectId) throws SquareException
	{
		
		getLogic(prioritizeRequirementBusiness, PrioritizeRequirementBusiness.class).resetPrioritizationStatus(user, projectId);
	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.client.remoteService.interfaces.PrioritizeRequirementsService#updateRequirementComparison(edu.cmu.square.client.model.GwtPrioritization, edu.cmu.square.client.model.GwtUser, java.lang.Integer)
	 */
	@Override
	public void updateRequirementComparison(GwtPrioritization currentPrioritization, GwtUser user, Integer projectId) throws SquareException
	{
		getLogic(prioritizeRequirementBusiness, PrioritizeRequirementBusiness.class).updateRequirementComparison(currentPrioritization, user, projectId);

	}

	@Override
	public void batchUpdatePrioritizations(List<GwtPrioritizedRequirement> priorities) throws SquareException
	{
		getLogic(prioritizeRequirementBusiness, PrioritizeRequirementBusiness.class).batchUpdatePriorities(priorities);
	}

	@Override
	public List<GwtPrioritizedRequirement> getPrioritizedRequirementsIndividual(GwtUser user, Integer projectId) throws SquareException
	{
		
		return getLogic(prioritizeRequirementBusiness, PrioritizeRequirementBusiness.class).getPrioritizedRequirementsForUser(user, projectId);
	}

}
