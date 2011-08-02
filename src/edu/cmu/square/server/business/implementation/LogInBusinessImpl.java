package edu.cmu.square.server.business.implementation;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GWTAuthorization;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.server.business.interfaces.LogInBusiness;
import edu.cmu.square.server.dao.interfaces.RoleDao;
import edu.cmu.square.server.dao.interfaces.UserDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Role;
import edu.cmu.square.server.dao.model.User;

@Service
@Scope("prototype")
public class LogInBusinessImpl extends BaseBusinessImpl implements LogInBusiness 
{

	@Resource
	private UserDao userDao;
	@Resource
	private RoleDao userRoleDao;
	
	
	
	public GwtUser logIn(String userName, String password) throws SquareException
	{
		if (password.matches("'")) 
		{
			SquareException se  = new SquareException("password should not contain single-quote(')");
			se.setType(ExceptionType.constraintViolated);
			throw se;
		}
		GwtUser user = new GwtUser();
		List<User> users = userDao.getUsersbyUsername(userName);
		
		if (users.size() == 0)
		{
			user.setUserName(userName);
			user.setAuthenticated(false);
			user.setSessionID("");
			return user;
		}
		
		//We should never get more than one user for a given username/password combination
		if (users.size() > 1)
		{
			SquareException se  = new SquareException("Multiple users exist for " + userName);
			se.setType(ExceptionType.duplicateName);
			throw se;
		}
		
		User currentUser = users.get(0);
		
		if( currentUser.getPassword().equals(password))
		{
		   
			if(currentUser.isLocked())
			{
				user.setAuthenticated(false);
				user.setIsLocked(true);	
				user.setUserName(userName);
			}
			else
			{
				user.setUserId(currentUser.getId());
				user.setAuthenticated(true);
				user.setIsLocked(false);
				user.setSessionID(UUID.randomUUID().toString());	
				user.setUserName(userName);
				user.setFullName(currentUser.getFullName());
				user.setDepartment(currentUser.getDepartment());
				user.setEmailAddress(currentUser.getEmail());
				user.setLocation(currentUser.getLocation());
				user.setOrganization(currentUser.getOrganization());
				user.setPhone(currentUser.getPhone());
				user.setSkipTeachStep(currentUser.getSkipTeachStep());
				user.setIsAdmin(currentUser.getIsAdmin());
			}
		}
		else
		{
			user.setAuthenticated(false);
		}
		
	
		return user;
		
	}


	public GwtUser lockAccount(String userName) throws SquareException
	{
		GwtUser user = new GwtUser();
		user.setUserName(userName);
		user.setAuthenticated(false);
		user.setSessionID("");
		user.setIsLocked(true);
		user.setIsAdmin(false);
		userDao.setLockOnAccount(userName);
		return user;
	}
	

	public void unlockAccount(String userName) throws SquareException
	{
		List<User> users = userDao.getUsersbyUsername(userName);
		if (users.size() == 0)
		{
			throw new SquareException("There are no users having the name " + userName);
		}
		
		if (users.size() > 1)
		{
			throw new SquareException("Multiple users exist for " + userName);
		}
		else
		{
			users.get(0).setLocked(false);
		}
	}
	
	
	
	public GWTAuthorization getRoles(GwtUser userName, int projectID) 
	{		
		User user = new User(userName, "");
		Project project = new Project();
		project.setId(projectID);
		
		List<Role> roles = this.userRoleDao.getRoleByUsernameProject(user, project);
		GWTAuthorization obj = new GWTAuthorization();
		
		String arry[] = new String[roles.size()];
		for(int i = 0; i < roles.size(); i++)
		{
			arry[i] = roles.get(i).getName();
			String roleName = roles.get(i).getName().trim();
			ProjectRole roleEnumType = ProjectRole.valueOf(roleName.replaceAll(" ","_"));
			
			obj.getRoles().add(roleEnumType);
		}

		return obj;
	}
	


}
