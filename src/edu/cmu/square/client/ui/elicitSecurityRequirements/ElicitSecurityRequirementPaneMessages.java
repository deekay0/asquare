package edu.cmu.square.client.ui.elicitSecurityRequirements;


import edu.cmu.square.client.ui.core.content.CommonMessages;


public interface ElicitSecurityRequirementPaneMessages extends CommonMessages
{
	String labelstepTitle();
	String linkPreviousRequirement();
	String linkNextRequirement();
	String linkBackRequirementSummary();
	String linkEditRequirement();
	String linkDeleteRequirement();
	String labelRequirementTitle();

	String buttonAddRequirement();
	String confirmDelete();
	String labelFieldsRequired();
	String labelLoading();

	String errorGettingRequirements();
	String errorAuthorization();
	String errorAddingRequirements();
	String errorUpdatingRequirements();
	String errorRemovingRequirements();
	
	String editSubGoalsLink();
	String editRisksLink();
	String editArtifactLink();
	String noSelectedSecurityGoal();
	String noSelectedArtifacts();
	String noSelectedRisks();
	String associateGoals();
	String associateRisks();
	String associateArtifacts();
	String securityGoal();
	String risks();
	String artifacts();
	String description();
	String duplicateTitleError();
	String errorFilePath(String filePath);
	String titleDescriptionMissing();
	String parsingError();
	String emptyFile();
	String noRequirementsFound();
	String requirementTitle();
	String uploadFileFormat();
	String associateSubGoalFirst();
}
