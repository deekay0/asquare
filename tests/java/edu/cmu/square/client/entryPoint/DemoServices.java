package edu.cmu.square.client.entryPoint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.remoteService.interfaces.LogInService;
import edu.cmu.square.client.remoteService.interfaces.LogInServiceAsync;


public class DemoServices extends GWTTestCase 
{  
	
	/**   * Must refer to a valid module that sources this class.   */  
	public String getModuleName() 
	{    
		return "edu.cmu.square.SquareGwt"; 
	} 
	
	/**   * Add as many tests as you like.   */ 

	public void testSimple()
	{    
		LogInServiceAsync loginService = GWT.create(LogInService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) loginService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "logIn.rpc");
		loginService.logIn("nancy", "mead", new AsyncCallback<GwtUser>()
		{
			
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub
			                                 
			    fail("big time failure");
			    
			}

			
			public void onSuccess(GwtUser result)
			{
				 
				 assertTrue(result.getUserId()!=null);
				 finishTest();
				 
				
			}
		});
		
		delayTestFinish(600000);
	}
}
	