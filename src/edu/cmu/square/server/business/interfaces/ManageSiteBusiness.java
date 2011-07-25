package edu.cmu.square.server.business.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtUser;

public interface ManageSiteBusiness extends BaseBusinessInterface
{
	
	/**
	 * Gets all the users of the site
	 * @return List of GwtUsers
	 */
	List<GwtUser> getAllUsers() throws SquareException;
	
	GwtUser getUserInfo(Integer userID) throws SquareException;
	
	void updateUserNameEmailRole(GwtUser gwtUser) throws SquareException;
	
	void updateUserProfile(GwtUser gwtUser, String password) throws SquareException;
	
	void updateUserPassword(GwtUser gwtUser, String password, boolean email)throws SquareException;
	
	boolean removeUser(GwtUser user) throws SquareException;
	
	GwtUser createUser(GwtUser newUser, String password, boolean emailPassword) throws SquareException;
	
	
}
