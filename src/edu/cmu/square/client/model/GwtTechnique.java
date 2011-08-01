package edu.cmu.square.client.model;

import java.io.Serializable;

/**
 * An elicitation technique, modified in the project dashboard, used in the "Choose Elicitation Technique" step of the SQUARE method
 * 
 *
 */
public class GwtTechnique implements Serializable
{
	private static final long serialVersionUID = -1301050361393748601L;

	private Integer techniqueId = -1;
	
	private String title;
	private String description;
	private boolean type;
	
	
	public GwtTechnique()
	{
		
	}
	
	public GwtTechnique(Integer id, String newTitle, String newDescription)
	{
		this.techniqueId = id;
		
		this.description = newDescription;
		this.title = newTitle;
	}
	
	
	
	public String getTitle() { return this.title; }
	public void setTitle(String value) { this.title = value; }
	
	public String getDescription() { return this.description; }
	public void setDescription(String value) { this.description = value; } 

	public boolean isSavedInDb() { return this.techniqueId != -1; }
	
	public Integer getTechniqueId() {return this.techniqueId; }
	public void setTechniqueId(Integer value) { this.techniqueId = value; }

	public boolean isSecurityTechnique() { return type;}
	public boolean isPrivacyTechnqiue() { return !type; }
	
	public void setToSecurity() { this.type = true; }
	public void setToPrivacy() { this.type = false; }
	
}
