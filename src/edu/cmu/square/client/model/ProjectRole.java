package edu.cmu.square.client.model;

public enum ProjectRole
{
	Lead_Requirements_Engineer("Lead Requirements Engineer"), 
	Requirements_Engineer("Requirements Engineer"), 
	Stakeholder("Stakeholder"),
	None("None");
	
	private String label;
	
	private ProjectRole(String label) {
		this.label = label;
	}
	public String getLabel() {
		return this.label;
	}
	
}
