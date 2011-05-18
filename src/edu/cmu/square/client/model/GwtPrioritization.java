package edu.cmu.square.client.model;

import java.io.Serializable;

/**
 * Contains the current pair of requirements and their cost and value.
 */
public class GwtPrioritization implements Serializable
{

	private static final long serialVersionUID = -1901492313028988990L;
	private GwtRequirement requirementA;
	private double costA;
	private double valueA;
	
	private GwtRequirement requirementB;
	private double costB;
	private double valueB;
	public GwtRequirement getRequirementA()
	{
		return requirementA;
	}
	public void setRequirementA(GwtRequirement requirementA)
	{
		this.requirementA = requirementA;
	}
	public double getCostA()
	{
		return costA;
	}
	public void setCostA(double costA)
	{
		this.costA = costA;
	}
	public double getValueA()
	{
		return valueA;
	}
	public void setValueA(double valueA)
	{
		this.valueA = valueA;
	}
	public GwtRequirement getRequirementB()
	{
		return requirementB;
	}
	public void setRequirementB(GwtRequirement requirementB)
	{
		this.requirementB = requirementB;
	}

	public double getCostB()
	{
		return costB;
	}
	public void setCostB(double costB)
	{
		this.costB = costB;
	}
	public double getValueB()
	{
		return valueB;
	}
	public void setValueB(double valueB)
	{
		this.valueB = valueB;
	}

}
