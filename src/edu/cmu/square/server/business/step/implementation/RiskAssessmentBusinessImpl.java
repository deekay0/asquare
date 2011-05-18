package edu.cmu.square.server.business.step.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtArtifact;
import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtRisk;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.step.interfaces.RiskAssessmentBusiness;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.RiskDao;
import edu.cmu.square.server.dao.model.Artifact;
import edu.cmu.square.server.dao.model.Asset;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Risk;
@Service
@Scope("prototype")
public class RiskAssessmentBusinessImpl extends BaseBusinessImpl implements RiskAssessmentBusiness
{

	@Resource
	private ProjectDao projectDao;

	@Resource
	private RiskDao riskDao;

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer, Roles.Requirements_Engineer})
	public int addRisksToProject(Integer projectId, GwtRisk gwtRisk) throws SquareException
	{
		Project p = projectDao.fetch(projectId);
		Risk risk = new Risk(gwtRisk);
		risk.setProject(p);

		
		riskDao.create(risk);

		return risk.getId();

	}

	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer, Roles.Requirements_Engineer})
	public void deleteRisk(Integer riskId) throws SquareException
	{
		riskDao.deleteById(riskId);
	}

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer, Roles.Requirements_Engineer})
	public void updateRisk(GwtRisk gwtRisk) throws SquareException
	{

		Risk r = riskDao.fetch(gwtRisk.getId());
		
		r.getAssets().clear();
		r.getArtifacts().clear();
		riskDao.update(r);
	
		for (GwtAsset a : gwtRisk.getAssets())
		{
			Asset asset = new Asset(a);
			r.getAssets().add(asset);
		}

		for (GwtArtifact a : gwtRisk.getArtifact())
		{
			Artifact artifact = new Artifact(a);
			r.getArtifacts().add(artifact);
		}
		
		r.update(gwtRisk);
		riskDao.update(r);

	}

	@AllowedRoles(roles = {Roles.All})
	public List<GwtRisk> getRisksFromProject(int projectID) throws SquareException
	{

		List<GwtRisk> gwtRiskList = new ArrayList<GwtRisk>();
		Project project = new Project();
		project.setId(projectID);

		List<Risk> riskList = riskDao.getRisksByProject(project);
		for (Risk r : riskList)
		{
			GwtRisk gwtRisk = r.createGwtRisk();
			gwtRiskList.add(gwtRisk);

		}
		return gwtRiskList;
	}

	@Override
	public StepEnum getStepDescription() throws SquareException
	{
		return StepEnum.Perform_Risk_Assessment;
	}

	@Override
	public GwtStepVerficationResult verifyStep(Project project) throws SquareException
	{
		GwtStepVerficationResult result = new GwtStepVerficationResult();

		List<GwtRisk> risks = getRisksFromProject(project.getId());
		int riskWithoutArtifact = 0;
		int riskWithoutAsset = 0;
		for (GwtRisk r : risks)
		{
			if (r.getArtifact().size() == 0)
			{
				riskWithoutArtifact++;
			}
			if (r.getAssets().size() == 0)
			{
				riskWithoutAsset++;
			}
		}
		if (risks.size() == 0)
		{
			result.getMessages().add("There are no risks collected!");
			result.setHasWarning(true);
		}

		if (riskWithoutArtifact != 0)
		{

			result.getMessages().add("There are " + riskWithoutArtifact + " risks not linked to artifacts.");
			result.setHasWarning(true);
		}
		if (riskWithoutAsset != 0)
		{

			result.getMessages().add("There are " + riskWithoutAsset + " risks not linked to assets.");
			result.setHasWarning(true);
		}

		return result;
	}

}
