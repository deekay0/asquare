/**
 * 
 */
package edu.cmu.square.client.model;

import java.io.Serializable;

/**
 * @author DK
 *
 */
public class GwtQualityAttribute implements Serializable
{

	private static final long serialVersionUID = -7358421275273341044L;
	private Integer id;
	private String name;
	private String description;
	
	public GwtQualityAttribute(Integer id)
	{
		this.id = id;
	}
	public GwtQualityAttribute()
	{
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}