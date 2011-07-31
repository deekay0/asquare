package edu.cmu.square.client.navigation;

import com.google.gwt.user.client.ui.Widget;


public abstract class Pilot
{	
	public abstract Widget navigateTo(String pageId, State currentStateInformation);

	public abstract void determineAccessRights(String page, State currentState);
	
	public abstract String getBreadCrumb();
	
	public String STEP_DESCRIPTION = "";
	
	public boolean isStep = false;
	
	
}