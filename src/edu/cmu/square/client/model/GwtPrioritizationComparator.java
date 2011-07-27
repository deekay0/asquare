package edu.cmu.square.client.model;

import java.util.Comparator;

public class GwtPrioritizationComparator implements Comparator<GwtPrioritizedRequirement>
{

	@Override
	public int compare(GwtPrioritizedRequirement o1, GwtPrioritizedRequirement o2)
	{
		if (o1 == null || o2==null) 
		{
			throw new NullPointerException("Cannot compare requirement to null");
		}
		if (o1.getRequirement() == null || o2.getRequirement() ==null) 
		{
			throw new NullPointerException("Requirement is null");
		}
		if (o1.getRequirement().getPriority() > o2.getRequirement().getPriority() )
		{
			return 1;
		}
		if (o1.getRequirement().getPriority() < o2.getRequirement().getPriority() )
		{
			return -1;
		}	
		return 0;
	}

}
