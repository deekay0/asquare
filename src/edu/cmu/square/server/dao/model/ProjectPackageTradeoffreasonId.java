package edu.cmu.square.server.dao.model;

// @author: Nan

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProjectPackageTradeoffreasonId implements java.io.Serializable {

	private static final long serialVersionUID = 7966111034051149077L;
	private int projectId;
	private int packageId;

	public ProjectPackageTradeoffreasonId() {
	}

	public ProjectPackageTradeoffreasonId(int projectId, int packageId, int attributeId) {
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
		if (!(other instanceof ProjectPackageTradeoffreasonId))
			return false;
		ProjectPackageTradeoffreasonId castOther = (ProjectPackageTradeoffreasonId) other;

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
