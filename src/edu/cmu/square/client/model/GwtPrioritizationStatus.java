package edu.cmu.square.client.model;

import java.io.Serializable;

/**
 * Contains the current user's tally of prioritizations and completion status.
 *
 */
public class GwtPrioritizationStatus implements Serializable
{
	private static final long serialVersionUID = 5833318958566924285L;
	private Integer totalComparisons;
	private Integer prioritizationsDone;
	private Integer totalUsers;
	private Integer completedUsers;
	private Boolean completed;
	public Integer getTotalComparisons()
	{
		return totalComparisons;
	}
	public void setTotalComparisons(Integer totalRequirements)
	{
		this.totalComparisons = totalRequirements;
	}
	public Integer getPrioritizationsDone()
	{
		return prioritizationsDone;
	}
	public void setPrioritizationsDone(Integer prioritizationsDone)
	{
		this.prioritizationsDone = prioritizationsDone;
	}
	public Boolean getCompleted()
	{
		return completed;
	}
	public void setCompleted(Boolean completed)
	{
		this.completed = completed;
	}
	public Integer getTotalUsers()
	{
		return totalUsers;
	}
	public void setTotalUsers(Integer totalUsers)
	{
		this.totalUsers = totalUsers;
	}
	public Integer getCompletedUsers()
	{
		return completedUsers;
	}
	public void setCompletedUsers(Integer completedUsers)
	{
		this.completedUsers = completedUsers;
	}

}
