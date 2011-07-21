package edu.cmu.square.server.authorization;



public enum Roles
{
	//ASQUARE
	Acquisition_Organization_Engineer("Acquisition Organization Engineer"),
	Contractor("Contractor"),
	COTS_Vendor("COTS Vendor"),
	Security_Specialist("Security Specialist"),
	
	//SQUARE
	Administrator("Administrator"),
	//Lead_Requirements_Engineer("Lead Requirements Engineer"),
	//Stakeholder("Stakeholder"),
	//Requirements_Engineer("Requirements Engineer"),
	
	None("None"),
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