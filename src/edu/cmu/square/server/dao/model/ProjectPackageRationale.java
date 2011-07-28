package edu.cmu.square.server.dao.model;

// @author DK

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.cmu.square.client.model.GwtRationale;


@Entity
@Table(name = "project_package_rationale")
public class ProjectPackageRationale implements java.io.Serializable {
	
	private static final long serialVersionUID = -7859061386737307529L;
	private ProjectPackageRationaleId id;
	private String rationale;
	private SoftwarePackage softwarePackage;
	private Project project;
	

	public ProjectPackageRationale() {
	}

	public ProjectPackageRationale(ProjectPackageRationaleId id, Project project, 
			SoftwarePackage softwarePackage, String rationale) {
		this.id = id;
		this.rationale = rationale;
		this.softwarePackage = softwarePackage;
		this.project = project;
	}
	
	public ProjectPackageRationale(Project project, 
			SoftwarePackage softwarePackage, String rationale) {
		this.id = new ProjectPackageRationaleId(project.getId(), softwarePackage.getId());
		this.rationale = rationale;
		this.softwarePackage = softwarePackage;
		this.project = project;
	}
	
	public ProjectPackageRationale(GwtRationale rationale) {
		this.id = new ProjectPackageRationaleId(rationale.getProject().getId(), rationale.getPackage().getId());
		this.project = new Project(rationale.getProject());
		this.softwarePackage = new SoftwarePackage(rationale.getPackage());
		this.rationale = rationale.getRationale();
	}


	public ProjectPackageRationale(Integer pid, Integer spid, SoftwarePackage softwarePackage, String rationale)
		{
			this.id = new ProjectPackageRationaleId(pid, spid);
			//this.project = new Project(rationale.getProject());
			this.softwarePackage = softwarePackage;
			this.rationale = rationale;
		}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "projectId", column = @Column(name = "project_id", nullable = false)),
			@AttributeOverride(name = "packageId", column = @Column(name = "package_id", nullable = false))})
	public ProjectPackageRationaleId getId() {
		return this.id;
	}

	public void setId(ProjectPackageRationaleId id) {
		this.id = id;
	}
	public void setIdInt(Integer pid, Integer sid){
		this.id = new ProjectPackageRationaleId(pid, sid);
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
	public String getRationale() {
		return this.rationale;
	}

	public void setRationale(String tradeoffreason) {
		this.rationale = tradeoffreason;
	}
	
//	public GwtFinalChoiceRationale createGwtFinalChoiceRationale() {
//		GwtFinalChoiceRationale gwtFinalChoiceRationale = new GwtFinalChoiceRationale();
//		gwtFinalChoiceRationale.setProject(this.getProject());
//		gwtFinalChoiceRationale.setSoftwarePackage(this.getSoftwarePackage());
//		gwtFinalChoiceRationale.setRationale(this.rationale);
//	
//		return gwtFinalChoiceRationale;
//	}

}
