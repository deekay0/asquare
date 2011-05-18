package edu.cmu.square.client.exceptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

import edu.cmu.square.client.ui.chooseProject.HomePilot;
import edu.cmu.square.client.ui.core.content.RPCCallsMessages;

public class ExceptionHelper
{
	private static RPCCallsMessages messages = (RPCCallsMessages) GWT.create(RPCCallsMessages.class);
	
	public static void SquareRootRPCExceptionHandler(Throwable caught, String action)
	{
		if (caught instanceof SquareException)
		{
			SquareException se = (SquareException) caught;
			switch (se.getType())
			{
				case authorization :
					Window.alert(messages.errorAuthorization(action));
					break;
				
				case sessionTimeOut :
					
					History.newItem(HomePilot.generateNavigationId(HomePilot.PageId.logout)+"?timeout=1");
					break;

				default :
					Window.alert(messages.errorAction(action));
					break;
			}
		}
		else
		{
			Window.alert(messages.errorGeneral(action));
		}
	}
	
}
