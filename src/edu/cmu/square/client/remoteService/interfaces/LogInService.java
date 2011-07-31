package edu.cmu.square.client.remoteService.interfaces;



import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.model.GWTAuthorization;
import edu.cmu.square.client.model.GwtUser;


/**
 * Purpose: This class provides the asynchronous services for getting steps,  
 * adding, removing, and editing users, and updating project settings.
 */
@RemoteServiceRelativePath("logIn.rpc")
public interface LogInService extends RemoteService
{
		/**
		 * Allows to Log In to the system, this method will keep track of the failed attempts
		 * and will lock the system after 5 
		 * @param userName 
		 * @param password 
		 * @return GWTLogIn this object contain all the information related to Log In
		 * such as sessionID, whether the account is lock and so on.
		 */
		GwtUser logIn(String userName, String password) throws  Exception;
		/**
		 * The logOut method will only delete the server sessionID. This method
		 * do not talk with the database. 
		 * @param userName of the user to be logged out.
		 * @param sessionID to validate the current server request.
		 * @return GWTLogIn this object contain all the information related to Log In
		 * such as sessionID, whether the account is lock and so on.
		 */
		void logOut() throws Exception;
		
		/**
		 * The logAccount will allow to lock a user account
		 * @param userName of the which account will be unlock.
		 * @param sessionID to validate the current server request.	
		 * @return GWTLogIn this object contain all the information related to Log In
		 * such as sessionID, whether the account is lock and so on.
		 */
		GwtUser lockAccount(String userName,String sessionID ) throws Exception;
		/**
		 * The unlockAccount will allow  site administrator do unlock a user account
		 * @param userName of the which account will be unlock.
		 * @param sessionID to validate the current server request.
		 * @return GWTLogIn this object contain all the information related to Log In
		 * such as sessionID, whether the account is lock and so on.
		 */
		GwtUser unlockAccount(String userName,String sessionID ) throws Exception;
		
		/**
		 * The loadRoles will allow to load the roles
		 * @param userName of the which account will be unlock.
		 * @param sessionID to validate the current server request.
		 * @return GWTLogIn this object contain all the information related to Log In
		 * such as sessionID, whether the account is lock and so on.
		 */
		GWTAuthorization loadRoles(GwtUser user,int projectID) throws  Exception;
		void setProjectToSession(int projectId);
		
		
		
		
		
}
