package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.UserAhpDao;
import edu.cmu.square.server.dao.model.Requirement;
import edu.cmu.square.server.dao.model.User;
import edu.cmu.square.server.dao.model.UserAhp;
import edu.cmu.square.server.dao.model.UserAhpId;
@Repository
@SuppressWarnings("unchecked")
public class HbnUserAhpDao extends HbnAbstractDao<UserAhp, UserAhpId> implements UserAhpDao
{

	@Override
	public List<UserAhp> getAllPrioritizationsForUser(Integer userId)
	{
		String query = "Select u from UserAhp u where u.user.id = :userId";
		Query q = getSession().createQuery(query);
		q.setParameter("userId", userId);
		return q.list();
	}

	@Override
	public Integer getCountForUser(Integer userId, Integer projectId)
	{
		String query = "Select count(u.id.uid) from UserAhp u where u.user.id = :userId" + " and u.requirementByRidA.project.id=:projectId";
		Query q = getSession().createQuery(query);
		q.setParameter("userId", userId);
		q.setParameter("projectId", projectId);
		List<Long> intList = q.list();
		if (intList.isEmpty())
		{
			return null;
		}
		return intList.get(0).intValue();
	}

	@Override
	public Integer getCompletedCountForUser(Integer userId, Integer projectId)
	{
		String query = "Select count(u.id.uid) from UserAhp u where u.user.id = :userId "
				+ "and u.requirementByRidA.project.id=:projectId and u.costA=0 and u.costB=0 and u.valueA=0 and u.valueB=0";
		Query q = getSession().createQuery(query);
		q.setParameter("userId", userId);
		q.setParameter("projectId", projectId);
		List<Long> intList = q.list();
		if (intList.isEmpty())
		{
			return null;
		}
		return intList.get(0).intValue();
	}

	@Override
	public List<UserAhp> getAllEmptyComparisons(Integer userId, Integer projectId)
	{
		String query = "Select u from UserAhp u where u.user.id = :userId " + "and u.requirementByRidA.project.id=:projectId "
				+ "and u.costA=0 and u.costB=0 and u.valueA=0 and u.valueB=0";
		Query q = getSession().createQuery(query);
		q.setParameter("userId", userId);
		q.setParameter("projectId", projectId);
		List<UserAhp> intList = q.list();
		if (intList.isEmpty())
		{
			return null;
		}
		return intList;
	}

	@Override
	public void resetAllPririties(Integer userId, Integer projectId)
	{
		String query = "Update from UserAhp u set u.costA=:costA " + " and u.costB=:costB and u.valueA=:valueA and u.valueB=:valueB "
				+ "where u.user.id = :userId";
		Query q = getSession().createQuery(query);
		q.setParameter("userId", userId);

		q.setParameter("costA", 0);
		q.setParameter("costB", 0);
		q.setParameter("valueA", 0);
		q.setParameter("valueB", 0);

		q.executeUpdate();
	}

	@Override
	public List<UserAhp> getAllComparisons(Integer userId, Integer projectId)
	{
		String query = "Select u from UserAhp u where u.user.id = :userId " + "and u.requirementByRidA.project.id=:projectId";
		Query q = getSession().createQuery(query);
		q.setParameter("userId", userId);
		q.setParameter("projectId", projectId);
		return q.list();
	}

	@Override
	public List<UserAhp> getAllComparisons(Integer projectId)
	{
		String query = "Select u from UserAhp u where u.requirementByRidA.project.id=:projectId";
		Query q = getSession().createQuery(query);
		q.setParameter("projectId", projectId);
		return q.list();
	}

	@Override
	public List<User> getUsersCompleted(Integer projectId, Long totalPriorities)
	{
		String query = "Select u.user from UserAhp u where " + " u.requirementByRidA.project.id=:projectId "
				+ "and ( u.costA<>0 or u.costB<>0 or u.valueA<>0 or u.valueB<>0 ) group by u.user having count(u.user.id) >= :totalPriorities";
		Query q = getSession().createQuery(query);

		q.setParameter("projectId", projectId);
		q.setParameter("totalPriorities", totalPriorities);

		List<User> count = q.list();

		return count;
	}

	@Override
	public List<Requirement> getRequirementsPrioritized(Integer projectId)
	{
		String query = "Select distinct (r) from UserAhp u, Requirement r where " + " u.requirementByRidA.project.id=:projectId "
				+ "and r.project.id = :projectId and ((r = u.requirementByRidA) or (r = u.requirementByRidB)) ";
		Query q = getSession().createQuery(query);

		q.setParameter("projectId", projectId);

		List<Requirement> requirements = q.list();

		return requirements;
	}

}
