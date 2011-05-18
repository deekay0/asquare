package edu.cmu.square.server.remoteService.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.gwtrpcspring.RemoteServiceUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.remoteService.interfaces.StepService;
import edu.cmu.square.server.business.interfaces.StepBusiness;
@Service
public class StepServiceImpl extends SquareRemoteServiceServlet implements StepService
{

	private static final long serialVersionUID = 4548900135693762692L;
	@Resource
	private StepBusiness stepBusiness;
	

	public void setValuesForAuthorization()
	{
		setValuesForAuthorization(stepBusiness);

	}


	public boolean isStepClosed(String description, Integer projectId) throws SquareException
	{
		return stepBusiness.isStepClosed(description, projectId);
	}


	public void updateStepStatus(int projectId, int stepId, String status) throws SquareException
	{
		 stepBusiness.updateStepStatus(projectId, stepId, status);
		
	}

	
	@Override
	public List<GwtStepVerficationResult> getStepsWithVerification(GwtProject project) throws SquareException
	{
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(RemoteServiceUtil.getThreadLocalContext());
		
		return stepBusiness.verifyAllSteps(project.getId(), ctx);
	}

}
