package edu.cmu.square.server.business.step.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.GwtTechnique;
import edu.cmu.square.client.model.GwtTechniqueEvaluationValue;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.step.interfaces.ElicitationTechniqueBusiness;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.TechniqueDao;
import edu.cmu.square.server.dao.interfaces.TechniqueEvaluationCriteriaDao;
import edu.cmu.square.server.dao.interfaces.TechniqueEvaluationDao;
import edu.cmu.square.server.dao.model.EvaluationCriteria;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Technique;
import edu.cmu.square.server.dao.model.TechniqueEvaluationCriteria;
import edu.cmu.square.server.dao.model.TechniqueTechniqueEvaluationId;

@Service
@Scope("prototype")
public class ElicitationTechniqueBusinessImpl extends BaseBusinessImpl implements ElicitationTechniqueBusiness
{

	@Resource
	private TechniqueDao techniqueDao;
	@Resource
	private ProjectDao projectDao;

	@Resource
	private TechniqueEvaluationDao evaluationDao;
	@Resource
	private TechniqueEvaluationCriteriaDao techniqueEvaluationDao;

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer,Roles.Administrator})
	public void addEvaluation(GwtProject gwtProject, GwtEvaluation gwtEvaluation) throws SquareException
	{
		Project currentProject = new Project(gwtProject);
		EvaluationCriteria evaluationToAdd = new EvaluationCriteria(gwtEvaluation);
		evaluationToAdd.setProject(currentProject);

		// get the evaluation by name and check for duplicates
		List<EvaluationCriteria> evaluations = this.evaluationDao.getEvaluationsByNameAndProject(evaluationToAdd.getName(), gwtProject.getId());
		if (!evaluations.isEmpty())
		{
			SquareException se = new SquareException("Already exists");
			se.setType(ExceptionType.duplicateName);
			throw se;
		}
		this.evaluationDao.create(evaluationToAdd);
		gwtEvaluation.setEvaluationId(evaluationToAdd.getId());

	}

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer,Roles.Administrator})
	public void addTechnique(GwtProject gwtProject, GwtTechnique gwtElicitationTechnique) throws SquareException
	{
		Project currentProject = new Project(gwtProject);
		Technique techniqueToAdd = new Technique(gwtElicitationTechnique);

		// get the technique by name and check for duplicates
		List<Technique> techniques = this.techniqueDao.getTechniquesByNameAndProject(techniqueToAdd.getName(), gwtProject.getId());
		if (!techniques.isEmpty())
		{
			SquareException se = new SquareException("Already exists");
			se.setType(ExceptionType.duplicateName);
			throw se;
		}

		this.techniqueDao.addTechniqueToProject(currentProject, techniqueToAdd);
		gwtElicitationTechnique.setTechniqueId(techniqueToAdd.getId());
	}

	@AllowedRoles(roles = {Roles.All})
	public List<GwtEvaluation> getEvaluations(GwtProject gwtProject, StepStatus stepStatus) throws SquareException
	{
		List<GwtEvaluation> evaluationList = new ArrayList<GwtEvaluation>();
		Project project = new Project(gwtProject);
		List<EvaluationCriteria> evaluations = evaluationDao.getEvaluationsByProject(project);

		if (evaluations == null)
		{
			return evaluationList;
		}
		
		for (EvaluationCriteria t : evaluations)
		{
			evaluationList.add(t.createGwtTechnique());
		}

		return evaluationList;
	}

	@AllowedRoles(roles = {Roles.All})
	public List<GwtTechnique> getTechniques(GwtProject gwtProject, StepStatus stepStatus) throws SquareException
	{
		List<GwtTechnique> techniqueList = new ArrayList<GwtTechnique>();
		Project project = new Project(gwtProject);
		List<Technique> techniques = techniqueDao.getTechniquesByProject(project);

		if (techniques == null)
		{
			return techniqueList;
		}
		
		for (Technique t : techniques)
		{
			techniqueList.add(t.createGwtTechnique());
		}

		return techniqueList;
	}

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer})
	public void removeEvaluation(GwtEvaluation gwtElicitEvaluation) throws SquareException
	{
		evaluationDao.deleteById(gwtElicitEvaluation.getEvaluationId());
	}

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer})
	public void removeTechnique(GwtTechnique gwtElicitTechnique, Integer projectId) throws SquareException
	{

		Project p = projectDao.fetch(projectId);
//		if (p.getSecurityTechnique() != null && p.getSecurityTechnique().getId().equals(gwtElicitTechnique.getTechniqueId()))
//		{
//			SquareException se = new SquareException();
//			se.setType(ExceptionType.constraintViolated);
//			throw se;
//		}
//		
//		if (p.getTechniques().size() == 1)
//		{
//			SquareException se = new SquareException();
//			se.setType(ExceptionType.onlyOneTechnique);
//			throw se;
//		}
//		
		try
		{
			techniqueDao.deleteById(gwtElicitTechnique.getTechniqueId());
		}
		catch (Throwable t)
		{
			throw new SquareException(t);
		}
	}

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer})
	public void updateEvaluation(GwtEvaluation gwtEvaluation, GwtProject gwtProject) throws SquareException
	{
		if (gwtEvaluation == null)
		{
			throw new SquareException("gwtEvaluation should not be null.");
		}

		if (gwtEvaluation.getEvaluationId() <= 0)
		{
			throw new SquareException("gwtEvaluation must have an id.");
		}

		try
		{

			// get the evaluation by name and check for duplicates
			List<EvaluationCriteria> evaluations = this.evaluationDao.getEvaluationsByNameAndProject(gwtEvaluation.getTitle(), gwtProject.getId());
			if (evaluations.size() > 0)
			{
				if (!evaluations.get(0).getId().equals(gwtEvaluation.getEvaluationId()))
				{
					SquareException se = new SquareException("Already exists");
					se.setType(ExceptionType.duplicateName);
					throw se;
				}
			}
			EvaluationCriteria currentEvaluation = new EvaluationCriteria(gwtEvaluation);
			currentEvaluation.setProject(new Project(gwtProject));
			evaluationDao.update(currentEvaluation);
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

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer})
	public void updateTechnique(GwtTechnique gwtElicitationTechnique, GwtProject gwtProject) throws SquareException
	{
		if (gwtElicitationTechnique == null)
		{
			throw new SquareException("gwtElicitation technique should not be null.");
		}

		if (gwtElicitationTechnique.getTechniqueId() <= 0)
		{
			throw new SquareException("gwtElicitaiton technique must have an id.");
		}

		try
		{
			// get the technique by name and check for duplicates
			List<Technique> techniques = this.techniqueDao.getTechniquesByNameAndProject(gwtElicitationTechnique.getTitle(), gwtProject.getId());
			if (techniques.size() > 0)
			{
				if (!techniques.get(0).getId().equals(gwtElicitationTechnique.getTechniqueId()))
				{
					SquareException se = new SquareException("Already exists");
					se.setType(ExceptionType.duplicateName);
					throw se;
				}
			}

			Technique currentTechnique = new Technique(gwtElicitationTechnique);
			currentTechnique.setProject(new Project(gwtProject));
			techniqueDao.update(currentTechnique);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException("updating technique failed", t);
		}

	}

	@AllowedRoles(roles = {Roles.All})
	public List<GwtTechniqueEvaluationValue> getRateValues(int projectID) throws SquareException
	{
		Project project = new Project();
		project.setId(projectID);

		List<GwtTechniqueEvaluationValue> list = new ArrayList<GwtTechniqueEvaluationValue>();
		List<TechniqueEvaluationCriteria> hbnList = techniqueEvaluationDao.getAllValues(project);

		for (TechniqueEvaluationCriteria item : hbnList)
		{
			GwtTechniqueEvaluationValue gwtItem = new GwtTechniqueEvaluationValue();
			gwtItem.setEvaluationId(item.getId().getEid());
			gwtItem.setTechniqueId(item.getId().getTid());
			gwtItem.setValue(item.getValue());

			list.add(gwtItem);
		}

		return list;
	}

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer, Roles.Requirements_Engineer})
	public void setRateValue(int projectID, int techniqueID, int evaluationCriteriaID, int value) throws SquareException
	{

		TechniqueEvaluationCriteria tec = new TechniqueEvaluationCriteria();
		TechniqueTechniqueEvaluationId id = new TechniqueTechniqueEvaluationId();
		id.setTid(techniqueID);
		id.setEid(evaluationCriteriaID);
		id.setPid(projectID);

		tec.setId(id);
		tec.setValue(value);

		TechniqueEvaluationCriteria tecEva = techniqueEvaluationDao.fetch(id);

		if (tecEva == null)
		{

			techniqueEvaluationDao.create(tec);
		}
		else
		{
			techniqueEvaluationDao.update(tec);
		}

	}

	@AllowedRoles(roles = {Roles.All})
	public int getRateValue(int projectID, int techniqueID, int evaluationCriteriaID) throws SquareException
	{

		int result = -1;

		TechniqueTechniqueEvaluationId id = new TechniqueTechniqueEvaluationId();
		id.setTid(techniqueID);
		id.setEid(evaluationCriteriaID);
		id.setPid(projectID);

		TechniqueEvaluationCriteria tecEva = techniqueEvaluationDao.fetch(id);;

		if (tecEva == null)
		{
			result = -1;

		}
		else
		{
			result = tecEva.getValue();
		}

		return result;

	}
	
	public void loadDefaultTechniques(int projectId, List<GwtTechnique> techniques) throws SquareException
	{
		for (GwtTechnique technique : techniques)
		{
			this.addTechnique(new GwtProject(projectId), technique);
		}

	}
	public void loadDefaultEvaluations(int projectId, List<GwtEvaluation> evaluations) throws SquareException
	{
		for (GwtEvaluation evaluation : evaluations)
		{
			this.addEvaluation(new GwtProject(projectId), evaluation);
		}
	}


	@Override
	public StepEnum getStepDescription() throws SquareException
	{
		return StepEnum.Select_Elicitation_Technique;
	}


	@Override
	public GwtStepVerficationResult verifyStep(Project project) throws SquareException
	{
		GwtStepVerficationResult result = new GwtStepVerficationResult();

		

		

//		if (project.isSecurity())
//		{
//			if (project.getSecurityTechnique() == null)
//			{
//				result.getMessages().add("The security requirements elicitation technique has not been selected!");
//				result.setHasWarning(true);
//
//			}
//		}
		return result;
	}
	
}
