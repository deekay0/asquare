package edu.cmu.square.client.model;

import java.io.Serializable;
import java.util.Date;

public class GwtUser implements Serializable
{

	private static final long serialVersionUID = 67169359656154859L;
	
	private Integer userId = -1;
	private String userName = "";
	private String fullName = "";
	private String emailAddress = "";
	
	private String roleProject = "";
	
	private boolean isAdmin = false;
	private boolean isAuthenticated;
	
	private String phone;
	private String organization;
	private String department;
	private String location;
	
	private boolean isLocked;
	private boolean skipTeachStep;
	private String sessionID;
	private Date dateCreated;
	

	public GwtUser(Integer id)
	{
		this.userId=id;
	}
	
	public GwtUser()
	{

	}
	
	public GwtUser(Integer userId, String fullName, String emailAddress, String role, boolean isAdmin, String userName)
	{
		this.userId = userId;
		this.fullName = fullName;
		this.emailAddress = emailAddress;
		this.roleProject = role;
		this.isAdmin = isAdmin;
		this.userName = userName;
	}
	public GwtUser(Integer userId, String fullName, String emailAddress, String role, boolean isAdmin, String userName, boolean skipTeachStep)
	{
		this.userId = userId;
		this.fullName = fullName;
		this.emailAddress = emailAddress;
		this.roleProject = role;
		this.isAdmin = isAdmin;
		this.userName = userName;
		this.skipTeachStep=skipTeachStep;
	}
	
	public String getFullName() { return this.fullName; }
	public void setFullName(String value) { this.fullName = value; }
	
	public String getEmailAddress() { return this.emailAddress; }
	public void setEmailAddress(String value) { this.emailAddress = value; }
	
	public String getRole() { return this.roleProject; }
	public void setRole(String value) { this.roleProject = value; }
	
	public Integer getUserId() { return this.userId; }
	public void setUserId(Integer value) { this.userId = value; }

	public boolean isSavedInDb() { return this.userId != -1; }

	public String getUserName() { return userName; }
	public void setUserName(String value) { this.userName = value; }
	
	public String getPhone() { return phone; }
	public void setPhone(String value) { this.phone = value; }
	
	public String getOrganization() { return organization; }
	public void setOrganization(String value) { this.organization = value; }
	
	public String getDepartment() { return department; }
	public void setDepartment(String value) { this.department = value; }
	
	public String getLocation() { return location; }
	public void setLocation(String value) { this.location = value; }
	
	public String getSessionID() { return sessionID; }
	public void setSessionID(String value) { this.sessionID = value; }
	
	public boolean getIsAdmin() { return isAdmin; }
	public void setIsAdmin(boolean value) { this.isAdmin = value; }
	
	public boolean isLocked() { return isLocked; }
	public void setIsLocked(boolean value) { this.isLocked = value; }
	
	public boolean isAuthenticated() { return isAuthenticated; }
	public void setAuthenticated(boolean value) { this.isAuthenticated = value; }

	public void setSkipTeachStep(boolean skipTeachStep){ this.skipTeachStep = skipTeachStep;}
	public boolean isSkipTeachStep(){return skipTeachStep;}
	
	//public void setDateCreated(this.dateCreated = dateCreated;)
	//public void setDateModified


}
