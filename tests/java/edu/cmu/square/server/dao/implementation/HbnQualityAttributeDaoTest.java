/**
 * @author: deekay
 */

package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.QualityAttributeDao;
import edu.cmu.square.server.dao.model.InspectionTechnique;
import edu.cmu.square.server.dao.model.ProjectPackageAttributeRating;
import edu.cmu.square.server.dao.model.QualityAttribute;
import edu.cmu.square.server.dao.model.Step;
import edu.cmu.square.server.dao.model.Technique;


public class HbnQualityAttributeDaoTest extends AbstractSpringBase
{
	
	@Before
	public void setUp()
	{
		super.createUserProjectforRole();	
	}
	
	
	@Resource
	QualityAttributeDao qualityAttributeDao;
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnAbstractDao#fetchAll()}.
	 */
	@Test
	public void testGetAll() {
		
		List<QualityAttribute> attributeList = qualityAttributeDao.getAll();
		assertNotNull(attributeList);
		assertTrue(attributeList.size()>0);
	}
/*	
	@Test
	public void testGetProjectAttributes() {
		
		List<QualityAttribute> attributeList = qualityAttributeDao.getProjectAttributes(getTestProject());
		assertNotNull(attributeList);
		assertTrue(attributeList.size()>0);
	}
	
	@Test
	public void testGetAll2() {
		
		List<ProjectPackageAttributeRating> attributeList = qualityAttributeDao.getAll2();
		assertNotNull(attributeList);
		assertTrue(attributeList.size()>0);
	}
*/
}
