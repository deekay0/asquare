package edu.cmu.square.client.ui.core;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

import edu.cmu.square.client.navigation.State;

public class AccessDeniedPane extends Composite
{
	public AccessDeniedPane(final State stateInfo)
	{
		Label denied = new Label("Access denied.");
		
		this.initWidget(denied);
		
	}
	
}
