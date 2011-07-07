package edu.cmu.square.server.dao.implementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Role;
@Repository
@SuppressWarnings("unchecked")
public class HbnProjectDao extends HbnAbstractDao<Project, Integer> implements ProjectDao {
	
	public Project findByName(String projectName) {
		Session session = getSession();
		String query = "Select p from Project p where p.name = :name";
		Query q = session.createQuery(query);
		q.setParameter("name", projectName);
		
		List<Project> pList = q.list();
		if (pList.isEmpty()) {
			return null;
		}
		return pList.get(0);
	}

	@Override
	public List<Project> getIncompleteProjectsForUser(Integer userId, Integer caseId)
	{
		String query = "Select p, upr.role from Project p, " +
				"UserProjectRole upr " +
				"where p in (Select ps.project from ProjectStep ps where " +
				"(ps.status=:stepStatus1 or ps.status=:stepStatus2)) " +
				"and upr.user.id =:userId and upr.project=p" +
				" and p.cases.id=:caseId" +
				" group by p";
		
		Query q = getSession().createQuery(query);
		q.setParameter("stepStatus1", StepStatus.NotStarted.getLabel());
		q.setParameter("stepStatus2", StepStatus.InProgress.getLabel());
		q.setParameter("userId", userId);
		q.setParameter("caseId", caseId);
		List<Object[]> list =  q.list();
		List<Project> projects = new ArrayList<Project>(list.size());
		for (Object[] o: list)
		{
			Project p = (Project)o[0];
			Role r = (Role)o[1];
			p.setCurrentRole(r);
			projects.add(p);
		}
		
/*		String query2 = "Select c from role c where c.id=1";
		Query q2 = getSession().createQuery(query2);
		System.out.println("asdsfasdfasdf   "+q2.list().size()+"asfadf");*/
		
		return projects;
	}
	
	@Override
	public List<Project> getCompletedProjectsForUser(Integer userId, Integer caseId)
	{
		String query = "Select p, upr.role from Project p, " +
				"UserProjectRole upr " +
				"where p not in (Select ps.project from ProjectStep ps " +
				"where (ps.status=:stepStatus1 or ps.status=:stepStatus2)) " +
				"and upr.user.id=:userId and upr.project=p" +
				" and p.cases.id=:caseId" +
				" group by p";
		
		Query q = getSession().createQuery(query);
		q.setParameter("stepStatus1", StepStatus.NotStarted.getLabel());
		q.setParameter("stepStatus2", StepStatus.InProgress.getLabel());
		q.setParameter("userId", userId);
		q.setParameter("caseId", caseId);
		List<Object[]> list =  q.list();
		List<Project> projects = new ArrayList<Project>(list.size());
		for (Object[] o: list)
		{
			Project p = (Project)o[0];
			Role r = (Role)o[1];
			p.setCurrentRole(r);
			projects.add(p);
		}
		return projects;
	}



}
