package edu.cmu.square.server.business.step.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.step.interfaces.PerformTradeoffAnalysisBusiness;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.TradeoffReasonDao;
import edu.cmu.square.server.dao.interfaces.ProjectPackageRequirementRatingDao;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageTradeoffreason;
import edu.cmu.square.server.dao.model.QualityAttribute;


@Service
@Scope("prototype")
public class PerformTradeoffAnalysisBusinessImpl extends BaseBusinessImpl implements PerformTradeoffAnalysisBusiness
{

	@Resource
	private ProjectDao projectDao;
	@Resource
	private TradeoffReasonDao tradeoffReasonDao;
	@Resource
	private ProjectPackageRequirementRatingDao projectRequirementRatingDao;

	@AllowedRoles(roles = {Roles.All})
	public void setRequirementRateValue(int projectID, int packageID, int requirementID, int value) throws SquareException
	{
		//System.out.println("here.............."+projectID+"  "+packageID+"  "+requirementID+"  "+value);
		if( -1 == projectRequirementRatingDao.getRating(projectID, packageID,requirementID))
			projectRequirementRatingDao.setRating(projectID,packageID, requirementID, value);
		else
		{
			projectRequirementRatingDao.updateRating(projectID,packageID,requirementID, value);
		}

	}

	@Override
	@AllowedRoles(roles = {Roles.All})
	public List<GwtRequirementRating> getRequirementRateValues(int projectID) throws SquareException
	{
		Project project = new Project();
		project.setId(projectID);
		return projectRequirementRatingDao.getAllRatings(project);
	}

	@Override
	@AllowedRoles(roles = {Roles.All})
	public int getRequirementRateValue(int projectID,int packageID, int requirementID) throws SquareException
	{
		return projectRequirementRatingDao.getRating(projectID,packageID, requirementID);
	}
	

	@Override
	public StepEnum getStepDescription() throws SquareException
	{
		return StepEnum.Perform_Tradeoff_Analysis;
	}


	@Override
	public GwtStepVerficationResult verifyStep(Project project) throws SquareException
	{
		GwtStepVerficationResult result = new GwtStepVerficationResult();

		if (project.isSecurity())
		{
			if (project.getSecurityTechnique() == null)
			{
				result.getMessages().add("The security requirements elicitation technique has not been selected!");
				result.setHasWarning(true);
			}
		}
		return result;
	}

	@Override
	public List<GwtTradeoffReason> getTradeoffReasons(int projectID) throws SquareException
	{
		Project project = new Project();
		project.setId(projectID);
		return tradeoffReasonDao.getAllTradeoffReasons(project);
	}

	@Override
	public void addTradeoffReason(int projectID, int packageID, String tradeoffReason) throws SquareException
	{
		// TODO Auto-generated method stub
		tradeoffReasonDao.setTradeoffReason(projectID, packageID, tradeoffReason);
	}

	@Override
	public void updateTradeoffReason(int projectID, int packageId, String tradeoffReason) throws SquareException
	{
		// TODO Auto-generated method stub
		if( "".equals( tradeoffReasonDao.getTradeoffReason(projectID, packageId)))
				tradeoffReasonDao.setTradeoffReason(projectID, packageId, tradeoffReason);
		else
		{
			tradeoffReasonDao.updateTradeoffReason(projectID, packageId, tradeoffReason);
		}
	}

	@Override
	public void updatePriority(int projectID, int packageID, int priority) throws SquareException
	{
		tradeoffReasonDao.setPriority(projectID, packageID, priority);
	}



	

}