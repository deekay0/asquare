package edu.cmu.square.server.authorization;



public enum Roles
{
	Administrator("Administrator"),
	Lead_Requirements_Engineer("Lead Requirements Engineer"),
	Stakeholder("Stakeholder"),
	Requirements_Engineer("Requirements Engineer"),
	All("All");
	
	private String roleName;
	
	private Roles(String roleNames)
	{
		this.roleName = roleNames;
	}
	
	public String getRoleName()
	{
		return roleName;
	}

}
