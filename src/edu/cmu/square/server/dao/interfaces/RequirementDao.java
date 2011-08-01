package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Requirement;

public interface RequirementDao extends AbstractDao<Requirement, Integer>
{

	List<Requirement> findByCategoryId(Integer categoryFromId);

	void updatePriority(Requirement r);

	void zeroOutPriorities(Integer projectId);

	List<Requirement> getRequirementByProject(Project project);

	void changeStatusToApproved(Requirement r);

	void changeStatusToRequestRevision(Requirement r);

	List<String> getRequirementTitleByReqId(Integer originalReqId);
	
	List<Integer> getNewRequirementIdByTitleProject(String title, Integer projectId);
}
