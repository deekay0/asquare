/**
 * 
 */
package edu.cmu.square.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author kaalpurush
 *
 */
public class GwtBusinessGoal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4581743510999176026L;
	private Integer id;
	private String description="";
	private List<GwtSubGoal> subGoals = new  ArrayList<GwtSubGoal>();
	private List<GwtAsset> assets =new  ArrayList<GwtAsset>();;
	
	
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
	public void setSubGoals(List<GwtSubGoal> subGoals) {
		this.subGoals = subGoals;
	}
	public List<GwtSubGoal> getSubGoals() {
		return subGoals;
	}
	public void setAssets(List<GwtAsset> assets) {
		this.assets = assets;
	}
	public List<GwtAsset> getAssets() {
		return assets;
	}

}
