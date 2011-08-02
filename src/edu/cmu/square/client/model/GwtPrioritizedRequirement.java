package edu.cmu.square.client.model;

import java.io.Serializable;
/**
 * This contains the current requirement with individual, group rank, and lead requirements engineer rank. 
 *
 */
public class GwtPrioritizedRequirement implements Serializable, Comparable<GwtPrioritizedRequirement>
{

	private static final long serialVersionUID = -8535256716168467663L;
	
	private GwtRequirement requirement;
	private Integer individualRank;
	private Integer groupRank;
//	private Integer leadRequirementsEngineerRank;
	private Double ahpPriority;
	public GwtRequirement getRequirement()
	{
		return requirement;
	}
	public void setRequirement(GwtRequirement requirement)
	{
		this.requirement = requirement;
	}
	public Integer getIndividualRank()
	{
		return individualRank;
	}
	public void setIndividualRank(Integer individualRank)
	{
		this.individualRank = individualRank;
	}
	public Integer getGroupRank()
	{
		return groupRank;
	}
	public void setGroupRank(Integer groupRank)
	{
		this.groupRank = groupRank;
	}
	
	/**
	 * @param prioritySum the prioritySum to set
	 */
	public void setAhpPriority(Double prioritySum)
	{
		this.ahpPriority = prioritySum;
	}
	/**
	 * @return the prioritySum
	 */
	public Double getAhpPriority()
	{
		return ahpPriority;
	}

	@Override
	public int compareTo(GwtPrioritizedRequirement o)
	{
		if (o == null) 
		{
			throw new NullPointerException("Cannot compare requiremen to null");
		}
		if (this.ahpPriority > o.ahpPriority )
		{
			return -1;
		}
		if (this.ahpPriority < o.ahpPriority )
		{
			return 1;
		}	
		return 0;
	}
	@Override
	public String toString()
	{
		String returnVal = "requirement " + requirement + " ahpPriority " + ahpPriority; 
		return returnVal;
	}
	
}
