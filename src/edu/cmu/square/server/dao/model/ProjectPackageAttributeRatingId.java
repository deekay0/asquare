package edu.cmu.square.server.dao.model;

// @author: deekay

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProjectPackageAttributeRatingId implements java.io.Serializable {

	private static final long serialVersionUID = 7966111034051149077L;
	private int projectId;
	private int packageId;
	private int attributeId;

	public ProjectPackageAttributeRatingId() {
	}

	public ProjectPackageAttributeRatingId(int projectId, int packageId, int attributeId) {
		this.projectId = projectId;
		this.packageId = packageId;
		this.attributeId = attributeId;
	}

	@Column(name = "project_id", nullable = false)
	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	@Column(name = "package_id", nullable = false)
	public int getPackageId() {
		return this.packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	
	@Column(name = "attribute_id", nullable = false)
	public int getAttributeId() {
		return this.attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProjectPackageAttributeRatingId))
			return false;
		ProjectPackageAttributeRatingId castOther = (ProjectPackageAttributeRatingId) other;

		return (this.getPackageId() == castOther.getPackageId())
				&& (this.getProjectId() == castOther.getProjectId())
				&& (this.getAttributeId() == castOther.getAttributeId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getProjectId();
		result = 37 * result + this.getPackageId();
		result = 37 * result + this.getAttributeId();
		return result;
	}

}
