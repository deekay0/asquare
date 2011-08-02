package edu.cmu.square.client.exceptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

import edu.cmu.square.client.ui.ManageProject.ManageProjectPilot;
import edu.cmu.square.client.ui.chooseCase.ChooseCasePilot;
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
					
					History.newItem(ChooseCasePilot.generateNavigationId(ChooseCasePilot.PageId.logout)+"?timeout=1");
					break;

				case duplicateName :
					Window.alert(messages.errorDupiliatedName(action));
					break;
					//
				case constraintViolated :
					//System.out.println("hey");
					Window.alert(messages.errorDupiliatedName(action));
					//History.newItem(ManageProjectPilot.generateNavigationId(ManageProjectPilot.PageId.home)+"?timeout=1");
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
