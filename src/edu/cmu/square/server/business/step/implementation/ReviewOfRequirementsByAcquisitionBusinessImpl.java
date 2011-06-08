package edu.cmu.square.server.business.step.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.GwtTerm;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.step.interfaces.AgreeOnDefinitionsBusiness;
import edu.cmu.square.server.business.step.interfaces.ReviewOfRequirementsByAcquisitionBusiness;
import edu.cmu.square.server.dao.interfaces.RequirementDao;
import edu.cmu.square.server.dao.interfaces.TermDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Requirement;
import edu.cmu.square.server.dao.model.Term;


@Service
@Scope("prototype")
public class ReviewOfRequirementsByAcquisitionBusinessImpl extends BaseBusinessImpl implements ReviewOfRequirementsByAcquisitionBusiness
{

	@Resource
	private RequirementDao requirementDao;

	@AllowedRoles(roles = {Roles.All})
	public List<GwtRequirement> getRequirements(GwtProject gwtProject) throws SquareException
	{
		List<GwtRequirement> requirementList = new ArrayList<GwtRequirement>();
		Project project = new Project(gwtProject.getId());
		List<Requirement> requirements = requirementDao.getRequirementByProject(project);

		for (Requirement req : requirements)
		{
			requirementList.add(req.createGwtRequirement());
		}

		return requirementList;

	}

	@Override
	public GwtStepVerficationResult verifyStep(Project project) throws SquareException
	{
		GwtStepVerficationResult result = new GwtStepVerficationResult();

		
		List<GwtRequirement> requirements = getRequirements(project.createGwtProject());

		if (requirements.size() == 0)
		{
			result.getMessages().add("There are no requirements defined yet!");
			result.setHasWarning(true);
		}

		return result;
	}
		

	@Override
	public StepEnum getStepDescription() throws SquareException
	{
		return StepEnum.Review_Of_Requirements_By_Acquisition_Organization;
	}

	@Override
	public void loadDefaultRequirements(int projectId, List<GwtRequirement> defaultRequirements) throws SquareException
	{
		GwtProject project = new GwtProject(projectId);
		for (GwtRequirement req : defaultRequirements)
		{

			this.addRequirement(project, req);	

		}
	}
	
	@AllowedRoles(roles = {Roles.All})
	public GwtRequirement addRequirement(GwtProject gwtProject, GwtRequirement gwtRequirement) throws SquareException
	{
		Project project = new Project(gwtProject.getId());
		Requirement requirement = new Requirement(gwtRequirement);
		requirement.setProject(project);

		requirementDao.create(requirement);

		return requirement.createGwtRequirement();
	}

	
		@AllowedRoles(roles = {Roles.Administrator, Roles.Contractor, Roles.Acquisition_Organization_Engineer, Roles.Security_Specialist})
		public void removeRequirement(GwtRequirement gwtRequirement) throws SquareException
		{
			requirementDao.deleteById(gwtRequirement.getId());
		}
		
		@AllowedRoles(roles = {Roles.Administrator, Roles.Contractor, Roles.Acquisition_Organization_Engineer, Roles.Security_Specialist})
		public void updateRequirement(GwtProject gwtProject, GwtRequirement gwtRequirement) throws SquareException
		{
			Requirement requirement = new Requirement(gwtRequirement);
			requirement.setProject(new Project(gwtProject.getId()));
			requirementDao.update(requirement);
		}
		
	
	
}
