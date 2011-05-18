/**
 * 
 */
package edu.cmu.square.client.model;

import java.io.Serializable;

/**
 * @author kaalpurush
 * 
 */
public class GwtAsset implements Serializable,Comparable<GwtAsset>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4581743510999176026L;
	private Integer id;
	private String description;

	public GwtAsset()
		{
		}

	
	public int compareTo(GwtAsset otherAsset) 
	{
		if (this.description == null || this.description.trim().equalsIgnoreCase("") || otherAsset.description == null || otherAsset.getDescription().trim().equalsIgnoreCase("")) 
		{
			return 0;
		}
		return ( this.getDescription().trim()).compareTo( otherAsset.getDescription().trim());
	}

	
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}

	   public boolean equals(Object obj) {
	       if (this == obj)
	           return true;
	       if (obj == null)
	           return false;
	       if (getClass() != obj.getClass())
	           return false;
	       final GwtAsset other = (GwtAsset) obj;
	       if (this.id != other.getId().intValue())
	           return false;
	       return true;
	   }

}
