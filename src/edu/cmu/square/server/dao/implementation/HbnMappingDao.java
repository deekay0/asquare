package edu.cmu.square.server.dao.implementation;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.MappingDao;

@Repository
public class HbnMappingDao implements MappingDao {
	
	@Resource
	protected SessionFactory sessionFactory;
	
	public void addAssetGoalMapping(int goalId, int assetId) {
		addMapping("goal_asset", goalId, assetId);
	}
/*	
	public void addRiskArtifactMapping(int riskId, int artifactId) {
		addMapping("risk_artifact", riskId, artifactId);
	}
	
	public void addRiskAssetMapping(int riskId, int assetId) {
		addMapping("risk_asset", riskId, assetId);
	}
	
	public void addRequirementArtifactMapping(int requirementId, int artifactId){
		addMapping("requirement_artifact", requirementId, artifactId);
	}
	
	public void addRequirementCategoryMapping(int requirementId, int categoryId) {
		addMapping("requirement_category", requirementId, categoryId);
	}
	
	public void addRequirementRiskMapping(int requirementId, int riskId) {
		addMapping("requirement_risk", requirementId, riskId);
	}
	*/
	public void addRequirementGoalMapping(int requirementId, int goalId) {
		addMapping("requirement_goal", requirementId, goalId);
	}
	
	public void addMapping(String tableName, int firstId, int secondId) {
		Session session = getSession();
		String sql = "insert into "+tableName+" value ("+firstId+", "+secondId+")";
		Query q = session.createSQLQuery(sql);
		
		q.executeUpdate();		
	}
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
