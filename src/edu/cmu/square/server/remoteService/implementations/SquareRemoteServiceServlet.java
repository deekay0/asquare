package edu.cmu.square.server.remoteService.implementations;

import java.beans.Introspector;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.gwtrpcspring.RemoteServiceUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.cmu.square.client.model.GWTAppProperties;
import edu.cmu.square.server.business.interfaces.BaseBusinessInterface;


public abstract class SquareRemoteServiceServlet extends RemoteServiceServlet
{
	private static Logger logger = Logger.getLogger(SquareRemoteServiceServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 3176204595263307327L;

	public String getUserName()
	{
		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();

		if (request == null)
		{
			request = getThreadLocalRequest();
		}

		HttpSession sessions = request.getSession();
		if (sessions.getAttribute("userName") != null)
		{
			return sessions.getAttribute("userName").toString();
		}
		return "anonymous";
	}

	/**
	 *Load properties method is a server side mechanism to load a property
	 * file, for application values. Such as logo, header title and so on. The
	 * GWT messages could not be changed at runtime because, they are hardcoded
	 * in the Javascript generated.
	 */

	public GWTAppProperties LoadProperties()
	{
		GWTAppProperties gwtPropeties = new GWTAppProperties();
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties");
		Properties props = new Properties();
		try
		{

			props.load(inputStream);

			gwtPropeties.setHeaderTitle1(props.getProperty("HeaderTitle1"));
			gwtPropeties.setHeaderTitle2(props.getProperty("HeaderTitle2"));
			gwtPropeties.setHeaderLogo(props.getProperty("HeaderLogo"));
			gwtPropeties.setHelpLink(props.getProperty("HelpLink"));
			gwtPropeties.setFooterTitle(props.getProperty("FooterTitle1"));
			gwtPropeties.setFooterLinkOrganizationName(props.getProperty("FooterlinkOrganizationName"));
			gwtPropeties.setFooterOrganizationURL(props.getProperty("FooterlinkOrganizationURL"));
			gwtPropeties.setFooterLabelCopyRight(props.getProperty("FooterlabelCopyRight"));
			
			return gwtPropeties;

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gwtPropeties;
	}

	/**
	 * Reset the counter of failed attempts when the user successfully logs into
	 * the application
	 */

	public void resetCounter(String userName)
	{
		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();

		if (request == null)
		{
			request = getThreadLocalRequest();
		}

		HttpSession sessions = request.getSession();
		sessions.setAttribute(userName + "_counter", null);
		sessions.setAttribute(userName + "_locked", "false");
	}
	/**
	 * Count the number of failed attempts in this session. If the count exceeds
	 * MAX_FAILED_LOGIN_ATTEMPTS then the function should return true indicating
	 * that the user should be locked out
	 * 
	 * @return
	 */
	public boolean countFailedLoginAttempts(String userName)
	{

		boolean LOCKED = false;
		final int MAX_FAILED_LOGIN_ATTEMPTS = 5; // number of log in attempts
													// before user is locked out
		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();

		if (request == null)
		{
			request = getThreadLocalRequest();
		}

		HttpSession sessions = request.getSession();
		int temp = 0;

		if (sessions.getAttribute(userName + "_counter") != null)
		{
			temp = Integer.parseInt(sessions.getAttribute(userName + "_counter").toString());
			sessions.setAttribute(userName + "_counter", temp + 1);
		}
		else
		{
			sessions.setAttribute(userName + "_counter", 1);// initialize the
															// count to one
		}
		temp = Integer.parseInt(sessions.getAttribute(userName + "_counter").toString());
		if (temp >= MAX_FAILED_LOGIN_ATTEMPTS)
		{
			LOCKED = true;
			sessions.setAttribute(userName + "_locked", "true");// initialize
																// the count to
																// one
		}
		else
		{
			LOCKED = false;
		}
		return LOCKED;
	}

	/**
	 * Returns wether a specific sessionID is valid on the server
	 * 
	 * @return
	 */
	public boolean isAuthenticated(String sessionID)
	{

		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();

		if (request == null)
		{
			request = getThreadLocalRequest();
		}

		HttpSession sessions = request.getSession();
		if (sessions.getAttribute("sessionID") != null)
		{
			if (sessions.getAttribute("sessionID").equals(sessionID))
			{
				return true;
			}
		}
		return false;

	}
	/**
	 * Determine if the user account for a given session is locked or not.
	 * 
	 * @return boolean which indicats if the account is blocked or not
	 */
	public boolean isLocked(String userName)
	{
		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();

		if (request == null)
		{
			request = getThreadLocalRequest();
		}

		HttpSession sessions = request.getSession();
		if (sessions.getAttribute(userName + "_locked") != null)
		{
			if (sessions.getAttribute(userName + "_locked").toString().equalsIgnoreCase("true"))
			{
				return true;
			}
		}
		return false;

	}
	/**
	 * this will be our indicator to determine if the session is expired after
	 * logged in
	 * 
	 * @return boolean which indicates if the session is expired
	 */
	public boolean isSessionExpired()
	{
		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();

		HttpSession sessions = request.getSession();

		if (sessions.getAttribute("sessionID") == null)
		{
			return true;
		}
		else
		{
			logger.info("----Session Value name: " + sessions.getAttribute("sessionID").toString());
		}
		return false;

	}

	/**
	 * This will set the session ID in a session variable
	 * 
	 * @param sessionID
	 *            the session identification string to be set
	 */
	public void storeSessionID(String sessionID)
	{
		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();
		if (request == null)
		{
			request = getThreadLocalRequest();
		}

		HttpSession sessions = request.getSession();
		sessions.setAttribute("sessionID", sessionID);
	}

	public void storeUserID(String userID)
	{
		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();

		if (request == null)
		{
			request = getThreadLocalRequest();
		}

		HttpSession sessions = request.getSession();
		sessions.setAttribute("userID", userID);
	}

	public void storeUserName(String userName)
	{
		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();

		if (request == null)
		{
			request = getThreadLocalRequest();
		}

		HttpSession sessions = request.getSession();
		sessions.setAttribute("userName", userName);
	}

	public void storeProjectName(String projectName)
	{
		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();

		if (request == null)
		{
			request = getThreadLocalRequest();
		}

		HttpSession sessions = request.getSession();
		sessions.setAttribute("projectName", projectName);
	}

	public String getProjectName()
	{
		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();

		if (request == null)
		{
			request = getThreadLocalRequest();
		}

		HttpSession sessions = request.getSession();
		if (sessions.getAttribute("projectName") != null)
		{
			return sessions.getAttribute("projectName").toString();
		}
		return "";
	}

	protected void cleanupSession()
	{
		HttpServletRequest request = RemoteServiceUtil.getThreadLocalRequest();

		if (request == null)
		{
			request = getThreadLocalRequest();
		}

		HttpSession session = request.getSession();
		session.removeAttribute("sessionID");
	}
	protected void setValuesForAuthorization(BaseBusinessInterface baseBusinessImpl)
	{

		baseBusinessImpl.setSessionExpired(isSessionExpired());
		logger.info("Session Expired: " + isSessionExpired());
		baseBusinessImpl.setProjectName(getProjectName());
		logger.info("Setting project name: " + getProjectName());
		baseBusinessImpl.setUserName(getUserName());
		logger.info("Setting user name: " + getUserName());
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getLogic(T businessLogic, Class<T> logicClass)
	{
		if (businessLogic == null)
		{
			WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
			businessLogic = (T) ctx.getBean(Introspector.decapitalize(logicClass.getSimpleName()) +"Impl");
		}
		return businessLogic;
	}

	public abstract void setValuesForAuthorization();
}
