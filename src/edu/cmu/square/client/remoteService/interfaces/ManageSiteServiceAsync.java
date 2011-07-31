package edu.cmu.square.client.remoteService.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.model.GwtUser;


public interface ManageSiteServiceAsync
{
	void getAllUsers(AsyncCallback<List<GwtUser>> callback);
	
	void getUserInfo(Integer userID, AsyncCallback<GwtUser> callback);
	
	void updateUserProfile(GwtUser gwtUser, String password, AsyncCallback<Void> callback); 
	
	void updateUserNameEmailRole(GwtUser gwtUser, AsyncCallback<Void> callback); 
	
	void updateUserPassword(GwtUser gwtUser, String password, boolean email, AsyncCallback<Void> callback); 
	
	void removeUser(GwtUser userToRemove, AsyncCallback<Boolean> callback);
	
	void createUser(GwtUser newUser, String password, boolean emailPassword, AsyncCallback<GwtUser> callback);
}
