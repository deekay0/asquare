/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Technique;

/**
 * @author kaalpurush
 *
 */
public interface TechniqueDao extends AbstractDao<Technique, Integer>
{
	
	List<Technique> getTechniquesByProject(Project testProject);

	void addTechniqueToProject(Project project, Technique technique);

	List<Technique> getTechniquesByNameAndProject(String name, Integer projectId);
	
}
