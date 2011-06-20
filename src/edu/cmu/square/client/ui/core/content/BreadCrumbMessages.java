package edu.cmu.square.client.ui.core.content;

import com.google.gwt.i18n.client.Messages;

/**
 * This interface binds with the corresponding properties file
 * This is intended to handle the messaging of the site
 * instead of hardcoding text in the UI, we should use this pattern.
 * 
 */
public interface BreadCrumbMessages extends Messages
{
	String chooseStep();
	String assetsAndGoals();
	String agreeOnDefinitions();
	String categorizeRequirements();
	String collectArtifacts();
	String elicitSecurityRequirements();
	String selectSecurityElicitationTechnique();
	String securityRiskAssessment();
	String prioritizeRequirements();
	String inspectRequirements();
	String manageSite();
	String projectSettings();
	String preferences();
	String projectMembers();
	String chooseProject();
	String chooseCase();
	String welcome(String username);
	String reviewOfRequirementsByAcquisitionOrganization();
	String reviewPackages();
}
