package edu.cmu.square.client.ui.reviewPackages;

import com.google.gwt.safehtml.shared.SafeHtml;

import edu.cmu.square.client.ui.core.content.CommonMessages;

public interface ReviewPackagesPaneMessages extends CommonMessages
{
	String editRatingsLink();
	String finishRatingsLink();
	String editPackagesLink();
	String finishPackagesLink();
	String editAttributesLink();
	String finishAttributesLink();
	String attributesRetrievalError();
	String packagesRetrievalError();
	String ratingsRetrievalError();
	String rateAuthorization();
	String error();
	String matrixLableX();
	String matrixLableY();
	String rateLegend();
	String buttonGotoSelect();
	String addSoftwarePackage();
	String addQualityAttribute();
	String createSoftwarePackageDialogBoxAdd();
	String createQualityAttributeDialogBoxAdd();
	
	String createQualityAttributeDialogBoxTitle();

	String createQualityAttributeDialogBoxSave();

	String createQualityAttributeDialogBoxDefinition();

	String createQualityAttributeDialogBoxName();

	String createQualityAttributeDialogBoxCancel();

	String createQualityAttributeDialogBoxAlreadyExist();

	String createSoftwarePackageDialogBoxTitle();

	String createSoftwarePackageDialogBoxName();

	String createSoftwarePackageDialogBoxDefinition();

	String createSoftwarePackageDialogBoxSave();

	String createSoftwarePackageDialogBoxCancel();

	String createSoftwarePackageDialogBoxAlreadyExist();

	String editQualityAttributeDialogBoxTitle();

	String editSoftwarePackageDialogBoxTitle();
	String createQualityAttributeDialogBoxDelete();
	String confirmDelete();
	String createSoftwarePackageDialogBoxDelete();
	String QAAuthorization();
	String SPAuthorization();
}
