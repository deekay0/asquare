/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Artifact;
import edu.cmu.square.server.dao.model.Project;

/**
 * @author kaalpurush
 *
 */
public interface ArtifactDao extends AbstractDao<Artifact, Integer> {

	public List<Artifact> getArtifactsByProject(Project project);
}
