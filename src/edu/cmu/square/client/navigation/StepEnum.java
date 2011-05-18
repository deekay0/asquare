package edu.cmu.square.client.navigation;

import edu.cmu.square.client.ui.SelectSecurityTechnique.SelectSecurityElicitationTechniquePilot;
import edu.cmu.square.client.ui.agreeOnDefinitions.AgreeOnDefinitionsPilot;
import edu.cmu.square.client.ui.assetsAndGoals.AssetsAndGoalsPilot;
import edu.cmu.square.client.ui.categorizeRequirements.CategorizeRequirementsPilot;
import edu.cmu.square.client.ui.collectArtifacts.CollectArtifactsPilot;
import edu.cmu.square.client.ui.elicitSecurityRequirements.ElicitSecurityRequirementsPilot;
import edu.cmu.square.client.ui.inspectRequirements.InspectRequirementsPilot;
import edu.cmu.square.client.ui.prioritizeRequirements.PrioritizeRequirementsPilot;
import edu.cmu.square.client.ui.risksAssessment.RiskAssessmentPilot;

public enum StepEnum
{
	Agree_On_Definitions("Step 1: Agree On Definitions",
			AgreeOnDefinitionsPilot.generateNavigationId(AgreeOnDefinitionsPilot.PageId.home)),
	Identify_Assets_And_Goals("Step 2: Identify Assets and Goals", 
			AssetsAndGoalsPilot.generateNavigationId(AssetsAndGoalsPilot.PageId.home)),
	Collect_Artifacts("Step 3: Collect Artifacts", 
			CollectArtifactsPilot.generateNavigationId(CollectArtifactsPilot.PageId.home)),
	Perform_Risk_Assessment("Step 4: Perform Risk Assessment",
			RiskAssessmentPilot.generateNavigationId(RiskAssessmentPilot.PageId.home)),
	Select_Elicitation_Technique("Step 5: Select Elicitation Technique",
			SelectSecurityElicitationTechniquePilot.generateNavigationId(SelectSecurityElicitationTechniquePilot.PageId.home)),		
	Elicit_Security_Requirements("Step 6: Elicit Security Requirements",
			ElicitSecurityRequirementsPilot.generateNavigationId(ElicitSecurityRequirementsPilot.PageId.home)),		
	Categorize_Requirements("Step 7: Categorize Requirements",
			CategorizeRequirementsPilot.generateNavigationId(CategorizeRequirementsPilot.PageId.home)),
	Prioritize_Requirements("Step 8: Prioritize Requirements",
			PrioritizeRequirementsPilot.generateNavigationId(PrioritizeRequirementsPilot.PageId.home)),
	Inspect_Requirements("Step 9: Inspect Requirements",
			InspectRequirementsPilot.generateNavigationId(InspectRequirementsPilot.PageId.home));
	
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
