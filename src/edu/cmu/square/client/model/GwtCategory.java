package edu.cmu.square.client.model;

import java.io.Serializable;

/**
 * A class that represents a requirements category
 */
public class GwtCategory implements Serializable, Comparable<GwtCategory>
{
	private static final long serialVersionUID = -8970436378794994504L;
	private int id=-1;
	private String label;
	private int projectID;
	private Integer count=0;
	public boolean isSavedInDb() { return this.id != -1; }
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}

	public String getCategoryName()
	{
		return label;
	}

	public void setCategoryName(String label) {
		this.label = label;
	}

	public void setProjectID(int projectID)
	{
		this.projectID = projectID;
	}

	public int getProjectID()
	{
		return projectID;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count)
	{
		this.count = count;
	}
	/**
	 * @return the count
	 */
	public Integer getCount()
	{
		return count;
	}
	  
	   public boolean equals(Object obj) {
	       if (this == obj)
	           return true;
	       if (obj == null)
	           return false;
	       if (getClass() != obj.getClass())
	           return false;
	       final GwtCategory other = (GwtCategory) obj;
	       if (this.id != other.getId())
	           return false;
	       return true;
	   }
	  
	  public int compareTo(GwtCategory otherCategory) {
			
			return this.getCategoryName().compareTo(otherCategory.getCategoryName());
		}
	/*  public int compareTo(GwtCategory otherCategory) {
			if (this.id <= 0 || otherCategory.id <= 0) {
				return 0;
			}
			return this.id.compareTo(otherCategory.id);
		}*/
}
