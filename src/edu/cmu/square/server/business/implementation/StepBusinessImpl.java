package edu.cmu.square.server.business.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtStep;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.server.business.interfaces.StepBusiness;
import edu.cmu.square.server.business.step.interfaces.CategorizeRequirementsBusiness;
import edu.cmu.square.server.business.step.interfaces.PrioritizeRequirementBusiness;
import edu.cmu.square.server.business.step.interfaces.StepBusinessInterface;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.ProjectStepDao;
import edu.cmu.square.server.dao.interfaces.StepDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectStep;
import edu.cmu.square.server.dao.model.ProjectStepId;
import edu.cmu.square.server.dao.model.Step;

@Service
public class StepBusinessImpl extends BaseBusinessImpl implements StepBusiness
{

	private static Logger logger = Logger.getLogger(StepBusinessImpl.class);
	
	@Resource ProjectDao projectDao;
	
	@Resource
	private StepDao stepDao;

	@Resource
	private ProjectStepDao projectStepDao;

	
	@Resource
	private PrioritizeRequirementBusiness prioritizeBusiness;
	
	@Resource CategorizeRequirementsBusiness categoryBusiness;

	public boolean isStepClosed(String description, Integer projectId) throws SquareException
	{
		String status = stepDao.isStepClosed(description, projectId);
		try
		{
			if (StepStatus.valueOf(status) == StepStatus.Complete)
			{
				return true;
			}
		}
		catch (Throwable ex)
		{
			return false;
		}
		return false;
	}

	public void updateStepStatus(int projectId, int stepId, String status) throws SquareException
	{
		ProjectStepId projectStepId = new ProjectStepId();
		projectStepId.setStepId(stepId);
		projectStepId.setProjectId(projectId);
		ProjectStep step = projectStepDao.fetch(projectStepId);
		step.setStatus(status);
		if (step.getStep().getDescription().equals("Step 8: Prioritize Requirements")
				&& status.equals(StepStatus.Complete.getLabel()))
		{
			prioritizeBusiness.setProjectName(getProjectName());
			prioritizeBusiness.setUserName(getUserName());
			prioritizeBusiness.setFinalPriorities(projectId);
		}
		projectStepDao.update(step);

	}
	
	
	@Override
	public void createStepsForProject(GwtProject project)
	{
		List<Step> steps;
		if(project.getCases().getId()==1)
		{
			steps = stepDao.getCase1Steps();
		}
		else
		{
			steps = stepDao.getCase3Steps();
		}
		  
		System.out.println("create new project....case:"+project.getCases().getId()+"  size:"+steps.size());
		
		   for(Step s: steps)
		   {
			   /*if (project.isSecurity()&&!project.isPrivacy()&& s.isSecurity())
			   {
				   ProjectStep projectStep = new ProjectStep();
				   ProjectStepId projectStepId = new ProjectStepId();
				   
				   projectStepId.setProjectId(project.getId());
				   projectStepId.setStepId(s.getId());
				   
				   projectStep.setId(projectStepId);
				   projectStep.setStatus(StepStatus.NotStarted.getLabel());
				   
				   projectStepDao.create(projectStep);
			   }*/
			   ProjectStep projectStep = new ProjectStep();
			   ProjectStepId projectStepId = new ProjectStepId();
				   
			   projectStepId.setProjectId(project.getId());
			   projectStepId.setStepId(s.getId());
				   
			   projectStep.setId(projectStepId);
			   projectStep.setStatus(StepStatus.NotStarted.getLabel());
				   
			   projectStepDao.create(projectStep);
		   }
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<GwtStepVerficationResult> verifyAllSteps(Integer projectId, WebApplicationContext ctx) throws SquareException
	{
		Map<String, StepBusinessInterface> beanMap = ctx.getBeansOfType(StepBusinessInterface.class);
		List<GwtStepVerficationResult> result = new ArrayList<GwtStepVerficationResult>();
		Project project = projectDao.fetch(projectId);
		Set<Step> steps = project.getSteps();
		GwtStepVerficationResult r = null;

		
		
		if (steps.isEmpty())
		{
			throw new SquareException("No steps in the database");
		}
		for (Step s : steps)
		{
			
			GwtStep gs = s.createGwtStep(projectId);
			
			//find the logic
			for (StepBusinessInterface sb: beanMap.values())
			{
				System.out.println("ddddddd.....  "+sb.getStepDescription().getDescription() + "   "+ gs.getDescription());
				//if (sb.getStepDescription().getDescription().equals(gs.getDescription()))
				//{
					r =  sb.verifyStep(project);
					if (r==null)
					{
						System.out.println("wrongwrong.......");
						logger.info("null business " + sb);
					}
					break;
				//}
			}
			r.setStep(gs);
			result.add(r);
		}
		
		//for(GwtStepVerficationResult s: result)
			//System.out.println("step business impl....."+s.getStep().getDescription());
		
		return result;
	}

}