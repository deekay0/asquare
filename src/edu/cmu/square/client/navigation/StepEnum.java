package edu.cmu.square.client.navigation;

import edu.cmu.square.client.ui.SelectSecurityTechnique.SelectSecurityElicitationTechniquePilot;
import edu.cmu.square.client.ui.agreeOnDefinitions.AgreeOnDefinitionsPilot;
import edu.cmu.square.client.ui.assetsAndGoals.AssetsAndGoalsPilot;
import edu.cmu.square.client.ui.categorizeRequirements.CategorizeRequirementsPilot;
import edu.cmu.square.client.ui.collectArtifacts.CollectArtifactsPilot;
import edu.cmu.square.client.ui.elicitSecurityRequirements.ElicitSecurityRequirementsPilot;
import edu.cmu.square.client.ui.inspectRequirements.InspectRequirementsPilot;
import edu.cmu.square.client.ui.prioritizeRequirements.PrioritizeRequirementsPilot;
import edu.cmu.square.client.ui.reviewOfRequirementsByAcquisitionOrganization.ReviewOfRequirementsByAcquisitionPilot;
import edu.cmu.square.client.ui.reviewPackages.ReviewPackagesPilot;
import edu.cmu.square.client.ui.risksAssessment.RiskAssessmentPilot;
import edu.cmu.square.client.ui.reviewAndFinalizeRequirements.ReviewAndFinalizeRequirementsPilot;

public enum StepEnum
{
	Agree_On_Definitions("Step 1: Agree On Definitions",
			AgreeOnDefinitionsPilot.generateNavigationId(AgreeOnDefinitionsPilot.PageId.home)),
	Identify_Assets_And_Goals("Step 2: Identify Assets and Goals", 
			AssetsAndGoalsPilot.generateNavigationId(AssetsAndGoalsPilot.PageId.home)),
	Collect_Artifacts("Step 3: Collect Artifacts", 
			CollectArtifactsPilot.generateNavigationId(CollectArtifactsPilot.PageId.home)),
	Perform_Risk_Assessment("Step 4: Review COTS software package information and specifications",
			RiskAssessmentPilot.generateNavigationId(RiskAssessmentPilot.PageId.home)),
	Select_Elicitation_Technique("Step 5: Review and finalize security requirements",
			SelectSecurityElicitationTechniquePilot.generateNavigationId(SelectSecurityElicitationTechniquePilot.PageId.home)),		
	Elicit_Security_Requirements("Step 3: Identify Preliminary Security Requirements",
			ElicitSecurityRequirementsPilot.generateNavigationId(ElicitSecurityRequirementsPilot.PageId.home)),		
	Categorize_Requirements("Step 7: Final product selection",
			CategorizeRequirementsPilot.generateNavigationId(CategorizeRequirementsPilot.PageId.home)),
	Prioritize_Requirements("Step 8: Prioritize Requirements",
			PrioritizeRequirementsPilot.generateNavigationId(PrioritizeRequirementsPilot.PageId.home)),
	Inspect_Requirements("Step 9: Inspect Requirements",
			InspectRequirementsPilot.generateNavigationId(InspectRequirementsPilot.PageId.home)),
	Review_Of_Requirements_By_Acquisition_Organization("Case 1 - Step 4: Review Of Requirements By Acquisition Organization", 
			ReviewOfRequirementsByAcquisitionPilot.generateNavigationId(ReviewOfRequirementsByAcquisitionPilot.PageId.home)),
	Review_COTS_Software_Package_Information_And_Specification("Case 3 - Step 4: Review COTS software package information and specification", 
			ReviewPackagesPilot.generateNavigationId(ReviewPackagesPilot.PageId.home)),
	Review_And_Finalize_Security_Requirements("Case 3 - Step 5: Review and Finalize Security Requirements", 
			ReviewAndFinalizeRequirementsPilot.generateNavigationId(ReviewAndFinalizeRequirementsPilot.PageId.home));
	
	private String description;
	private String link;
	private StepEnum(String description, String link)
	{
		this.description = description;
		this.link = link;
	}
	
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return the link
	 */
	public String getLink()
	{
		return link;
	}
	
	
	
}
