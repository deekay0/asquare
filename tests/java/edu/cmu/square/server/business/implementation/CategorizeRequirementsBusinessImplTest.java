/**
 * 
 */
package edu.cmu.square.server.business.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.step.interfaces.CategorizeRequirementsBusiness;
import edu.cmu.square.server.dao.model.Category;
import edu.cmu.square.server.dao.model.Requirement;



public class CategorizeRequirementsBusinessImplTest extends AbstractSpringBase
{

	private Category testCategory3;
	@Resource
	private CategorizeRequirementsBusiness categorizeRequirementsBusiness;
	
	
	@Before
	public void setupTest() 
	{
		super.createRequirementsWithCategories();
		categorizeRequirementsBusiness.setProjectName(testProject.getName());
		categorizeRequirementsBusiness.setUserName(testUser.getUserName());
		
		testCategory3 = new Category();
		testCategory3.setLabel("newest test category");
	}
	
	/**
	 * Test method for {@link edu.cmu.square.server.business.step.implementation.CategorizeRequirementsBusinessImpl#addCategory(edu.cmu.square.client.model.GwtProject, edu.cmu.square.client.model.GwtCategory)}.
	 */
	@Test
	public void testAddCategoryDuplicateName()
	{
		try {
			
			
			categorizeRequirementsBusiness.addCategory(testProject.createGwtProject(), testCategory1.createGwtCategory());
		} catch(SquareException e){
			if (e.getType()!=ExceptionType.duplicateName) {
				fail("allowing duplicate entries");
			}
		}
	}

	@Test
	public void testAddCategory()
	{
		try {
			GwtCategory cat3 = testCategory3.createGwtCategory();
			categorizeRequirementsBusiness.addCategory(testProject.createGwtProject(), cat3);
			Category cat = new Category(cat3);
			List<Category> categories = categoryDao.getCategoriesByProject(testProject);
			assertTrue(categories.contains(cat));	
		} catch(SquareException e){
			fail("error"+ e);
			
		}
	}
	/**
	 * Test method for {@link edu.cmu.square.server.business.step.implementation.CategorizeRequirementsBusinessImpl#getCategories(edu.cmu.square.client.model.GwtProject)}.
	 */
	@Test
	public void testGetCategories()
	{
		try
		{
			List<GwtCategory> categories = categorizeRequirementsBusiness.getCategories(testProject.createGwtProject());
			assertTrue(categories.size()==2);
			GwtCategory cat3 = testCategory3.createGwtCategory();
			categorizeRequirementsBusiness.addCategory(testProject.createGwtProject(), cat3);
			List<GwtCategory> categories1 = categorizeRequirementsBusiness.getCategories(testProject.createGwtProject());
			assertTrue(categories1.size()==3);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("error"+ e);
		}
	}

	/**
	 * Test method for {@link edu.cmu.square.server.business.step.implementation.CategorizeRequirementsBusinessImpl#removeCategory(edu.cmu.square.client.model.GwtCategory)}.
	 */
	@Test
	public void testRemoveCategory()
	{
		try
		{
			List<GwtCategory> categories = categorizeRequirementsBusiness.getCategories(testProject.createGwtProject());
			assertTrue(categories.size()==2);
			GwtCategory catToDelete  = testCategory1.createGwtCategory();
			categorizeRequirementsBusiness.removeCategory(catToDelete);
			List<GwtCategory> categories1 = categorizeRequirementsBusiness.getCategories(testProject.createGwtProject());
			assertTrue(categories1.size()==1);
			assertTrue(!categories1.contains(catToDelete));
		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("error"+ e);
		}
		
	}

	/**
	 * Test method for {@link edu.cmu.square.server.business.step.implementation.CategorizeRequirementsBusinessImpl#updateCategory(edu.cmu.square.client.model.GwtProject, edu.cmu.square.client.model.GwtCategory)}.
	 */
	@Test
	public void testUpdateCategory()
	{
		 
		GwtCategory catToUpdate = testCategory1.createGwtCategory();
		catToUpdate.setCategoryName("Updated label");
		assertNotSame(catToUpdate.getCategoryName(), testCategory1.getLabel());
		try
		{
			categorizeRequirementsBusiness.updateCategory(testProject.createGwtProject(), catToUpdate);
			Category updated = categoryDao.fetch(testCategory1.getId());
			assertEquals(catToUpdate.getCategoryName(), updated.getLabel());
		}
		catch (SquareException e)
		{
			
			e.printStackTrace();
			fail("error"+ e);
		}
		
		 
	}

	/**
	 * Test method for {@link edu.cmu.square.server.business.step.implementation.CategorizeRequirementsBusinessImpl#mergeCategories(java.lang.Integer, java.lang.Integer)}.
	 */
	@Test
	public void testMergeCategories()
	{
		try
		{
			
			categorizeRequirementsBusiness.mergeCategories(testCategory1.getId(), testCategory2.getId());
		
			List<Requirement> requirements = requirementDao.findByCategoryId(testCategory1.getId());
			assertTrue(requirements.size()==0);
			
			List<Requirement> requirements2 = requirementDao.findByCategoryId(testCategory2.getId());
			assertTrue(requirements2.size()==2);
			
			Category category = categoryDao.fetch(testCategory1.getId());
			assertTrue(category == null);
			
		}
		catch (SquareException e)
		{
			
			e.printStackTrace();
			fail("Error " + e);
		}
	}
	
	/**
	 * Test method for {@link edu.cmu.square.server.business.step.implementation.CategorizeRequirementsBusinessImpl#addCategoryToRequirements(List, GwtCategory)
	 */
	@Test
	public void testAddCategoryToRequirements()
	{
		try
		{
			
			List<GwtRequirement> requirementList = new ArrayList<GwtRequirement>(2);
			
			requirementList.add(testRequirement.createGwtRequirement());
			requirementList.add(testRequirement2.createGwtRequirement());
			GwtCategory cat1 = testCategory1.createGwtCategory();
			categorizeRequirementsBusiness.addCategoryToRequirements(requirementList, cat1);
		
			List<Requirement> requirements = requirementDao.findByCategoryId(testCategory1.getId());
			assertTrue(requirements.size()==2);
			
		}
		catch (SquareException e)
		{
			
			e.printStackTrace();
			fail("Error " + e);
		}
	}
	
	/**
	 * Test method for {@link edu.cmu.square.server.business.step.implementation.CategorizeRequirementsBusinessImpl#addCategoryToRequirements(List, GwtCategory)
	 */
	@Test
	public void testRemoveCategoryFromRequirements()
	{
		try
		{
			
			List<GwtRequirement> requirementList = new ArrayList<GwtRequirement>(2);
			
			requirementList.add(testRequirement.createGwtRequirement());
			requirementList.add(testRequirement2.createGwtRequirement());
			GwtCategory cat1 = testCategory1.createGwtCategory();
			categorizeRequirementsBusiness.removeCategoryFromRequirements(requirementList, cat1);
		
			List<Requirement> requirements = requirementDao.findByCategoryId(testCategory1.getId());
			assertTrue(requirements.size()==0);
			
		}
		catch (SquareException e)
		{
			
			e.printStackTrace();
			fail("Error " + e);
		}
	}
	
	@Test
	public void testGetCategoriesWithCount() {
		try
		{
			List<GwtCategory> categoriesWithCount = categorizeRequirementsBusiness.getCategoriesWithCount(testProject.createGwtProject());
			assertTrue(categoriesWithCount.size()==2);
			for(GwtCategory cat: categoriesWithCount) {
				assertTrue(cat.getCount() >= 1);
			}
		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Error " + e);
		}
	}

}
