package edu.cmu.square.client.ui.risksAssessment;

import edu.cmu.square.client.ui.core.content.CommonMessages;

public interface RiskAssessmentPaneMessages  extends CommonMessages
{
	String labelstepTitle();
	String linkPreviousRisk();
	String linkNextRisk();
	String linkBackRiskSummary();
	String linkEditRisk();
	String linkDeleteRisk();
	String labelRiskTitle();
	String labelThreadSource();
	String labelThreadAction();
	String labelVulnerability();
	String labelControlMeasure();
	String labelExposure();
	String labelLikelihood();
	String labelImpact();
	String buttonAddRisk();
	String confirmDelete();
	String labelFieldsRequired();
	String labelAssets();
	String labelArtifacts();
	String errorGettingRisks();
	String errorAuthorization();
	String errorAddingRisks();
	String errorUpdatingRisks();
	String errorRemovingRisks();
		
	String editAssets();
	String editArtifacts();
	String noAssetSelected();
	String noArtifactSelected();
	String associateArtifacts();
	
	String riskTitle();
	String associateAssets();
	String noRisksFound();
	String exposure();

}
