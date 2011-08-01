package edu.cmu.square.server.dao.model;

// Generated May 17, 2009 5:48:52 PM by Hibernate Tools 3.2.4.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.cmu.square.client.model.GwtArtifact;
import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtRisk;

/**
 * Risk generated by hbm2java
 */
@Entity
@Table(name = "risk")
public class Risk implements java.io.Serializable {

	private static final long serialVersionUID = 318829934504667535L;
	private Integer id;
	private String riskTitle;
	private Project project;
	private String threatSource;
	private String threatAction;
	private String currentMeasures;
	private String plannedMeasures;
	private int impact;
	private int likelihood;
	private String vulnerability;
	private Date dateCreated;
	private Date dateModified;
	private Set<Requirement> requirements = new HashSet<Requirement>(0);
	private Set<Artifact> artifacts = new HashSet<Artifact>(0);
	
	private Set<Asset> assets = new HashSet<Asset>(0);

	public Risk() {
	}

	public Risk(Project project,String riskTitle, String threatSource, String threatAction,
			String currentMeasures, String plannedMeasures, int impact,
			int likelihood, String vulnerability, Date dateCreated,
			Date dateModified) {
		this.project = project;
		this.riskTitle=riskTitle;
		this.threatSource = threatSource;
		this.threatAction = threatAction;
		this.currentMeasures = currentMeasures;
		this.plannedMeasures = plannedMeasures;
		this.impact = impact;
		this.likelihood = likelihood;
		this.vulnerability = vulnerability;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	

	public Risk(GwtRisk risk) {
		this.id = risk.getId();
		this.riskTitle=risk.getRiskTitle();
		this.currentMeasures = risk.getCurrentMeasures();
		this.impact = risk.getImpact();
		this.likelihood = risk.getLikelihood();
		this.plannedMeasures = risk.getPlannedMeasures();
		this.threatAction = risk.getThreatAction();
		this.threatSource = risk.getThreatSource();
		this.vulnerability = risk.getVulnerability();
		this.project = new Project(risk.getProjectId()!=null?risk.getProjectId():0);
		for(GwtAsset a: risk.getAssets())
		{
			Asset asset = new Asset(a);
			this.assets.add(asset); 
		}
		
		for(GwtArtifact a: risk.getArtifact())
		{
			Artifact artifact = new Artifact(a);
			this.artifacts.add(artifact); 
		}
	}

	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "riskTitle", nullable = false, length = 250)
	public String getRiskTitle() {
		return this.riskTitle;
	}

	public void setRiskTitle(String riskTitle) {
		this.riskTitle = riskTitle;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "threatSource", nullable = false, length = 65535)
	public String getThreatSource() {
		return this.threatSource;
	}

	public void setThreatSource(String threatSource) {
		this.threatSource = threatSource;
	}

	@Column(name = "threatAction", nullable = false, length = 65535)
	public String getThreatAction() {
		return this.threatAction;
	}

	public void setThreatAction(String threatAction) {
		this.threatAction = threatAction;
	}

	@Column(name = "currentMeasures", nullable = false, length = 65535)
	public String getCurrentMeasures() {
		return this.currentMeasures;
	}

	public void setCurrentMeasures(String currentMeasures) {
		this.currentMeasures = currentMeasures;
	}

	@Column(name = "plannedMeasures", nullable = true, length = 65535)
	public String getPlannedMeasures() {
		return this.plannedMeasures;
	}

	public void setPlannedMeasures(String plannedMeasures) {
		this.plannedMeasures = plannedMeasures;
	}

	@Column(name = "impact", nullable = false)
	public int getImpact() {
		return this.impact;
	}

	public void setImpact(int impact) {
		this.impact = impact;
	}

	@Column(name = "likelihood", nullable = false)
	public int getLikelihood() {
		return this.likelihood;
	}

	public void setLikelihood(int likelihood) {
		this.likelihood = likelihood;
	}

	@Column(name = "vulnerability", nullable = false, length = 65535)
	public String getVulnerability() {
		return this.vulnerability;
	}

	public void setVulnerability(String vulnerability) {
		this.vulnerability = vulnerability;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 19)
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", nullable = false, length = 19)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "risks", targetEntity = Requirement.class)
	public Set<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(Set<Requirement> requirements) {
		this.requirements = requirements;
	}
	
	@ManyToMany
	@JoinTable(name = "risk_artifact", joinColumns = { @JoinColumn(name = "riskid") }, inverseJoinColumns = { @JoinColumn(name = "aid") })
	public Set<Artifact> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(Set<Artifact> artifacts) {
		this.artifacts = artifacts;
	}

	@ManyToMany
	@JoinTable(name = "risk_asset", joinColumns = { @JoinColumn(name = "risk_id") }, inverseJoinColumns = { @JoinColumn(name = "asset_id") })
	public Set<Asset> getAssets() {
		return assets;
	}

	public void setAssets(Set<Asset> assets) {
		this.assets = assets;
	}

	public void update(GwtRisk risk) {
		
		this.id = risk.getId();
		this.riskTitle=risk.getRiskTitle();
		this.currentMeasures = risk.getCurrentMeasures();
		this.impact = risk.getImpact();
		this.likelihood = risk.getLikelihood();
		this.plannedMeasures = risk.getPlannedMeasures();
		this.threatAction = risk.getThreatAction();
		this.threatSource = risk.getThreatSource();
		this.vulnerability = risk.getVulnerability();
		//this.project = new Project(risk.getProjectId()!=null?risk.getProjectId():0);

		
	}
	public GwtRisk createGwtRisk() {
		GwtRisk gwtRisk = new GwtRisk();
		gwtRisk.setId(this.id);
		gwtRisk.setRiskTitle(this.riskTitle);
		gwtRisk.setCurrentMeasures(this.currentMeasures);
		gwtRisk.setImpact(this.impact);
		gwtRisk.setLikelihood(this.likelihood);
		gwtRisk.setPlannedMeasures(this.plannedMeasures);
		gwtRisk.setThreatAction(this.threatAction );
		gwtRisk.setThreatSource(this.threatSource);
		gwtRisk.setVulnerability(this.vulnerability );
		gwtRisk.setProjectId(this.project!=null?this.project.getId():null);
	
		for (Asset a : this.assets)
		{
			gwtRisk.getAssets().add(a.createGwtAsset());
			
		}

	
		for (Artifact a : this.artifacts)
		{
			gwtRisk.getArtifact().add(a.createGwtArtifact());
		}
		
		return gwtRisk;
	}

	
	public String toString()
	{
		String toString = "risk id: "+ this.id + " risk title: " + this.riskTitle;  
		return toString;
	}
	
	

}
