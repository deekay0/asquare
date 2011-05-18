/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.GoalDao;
import edu.cmu.square.server.dao.model.Goal;
import edu.cmu.square.server.dao.model.GoalType;
import edu.cmu.square.server.dao.model.Project;

/**
 * @author yirul
 *
 */
@Repository
public class HbnGoalDao extends HbnAbstractDao<Goal, Integer> implements GoalDao{


	@SuppressWarnings("unchecked")
	public List<Goal> getBusinessGoalByProject(Project project) {
		
		GoalType goalType = new GoalType();
		goalType.setId(1); ////This fetches the business goal. There is only one per project
		Session session = getSession();
		String query = "Select u from Goal u where u.project= :project and u.goalType=:goalType " ;
		Query q = session.createQuery(query);
		q.setParameter("project", project);
		q.setParameter("goalType",goalType);
		return q.list();

	}


	@SuppressWarnings("unchecked")
	public List<Goal> getSubGoalByProject(Project project) {
		GoalType goalType = new GoalType();
		goalType.setId(2); ////This fetches the business goal. There is only one per project
		Session session = getSession();
		String query = "Select u from Goal u where u.project= :project and u.goalType=:goalType " ;
		Query q = session.createQuery(query);
		q.setParameter("project", project);
		q.setParameter("goalType",goalType);
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	
	public List<Goal> getSubGoalByDescription(String goalDescription) {
		GoalType goalType = new GoalType();
		goalType.setId(2); ////This fetches the business goal. There is only one per project
		Session session = getSession();
		String query = "Select u from Goal u where u.description= :goalDescription and u.goalType=:goalType " ;
		Query q = session.createQuery(query);
		q.setParameter("goalDescription", goalDescription);
		q.setParameter("goalType",goalType);
		return q.list();
	}


	
	
}
