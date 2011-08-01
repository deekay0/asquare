package edu.cmu.square.server.remoteService.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.remoteService.interfaces.ManageSiteService;
import edu.cmu.square.server.business.interfaces.ManageSiteBusiness;


@Service
public class ManageSiteServiceImpl extends SquareRemoteServiceServlet implements ManageSiteService
{

	@Resource
	private ManageSiteBusiness manageSiteValidator;
	
	private static final long serialVersionUID = 3193285364104147719L;

	
	@Override
	public List<GwtUser> getAllUsers() throws SquareException
	{
		return manageSiteValidator.getAllUsers();
	}

	
	@Override
	public boolean removeUser(GwtUser userToRemove) throws SquareException
	{
		return manageSiteValidator.removeUser(userToRemove);
	}

	
	@Override
	public void setValuesForAuthorization() {
		setValuesForAuthorization(manageSiteValidator);
		
	}

	@Override
	public GwtUser getUserInfo(Integer userID) throws SquareException
	{
		return manageSiteValidator.getUserInfo(userID);
	}

	@Override
	public void updateUserNameEmailRole(GwtUser gwtUser) throws SquareException
	{
		manageSiteValidator.updateUserNameEmailRole(gwtUser);
	}
	

	@Override
	public void updateUserProfile(GwtUser gwtUser, String password) throws SquareException
	{
		manageSiteValidator.updateUserProfile(gwtUser, password);
	}
	
	@Override
	public void updateUserPassword(GwtUser gwtUser, String password, boolean email) throws SquareException
	{
		manageSiteValidator.updateUserPassword(gwtUser, password, email);
	}


	@Override
	public GwtUser createUser(GwtUser newUser, String password, boolean emailPassword) throws SquareException
	{
		return manageSiteValidator.createUser(newUser, password, emailPassword);
	}


}
