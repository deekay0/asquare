package edu.cmu.square.client.model;

import java.io.Serializable;

public class GwtInspectionTechnique implements Serializable
{
	private static final long serialVersionUID = 9216764638701217533L;

	private Integer id = -1;
	private String title;
	private String description;

	public Integer getInspectionTechniqueId() { return this.id; }
	public void setInspectionTechniqueId(Integer value) { this.id = value; }
	
	public String getTitle() { return this.title; }
	public void setTitle(String value) { this.title = value; }
	
	public String getDescription() { return this.description; }
	public void setDescription(String value) { this.description = value; }

	public boolean isSavedInDb() { return this.id != -1; }
}
