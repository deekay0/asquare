/**
 * 
 */
package edu.cmu.square.client.model;

import java.io.Serializable;


/**
 * @author kaalpurush
 *
 */
public class GwtStep implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4581743510999176026L;
	private Integer id;
	private String description;
	private StepStatus status;

	
	private boolean privacy;
	private boolean security;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPrivacy() {
		return privacy;
	}
	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	public boolean isSecurity() {
		return security;
	}
	public void setSecurity(boolean security) {
		this.security = security;
	}
	
	public String toString() {
		String ret = "description: " + this.description + "\n"
		+ "status: " + this.status;
		return ret;
	}
	public StepStatus getStatus() {
		return status;
	}
	public void setStatus(StepStatus status) {
		this.status = status;
	}
	
}
