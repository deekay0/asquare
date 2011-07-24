package edu.cmu.square.server.dao.interfaces;

public interface MappingDao {
	/**
	 * Add mapping into tableName table. For example, goal_asset, your firstId will be goal and second will be asset
	 * @param tableName
	 * @param firstId
	 * @param secondId
	 */
	void addMapping(String tableName, int firstId, int secondId);
	
	void addAssetGoalMapping(int goalId, int assetId);
	
	void addRiskArtifactMapping(int riskId, int artifactId);
	
	void addRiskAssetMapping(int riskId, int assetId);
	
	void addRequirementArtifactMapping(int requirementId, int artifactId);
	
	void addRequirementCategoryMapping(int requirementId, int categoryId);
	
	void addRequirementRiskMapping(int requirementId, int riskId);
	
	void addRequirementGoalMapping(int requirementId, int goalId);
}
