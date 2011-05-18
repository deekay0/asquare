/**
 * 
 */
package edu.cmu.square.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GwtRisk implements Serializable
{

	private static final long serialVersionUID = 4203458172360476849L;
	private Integer id;
	private String riskTitle;
	private String threatSource;
	private String threatAction;
	private String currentMeasures;
	private String plannedMeasures;
	private int impact;
	private int likelihood;
	private String vulnerability;
	private Integer projectId;
	private List<GwtAsset> assets = new ArrayList<GwtAsset>();
	private List<GwtArtifact> artifact = new ArrayList<GwtArtifact>();

	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getThreatSource()
	{
		return threatSource;
	}
	public void setThreatSource(String threatSource)
	{
		this.threatSource = threatSource;
	}
	public String getThreatAction()
	{
		return threatAction;
	}
	public void setThreatAction(String threatAction)
	{
		this.threatAction = threatAction;
	}
	public String getCurrentMeasures()
	{
		return currentMeasures;
	}
	public void setCurrentMeasures(String currentMeasures)
	{
		this.currentMeasures = currentMeasures;
	}
	public String getPlannedMeasures()
	{
		return plannedMeasures;
	}
	public void setPlannedMeasures(String plannedMeasures)
	{
		this.plannedMeasures = plannedMeasures;
	}
	public int getImpact()
	{
		return impact;
	}
	public void setImpact(int impact)
	{
		this.impact = impact;
	}
	public int getLikelihood()
	{
		return likelihood;
	}
	public void setLikelihood(int likelihood)
	{
		this.likelihood = likelihood;
	}
	public String getVulnerability()
	{
		return vulnerability;
	}
	public void setVulnerability(String vulnerability)
	{
		this.vulnerability = vulnerability;
	}
	public void setRiskTitle(String riskTitle)
	{
		this.riskTitle = riskTitle;
	}
	public String getRiskTitle()
	{
		return riskTitle;
	}
	public void setAssets(List<GwtAsset> assets)
	{
		this.assets = assets;
	}
	public List<GwtAsset> getAssets()
	{
		return assets;
	}
	public void setArtifact(List<GwtArtifact> artifact)
	{
		this.artifact = artifact;
	}
	public List<GwtArtifact> getArtifact()
	{
		return artifact;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Integer projectId)
	{
		this.projectId = projectId;
	}
	/**
	 * @return the projectId
	 */
	public Integer getProjectId()
	{
		return projectId;
	}
	 public boolean equals(Object obj) {
	       if (this == obj)
	           return true;
	       if (obj == null)
	           return false;
	       if (getClass() != obj.getClass())
	           return false;
	       final GwtRisk other = (GwtRisk) obj;
	       if (this.id != other.getId().intValue())
	           return false;
	       return true;
	   }
}
