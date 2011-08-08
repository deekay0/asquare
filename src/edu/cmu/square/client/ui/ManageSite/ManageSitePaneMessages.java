package edu.cmu.square.client.ui.ManageSite;

import edu.cmu.square.client.ui.core.content.CommonMessages;



public interface ManageSitePaneMessages extends CommonMessages {

	String paneTitle();
	String createUserDialogTitle();
	String editUserDialogTitle();
	String tab1Title();
	String tab2Title();
	String tab3Title();
	String createButton();
	String resetPasswordLinkTitle();
	String squareUserRoleTitle();
	String squareAdministratorRoleTitle();
	String confirmDelete(String username);

	
	String nameLabelText();
	String emailLabelText();
	String passwordLabelText();
	String siteRoleLabelText();
	String siteRoleTitle();
	String permanentlyDelete();
	String createProject();
	String projectType();
	String leadRequirementsEngineer();
	String createProjectDialogBoxTitle();
	String projectName();
	String projectCase();
	String createProjectDialogBoxAlreadyExist();
	String useDefaultTerms();
	String createProjectDialogBoxBadUser();
	
	String errorAuthorization();
	String generalUserEditError();
	String lockCheck();
	String resetPasswordDialogTitle();
	String newPasswordLabelText();
	String confirmPasswordLabelText();
	String generalPasswordEditError();
	String passwordUpdated();
	String creatingProject();
	String deleteForever();
	String cancelDeleteProject();
	String confirmDeleteProject(String name);
	String confirmDeleteDialogTitle();
	String removeSessionTimeOut();
	String loadUserSessionTimeOut();
	String updateSessionTimeOut();
	String updatePwdSessionTimeOut();
	String errorSendingMail();
	String errorUpdatingPassword();
	String invalidNameOrEmail();
	
	String thisIsYou();
	String autogeneratePassword();
	String email();
	String duplicateEmail();
	String invalidEmailAddress();
	String username();
	String duplicateUserName();
	String emailDisclaimer();
	String deletingProject();
	String retrievingUsers();
	String retrievingAllProjects();
	String updateProjectDialogBoxTitle();
	String passwordDoesNotMatch();
	String emptyPassword();
	String useDefaultTechniques();
	String useDefaultEvaluations();
	String useDefaultInspections();
	String errorDeletingLeadRequirementsEngineer();
	
	String ASquareCase();
	String copyProject();
	String confirmCopyProject(String name);
	String cancelCopy();
	String confirmCopy();
	String confirmCopyDialogTitle();
	String copying();

}
