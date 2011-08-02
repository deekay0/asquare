package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.TermDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Term;

@Repository
public class HbnTermDao extends HbnAbstractDao<Term, Integer>implements TermDao
{

	@SuppressWarnings("unchecked")
	@Override
	public List<Term> getTermByProject(Project project)
	{
		Session session = super.getSession();
		String query = "Select p.terms from Project p where p=:myProject";
		Query q = session.createQuery(query);
		q.setParameter("myProject", project);
		return q.list();
	}

	
	

	
}
