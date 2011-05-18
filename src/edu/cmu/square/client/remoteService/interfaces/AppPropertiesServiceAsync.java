/**
 * 
 */
package edu.cmu.square.client.remoteService.interfaces;


import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.model.GWTAppProperties;


/**
 * @author kaalpurush
 *               
 *               
 */              
public interface AppPropertiesServiceAsync {
	
	void  loadProperties(AsyncCallback<GWTAppProperties> result);
	
	
	void isSessionTimeOut(AsyncCallback<Boolean> result); 
}
