package edu.cmu.square.client.ui.prioritizeRequirements;

import edu.cmu.square.client.ui.core.content.CommonMessages;

public interface PrioritizePaneMessages extends CommonMessages
{
	String prioritizationInstructions();

	String comparisonsCountHeader();
	
	String comparisonsCount(String count, String total);
	
	String resetComparisons();
	
	String startComparisons();

	String countHeader();

	String deleteConfirmation();

	String costLabel();
	
	String valueLabel();

	String zeroCostOrValueError();

	String reviewRequirements();
	
	String risksAddressed();
	
	String goalsAddressed();

	String artifactsReferenced();

	String groupStatus(int usersPrioritized, int totalUsers);

	String requirementPriorities();
	
	String updatePriorities();

	String viewIndividualPriorities();

	String finalRank();

	String requirement();

	String ahpRank();

	String disclaimer();

	String updateText();

	String requirementIndividualPriorities();

	String assignedPriorities();

	String individualRank();

	String individualDisclaimer();

	String viewGroupPriorities();

	String generalPrioritizationError();

	String groupStatusEmpty();

	String tooFewRequirements();


}
