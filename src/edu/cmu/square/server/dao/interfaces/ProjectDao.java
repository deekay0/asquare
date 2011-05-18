/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Project;

/**
 * @author kaalpurush
 *
 */
public interface ProjectDao extends AbstractDao<Project, Integer> {

	Project findByName(String projectName);
	
	List<Project> getIncompleteProjectsForUser(Integer userId);
	
	List<Project> getCompletedProjectsForUser(Integer userId);
	
}
