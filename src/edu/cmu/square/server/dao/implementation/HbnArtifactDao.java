/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.ArtifactDao;
import edu.cmu.square.server.dao.model.Artifact;
import edu.cmu.square.server.dao.model.Project;

/**
 * @author kaalpurush
 *
 */
@Repository
public class HbnArtifactDao extends HbnAbstractDao<Artifact, Integer> implements ArtifactDao{

	
	@SuppressWarnings("unchecked")
	public List<Artifact> getArtifactsByProject(Project project) {
	
		Session session = getSession();
		String query = "Select u from Artifact u where u.project= :project" ;
		Query q = session.createQuery(query);
		q.setParameter("project", project);
		return q.list();
	}


}
