/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.AssetDao;
import edu.cmu.square.server.dao.model.Asset;
import edu.cmu.square.server.dao.model.Project;


/**
 * @author yirul
 *
 */
@Repository
public class HbnAssetDao extends HbnAbstractDao<Asset, Integer> implements AssetDao{

	
	
	@SuppressWarnings("unchecked")
	public List<Asset> getAssetByProject(Project project) {
	
		Session session = getSession();
		String query = "Select u from Asset u where u.project= :project" ;
		Query q = session.createQuery(query);
		q.setParameter("project", project);
		return q.list();
	}
   






}
