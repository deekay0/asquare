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

import edu.cmu.square.client.model.GwtProjectPackageAttributeRating;
import edu.cmu.square.client.model.GwtRequirementRating;


@Entity
@Table(name = "project_package_requirement_rating")
public class ProjectPackageRequirementRating implements java.io.Serializable {

	private static final long serialVersionUID = 1175253865388434123L;
	private ProjectPackageRequirementRatingId id;
	private Project project;
	private SoftwarePackage softwarePackage;
	private Requirement requirement;
	private Integer rating;

	public ProjectPackageRequirementRating() {
	}

	public ProjectPackageRequirementRating(ProjectPackageRequirementRatingId id, Project project, 
			SoftwarePackage softwarePackage,
			Requirement requirement, Integer rating) {
		this.id = id;
		this.project = project;
		this.softwarePackage = softwarePackage;
		this.requirement = requirement;
		this.rating = rating;
	}
	public ProjectPackageRequirementRating(GwtRequirementRating gPprr) {
		
		this.project = new Project(gPprr.getProjectId());
		this.softwarePackage = softwarePackage;
		this.requirement = requirement;
		this.rating = rating;
	}
	
	
	public ProjectPackageRequirementRating(Integer projectId, Integer packageId, Integer requirementId, Integer rating, SoftwarePackage softwarePackage, Requirement req )
	{
		this.id = new ProjectPackageRequirementRatingId(projectId, packageId, requirementId);
		this.softwarePackage = softwarePackage;
		this.requirement = req;
		this.rating = rating;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "projectId", column = @Column(name = "project_id", nullable = false)),
			@AttributeOverride(name = "packageId", column = @Column(name = "package_id", nullable = false)),
			@AttributeOverride(name = "requirementId", column = @Column(name = "requirement_id", nullable = false))})
	public ProjectPackageRequirementRatingId getId() {
		return this.id;
	}

	public void setId(ProjectPackageRequirementRatingId id) {
		this.id = id;
	}
	
	public void setIdInt(Integer pid, Integer sid, Integer rid){
		this.id = new ProjectPackageRequirementRatingId(pid, sid, rid);
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "requirement_id", nullable = false, insertable = false, updatable = false)
	public Requirement getRequirement() {
		return this.requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
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
	
	@Column(name = "rating", nullable = false, length = 1)
	public Integer getRating() {
		return this.rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}
