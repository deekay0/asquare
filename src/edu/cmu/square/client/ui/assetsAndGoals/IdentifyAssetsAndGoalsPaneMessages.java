package edu.cmu.square.client.ui.assetsAndGoals;

import edu.cmu.square.client.ui.core.content.CommonMessages;

public interface IdentifyAssetsAndGoalsPaneMessages extends CommonMessages
{
	String businessGoalPage();
	String businessGoalDescription();
	String subGoalsButton();
	String errorSettingGoal();
	
	String assetsLabel();
	String addAssetButton();
	String securitySubGoalsLabel();
	String addSecuritySubGoal();
	String businessGoalButtonLabel();
	String associateAssetsAndGoalsButtonLabel();
	String errorAddingSubgoal();
	String errorAuthorization();
	String errorAssetAuthorization();
	String errorAddingAsset();
	String errorRemovingAsset();
	String errorUpdatingAsset();
	String confirmSubGoalDelete();
	String confirmAssetDelete();
	
	String assetAssociationButton();
	String summaryPageTitle();
	String reviewSubgoals();
	
	String securityGoal();
	String privacyGoal();
	String priority();
	String association();
	String subGoalButton();
	String duplicatePrioritiesError();
	String prioritiesCommitError();
	String subGoalsCommitError();
	String associateAssetsLink();
	String errorUpdatingSubGoal();
	String errorAuthorizationUpdateSubgoal();
	
	String dialogTitle();
	String errorAssociateGoalAsset();
	String errorAssociateSubgoalAssetAuth();
	String errorRemovingAssociation();
	String errorRemoveAssociationAuth();
	String subgoalAssociationError(String description);
	String assetAssociationError(String description);
	
	String subgoal();
	String assets();
	String noelementsFound();

}
