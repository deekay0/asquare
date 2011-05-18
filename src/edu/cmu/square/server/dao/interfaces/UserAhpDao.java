package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Requirement;
import edu.cmu.square.server.dao.model.User;
import edu.cmu.square.server.dao.model.UserAhp;
import edu.cmu.square.server.dao.model.UserAhpId;

public interface UserAhpDao extends AbstractDao<UserAhp, UserAhpId>
{

	List<UserAhp> getAllPrioritizationsForUser(Integer userId);

	Integer getCountForUser(Integer userId, Integer projectId);

	Integer getCompletedCountForUser(Integer userId, Integer projectId);

	List<UserAhp> getAllEmptyComparisons(Integer userId, Integer projectId);

	void resetAllPririties(Integer userId, Integer projectId);

	List<UserAhp> getAllComparisons(Integer userId, Integer projectId);

	List<UserAhp> getAllComparisons(Integer projectId);

	List<User> getUsersCompleted(Integer projectId, Long totalPriorities);

	List<Requirement> getRequirementsPrioritized(Integer projectId);
}
