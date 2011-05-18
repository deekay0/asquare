package edu.cmu.square.client.ui.editUserProfile;

import edu.cmu.square.client.ui.core.content.CommonMessages;


/**
 * This interface binds with the corresponding properties file
 * This is intended to handle the messaging of the site
 * instead of hardcoding text in the UI, we should use this pattern.
 * 
 */

public interface EditUserProfilePaneMessages extends CommonMessages {

	String editNameLabel();
	String editEmailLabel();
	String editPhoneLabel();
	String editCompanyLabel();
	String editDepartmentLabel();
	String editLocationLabel();
	String editSkipTeachStep();
	String generalUserEditError();

	String newPasswordLabel();
	String confirmPasswordLabel();
	String generalPasswordEditError();

	String profileSaved();
	String notSaved();
	
	String buttonSave();
	String buttonCancel();
	
	String noPermission();
	
	String updateSessionTimeOut();
	String loadSessionTimeOut();
	String passwordInstructions();
	String invalidPassword();
	String notMatchPassword();
	String requireField();
	String emailCheck();
	String duplicateEmail();
	
	
}
