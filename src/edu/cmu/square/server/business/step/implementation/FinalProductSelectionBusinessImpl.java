package edu.cmu.square.server.business.step.implementation;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRationale;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.step.interfaces.FinalProductSelectionBusiness;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.ProjectPackageRequirementRatingDao;
import edu.cmu.square.server.dao.interfaces.RationaleDao;
import edu.cmu.square.server.dao.interfaces.TradeoffReasonDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageRationale;
import edu.cmu.square.server.dao.model.ProjectPackageRationaleId;


@Service
@Scope("prototype")
public class FinalProductSelectionBusinessImpl extends BaseBusinessImpl implements FinalProductSelectionBusiness
{
	@Resource
	private ProjectDao projectDao;
	@Resource
	private TradeoffReasonDao tradeoffReasonDao;
	@Resource
	private ProjectPackageRequirementRatingDao projectRequirementRatingDao;
	@Resource
	private RationaleDao rationaleDao;

	
	@Override
	public StepEnum getStepDescription() throws SquareException
	{
		return StepEnum.Perform_Tradeoff_Analysis;
	}

	

	@Override
	public GwtRationale getRationale(GwtProject project) throws SquareException
	{
		ProjectPackageRationale rationale = rationaleDao.getRationale(new Project(project));
		if(rationale == null)
			return null;
		GwtRationale gwtRationale = new GwtRationale();
		
		GwtSoftwarePackage spackage = new GwtSoftwarePackage();
		spackage.setDescription(rationale.getSoftwarePackage().getDescription());
		spackage.setName(rationale.getSoftwarePackage().getName());
		spackage.setId(rationale.getSoftwarePackage().getId());
		
		gwtRationale.setPackage(spackage);
		gwtRationale.setProject(project);
		gwtRationale.setRationale(rationale.getRationale());
		return gwtRationale;
	}

	@Override
	public void setRationale(GwtRationale gwtRationale) throws SquareException
	{
		ProjectPackageRationale currentRationale = rationaleDao.getRationale(new Project(gwtRationale.getProject()));
		if(currentRationale != null)
		{			
			rationaleDao.deleteEntity(currentRationale);
		}	
	
		ProjectPackageRationale newRationale = new ProjectPackageRationale(gwtRationale);
		rationaleDao.create(newRationale);
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
	public List<GwtTradeoffReason> getTradeoffReasons(int projectID) throws SquareException
	{
		Project project = new Project();
		project.setId(projectID);
		return tradeoffReasonDao.getAllTradeoffReasons(project);
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
	public GwtStepVerficationResult verifyStep(Project projectId) throws SquareException
	{
		// TODO Auto-generated method stub
		GwtStepVerficationResult result = new GwtStepVerficationResult();
		result.setHasWarning(false);
		return result;
	}
}
