package edu.cmu.square.server.remoteService.implementations;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GWTAuthorization;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.remoteService.interfaces.LogInService;
import edu.cmu.square.server.business.interfaces.LogInBusiness;
import edu.cmu.square.server.business.interfaces.ManageProjectBusiness;

@Service("LogInService")
public class LogInServiceImpl  extends SquareRemoteServiceServlet
                               implements LogInService
{
	@Resource
	private LogInBusiness businessLogic;

	@Resource
	private ManageProjectBusiness manageBusiness;
	
	private static final long serialVersionUID = 5872889170749121113L;


	public GwtUser logIn(String userName, String password) throws SquareException 
	{

		GwtUser user = getLogic(businessLogic,LogInBusiness.class).logIn(userName, password);
	
		if(user.isAuthenticated())
		{
			super.storeSessionID(user.getSessionID());
			super.storeUserName(user.getUserName());
			
			this.resetCounter(userName); // reset the counter for counting the number of failed log-in attempts
		} 
		else if (!user.isLocked())
		{
			user.setIsLocked(countFailedLoginAttempts(userName));
			
			if(user.isLocked())
			{
				getBusinessLogic().lockAccount(userName);
				this.resetCounter(userName);			
			}			
		}
		
		return user;
	}

	
	
	private LogInBusiness getBusinessLogic()
	{
		if (businessLogic == null) 
		{
		WebApplicationContext ctx = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(
				this.getServletContext());
		businessLogic = (LogInBusiness) ctx.getBean("logInBusinessImpl");
		}
		return businessLogic;
	}


	@Override
	public void logOut() throws SquareException
	{
		cleanupSession();
	}
	
	
	public GwtUser lockAccount(String userName, String sessionID) throws SquareException
	{
		// TODO Sneader implement lockAccount 
		return null;
	}


	
	public GwtUser unlockAccount(String userName, String sessionID) throws SquareException
	{
		// TODO Sneader to implement unlockAccount
		return null;
	}
	
	
	public GWTAuthorization loadRoles(GwtUser user, int projectID) 
	{
        return this.getBusinessLogic().getRoles(user, projectID);
	}
	
	
	public void setProjectToSession(int projectId)
	{	
		GwtProject gp;
		try
		{
			gp = this.manageBusiness.getProject(projectId);
			storeProjectName(gp.getName());
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	
	public void setValuesForAuthorization() {
		setValuesForAuthorization(manageBusiness);
		setValuesForAuthorization(getBusinessLogic());
		
		
	}
	
	
}
