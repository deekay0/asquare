/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Term;
/**
 * This interface lists out the data access operations on the User table
 *
 */

public interface TermDao extends AbstractDao<Term, Integer> {

	 List<Term> getTermByProject(Project project);
	 


}
