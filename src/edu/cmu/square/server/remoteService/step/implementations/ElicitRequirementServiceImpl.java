/**
 * 
 */
package edu.cmu.square.server.remoteService.step.implementations;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.remoteService.step.interfaces.ElicitRequirementService;
import edu.cmu.square.server.business.step.interfaces.ElicitRequirementsBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;


@Service
public class ElicitRequirementServiceImpl extends SquareRemoteServiceServlet implements ElicitRequirementService
{

	private static final long serialVersionUID = -862162205855546182L;

	@Resource
	private ElicitRequirementsBusiness elicitBusiness;

	
	public void setValuesForAuthorization()
	{
		setValuesForAuthorization(elicitBusiness);

	}

	
	public Integer addRequirementToProject(Integer projectId, GwtRequirement Requirement) throws SquareException
	{
		return elicitBusiness.addRequirementToProject(projectId, Requirement);
		
	}

	
	public void deleteRequirement(Integer requirementId, Integer projectId) throws SquareException
	{
		elicitBusiness.deleteRequirement(requirementId, projectId);
		
	}

	
	public List<GwtRequirement> getRequirementsFromProject(Integer projectID) throws SquareException
	{
		
		return elicitBusiness.getRequirementsFromProject(projectID);
	}

	
	public void updateRequirement(GwtRequirement Requirement) throws SquareException
	{
		elicitBusiness.updateRequirement(Requirement);
		
	}
	
	public void assignRequirementsToCategory(List<GwtRequirement> requirements,int categoryID) throws SquareException
	{
		elicitBusiness.assignRequirementsToCategory(requirements,categoryID);
	}

	
	public void removeRequirementsFromCategory(List<GwtRequirement> requirements, int categoryID) throws SquareException
	{
		elicitBusiness.removeRequirementsFromCategory(requirements,categoryID);
		
	}

	
	

}
