package edu.cmu.square.server.dao.implementation;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.TradeoffReasonDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageTradeoffreason;
import edu.cmu.square.server.dao.model.ProjectPackageTradeoffreasonId;
import edu.cmu.square.server.dao.model.SoftwarePackage;

public class HbnTradeoffReasonDaoTest extends AbstractSpringBase
{
	@Resource
	private TradeoffReasonDao tradeoffReasonDao;
	
	@Test
	@Transactional
	public void testAddTradeoffReason()
	{
		ProjectPackageTradeoffreasonId id = new ProjectPackageTradeoffreasonId(1047,2);
		
		Project  newProject = new Project();
		SoftwarePackage softwarePackage = new SoftwarePackage();
		
		ProjectPackageTradeoffreason tradeoffReason = new ProjectPackageTradeoffreason(id, newProject, softwarePackage,"test reason", 1);
		tradeoffReasonDao.create(tradeoffReason);
		
		//ProjectPackageTradeoffreason result = tradeoffReasonDao.fetchAll();
		//Assert.assertEquals(tradeoffReason.getTradeoffreason(), result.getTradeoffreason());
	}
}
