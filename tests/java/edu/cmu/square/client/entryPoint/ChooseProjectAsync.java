package edu.cmu.square.client.entryPoint;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.remoteService.interfaces.ChooseProjectService;
import edu.cmu.square.client.remoteService.interfaces.ChooseProjectServiceAsync;

public class ChooseProjectAsync extends GWTTestCase
{
	
	
	public String getModuleName() 
	{    
		return "edu.cmu.square.SquareGwt"; 
	} 
	
	
	public void testGetProjectsForUser() 
	{
		ChooseProjectServiceAsync chooseService = GWT.create(ChooseProjectService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) chooseService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "chooseProject.rpc");
		
		chooseService.getProjectsForUser(1, 1,
				new AsyncCallback<List<GwtProject>[]>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(List<GwtProject>[] result)
					{
						
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
