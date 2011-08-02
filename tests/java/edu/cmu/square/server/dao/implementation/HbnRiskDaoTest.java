/**
 * 
 */
package edu.cmu.square.server.dao.implementation;


import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.dao.interfaces.ArtifactDao;
import edu.cmu.square.server.dao.interfaces.AssetDao;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.RiskDao;
import edu.cmu.square.server.dao.model.Artifact;
import edu.cmu.square.server.dao.model.Asset;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Risk;



public class HbnRiskDaoTest extends AbstractSpringBase {

	
	@Resource
	RiskDao riskDao;
	/**
	 * Test method for {@link edu.cmu.square.server.dao.implementation.HbnAbstractDao#fetchAll()}.
	 */
	Project testProject;
	
	@Resource
	ProjectDao projectDao;
	
	@Resource
	 AssetDao assetDao;
	
	@Resource
	ArtifactDao artifactDao;
	
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
	public int createRisk(String riskTitle)
	{
		Risk risk = new Risk();
		risk.setRiskTitle(riskTitle);
		risk.setThreatSource("ThreadSource");
		risk.setThreatAction("ThreadAction");
		risk.setVulnerability("Vulnerability...");
		risk.setPlannedMeasures("");
		risk.setCurrentMeasures("Control Measure");
		risk.setLikelihood(1);
		risk.setImpact(2);
		risk.setProject(testProject);
		riskDao.create(risk);
		return risk.getId();
	}
	
	public Asset createAsset()
	{
		String assetDescription="First Asset";
		Asset asset = new Asset();
		asset.setDescription(assetDescription);
		asset.setProject(testProject);
		assetDao.create(asset);
		Asset assetOut= assetDao.fetch(asset.getId());
		return assetOut;
	}
	
	public Artifact createArtifact()
	{
		String assetDescription="Artifact";
		Artifact artifact = new Artifact();
		artifact.setDescription(assetDescription);
		artifact.setProject(testProject);
		artifact.setRevision("1.0");
		artifact.setLink("www.google.com");
		artifact.setName("We found u!");
		artifactDao.create(artifact);
		return artifact;
	}

	@Test
	public void testAddRisk() {

		int id=createRisk("riskTitle1");
		Risk riskOut= riskDao.fetch(id);
		assertTrue(riskOut.getRiskTitle().equalsIgnoreCase("riskTitle1"));
		id=createRisk("riskTitle2");
		 riskOut= riskDao.fetch(id);
		assertTrue(riskOut.getRiskTitle().equalsIgnoreCase("riskTitle2"));

	}
	
	
	
	@Test
	public void testgetRisksFromProject() {

		@SuppressWarnings("unused")
		int id=createRisk("riskTitle1");
		id=createRisk("riskTitle2");
		id=createRisk("riskTitle3");
		id=createRisk("riskTitle4");
		List<Risk> list = riskDao.getRisksByProject(testProject);
		assertTrue(list.size()==4);

	}
	@Test
	public void testUpdateRisk() {
		
	int id=createRisk("riskTitle1");
	Risk risk= riskDao.fetch(id);
	
	risk.setRiskTitle("UpdatedTitle");
	risk.setThreatSource("UpdatedThreadSource");
	risk.setThreatAction("UpdatedThreadAction");
	risk.setVulnerability("UpdatedVulnerability...");
	risk.setPlannedMeasures("Updated");
	risk.setCurrentMeasures("UpdatedControl Measure");
	risk.setLikelihood(9);
	risk.setImpact(9);
	
	riskDao.update(risk);
	Risk updatedRisk= riskDao.fetch(id);
	assertTrue( updatedRisk.getVulnerability().equalsIgnoreCase("UpdatedVulnerability..."));
	
	
}
	
	
	@Test
	public void testAddAssetToRisk() {

		int id=createRisk("riskTitle1");
		Risk riskOut= riskDao.fetch(id);
		assertTrue(riskOut.getRiskTitle().equalsIgnoreCase("riskTitle1"));
		id=createRisk("riskTitle2");
		riskOut= riskDao.fetch(id);
		assertTrue(riskOut.getRiskTitle().equalsIgnoreCase("riskTitle2"));
		Asset asset1 = createAsset();
		Asset asset2 = createAsset();
		Set<Asset> listOfAssets= riskOut.getAssets();
	
		assertTrue(riskOut.getAssets().size()==0);
		listOfAssets.add(asset1);
		riskOut= riskDao.fetch(id);
		assertTrue(riskOut.getAssets().size()==1);
		listOfAssets.add(asset2);
		riskOut= riskDao.fetch(id);
		assertTrue(riskOut.getAssets().size()==2);
		
	}
	
	@Test
	public void testAddArtifactToRisk() {

		int id=createRisk("riskTitle1");
		Risk riskOut= riskDao.fetch(id);
		assertTrue(riskOut.getRiskTitle().equalsIgnoreCase("riskTitle1"));
		id=createRisk("riskTitle2");
		riskOut= riskDao.fetch(id);
		assertTrue(riskOut.getRiskTitle().equalsIgnoreCase("riskTitle2"));
		Artifact artifact1 = createArtifact();
		Artifact artifact2 = createArtifact();
		
		Set<Artifact> listOfArtifacts= riskOut.getArtifacts();
		
		assertTrue(riskOut.getArtifacts().size()==0);
		
		listOfArtifacts.add(artifact1);
		riskOut= riskDao.fetch(id);
		assertTrue(riskOut.getArtifacts().size()==1);
		
		listOfArtifacts.add(artifact2);
		riskOut= riskDao.fetch(id);
		assertTrue(riskOut.getArtifacts().size()==2);
		
	}
	
	
/*		
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
		
	}*/
	


}

