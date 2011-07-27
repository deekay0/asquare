package edu.cmu.square.server.dao.model;

// @author: Nan

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProjectPackageRequirementRatingId implements java.io.Serializable {

	private static final long serialVersionUID = -6507161562197844916L;
	private int projectId;
	private int packageId;
	private int requirementId;

	public ProjectPackageRequirementRatingId() {
	}

	public ProjectPackageRequirementRatingId(int projectId, int packageId, int requirementId) {
		this.projectId = projectId;
		this.packageId = packageId;
		this.requirementId = requirementId;
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

	@Column(name = "requirement_id", nullable = false)
	public int getRequirementId() {
		return this.requirementId;
	}

	public void setRequirementId(int requirementId) {
		this.requirementId = requirementId;
	}


	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProjectPackageRequirementRatingId))
			return false;
		ProjectPackageRequirementRatingId castOther = (ProjectPackageRequirementRatingId) other;

		return (this.getPackageId() == castOther.getPackageId())
				&&(this.getRequirementId() == castOther.getRequirementId())
				&& (this.getProjectId() == castOther.getProjectId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getProjectId();
		result = 37 * result + this.getPackageId();
		result = 37 * result + this.getRequirementId();
		return result;
	}

}
