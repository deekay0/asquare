package edu.cmu.square.server.dao.model;

// @author Nan

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtTradeoffReason;


@Entity
@Table(name = "project_package_tradeoffreason")
public class ProjectPackageTradeoffreason implements java.io.Serializable {
	
	private static final long serialVersionUID = -7859061386737307529L;
	private ProjectPackageTradeoffreasonId id;
	private Project project;
	private SoftwarePackage softwarePackage;
	private String tradeoffreason;
	private int priority;

	public ProjectPackageTradeoffreason() {
	}

	public ProjectPackageTradeoffreason(ProjectPackageTradeoffreasonId id, Project project, 
			SoftwarePackage softwarePackage,String tradeoffreason, int priority) {
		this.id = id;
		this.project = project;
		this.softwarePackage = softwarePackage;
		this.tradeoffreason = tradeoffreason;
		this.priority = priority;
	}


	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "projectId", column = @Column(name = "project_id", nullable = false)),
			@AttributeOverride(name = "packageId", column = @Column(name = "package_id", nullable = false))})
	public ProjectPackageTradeoffreasonId getId() {
		return this.id;
	}

	public void setId(ProjectPackageTradeoffreasonId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false, insertable = false, updatable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id", nullable = false, insertable = false, updatable = false)
	public SoftwarePackage getSoftwarePackage() {
		return this.softwarePackage;
	}

	public void setSoftwarePackage(SoftwarePackage softwarePackage) {
		this.softwarePackage = softwarePackage;
	}
	
	@Column(name = "tradeoffreason", nullable = false, length = 1)
	public String getTradeoffreason() {
		return this.tradeoffreason;
	}

	public void setTradeoffreason(String tradeoffreason) {
		this.tradeoffreason = tradeoffreason;
	}
	
	@Column(name = "priority", nullable = false, length = 1)
	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority =  priority;
	}
	
	public GwtTradeoffReason createGwtTradeoffReason() {
		GwtTradeoffReason gwtTradeoffReason = new GwtTradeoffReason();
		gwtTradeoffReason.setProjectId(this.id.getProjectId());
		gwtTradeoffReason.setPackageId(this.id.getPackageId());
		gwtTradeoffReason.setPriority(this.priority);
		gwtTradeoffReason.setTradeoffreason(this.tradeoffreason);
	
		return gwtTradeoffReason;
	}

}
