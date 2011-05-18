package edu.cmu.square.client.remoteService.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtUser;

@RemoteServiceRelativePath("manageSite.rpc")
public interface ManageSiteService extends RemoteService
{
	/**
	 * Gets all the users of the site
	 * @return List of GwtUsers
	 */
	List<GwtUser> getAllUsers() throws SquareException;
	
	GwtUser getUserInfo(Integer userID) throws SquareException;
	
	void updateUserProfile(GwtUser gwtUser, String password) throws SquareException;
	
	void updateUserNameEmailRole(GwtUser gwtUser) throws SquareException;
	
	void updateUserPassword(GwtUser gwtUser, String password, boolean email) throws SquareException;
	
	boolean removeUser(GwtUser userToRemove) throws SquareException;
	
	GwtUser createUser(GwtUser newUser, String password, boolean emailUser) throws SquareException;
}
