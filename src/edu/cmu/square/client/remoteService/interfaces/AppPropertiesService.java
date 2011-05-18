/**
 * 
 */
package edu.cmu.square.client.remoteService.interfaces;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GWTAppProperties;


/**
 * @author mlengarc
 *
 */
@RemoteServiceRelativePath("appPropertiesService.rpc")
public interface AppPropertiesService extends RemoteService {
	
	/**
	 * Allows to Log In to the system, this method will keep track of the failed attempts
	 * and will lock the system after 5 
	 * @param userName 
	 * @param password 
	 * @return GWTLogIn this object contain all the information related to Log In
	 * such as sessionID, whether the account is lock and so on.
	 */
	GWTAppProperties loadProperties() throws  SquareException;
	
	
	 Boolean isSessionTimeOut() throws SquareException; 
	
}
