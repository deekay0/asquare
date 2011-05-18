package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.RiskDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Risk;

@Repository
public class HbnRiskDao extends HbnAbstractDao<Risk, Integer> implements RiskDao {

	@SuppressWarnings("unchecked")
	public List<Risk> getRisksByProject(Project project) {
	
		Session session = getSession();
		String query = "Select u from Risk u where u.project= :project" ;
		Query q = session.createQuery(query);
		q.setParameter("project", project);
		return q.list();
	}
   
}
