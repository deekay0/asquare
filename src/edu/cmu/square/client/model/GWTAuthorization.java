package edu.cmu.square.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * This class is responsible for storing temporarily the roles
 *
 */
public class GWTAuthorization implements Serializable
{
	private static final long serialVersionUID = 6630789654791346650L;
	private List<ProjectRole> roles = new ArrayList<ProjectRole>();

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<ProjectRole> roles) { this.roles = roles; }

	/**
	 * @return the roles
	 */
	public List<ProjectRole> getRoles() { return roles; }

}
