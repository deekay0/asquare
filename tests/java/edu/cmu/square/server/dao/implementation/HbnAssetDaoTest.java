/**
 * 
 */
package edu.cmu.square.server.dao.implementation;


import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.AssetDao;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.model.Asset;
import edu.cmu.square.server.dao.model.Project;



public class HbnAssetDaoTest extends AbstractSpringBase {

	
	@Resource
	AssetDao assetDao;
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
	
	@Test
	public void testAddAsset() {
	
		String assetDescription="First Asset";
		Asset asset = new Asset();
		asset.setDescription(assetDescription);
		asset.setProject(testProject);
		assetDao.create(asset);
		Asset assetOut= assetDao.fetch(asset.getId());
		assertTrue(assetOut.getDescription().equalsIgnoreCase(assetDescription));

	}
	@Test
	public void testUpdateAsset() {
		
		
		String assetDescription="First Asset";
		Asset asset = new Asset();
		asset.setDescription(assetDescription);
		asset.setProject(testProject);	
		assetDao.create(asset);
		Asset assetOut= assetDao.fetch(asset.getId());
		assertTrue(assetOut.getDescription().equalsIgnoreCase(assetDescription));
		
		assetOut.setDescription("Party");
		assetDao.update(assetOut);
		Asset assetOut2 = assetDao.fetch(assetOut.getId());
		
		assertTrue(assetOut2.getDescription().equalsIgnoreCase("Party"));
		assertTrue(assetOut2.getProject().getId()==testProject.getId());	
	

		
	}
	@Test
	public void testDeleteAsset() {
		
		
		String assetDescription="First Asset";
		Asset asset = new Asset();
		asset.setDescription(assetDescription);
		asset.setProject(testProject);	
		assetDao.create(asset);
		Asset assetOut= assetDao.fetch(asset.getId());
		
		assetDao.deleteById(assetOut.getId());

		Asset goalOut2 = null;
		try
		{
			goalOut2= assetDao.fetch(assetOut.getId());
		}
		catch(Exception e)
		{
			goalOut2 = null;
		}
		
		assertTrue(goalOut2 == null);
		
	}
	@Test
	public void testgetAssetByProject(){
		
		Project project = testProject;
		project.setId(testProject.getId());
		String assetDescription="First Asset";
		Asset asset = new Asset();
		asset.setDescription(assetDescription);
		asset.setProject(testProject);		
		assetDao.create(asset);
		
		Asset asset2 = new Asset();
		asset2.setDescription(assetDescription);
		asset2.setProject(testProject);	
		assetDao.create(asset2);
		
		
		 List<Asset> assetList=	assetDao.getAssetByProject(testProject);//projectDao.getProjectAssets(project);
		 assertTrue(assetList.size()==2);
		
	}
	


}

