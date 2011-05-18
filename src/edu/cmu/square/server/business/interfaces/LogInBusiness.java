package edu.cmu.square.server.business.interfaces;


import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GWTAuthorization;
import edu.cmu.square.client.model.GwtUser;

/**
 * Purpose: This class provides the asynchronous services for getting steps,  
 * adding, removing, and editing users, and updating project settings.
 */

public interface LogInBusiness extends BaseBusinessInterface {

		/**
		 * This returns a SessionID if the authentication was correct. If not it will return empty.
		 * @param userName the userName that is trying to log in to the tool. 
		 * @return GWTLogIn object which contains SessionID based on a UUID, universal identifier that is randomly generated.
		 */
	    GwtUser	 logIn(String userName, String password) throws SquareException;
	    
		/**
		 * Locks the user account after N attempts of inputing the wrong password for a user.
		 * @param userName user that will have the account locked.
		 * @return GWTLogIn object which contains SessionID based on a UUID, universal identifier that is randomly generated.
		 */
	    
	    GwtUser	 lockAccount(String userName) throws SquareException;
	    
		/**
		 * Unlocks the user account after the administrator executes.
		 * @param userName user that will have the account locked.
		 */
	    
	    void unlockAccount(String userName) throws SquareException;
	    
	    
	     GWTAuthorization getRoles(GwtUser userName, int projectID); 

}

