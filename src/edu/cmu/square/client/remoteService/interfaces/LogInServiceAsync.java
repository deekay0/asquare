package edu.cmu.square.client.remoteService.interfaces;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.model.GWTAuthorization;
import edu.cmu.square.client.model.GwtUser;

public interface LogInServiceAsync
{

	
	/**
	 * Allows to Log In to the system, this method will keep track of the failed attempts
	 * and will lock the system after 5 
	 * @param userName 
	 * @param password 
	 * @return GWTLogIn this object contain all the information related to Log In
	 * such as sessionID, whether the account is lock and so on.
	 */
	void logIn(String userName,String password, AsyncCallback<GwtUser> result) ;
	/**
	 * The logOut method will only delete the server sessionID. This method
	 * do not talk with the database. 
	 * @param userName of the user to be logged out.
	 * @param sessionID to validate the current server request.
	 * @return GWTLogIn this object contain all the information related to Log In
	 * such as sessionID, whether the account is lock and so on.
	 */
	void logOut(AsyncCallback<Void> result) ;
	
	/**
	 * The logAccount will allow to lock a user account
	 * @param userName of the which account will be unlock.
	 * @param sessionID to validate the current server request.	
	 * @return GWTLogIn this object contain all the information related to Log In
	 * such as sessionID, whether the account is lock and so on.
	 */
	void lockAccount(String userName,String sessionID, AsyncCallback<GwtUser> result) ;
	/**
	 * The unlockAccount will allow  site administrator do unlock a user account
	 * @param userName of the which account will be unlock.
	 * @param sessionID to validate the current server request.
	 * @return GWTLogIn this object contain all the information related to Log In
	 * such as sessionID, whether the account is lock and so on.
	 */
	void unlockAccount(String userName,String sessionID, AsyncCallback<GwtUser> result) ;

	/**
	 * The loadRoles will allow  site administrator do unlock a user account
	 * @param userName of the which account will be unlock.
	 * @param sessionID to validate the current server request.
	 * @return GWTLogIn this object contain all the information related to Log In
	 * such as sessionID, whether the account is lock and so on.
	 */
	
	void  loadRoles(GwtUser user,int projectID,AsyncCallback<GWTAuthorization> result);
	void setProjectToSession(int projectID, AsyncCallback<Void> asyncCallback);
	 
}