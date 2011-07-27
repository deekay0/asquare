package edu.cmu.square.server.business.step.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtInspectionTechnique;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.InspectionStatus;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.step.interfaces.InspectionTechniqueBusiness;
import edu.cmu.square.server.dao.interfaces.InspectionTechniqueDao;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.model.InspectionTechnique;
import edu.cmu.square.server.dao.model.Project;

@Service
@Scope("prototype")
public class InspectionTechniqueBusinessImpl extends BaseBusinessImpl implements InspectionTechniqueBusiness
{
	@Resource
	private InspectionTechniqueDao inspectionDao;
	@Resource
	private ProjectDao projectDao;

	
	
	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer})
	public GwtInspectionTechnique addInspectionTechnique(int projectId, GwtInspectionTechnique gwtInspection) throws SquareException
	{
		InspectionTechnique inspectionToAdd = new InspectionTechnique(gwtInspection);
		inspectionToAdd.setProject(new Project(projectId));
		
		// get the inspection techniques by name and check for duplicates	
		if (this.inspectionDao.hasTechnique(gwtInspection.getTitle(), new Project(projectId)) > 0)  //there was a duplicate
		{
			SquareException se = new SquareException("Already exists");
			se.setType(ExceptionType.duplicateName);
			throw se;
		}
		
		this.inspectionDao.create(inspectionToAdd);
		gwtInspection.setInspectionTechniqueId(inspectionToAdd.getId());
		
		return gwtInspection;
	}
	

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer})
	public void chooseInspectionTechnique(int projectId, GwtInspectionTechnique gwtInspection) throws SquareException
	{
		// get the project to update
		if (projectId <= 0)
		{
			SquareException se = new SquareException("Not a valid project");
			se.setType(ExceptionType.other);
			throw se;
		}
		
		if (this.inspectionDao.hasTechnique(gwtInspection.getTitle(), new Project(projectId)) > 0)  // the given inspection does not exist for some reason.
		{
			SquareException se = new SquareException("does not exist");
			se.setType(ExceptionType.entityDoesNotExist);
			throw se;
		}
				
		Project targetProject = this.projectDao.fetch(projectId);
//		targetProject.setInspectionTechnique(new InspectionTechnique(gwtInspection));
		
		// update in the project DAO
		this.projectDao.update(targetProject);
	}

	
	@AllowedRoles(roles = { Roles.All })
	public List<GwtInspectionTechnique> getInspectionTechniques(int projectId, StepStatus stepStatus) throws SquareException
	{
		List<GwtInspectionTechnique> inspectionList = new ArrayList<GwtInspectionTechnique>(); 
		List<InspectionTechnique> inspectionTechniques = inspectionDao.getInspectionTechniquesByProject(new Project(projectId));

		for (InspectionTechnique technique : inspectionTechniques)
		{
			inspectionList.add(technique.createGwtInspectionTechnique());
		}

		return inspectionList;
	}

	
	@AllowedRoles(roles = { Roles.Lead_Requirements_Engineer })
	public void removeInspectionTechnique(GwtInspectionTechnique gwtInspection) throws SquareException
	{
		this.inspectionDao.deleteById(gwtInspection.getInspectionTechniqueId());
	}

	
	@AllowedRoles(roles = { Roles.Lead_Requirements_Engineer })
	public void updateInspectionTechnique(GwtInspectionTechnique gwtInspection, int projectId) throws SquareException
	{
		if (projectId <= 0)
		{
			throw new SquareException("Invalid project ID.");
		}
		
		if (gwtInspection == null)
		{
			throw new SquareException("gwtInspection should not be null.");
		}

		if (gwtInspection.getInspectionTechniqueId().intValue() <= 0)
		{
			throw new SquareException("gwtInspection must have an id.");
		}

		try
		{
			InspectionTechnique duplicateTechnique = this.inspectionDao.getInspectionTechniquesByNameAndProject(gwtInspection.getTitle(), projectId);
			if (duplicateTechnique != null &&
					!(duplicateTechnique.getId().equals(gwtInspection.getInspectionTechniqueId())))
			{
				SquareException se = new SquareException("Already exists");
				se.setType(ExceptionType.duplicateName);
				throw se;
			}
			
			InspectionTechnique inspectionToUpdate = new InspectionTechnique(gwtInspection);
			inspectionToUpdate.setProject(new Project(projectId));
			this.inspectionDao.update(inspectionToUpdate);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException("update failed", t);
		}		
	}



	@Override
	public void loadDefaultInspections(int projectId, List<GwtInspectionTechnique> inspections) throws SquareException
	{
	
			for (GwtInspectionTechnique inspection : inspections)
			{
				this.addInspectionTechnique(projectId, inspection);
			}
		
		
	}



	@Override
	public StepEnum getStepDescription() throws SquareException
	{
		return StepEnum.Inspect_Requirements;
	}



	@Override
	public GwtStepVerficationResult verifyStep(Project project) throws SquareException
	{
		GwtStepVerficationResult result = new GwtStepVerficationResult();
		

//		String inspectionStatus = project.getInspectionStatus();
		String inspectionStatus = "";
		if(inspectionStatus==null)
		{
			result.getMessages().add("The inspection has not been completed.");
			result.setHasWarning(true);	
		}
		else if (inspectionStatus.equals(InspectionStatus.Issues_Reinspect.getLabel()))
		{
			result.getMessages().add("There are pending issues.");
			result.setHasWarning(true);
		}

		return result;
	}
	
	
}
