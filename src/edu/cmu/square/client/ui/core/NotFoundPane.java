package edu.cmu.square.client.ui.core;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

import edu.cmu.square.client.navigation.State;

public class NotFoundPane extends Composite
{
	public NotFoundPane(final State stateInfo)
	{
		Label notFound = new Label("404 Not Found");
		
		this.initWidget(notFound);
		
	}
	
}
