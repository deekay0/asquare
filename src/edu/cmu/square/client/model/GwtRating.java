package edu.cmu.square.client.model;

import java.io.Serializable;

public class GwtRating implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2671167946873842684L;
	private Integer packageId = -1;
	private Integer attributeId = -1;
	private Integer value = -1;
	
	public Integer getPackageId() {
		return packageId;
	}
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}
	public Integer getAttributeId() {
		return attributeId;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getValue() {
		return value;
	}	
}
