package edu.cmu.square.client.model;

public enum InspectionStatus
{
	Issues_Reinspect("Issues Found, Re-inspect"), 
	Issues_NoFollowUp("Issues Found, No Follow-up"), 
	NoIssues("No Issues Found, Requirements ready");
	private String label;

	private InspectionStatus(String label)
		{
			this.label = label;
		}

	public String getLabel()
	{
		return label;
	}
	 
}
