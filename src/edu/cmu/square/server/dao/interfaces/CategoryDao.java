/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Category;
import edu.cmu.square.server.dao.model.Project;

/**
 * @author yirul
 *
 */
public interface CategoryDao extends AbstractDao<Category, Integer>
{
	List<Category> getCategoriesByProject(Project testProject);
	
	void addCategoryToProject(Project project, Category category);
	
	List<Category> getCategoriesByNameAndProject(String name, Integer projectID);

	List<Object[]> getCategoriesWithRequirementCount(Integer id);

}
