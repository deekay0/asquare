package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.remoteService.step.interfaces.ReviewAndFinalizeRequirementsService;
import edu.cmu.square.server.business.step.interfaces.ReviewAndFinalizeRequirementsBusiness;
import edu.cmu.square.server.business.step.interfaces.ReviewOfRequirementsByAcquisitionBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

@Service
public class ReviewAndFinalizeRequirementsServiceImpl extends SquareRemoteServiceServlet implements ReviewAndFinalizeRequirementsService
{
			//private static final long serialVersionUID = -4630780713009182467L;
			
			
			private static final long serialVersionUID = 4861905475235991853L;
			@Resource
			private ReviewAndFinalizeRequirementsBusiness reviewAndFinalizeRequirementsBusiness;

			public void setValuesForAuthorization()
			{
				setValuesForAuthorization(reviewAndFinalizeRequirementsBusiness);
				
			}


			

			public List<GwtRequirement> getRequirements(GwtProject gwtProject) throws SquareException
			{
				
				return	reviewAndFinalizeRequirementsBusiness.getRequirements(gwtProject);
			}


			public void updateRequirement(GwtProject gwtProject, GwtRequirement gwtRequirement) throws SquareException
			{
				reviewAndFinalizeRequirementsBusiness.updateRequirement(gwtProject, gwtRequirement);
			}
			
			
			//Detail Pane for ASQUARE
			public List<GwtRequirement> getRequirementsFromProject(Integer projectID) throws SquareException
			{
				
				return reviewAndFinalizeRequirementsBusiness.getRequirementsFromProject(projectID);
			}

		
			public void updateRequirement(GwtRequirement Requirement) throws SquareException
			{
				reviewAndFinalizeRequirementsBusiness.updateRequirement(Requirement);
				
			}



			@Override
			public void changeStatusToApproveRequirement(Integer projectId, GwtRequirement gwtRequirement) throws SquareException
			{
				reviewAndFinalizeRequirementsBusiness.changeStatusToApproveRequirement(projectId, gwtRequirement);
				
			}


			@Override
			public void changeStatusToRequestRevisionRequirement(Integer projectId, GwtRequirement gwtRequirement) throws SquareException
			{
				reviewAndFinalizeRequirementsBusiness.changeStatusToRequestRevisionRequirement(projectId, gwtRequirement);
				
			}

			
			
			

			public void removeRequirement(GwtRequirement gwtRequirement) throws SquareException
			{
				reviewAndFinalizeRequirementsBusiness.removeRequirement(gwtRequirement);
				
			}
			
			public GwtRequirement addRequirement(GwtProject gwtProject, GwtRequirement gwtRequirement) throws SquareException
			{
				return	reviewAndFinalizeRequirementsBusiness.addRequirement(gwtProject, gwtRequirement);
			
			}
			/*
			public void assignRequirementsToCategory(List<GwtRequirement> requirements,int categoryID) throws SquareException
			{
				reviewOfRequirementsByAcquisitionBusiness.assignRequirementsToCategory(requirements,categoryID);
			}

			
			public void removeRequirementsFromCategory(List<GwtRequirement> requirements, int categoryID) throws SquareException
			{
				reviewOfRequirementsByAcquisitionBusiness.removeRequirementsFromCategory(requirements,categoryID);
				
			}

	*/
			public void deleteRequirement(Integer requirementId, Integer projectId) throws SquareException
			{
				reviewAndFinalizeRequirementsBusiness.deleteRequirement(requirementId, projectId);
				
			}


			public Integer addRequirementToProject(Integer projectId, GwtRequirement Requirement) throws SquareException
			{
				return reviewAndFinalizeRequirementsBusiness.addRequirementToProject(projectId, Requirement);
				
			}

}
