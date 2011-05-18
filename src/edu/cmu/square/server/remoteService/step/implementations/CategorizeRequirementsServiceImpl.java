/**
 * 
 */
package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.remoteService.step.interfaces.CategorizeRequirementsService;
import edu.cmu.square.server.business.step.interfaces.CategorizeRequirementsBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

/**
 * @author yirul
 * 
 */
@Service
public class CategorizeRequirementsServiceImpl extends SquareRemoteServiceServlet implements CategorizeRequirementsService
{

	private static final long serialVersionUID = -2353832112072071414L;
	@Resource
	private CategorizeRequirementsBusiness categorizeRequirementsBusiness;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cmu.square.client.remoteService.interfaces.CategorizeRequirementsService
	 * #addCategory(edu.cmu.square.client.model.GwtProject,
	 * edu.cmu.square.client.model.GwtCategory)
	 */
	
	public GwtCategory addCategory(GwtProject gwtProject, GwtCategory gwtCategory) throws SquareException
	{

		categorizeRequirementsBusiness.addCategory(gwtProject, gwtCategory);

		return gwtCategory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cmu.square.client.remoteService.interfaces.CategorizeRequirementsService
	 * #getCategories(edu.cmu.square.client.model.GwtProject)
	 */
	
	public List<GwtCategory> getCategories(GwtProject gwtProject) throws SquareException
	{
		List<GwtCategory> categoryList = null;
		categoryList = categorizeRequirementsBusiness.getCategories(gwtProject);

		return categoryList;
	}
	public List<GwtCategory> getCategoriesWithCount(GwtProject gwtProject) throws SquareException
	{
		List<GwtCategory> categoryList = null;
		categoryList = categorizeRequirementsBusiness.getCategoriesWithCount(gwtProject);

		return categoryList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cmu.square.client.remoteService.interfaces.CategorizeRequirementsService
	 * #removeCategory(edu.cmu.square.client.model.GwtProject,
	 * edu.cmu.square.client.model.GwtCategory)
	 */
	
	public void removeCategory(GwtCategory gwtCategory) throws SquareException
	{
		categorizeRequirementsBusiness.removeCategory(gwtCategory);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cmu.square.client.remoteService.interfaces.CategorizeRequirementsService
	 * #updateCategory(edu.cmu.square.client.model.GwtProject,
	 * edu.cmu.square.client.model.GwtCategory)
	 */
	
	public void updateCategory(GwtProject gwtProject, GwtCategory gwtCategory) throws SquareException
	{
		categorizeRequirementsBusiness.updateCategory(gwtProject, gwtCategory);

	}

	
	public void setValuesForAuthorization()
	{
		setValuesForAuthorization(categorizeRequirementsBusiness);

	}

	
	public void mergeCategories(Integer categoryFromId, Integer categoryToId) throws SquareException
	{
		categorizeRequirementsBusiness.mergeCategories(categoryFromId, categoryToId);

	}

}
