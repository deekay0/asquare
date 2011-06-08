package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.remoteService.step.interfaces.ReviewOfRequirementsByAcquisitionService;
import edu.cmu.square.server.business.step.interfaces.ReviewOfRequirementsByAcquisitionBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

@Service
public class ReviewOfRequirementsByAcquisitionServiceImpl extends SquareRemoteServiceServlet implements ReviewOfRequirementsByAcquisitionService
{
		private static final long serialVersionUID = -4630780713009182467L;
		@Resource
		private ReviewOfRequirementsByAcquisitionBusiness reviewOfRequirementsByAcquisitionBusiness;

		public void setValuesForAuthorization()
		{
			setValuesForAuthorization(reviewOfRequirementsByAcquisitionBusiness);
			
		}


		public GwtRequirement addRequirement(GwtProject gwtProject, GwtRequirement gwtRequirement) throws SquareException
		{
		 return	reviewOfRequirementsByAcquisitionBusiness.addRequirement(gwtProject, gwtRequirement);
		}


		public List<GwtRequirement> getRequirements(GwtProject gwtProject) throws SquareException
		{
			
			return	reviewOfRequirementsByAcquisitionBusiness.getRequirements(gwtProject);
		}


		public void removeRequirement(GwtRequirement gwtRequirement) throws SquareException
		{
				reviewOfRequirementsByAcquisitionBusiness.removeRequirement(gwtRequirement);
			
		}


		public void updateRequirement(GwtProject gwtProject, GwtRequirement gwtRequirement) throws SquareException
		{
				reviewOfRequirementsByAcquisitionBusiness.updateRequirement(gwtProject, gwtRequirement);
		}
	
}
