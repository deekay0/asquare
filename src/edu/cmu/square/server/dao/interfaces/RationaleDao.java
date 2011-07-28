package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageRationale;
import edu.cmu.square.server.dao.model.ProjectPackageRationaleId;
import edu.cmu.square.server.dao.model.ProjectPackageTradeoffreason;

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
	//List<ProjectPackageRationale> getRationaleList(Project project);
	
}
