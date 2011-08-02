package edu.cmu.square.server.business.interfaces;

public interface BaseBusinessInterface {

	String getUserName();

	void setUserName(String userId);
	
	String getProjectName();
	
	void setProjectName(String projectName);
	
	void setSessionExpired(boolean sessionExpired);
	
	boolean isSessionExpired();
	
}
