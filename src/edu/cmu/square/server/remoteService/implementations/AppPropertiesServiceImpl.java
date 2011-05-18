package edu.cmu.square.server.remoteService.implementations;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GWTAppProperties;
import edu.cmu.square.client.remoteService.interfaces.AppPropertiesService;


@Service
public class AppPropertiesServiceImpl extends SquareRemoteServiceServlet implements AppPropertiesService
{

	private static final long serialVersionUID = 1L;

	public GWTAppProperties loadProperties() throws SquareException
	{
		GWTAppProperties appProperties = new GWTAppProperties();
		try
		{
			appProperties = LoadProperties();

		}
		
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		return appProperties;
	}
	
	public Boolean isSessionTimeOut() throws SquareException
	{
		return new Boolean(this.isSessionExpired());
		
	}

	public void setValuesForAuthorization()
	{
		// TODO Auto-generated method stub

	}

}
