package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtInspectionTechnique;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.remoteService.step.interfaces.InspectionTechniqueService;
import edu.cmu.square.server.business.step.interfaces.InspectionTechniqueBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

@Service
public class InspectionTechniqueServiceImpl extends SquareRemoteServiceServlet implements InspectionTechniqueService
{
	
	private static final long serialVersionUID = -5725843620206249786L;

	@Resource
	private InspectionTechniqueBusiness inspectionTechniqueBusiness;
	
	
	public GwtInspectionTechnique addInspectionTechnique(Integer projectId, GwtInspectionTechnique gwtInspection) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			return this.inspectionTechniqueBusiness.addInspectionTechnique(projectId, gwtInspection);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
	}

	
	
	public GwtInspectionTechnique chooseInspectionTechnique(Integer projectId, GwtInspectionTechnique gwtInspection) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			this.inspectionTechniqueBusiness.chooseInspectionTechnique(projectId, gwtInspection);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
		
		return gwtInspection;
	}

	
	
	public List<GwtInspectionTechnique> getInspectionTechniques(Integer projectId, StepStatus stepStatus) throws SquareException
	{
		List<GwtInspectionTechnique> inspectionList = null;
		
		try
		{
			this.setValuesForAuthorization();
			inspectionList = inspectionTechniqueBusiness.getInspectionTechniques(projectId, stepStatus);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}

		return inspectionList;
	}

	
	
	public void removeInspectionTechnique(Integer projectId, GwtInspectionTechnique gwtInspection) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			this.inspectionTechniqueBusiness.removeInspectionTechnique(gwtInspection);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
	}

	
	public void updateInspectionTechnique(Integer projectId, GwtInspectionTechnique inspection) throws SquareException
	{	
		try
		{
			this.setValuesForAuthorization();
			this.inspectionTechniqueBusiness.updateInspectionTechnique(inspection, projectId);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
	}


	
	public void setValuesForAuthorization()
	{
		setValuesForAuthorization(this.inspectionTechniqueBusiness);
	}



}
