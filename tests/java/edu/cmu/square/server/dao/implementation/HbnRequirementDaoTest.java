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
		List<Requirement> requirements = requirementDao.fetchAll();
		assertTrue(requirements.size()>0);
	}
	@Test
	public void testRequirementExport()
	{
		
		requirementDao.exportToXML(null);
	}

}
