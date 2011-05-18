/**
 * 
 */
package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtProject;


/**
 * @author yirul
 *
 */
public interface CategorizeRequirementsServiceAsync {
	/**
	 * This returns all categories for this project.	 * 
	 * @param gwtProject The project that the user selected.
	 * @return The Categories associated with this project.
	 */
	void getCategories(GwtProject gwtProject, AsyncCallback<List<GwtCategory>> callback);
	
	void getCategoriesWithCount(GwtProject gwtProject, AsyncCallback<List<GwtCategory>> callback);
	/**
	 * This add a new category to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtCategory The category to be added to the project.
	 */
	void addCategory(GwtProject gwtProject, GwtCategory gwtCategory, AsyncCallback<GwtCategory> callback);
	/**
	 * This update an exist category to the selected project.
	 * @param gwtCategory The category which has been updated.
	 * @param gwtProject the selected project
	 * @throws SquareException
	 */
	void updateCategory(GwtProject gwtProject, GwtCategory gwtCategory, AsyncCallback<Void> callback);

	/**
	 * This remove a selected category from the selected project.
	 * @param gwtCategory The category that wanted to be removed.
	 * @throws SquareException
	 */
	void removeCategory(GwtCategory gwtCategory, AsyncCallback<Void> callback);

	/**
	 * This will change all the requirements of categoryFromId category to
	 * categoryToId. It will remove the categoryFromId from all requirements of the 
	 * project, and then delete the categoryFromId.   
	 * @param categoryFromId The id of the category being merged to another category
	 * @param categoryToId The id of the category that the other category is being merged to.
	 * @throws SquareException
	 */
	void mergeCategories(Integer categoryFromId, Integer categoryToId, AsyncCallback<Void> callback);
}
