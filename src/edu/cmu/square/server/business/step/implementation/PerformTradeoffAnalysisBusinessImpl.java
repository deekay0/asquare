package edu.cmu.square.server.business.step.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.step.interfaces.PerformTradeoffAnalysisBusiness;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.ProjectPackageRequirementRatingDao;

import edu.cmu.square.server.dao.model.Project;


@Service
@Scope("prototype")
public class PerformTradeoffAnalysisBusinessImpl extends BaseBusinessImpl implements PerformTradeoffAnalysisBusiness
{

	@Resource
	private ProjectDao projectDao;
	@Resource
	private ProjectPackageRequirementRatingDao projectRequirementRatingDao;



	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer, Roles.Requirements_Engineer})
	public void setRequirementRateValue(int projectID, int packageID, int requirementID, int value) throws SquareException
	{
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
	

	
	
	
/*	@AllowedRoles(roles = {Roles.All})
	public List<GwtQualityAttribute> getQualityAttributes(GwtProject gwtProject, StepStatus stepStatus) throws SquareException
	{
		List<GwtQualityAttribute> evaluationList = new ArrayList<GwtQualityAttribute>();
		Project project = new Project(gwtProject);
		List<QualityAttribute> evaluations = qualityAttributeDao.getQualityAttributesByProject(project);

		if (evaluations == null)
		{
			return evaluationList;
		}
		for (QualityAttribute t : evaluations)
		{
			evaluationList.add(t.createGwtQualityAttribute());
		}
		return evaluationList;
	}

	@AllowedRoles(roles = {Roles.All})
	public List<GwtSoftwarePackage> getSoftwarePackages(GwtProject gwtProject, StepStatus stepStatus) throws SquareException
	{
		List<GwtSoftwarePackage> softwarePackageList = new ArrayList<GwtSoftwarePackage>();
		Project project = new Project(gwtProject);
		List<SoftwarePackage> packages = softwarePackageDao.getSoftwarePackagesByProject(project);
		if (packages == null)
		{
			return softwarePackageList;
		}
		for (SoftwarePackage t : packages)
		{
			softwarePackageList.add(t.createGwtSoftwarePackage());
		}
		return softwarePackageList;
	}
*/
	



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

}
