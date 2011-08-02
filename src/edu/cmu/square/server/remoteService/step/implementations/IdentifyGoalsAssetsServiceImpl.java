package edu.cmu.square.server.remoteService.step.implementations;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtBusinessGoal;
import edu.cmu.square.client.model.GwtSubGoal;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsService;
import edu.cmu.square.server.business.step.interfaces.IdentifyGoalsAssetsBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

@Service
public class IdentifyGoalsAssetsServiceImpl extends SquareRemoteServiceServlet
							                implements IdentifyGoalsAssetsService
  {
	@Resource
	private IdentifyGoalsAssetsBusiness businessLogic;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5872889170749121113L;
	

	
	public Integer addAsset(int projectId, GwtAsset gwtAsset) throws Exception
	{	
		businessLogic.addAsset(projectId, gwtAsset);
		return gwtAsset.getId();
	}
	
	
	public Integer addSubGoal(int projectId, GwtSubGoal gwtSubGoal) throws Exception
	{
		 businessLogic.addSubGoal(projectId, gwtSubGoal);
		 return gwtSubGoal.getId();
	}
	
	
	public void associateSubGoalAndAsset(int projectId, GwtSubGoal gwtSubGoal, GwtAsset gwtAsset) throws Exception
	{
		 businessLogic.associateSubGoalAndAsset(gwtSubGoal, gwtAsset);	
	}
	
	
	public GwtBusinessGoal loadBusinessGoalInfo(int projectId) throws Exception
	{
		return businessLogic.getBusinessGoalInformation(projectId);
	}
	
	public void removeAsset(int projectId, GwtAsset gwtAsset) throws Exception
	{
		  businessLogic.removeAsset(projectId, gwtAsset);	
	}
	
	
	
	public void removeAssociationGoalAsset(int projectId,
			                               GwtSubGoal gwtSubGoal,
			                               GwtAsset gwtAsset) throws Exception
	{
			businessLogic.removeAssociationSubGoalAndAsset(gwtSubGoal, gwtAsset);	
	}
	
	
	
	public void removeSubGoal(int projectId, GwtSubGoal gwtSubGoal) throws Exception
	{
		 businessLogic.removeSubGoal(projectId, gwtSubGoal);
	}
	
	
	
	public void setBusinessGoalInfo(int projectId,
			                        GwtBusinessGoal gwtBusinessGoal) throws Exception
	{
		businessLogic.setBusinessGoal(projectId, gwtBusinessGoal);	
	}
	
	
	
	public void updateAsset(int projectId, GwtAsset gwtAsset) throws Exception
	{
		businessLogic.updateAsset(projectId, gwtAsset);	
	}
	
	
	
	public void updateSubGoal(int projectId, GwtSubGoal gwtSubGoal) throws Exception
	{
	 	businessLogic.updateSubGoal(projectId, gwtSubGoal);		
	}

	
	public void setValuesForAuthorization() {
		setValuesForAuthorization(businessLogic);
		
	}



}
