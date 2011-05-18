package edu.cmu.square.client.model;

import java.io.Serializable;

public class GwtEvaluation implements Serializable
{
	private static final long serialVersionUID = -3671167946873842684L;

	private Integer evaluationId = -1;
	private String title;
	private String description;

	public Integer getEvaluationId()
	{
		return evaluationId;
	}
	public void setEvaluationId(Integer evaluationId)
	{
		this.evaluationId = evaluationId;
	}
	
	public String getTitle() { return title; }
	public void setTitle(String value) { this.title = value; }
	
	public String getDescription() { return description; }
	public void setDescription(String value) { this.description = value; }

	public boolean isSavedInDb() { return evaluationId != -1; }
}
