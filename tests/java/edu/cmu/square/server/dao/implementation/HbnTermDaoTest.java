/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.TermDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Term;


/**
 * @author yirul
 *
 */
public class HbnTermDaoTest extends AbstractSpringBase
{

	@Resource
	TermDao termDao;
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnAbstractDao#fetchAll()}.
	 */
	Project testProject;
	
	
	@Resource
	ProjectDao projectDao;
	
	@Before
	public void setUp()
	{
		Map<String, Object> testMap = super.createUserProjectforRole();
		testProject = (Project)testMap.get("project");
		this.projectDao.create(testProject);
	}
	
	@After
	public void tearDown()
	{
		this.testProject = null;
	}
	
	
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnCategoryDao#addCategoryToProject(edu.cmu.square.server.dao.model.Project, edu.cmu.square.server.dao.model.Category)}.
	 */
	public Term getNewTerm(Project project,String term, String definition)
	{
		
		Term term1= new Term();
		term1.setTerm(term);
		term1.setDefinition(definition);
		term1.setProject(project);
	
		
		return term1;
		
	}
	
	@Test
	public void testAddTermToProject()
	{
	
		Term term1= getNewTerm(this.testProject,"Teting","This is the process of finding bugs");
		Term term2= getNewTerm(this.testProject,"Coding","This is the process of constructing software");
		
		termDao.create(term1);
		termDao.create(term2);
		List<Term> terms= termDao.getTermByProject(testProject);
		Assert.assertEquals(terms.size(),2);
	
	}
	@Test
	public void testRemoveTermToProject()
	{
	
		Term term1= getNewTerm(this.testProject,"Teting","This is the process of finding bugs");
		Term term2= getNewTerm(this.testProject,"Coding","This is the process of constructing software");
		
		termDao.create(term1);
		termDao.create(term2);
		List<Term> terms= termDao.getTermByProject(testProject);
		Assert.assertEquals(terms.size(),2);
		
		termDao.deleteById(term1.getId());
		
		terms= termDao.getTermByProject(testProject);
		Assert.assertEquals(terms.size(),1);
		
		termDao.deleteById(term2.getId());
		
		terms= termDao.getTermByProject(testProject);
		Assert.assertEquals(terms.size(),0);
		
	
	}

	@Test
	public void testUpdateTermToProject()
	{
	
		Term term1= getNewTerm(this.testProject,"Teting","This is the process of finding bugs");
		Term term2= getNewTerm(this.testProject,"Coding","This is the process of constructing software");
		
		termDao.create(term1);
		termDao.create(term2);
		List<Term> terms= termDao.getTermByProject(testProject);
		Assert.assertEquals(terms.size(),2);
		
		
		Term termOutput = termDao.fetch(term1.getId());
		termOutput.setTerm("Loomi");
		termOutput.setDefinition("Good friend");
		termDao.update(termOutput);
		
		Term termOutput2= termDao.fetch(termOutput.getId());
		Assert.assertTrue(termOutput2.getTerm().equalsIgnoreCase(termOutput.getTerm()));
		Assert.assertTrue(termOutput2.getDefinition().equalsIgnoreCase(termOutput.getDefinition()));
		
	}
	@Test
	public void testgetTermByProjectTest()
	{
		
		Term term1= getNewTerm(this.testProject,"Teting","This is the process of finding bugs");
		Term term2= getNewTerm(this.testProject,"Coding","This is the process of constructing software");
		Term term3= getNewTerm(this.testProject,"Tennis","This is a sport that you hit the ball with a raquet");
		
		termDao.create(term1);
		termDao.create(term2);
		termDao.create(term3);
		List<Term> terms= termDao.getTermByProject(testProject);
		Assert.assertEquals(terms.size(),3);

	}

}
