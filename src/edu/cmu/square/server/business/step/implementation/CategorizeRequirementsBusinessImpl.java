package edu.cmu.square.server.business.step.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.step.interfaces.CategorizeRequirementsBusiness;
import edu.cmu.square.server.dao.interfaces.CategoryDao;
import edu.cmu.square.server.dao.interfaces.RequirementDao;
import edu.cmu.square.server.dao.model.Category;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Requirement;

@Service
@Scope("prototype")
public class CategorizeRequirementsBusinessImpl extends BaseBusinessImpl implements CategorizeRequirementsBusiness
{
	@Resource
	private CategoryDao categoryDao;
	@Resource
	private RequirementDao requirementDao;
	
	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer})
	public void addCategory(GwtProject gwtProject, GwtCategory gwtCategory) throws SquareException
	{
		Project currentProject = new Project(gwtProject);
		Category categoryToAdd = new Category(gwtCategory);
		categoryToAdd.setProject(currentProject);

		// get the category by name and check for duplicates
		List<Category> categories = this.categoryDao.getCategoriesByNameAndProject(categoryToAdd.getLabel(), currentProject.getId());

		if (!categories.isEmpty())
		{
			SquareException se = new SquareException("Already exists");
			se.setType(ExceptionType.duplicateName);
			throw se;
		}
		this.categoryDao.create(categoryToAdd);
		gwtCategory.setId(categoryToAdd.getId());
	}

	@AllowedRoles(roles = {Roles.All})
	public List<GwtCategory> getCategories(GwtProject gwtProject) throws SquareException
	{
		List<GwtCategory> categoryList = new ArrayList<GwtCategory>();
		Project project = new Project(gwtProject);
		List<Category> categories = categoryDao.getCategoriesByProject(project);

		for (Category c : categories)
		{
			categoryList.add(c.createGwtCategory());
		}

		return categoryList;
	}

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer})
	public void removeCategory(GwtCategory gwtCategory) throws SquareException
	{
		try
		{
			categoryDao.deleteById(gwtCategory.getId());
		}
		catch (Throwable t)
		{
			throw new SquareException("Error deleting category", t);
		}

	}

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer})
	public void updateCategory(GwtProject gwtProject, GwtCategory gwtCategory) throws SquareException
	{
		if (gwtCategory == null)
		{
			throw new SquareException("gwtCategory should not be null.");
		}
		if (gwtCategory.getId() <= 0)
		{
			throw new SquareException("gwtCategory must have an id.");
		}
		try
		{

			// get the evaluation by name and check for duplicates
			List<Category> categories = this.categoryDao.getCategoriesByNameAndProject(gwtCategory.getCategoryName(), gwtProject.getId());

			if (!categories.isEmpty())
			{
				SquareException se = new SquareException("Already exists");
				se.setType(ExceptionType.duplicateName);
				throw se;
			}
			Category currentCategory = new Category(gwtCategory);

			currentCategory.setProject(new Project(gwtProject));
			categoryDao.update(currentCategory);
		}
		catch (Throwable t)
		{
			throw new SquareException("update failed", t);
		}
	}

	
	@AllowedRoles(roles = Roles.Lead_Requirements_Engineer)
	public void mergeCategories(Integer categoryFromId, Integer categoryToId) throws SquareException
	{
		try
		{
			List<Requirement> requirements = requirementDao.findByCategoryId(categoryFromId);
			Category categoryFrom = new Category(categoryFromId);

			Category categoryTo = new Category(categoryToId);

			for (Requirement requirement : requirements)
			{
				Set<Category> requirementCategories = requirement.getCategories();
				requirementCategories.remove(categoryFrom);

				requirementCategories.add(categoryTo);
			}

			// delete the category
			categoryDao.deleteById(categoryFromId);
		}
		catch (Throwable t)
		{
			throw new SquareException("merge failed", t);
		}

	}

	@AllowedRoles(roles = {Roles.All})
	public void addCategoryToRequirements(List<GwtRequirement> requirements, GwtCategory category) throws SquareException
	{
		try
		{
			Category cat = new Category(category);
			// fetch the requirements, add the category
			for (GwtRequirement r : requirements)
			{
				Requirement req = requirementDao.fetch(r.getId());
				req.getCategories().add(cat);
			}
		}
		catch (Throwable t)
		{
			throw new SquareException("Error in adding categories to requirement", t);
		}
	}

	@AllowedRoles(roles = {Roles.All})
	public List<GwtCategory> getCategoriesWithCount(GwtProject gwtProject) throws SquareException
	{
		List<GwtCategory> categoryCounts = new ArrayList<GwtCategory>();
		List<Object[]> categoryCountMap = categoryDao.getCategoriesWithRequirementCount(gwtProject.getId());
		for (Object[] ci: categoryCountMap) {
			GwtCategory cat = ((Category)ci[0]).createGwtCategory();
			cat.setCount(((Long)ci[1]).intValue());
			categoryCounts.add(cat);
		}
		return categoryCounts;
	}

	@AllowedRoles(roles = {Roles.All})
	public void removeCategoryFromRequirements(List<GwtRequirement> requirements, GwtCategory category) throws SquareException
	{
		try
		{
			Category cat = new Category(category);
			// fetch the requirements, remove the category
			for (GwtRequirement r : requirements)
			{
				Requirement req = requirementDao.fetch(r.getId());
				req.getCategories().remove(cat);
			}
		}
		catch (Throwable t)
		{
			throw new SquareException("Error in removing categories from requirement", t);
		}

	}

	@Override
	public StepEnum getStepDescription() throws SquareException
	{
		return StepEnum.Categorize_Requirements;
	}

	@Override
	public GwtStepVerficationResult verifyStep(Project project) throws SquareException
	{
		GwtStepVerficationResult result = new GwtStepVerficationResult();

		Set<Category> categories = project.getCategories();
		
		Set<Requirement> requirements = project.getRequirements();
		
		int count = 0;
		for (Requirement r : requirements)
		{
			if (r.getCategories().size() == 0)
			{
				count++;
			}
		}

		if (categories.size() == 0)
		{
			result.getMessages().add("There are no categories created !");
			result.setHasWarning(true);
		}
		if (count > 0)
		{
			result.getMessages().add("There are " + count + " requirements that have not been assigned to any category");
			result.setHasWarning(true);
		}

		return result;
	}

}
