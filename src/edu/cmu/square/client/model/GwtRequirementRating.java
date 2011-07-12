package edu.cmu.square.client.model;

import java.io.Serializable;

public class GwtRequirementRating implements Serializable {

	
	private static final long serialVersionUID = -6144620742917868842L;
	private Integer projectId = -1;
	private Integer packageId = -1;
	private Integer requirementId = -1;
	private Integer value = -1;
	public Integer getProjectId()
	{
		return projectId;
	}
	public void setProjectId(Integer projectId)
	{
		this.projectId = projectId;
	}
	public Integer getPackageId()
	{
		return packageId;
	}
	public void setPackageId(Integer packageId)
	{
		this.packageId = packageId;
	}
	public Integer getRequirementId()
	{
		return requirementId;
	}
	public void setRequirementId(Integer requirementId)
	{
		this.requirementId = requirementId;
	}
	public Integer getValue()
	{
		return value;
	}
	public void setValue(Integer value)
	{
		this.value = value;
	}
	
	
}
