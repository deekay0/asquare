/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.CategoryDao;
import edu.cmu.square.server.dao.interfaces.ProjectDao;


/**
 * @author yirul
 *
 */
public class HbnCategoryDaoTest extends AbstractSpringBase
{

	@Resource
	private CategoryDao categoryDao;
	
	@Resource
	private ProjectDao projectDao;

	

	@Before
	public void setUp()
	{
		super.createRequirementsWithCategories();
	}
	
	@After
	public void tearDown()
	{
		this.testProject = null;
		this.testCategory1 = null;
		this.testCategory2 = null;
	}
	
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnCategoryDao#addCategoryToProject(edu.cmu.square.server.dao.model.Project, edu.cmu.square.server.dao.model.Category)}.
	 */
	@Test
	public void testAddCategoryToProject()
	{
//		//Add the category to the DB
//		this.categoryDao.addCategoryToProject(this.testProject, this.testCategory1);
//		
//		//check that the category was added
//		List<Category> categories = this.categoryDao.getCategoriesByProject(testProject);
//		Assert.assertEquals(1, categories.size());
//		Assert.assertEquals(testCategory1.getLabel(), categories.get(0).getLabel());
//		Assert.assertEquals(testCategory1.getProject().getId(), categories.get(0).getProject().getId());
	}

	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnCategoryDao#getCategoriesByNameAndProject(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testGetCategoriesByNameAndProject()
	{
//		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnCategoryDao#getCategoriesByProject(edu.cmu.square.server.dao.model.Project)}.
	 */
	@Test
	public void testGetCategoriesByProject()
	{
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetCategoriesWithRequirementCount() 
	{
		List<Object[]> categoryToCount = categoryDao.getCategoriesWithRequirementCount(testProject.getId());
		assertTrue(categoryToCount.size()==2);
		for(Object[] ci: categoryToCount) 
		{
			assertTrue((Long)ci[1]>=1);
		}
		
	}
}
