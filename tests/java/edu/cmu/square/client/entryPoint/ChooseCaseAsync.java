package edu.cmu.square.client.entryPoint;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import edu.cmu.square.client.model.GwtAsquareCase;
import edu.cmu.square.client.remoteService.interfaces.ChooseCaseServiceAsync;
import edu.cmu.square.client.remoteService.interfaces.ChooseCaseService;

public class ChooseCaseAsync extends GWTTestCase
{
	
	
	public String getModuleName() 
	{    
		return "edu.cmu.square.SquareGwt"; 
	} 
	
	
	public void testGetProjectsForUser() 
	{
		ChooseCaseServiceAsync chooseService = GWT.create(ChooseCaseService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) chooseService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "chooseCase.rpc");
		
		chooseService.getCasesForUser( 
				new AsyncCallback<List<GwtAsquareCase>>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(List<GwtAsquareCase> result)
					{
						// TODO Auto-generated method stub
						finishTest();
						
					}
					
				}
		);
		
		
		delayTestFinish(600000);
	}




	@Override
	protected void gwtTearDown() throws Exception
	{
		// TODO Auto-generated method stub
		super.gwtTearDown();
	}

}
