package edu.cmu.square.server.dao.model;

// @author deekay

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "project_package_attribute_rating")
public class ProjectPackageAttributeRating implements java.io.Serializable {

	private static final long serialVersionUID = -1336391021334170080L;
	private ProjectPackageAttributeRatingId id;
	private Project project;
	private SoftwarePackage softwarePackage;
	private QualityAttribute qualityAttribute;
	private Integer rating;

	public ProjectPackageAttributeRating() {
	}

	public ProjectPackageAttributeRating(ProjectPackageAttributeRatingId id, SoftwarePackage softwarePackage, Project project,
			QualityAttribute role, Integer rating) {
		this.id = id;
		this.softwarePackage = softwarePackage;
		this.project = project;
		this.qualityAttribute = role;
		this.rating = rating;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "projectId", column = @Column(name = "project_id", nullable = false)),
			@AttributeOverride(name = "packageId", column = @Column(name = "package_id", nullable = false)),
			@AttributeOverride(name = "attributeId", column = @Column(name = "attribute_id", nullable = false)) })
	public ProjectPackageAttributeRatingId getId() {
		return this.id;
	}

	public void setId(ProjectPackageAttributeRatingId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id", nullable = false, insertable = false, updatable = false)
	public SoftwarePackage getSoftwarePackage() {
		return this.softwarePackage;
	}

	public void setSoftwarePackage(SoftwarePackage softwarePackage) {
		this.softwarePackage = softwarePackage;
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
	@JoinColumn(name = "attribute_id", nullable = false, insertable = false, updatable = false)
	public QualityAttribute getQualityAttribute() {
		return this.qualityAttribute;
	}

	public void setQualityAttribute(QualityAttribute qualityAttribute) {
		this.qualityAttribute = qualityAttribute;
	}
	
	@Column(name = "rating", nullable = false, length = 1)
	public Integer getRating() {
		return this.rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}
