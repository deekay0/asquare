package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.server.dao.interfaces.AsquareCaseDao;
import edu.cmu.square.server.dao.model.AsquareCase;;

@Repository
@SuppressWarnings("unchecked")
public class HbnAsquareCaseDao extends HbnAbstractDao<AsquareCase, Integer> implements AsquareCaseDao {


	public List<AsquareCase> getAllCases()
	{	
		
		Session session = getSession();
		String query = "Select c from AsquareCase c";
		Query q = session.createQuery(query);
		//q.setParameter("caseName", "Case 1");
		return q.list();
		
	}
   
}



