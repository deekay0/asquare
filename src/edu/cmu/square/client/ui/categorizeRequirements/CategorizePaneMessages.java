package edu.cmu.square.client.ui.categorizeRequirements;

import com.google.gwt.i18n.client.Messages;

import edu.cmu.square.client.ui.core.content.CommonMessages;

/**
 * This interface binds with the corresponding properties file This is intended
 * to handle the messaging of the site instead of hardcoding text in the UI, we
 * should use this pattern.
 * 
 */
public interface CategorizePaneMessages extends CommonMessages
{

	String categoryInboxShowAllLink();
	String categoryInboxUnassignedLink();
	String categoryInboxTitleLabel();
	String noRequirementsLabel();
	String selectLable();
	String allLink();
	String noneLink();

	String requiremntTableTitle();
	String assignActionListBox();
	String removeActionListBox();
	String newActionListBox();
	String chooseCategoryListBox();
	String createCategoryDialogBoxTitle();
	String createCategoryDialogBoxAlreadyExist();
	String createCategoryDialogBoxSave();
	String createCategoryDialogBoxCancel();
	String createCategoryDialogBoxName();
	String noRequirementSelectedAlert();
	String view();
	String hide();
    String category();

	
	//RPC Calls messages
	String creatingCategoryRPCAction();
	String retrievingCategoriesRPCAction();
	String retrievingRequirementsRPCAction();
	String categorizingRequirementRPCAction();
	String removingCategoryRPCAction();
	//Status Bar Messages
	String loadingStatus();
	String assigningStatus();
	String removingStatus();
	String creatingStatus();

}
