/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.EvaluationCriteria;
import edu.cmu.square.server.dao.model.Project;

/**
 * @author kaalpurush
 *
 */
public interface TechniqueEvaluationDao extends
		AbstractDao<EvaluationCriteria, Integer> {
	
	List<EvaluationCriteria> getEvaluationsByProject(Project testProject);

	List<EvaluationCriteria> getEvaluationsByNameAndProject(String name, Integer projectId);
}
