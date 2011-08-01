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
public class GwtSubGoal implements Serializable, Comparable<GwtSubGoal> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4581743510999176026L;
	private Integer id;
	private Integer priority=0;
	private String description;
	private List<GwtAsset> assets = new ArrayList<GwtAsset>();


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
	public void setAssets(List<GwtAsset> assets) {
		this.assets = assets;
	}
	public List<GwtAsset> getAssets() {
		return assets;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getPriority() {
		return priority;
	}
	
	public int compareTo(GwtSubGoal o) {
		if (priority == null || o.priority == null) {
			return 0;
		}
		return priority.compareTo(o.priority);
	}
	
	
	
}
