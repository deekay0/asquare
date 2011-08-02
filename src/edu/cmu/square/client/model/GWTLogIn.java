package edu.cmu.square.client.model;
import java.io.Serializable;


public class GWTLogIn implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5742393700416141068L;
	private boolean isLocked;
	private String message;
	private boolean isAuthenticated;
	private boolean isAdmin = false;
	private String sessionID;
	private String userName;
	private String userID;

	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param isLocked set the status when the user account is locked because of N failed attempts
	 */
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	/**
	 * @return the isLocked
	 */
	public boolean isLocked() {
		return isLocked;
	}
	/**
	 * @param isAuthenticated the isAuthenticated to set
	 */
	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	/**
	 * @return the isAuthenticated
	 */
	public boolean isAuthenticated() {
		return isAuthenticated;
	}
	/**
	 * @param sessionID the sessionID that will be generated held to identify is the session is valid
	 * for a any client requesting the server
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	/**
	 * @return the sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	
	public void setIsAdmin(boolean value)
	{
		this.isAdmin = value;
	}
	
	public boolean getIsAdmin()
	{
		return this.isAdmin;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserID() {
		return userID;
	}

}
