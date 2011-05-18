/**
 * 
 */
package edu.cmu.square.client.model;

import java.io.Serializable;

/**
 * @author kaalpurush
 * 
 */
public class GwtTerm implements Serializable, Comparable<GwtTerm>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4581743510999176026L;

	private Integer id;
	private Integer projectId;
	private String term;
	private String definition;
	// TODO: clarify the field approved of terms with michael.
	public Integer getProjectId()
	{
		return projectId;
	}
	public void setProjectId(Integer projectId)
	{
		this.projectId = projectId;
	}
	public String getTerm()
	{
		return term;
	}
	public void setTerm(String term)
	{
		this.term = term;
	}
	public String getDefinition()
	{
		return definition;
	}
	public void setDefinition(String definition)
	{
		this.definition = definition;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getId()
	{
		return id;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GwtTerm other = (GwtTerm) obj;
		if (this.id != other.getId())
			return false;
		return true;
	}

	public int compareTo(GwtTerm otherTerm)
	{

		return this.term.compareTo(otherTerm.getTerm());
	}

}
