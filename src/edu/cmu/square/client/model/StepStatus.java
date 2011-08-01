package edu.cmu.square.client.model;


public enum StepStatus
{
	NotStarted("Not Started"), 
	InProgress("In Progress"), 
	Complete("Complete");
	
	private String label;
	
	private StepStatus(String label)
	{
		this.label = label;
	}
	
	public String getLabel()
	{
		return this.label;
	}
	
	/**
	 * Return the proper enumeration based on a label string.
	 * @param enumLabel a valid label string
	 * @return the enum that matches the label or NotStarted if there is no match.
	 */
	public static StepStatus convertLabel(String enumLabel)
	{
		if (enumLabel.equals(Complete.getLabel()))
		{
			return Complete;
		}
		else if (enumLabel.equals(InProgress.getLabel()))
		{
			return InProgress;
		}
		
		return NotStarted;
	}
}
