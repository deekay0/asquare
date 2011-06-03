package edu.cmu.square.client.model;

public enum ProjectRole
{
	Lead_Requirements_Engineer("Lead Requirements Engineer"), 
	Requirements_Engineer("Requirements Engineer"), 
	Stakeholder("Stakeholder"),
	
	Acquisition_Organization_Engineer("Acquisition Organization Engineer"),
	Contractor("Contractor"),
	COTS_Vendor("COTS Vendor"),
	Security_Specialist("Security Specialist"),
	
	None("None");
	
	
	
	private String label;
	
	private ProjectRole(String label) {
		this.label = label;
	}
	public String getLabel() {
		return this.label;
	}
	
}
