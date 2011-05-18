/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirement;


public interface CategorizeRequirementsBusiness extends StepBusinessInterface {
	/**
	 * This returns all the categories for this project.	 * 
	 * @param gwtProject The project that the user selected.
	 * @return The categories associated with this project.
	 * @throws SquareException  This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	List<GwtCategory> getCategories(GwtProject gwtProject) throws SquareException;
	
	/**
	 * This add a new category to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtCategory The category to be added to the project.
	 * @throws SquareException
	 */
	void addCategory(GwtProject gwtProject, GwtCategory gwtCategory) throws SquareException;
	
	/**
	 * This update an exist category to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtCategory The category which has been updated.
	 * @throws SquareException
	 */
	void updateCategory(GwtProject gwtProject, GwtCategory gwtCategory) throws SquareException;
	
	/**
	 * This remove a selected category from the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtCategory The category that wanted to be removed.
	 * @throws SquareException
	 */
	void removeCategory(GwtCategory gwtCategory) throws SquareException;
	/**
	 * This will change all the requirements of categoryFromId category to
	 * categoryToId. It will remove the categoryFromId from all requirements of the 
	 * project, and then delete the categoryFromId.   
	 * @param categoryFromId The id of the category being merged to another category
	 * @param categoryToId The id of the category that the other category is being merged to.
	 * @throws SquareException
	 */
	void mergeCategories(Integer categoryFromId, Integer categoryToId) throws SquareException;
	
	/**
	 * This adds a category to the given list of requirements. It would not add the category if it 
	 * already exits. The category must have an id.
	 * @param requirements The list of requirements to be categorized. 
	 * @param category The category to be added.
	 * @throws SquareException
	 */
	void addCategoryToRequirements(List<GwtRequirement> requirements, GwtCategory category) throws SquareException;
	
	/**
	 * This removes the given category from the given list of requirements. 
	 * @param requirements The given list of requirements.
	 * @param category The category to be removed. This must contain an Id.
	 * @throws SquareException Thrown if the database throws errors, or if the current user does
	 * not have the access to perform this.
	 */
	void removeCategoryFromRequirements(List<GwtRequirement> requirements, GwtCategory category) throws SquareException;
	
	/**
	 * This returns all the categories of the project along with the count of each requirement per category.
	 * @param gwtProject The given project.
	 * @return A list of GwtCategory with the count of each category
	 * @throws SquareException Thrown if there's an authorization.
	 */
	List<GwtCategory> getCategoriesWithCount(GwtProject gwtProject) throws SquareException;
}
