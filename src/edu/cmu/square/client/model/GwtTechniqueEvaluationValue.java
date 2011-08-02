package edu.cmu.square.client.model;

import java.io.Serializable;

public class GwtTechniqueEvaluationValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3671167946873842684L;
	private Integer evaluationId = -1;
	private Integer techniqueId = -1;
	private Integer value = -1;
	
	public Integer getEvaluationId() {
		return evaluationId;
	}
	public void setEvaluationId(Integer evaluationId) {
		this.evaluationId = evaluationId;
	}
	public void setTechniqueId(Integer techniqueId) {
		this.techniqueId = techniqueId;
	}
	public Integer getTechniqueId() {
		return techniqueId;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getValue() {
		return value;
	}
	
}
