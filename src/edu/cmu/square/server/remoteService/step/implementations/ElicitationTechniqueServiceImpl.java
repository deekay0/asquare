package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtTechnique;
import edu.cmu.square.client.model.GwtTechniqueEvaluationValue;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.remoteService.step.interfaces.ElicitationTechniqueService;
import edu.cmu.square.server.business.step.interfaces.ElicitationTechniqueBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

@Service
public class ElicitationTechniqueServiceImpl extends SquareRemoteServiceServlet implements ElicitationTechniqueService
{

	private static final long serialVersionUID = -5725843620206249786L;

	@Resource
	private ElicitationTechniqueBusiness elicitationTechniqueBusiness;

	
	public GwtEvaluation addEvaluation(GwtProject gwtProject, GwtEvaluation gwtElicitEvaluation) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			this.elicitationTechniqueBusiness.addEvaluation(gwtProject, gwtElicitEvaluation);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
		
		return gwtElicitEvaluation;
	}

	
	public GwtTechnique addTechnique(GwtProject gwtProject, GwtTechnique gwtElicititationTechnique) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();

			this.elicitationTechniqueBusiness.addTechnique(gwtProject, gwtElicititationTechnique);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
		
		return gwtElicititationTechnique;
	}

	
	public List<GwtEvaluation> getEvaluations(GwtProject gwtProject, StepStatus stepStatus) throws SquareException
	{
		List<GwtEvaluation> evaluationList = null;
		
		try
		{
			this.setValuesForAuthorization();
			evaluationList = elicitationTechniqueBusiness.getEvaluations(gwtProject, stepStatus);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}

		return evaluationList;
	}

	
	public List<GwtTechnique> getTechniques(GwtProject gwtProject, StepStatus stepStatus) throws SquareException
	{
		List<GwtTechnique> techniques = null;
		
		try
		{
			this.setValuesForAuthorization();
			techniques = elicitationTechniqueBusiness.getTechniques(gwtProject, stepStatus);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}

		return techniques;
	}

	
	public void removeEvaluation(GwtEvaluation evaluation) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			this.elicitationTechniqueBusiness.removeEvaluation(evaluation);
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

	
	public void removeTechnique(GwtTechnique technique, Integer projectId) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			this.elicitationTechniqueBusiness.removeTechnique(technique, projectId);
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

	
	public void updateEvaluation(GwtEvaluation elicitation, GwtProject gwtProject) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			this.elicitationTechniqueBusiness.updateEvaluation(elicitation, gwtProject);
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

	
	public void updateTechnique(GwtTechnique gwtElicitationTechnique, GwtProject gwtProject) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			elicitationTechniqueBusiness.updateTechnique(gwtElicitationTechnique, gwtProject);
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

	
	public List<GwtTechniqueEvaluationValue> getRateValues(int projectID) throws SquareException
	{

		return elicitationTechniqueBusiness.getRateValues(projectID);
	}

	
	public void setRateValue(int projectID, int techniqueID, int evaluationID, int value) throws SquareException
	{
		elicitationTechniqueBusiness.setRateValue(projectID, techniqueID, evaluationID, value);

	}

	
	public void setValuesForAuthorization()
	{
		setValuesForAuthorization(elicitationTechniqueBusiness);
	}

}
