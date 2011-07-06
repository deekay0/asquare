package edu.cmu.square.server.dao.model;

// @author: DK

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProjectPackageRationaleId implements java.io.Serializable {

	private static final long serialVersionUID = 7966111034051149077L;
	private int projectId;
	private int packageId;

	public ProjectPackageRationaleId() {
	}

	public ProjectPackageRationaleId(int projectId, int packageId) {
		this.projectId = projectId;
		this.packageId = packageId;
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



	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProjectPackageRationaleId))
			return false;
		ProjectPackageRationaleId castOther = (ProjectPackageRationaleId) other;

		return (this.getPackageId() == castOther.getPackageId())
				&& (this.getProjectId() == castOther.getProjectId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getProjectId();
		result = 37 * result + this.getPackageId();
		return result;
	}

}
