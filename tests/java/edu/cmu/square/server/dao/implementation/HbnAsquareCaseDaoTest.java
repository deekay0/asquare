/**
 * Nan
 */

package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.AsquareCaseDao;
import edu.cmu.square.server.dao.model.AsquareCase;


public class HbnAsquareCaseDaoTest extends AbstractSpringBase
{
	
	@Resource
	AsquareCaseDao asquareCaseDao;
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnAbstractDao#fetchAll()}.
	 */
	@Test
	public void testFetchAll() {
		
		List<AsquareCase> caseList = asquareCaseDao.fetchAll();
		assertNotNull(caseList);
		assertTrue(caseList.size()>0);
		
	}

}
