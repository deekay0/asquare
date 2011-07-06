package edu.cmu.square.server.dao.interfaces;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageRationale;
import edu.cmu.square.server.dao.model.ProjectPackageRationaleId;

/**
 * @author DK
 *
 */
public interface RationaleDao extends AbstractDao<ProjectPackageRationale, ProjectPackageRationaleId> 
{
	void setRationale(ProjectPackageRationale rationale);
	ProjectPackageRationale getRationale(Project projectID);
	void updateRationale(int projectID, int packageId, String tradeoffreason);
	ProjectPackageRationaleId getIdForProject(Project project);
}
