/**
 * 
 */
package edu.cmu.square.server.authorization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.server.business.interfaces.BaseBusinessInterface;
import edu.cmu.square.server.dao.interfaces.RoleDao;
import edu.cmu.square.server.dao.interfaces.UserDao;
import edu.cmu.square.server.dao.model.Role;
import edu.cmu.square.server.dao.model.User;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

/**
 * 
 *
 */
@Component
@Aspect
public class AuthorizationAspect
{
	private static Logger logger = Logger.getLogger(AuthorizationAspect.class);
	@Resource
	private RoleDao roleDao;

	@Resource
	private UserDao userDao;

	@Pointcut("execution(* edu.cmu.square.server.business..*.*(..))")
	public void anyBusinessOperation()
	{
	}

	/**
	 * This method would intercepts all business layer methods decorated with
	 * the @allowedRoles annotation. It would look for the userId parameter from
	 * the method, and retrieve the user from the database. Then it would match
	 * the roles of the database with the roles available in the @allowedRoles
	 * annotation. It will throw a NotAuthorizedException when the database
	 * roles do not contain any of the allowedRoles.
	 */
	@Before(value = "anyBusinessOperation()" + "&& target(bean) && @annotation(allowedRoles)", argNames = "bean, allowedRoles")
	public void authorize(JoinPoint jp, BaseBusinessInterface bean, AllowedRoles allowedRoles) throws SquareException
	{
		boolean authorized = false;
		boolean isAdmin=false;
		List<Role> roles = new ArrayList<Role>();
		logger.info("User name: " + bean.getUserName());
		logger.info("Project name: " + bean.getProjectName());

			//If there is not project in context identify
			List<User> users = userDao.getUsersbyUsername(bean.getUserName());
			if (users.size() == 1)
			{
				if (users.get(0).getIsAdmin())
				{
					isAdmin=true;
				}
			}

		logger.info("allowedRoles: " + Arrays.toString(allowedRoles.roles()));				
		for (Roles allowedRole : allowedRoles.roles())
		{
			if (allowedRole == Roles.All)
			{
				authorized = true;
				break;
			}
		}


		if (authorized == false)
		{
			roles = roleDao.getRoleByUsernameProjectName(bean.getUserName(), bean.getProjectName());
			
			if(isAdmin)
			{
				Role adminRole = new Role();
				adminRole.setName(Roles.Administrator.getRoleName());
				roles.add(adminRole);
			}
			
			logger.info("user roles " + roles);
			for (Roles allowedRole : allowedRoles.roles())
			{
				if (allowedRole == Roles.All)
				{
					authorized = true;
					break;
				}
				for (Role dbRole : roles)
				{
					if (allowedRole.getRoleName().equals(dbRole.getName()))
					{
						authorized = true;
					}
				}
			}
		}
		
		logger.info("Aspect triggered expired from aspect:" + bean.isSessionExpired() + " " + jp.toShortString());
		// Validate is the session is expired
		if (bean.isSessionExpired())
		{
			logger.error("expired from aspect: " + bean.isSessionExpired());
			SquareException se = new SquareException("Session expired");
			se.setType(ExceptionType.sessionTimeOut);
			throw se;
		}
		if (!authorized)
		{
			SquareException se = new SquareException(bean.getUserName() + " is not allowed to invoke " + jp.toShortString());
			se.setType(ExceptionType.authorization);
			throw se;
		}

	}

	@Pointcut("execution(* edu.cmu.square.server.remoteService..*.*(..))")
	public void anyServiceOperation()
	{
	}
	@Before(value = "anyServiceOperation() && target(bean)")
	public void setValuesForAuthorization(SquareRemoteServiceServlet bean)
	{
		bean.setValuesForAuthorization();
	}

}
