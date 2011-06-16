/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Step;

/**
 * @author kaalpurush
 *
 */
public interface StepDao extends AbstractDao<Step, Integer> 
{	
	List<Step> getProjectSteps(Project project);

	String isStepClosed(String description, Integer projectId);

	List<Step> getSecuritySteps();
	
	List<Step> getCase1Steps();
	
	List<Step> getCase3Steps();
}
