/**
 * 
 */
package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtProject;

/**
 * @author yirul
 *
 */
@RemoteServiceRelativePath("categorizeRequirements.rpc")
public interface CategorizeRequirementsService extends RemoteService {
	
	/**
	 * This returns all the categories in this project.
	 * @param gwtProject The project that the user selected.
	 * @return The categories associated with this project.
	 * @throws SquareException
	 */
	List<GwtCategory> getCategories(GwtProject gwtProject)throws SquareException;
	
	
	List<GwtCategory> getCategoriesWithCount(GwtProject gwtProject) throws SquareException;
	/**
	 * This add a new categories to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtCategory The category to be added to the project
	 * @return The category that has been added
	 * @throws SquareException
	 */
	GwtCategory addCategory(GwtProject gwtProject, GwtCategory gwtCategory)throws SquareException;
	
	/**
	 * This update the existing category in the project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtCategory The category to be updated to the project
	 * @throws SquareException
	 */
	void updateCategory(GwtProject gwtProject, GwtCategory gwtCategory) throws SquareException;
	
	/**
	 * This remove the existing category from the project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtCategory The category to be removed from the project.
	 * @throws SquareException
	 */
	void removeCategory(GwtCategory gwtCategory)throws SquareException;
	
	/**
	 * This will change all the requirements of categoryFromId category to
	 * categoryToId. It will remove the categoryFromId from all requirements of the 
	 * project, and then delete the categoryFromId.   
	 * @param categoryFromId The id of the category being merged to another category
	 * @param categoryToId The id of the category that the other category is being merged to.
	 * @throws SquareException
	 */
	void mergeCategories(Integer categoryFromId, Integer categoryToId) throws SquareException;
	

}
