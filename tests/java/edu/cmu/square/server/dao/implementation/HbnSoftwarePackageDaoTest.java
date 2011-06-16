/**
 * @author: deekay
 */

package edu.cmu.square.server.dao.implementation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.SoftwarePackageDao;
import edu.cmu.square.server.dao.model.SoftwarePackage;


public class HbnSoftwarePackageDaoTest extends AbstractSpringBase
{
	
	@Resource
	SoftwarePackageDao softwarePackageDao;
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnAbstractDao#fetchAll()}.
	 */
	@Test
	public void testGetAll() {
		
		List<SoftwarePackage> packageList = softwarePackageDao.getAll();
		System.out.println(packageList.get(0).getDescription());
		assertNotNull(packageList);
		assertTrue(packageList.size()>0);
	}

}
