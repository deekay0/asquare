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

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtProjectPackageAttributeRating;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtSoftwarePackage;


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

	public ProjectPackageAttributeRating(ProjectPackageAttributeRatingId id, SoftwarePackage softwarePackage, Project project, QualityAttribute role, Integer rating) 
	{
		this.id = id;
		this.softwarePackage = softwarePackage;
		this.project = project;
		this.qualityAttribute = role;
		this.rating = rating;
	}
	
	public ProjectPackageAttributeRating(GwtProjectPackageAttributeRating ppar) 
	{
		this.softwarePackage = new SoftwarePackage(ppar.getPackage());
		this.project = new Project(ppar.getProject());
		this.qualityAttribute = new QualityAttribute(ppar.getAttribute());
		this.rating = ppar.getValue();
		
		this.id = new ProjectPackageAttributeRatingId(project.getId(), softwarePackage.getId(), qualityAttribute.getId());
	}
	


	public ProjectPackageAttributeRating(Integer projectId, Integer packageId, Integer attributeId, Integer rating, SoftwarePackage softwarePackage, QualityAttribute qa )
	{
		this.id = new ProjectPackageAttributeRatingId(projectId, packageId, attributeId);
		this.softwarePackage = softwarePackage;
		this.qualityAttribute = qa;
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
	
	public void setIdInt(Integer pid, Integer sid, Integer qid){
		this.id = new ProjectPackageAttributeRatingId(pid, sid, qid);
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
