/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.StepDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Step;

/**
 * @author kaalpurush
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class HbnStepDao extends HbnAbstractDao<Step, Integer> implements StepDao {
	

	
	public List<Step> getProjectSteps(Project project) {
		List<Step> steps = null;
		
		String query = "Select s from Step s, ProjectStep ps where s=ps.step and ps.project =:project";
		
		Query q = getSession().createQuery(query);
		q.setParameter("project", project);
		steps = (List<Step>)q.list();
		return steps;
		
	}

	@Override
	public String isStepClosed(String description, Integer projectId)
	{
		String query = "Select ps.status from ProjectStep ps where ps.project.id=:projectId " +
				"and ps.step.description=:description";
		
		Query q = getSession().createQuery(query);
		q.setParameter("projectId", projectId);
		q.setParameter("description", description);
		List<String> list = q.list();
		if (list.isEmpty())
		{
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<Step> getSecuritySteps()
	{
		String query = "Select s from Step s where s.security=true";
		Query q = getSession().createQuery(query);
		return q.list();
	}

	@Override
	public List<Step> getCase1Steps()
	{
		String query = "Select s from Step s where s.security=true";
		Query q = getSession().createQuery(query);
		return q.list();
	}

	@Override
	public List<Step> getCase3Steps()
	{
		String query = "Select s from Step s where s.privacy=true";
		Query q = getSession().createQuery(query);
		return q.list();
	}
}
