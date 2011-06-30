package edu.cmu.square.client.model;

import java.io.Serializable;

public class GwtTradeoffReason implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer projectId = -1;
	private Integer packageId = -1;
	private String tradeoffreason = "";
	private Integer priority = -1;
	public Integer getProjectId()
	{
		return projectId;
	}
	public Integer getPriority()
	{
		return priority;
	}
	public void setPriority(Integer priority)
	{
		this.priority = priority;
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
	public String getTradeoffreason()
	{
		return tradeoffreason;
	}
	public void setTradeoffreason(String tradeoffreason)
	{
		this.tradeoffreason = tradeoffreason;
	}
	
	
	
}
