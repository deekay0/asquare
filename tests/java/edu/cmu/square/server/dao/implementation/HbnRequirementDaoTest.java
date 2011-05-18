package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.model.Requirement;

public class HbnRequirementDaoTest extends AbstractSpringBase
{
	
	@Before
	public void setupTest() 
	{
		super.createRequirementsWithCategories();
	}
	
	@Test
	public void testFindByCategoryId()
	{
		List<Requirement> requirements = requirementDao.findByCategoryId(testCategory1.getId());
		assertTrue(requirements.size()==1);
		assertEquals(requirements.get(0).getId(), testRequirement.getId());
		
		List<Requirement> requirements2 = requirementDao.findByCategoryId(testCategory2.getId());
		assertTrue(requirements2.size()==1);
		assertEquals(requirements2.get(0).getId(), testRequirement2.getId());
	}
	@Test
	public void testRequirementExport()
	{
		
		requirementDao.exportToXML(null);
	}

}
