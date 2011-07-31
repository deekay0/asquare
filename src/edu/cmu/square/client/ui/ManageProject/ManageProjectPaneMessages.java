package edu.cmu.square.client.ui.ManageProject;


import edu.cmu.square.client.ui.core.content.CommonMessages;


public interface ManageProjectPaneMessages extends CommonMessages
{
	// Overall structre of the main tab panel
	String panelTitle1();
	String panelTitle2();
	String tab1Title();
	String tab2Title();
	String tab3Title();
	String tab4Title();
	String tab5Title();
	String tab6Title();
	
	// User Stuff
	String addUserButton();
	String projectNameLabel();
	String stepStatusLable();
	String addUserTextBox();
	String editUserDialogTitle();
	String userSuggestBox();
	String errorRoleRetrieval();
	String errorUserRetrieval();
	String invalidInput();
	String accessAlert();
	String existingProjectUser();
	String addUserError();
	String deleteUserConfirmation();
	String nameText();
	String projectRole();
	String userRemovalError();
	
	// Created or edit elicitation techniques and evaluation criteria
	String createTechniqueButton();
	String description();
	String addTechniqueTitle();
	String editTechniqueTitle();
	String deleteConfirmation(String techniqueName);
	String techniqueName();
	String techniqueDescription();
	String techniqueTableDescription();
	String evaluationTableDescription();
	String createEvaluationButton();
	String evaluationName();
	String addEvaluationTitle();
	String editEvaluationTitle();
	String evaluationDescription();
	String deleteEvaluationConfirmation(String evaluationName);
	String emptyProjectName();
	String emptyEvaluationNameDesc();
	String emptyTechniqueNameDesc();
	String duplicateProjectNameError();
	String evaluationSelectedError();
	String techniqueSelectedError();
	String generalTechniqueRemovalError();
	String errorSelfDelete();
	String errorDeleteAuthorization();
	String invalidTechniqueDesc();
	String generalTechniqueAddError();
	String generalTechniqueUpdateError();
	String errorAuthorization();
	String generalEvaluationRemovalError();
	String generalEvaluationAddError();
	String generalEvaluationUpdateError();
	String errorNameUpdateAuthorization();
	String errorDeleteLastTechnique();
	String categoryTableDescription();
	String evaluationDuplicatedName();
	String techniqueDuplicateName();
	
	//Categories
	String generalCategoryAddError();
	String generalCategoryUpdateError();
	String createCategoryButton();
	String editCategoryTitle();
	String categorySelectedError();
	String generalCategoryRemovalError();
	String categoryName();
	String invalidCategoryDesc();
	String emptyCategoryNameDesc();
	String confirmMerge(String par1, String par2);
	String categoryRetrievalError();
	String editSelection();
	String mergeSelection();
	String duplicateCategoryError();
	
	
	//Inspection Technique
	String inspectionName();
	String inspectionDescription();
	String emptyInspectionNameDesc();
	String editInspectionTitle();
	String addInspectionTitle();
	String createInspectionButton();
	String generalInspectionAddError();
	String deleteInspectionConfirmation(String title);
	String generalInspectionRemovalError();
	String generalInspectionUpdateError();
	String inspectionTableDescription();
	String generalInspectionError();
	String inspectionSelectedError();
	String inspectionDuplicatedName();
	String duplicateEvaluation();
	String duplicateTechnique();

	
	
	String errorChangingProjectName();
	String stepDescription();
	String squareAnalysisStatus();
	String statusUpdated();
	String evaluationError();
	String techniqueError();
	
	//project detail pane
	String cannotChangeToNotStarted();
	String autoChangeToInProgress();
	
}
