package edu.cmu.square.client.ui.ManageProject;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.model.GwtInspectionTechnique;


public class InspectionTechniqueDialog extends DialogBox
{
	private final TextBox inspectionNameTextbox = new TextBox();
	private final TextArea inspectionDescriptionTextArea = new TextArea();
	private final Label errorLabel = new Label();

	private InspectionTechniqueGrid caller = null;
	private List<GwtInspectionTechnique> inspections = null;
	final ManageProjectPaneMessages messages = (ManageProjectPaneMessages) GWT.create(ManageProjectPaneMessages.class);

	/**
	 * Creates a new dialog box with a talk back pointer to the given technique
	 * grid.
	 * 
	 * @param gridPointer
	 *            The technique grid to which this dialog should talk back.
	 */
	public InspectionTechniqueDialog(InspectionTechniqueGrid gridPointer, List<GwtInspectionTechnique> inspections)
	{
		super();
		this.caller = gridPointer;
		this.inspections = inspections;
		this.initializeDialog(new GwtInspectionTechnique());
	}

	/**
	 * Creates a new dialog box with a talk back to the pointer to the given
	 * technique grid meant for editing the given technique.
	 * 
	 * @param gridPointer
	 *            The technique grid to which this dialog should talk back.
	 * @param inspections 
	 * @param inspection
	 *            The inspection to be edited.
	 */
	public InspectionTechniqueDialog(InspectionTechniqueGrid gridPointer, GwtInspectionTechnique technique, List<GwtInspectionTechnique> inspections)
	{
		super();
		this.caller = gridPointer;
		this.inspections = inspections;
		this.initializeDialog(technique);
	}

	/**
	 * Sets up the controls in the dialog
	 * 
	 * @param inspection
	 *            The inspection to be updated in this dialog.
	 */
	private void initializeDialog(GwtInspectionTechnique technique)
	{
		VerticalPanel baseLayout = new VerticalPanel();
		VerticalPanel nameLayout = new VerticalPanel();
		VerticalPanel descriptionLayout = new VerticalPanel();
		HorizontalPanel buttonsLayout = new HorizontalPanel();

		nameLayout.add(new Label(this.messages.inspectionName()));
		nameLayout.add(this.inspectionNameTextbox);
		this.inspectionNameTextbox.setWidth("500px");
		this.inspectionNameTextbox.setText(technique.getTitle());

		descriptionLayout.add(new Label(this.messages.inspectionDescription()));
		descriptionLayout.add(this.inspectionDescriptionTextArea);
		descriptionLayout.add(this.errorLabel);
		this.inspectionDescriptionTextArea.setWidth("500px");
		this.inspectionDescriptionTextArea.setHeight("100px");
		this.inspectionDescriptionTextArea.setText(technique.getDescription());

		// Set up the buttons
		Button saveButton = new Button(messages.save(), new SaveHandler(this, technique));
		Button cancelButton = new Button(messages.cancel(), new CancelHandler(this));

		saveButton.setWidth("100px");
		cancelButton.setWidth("100px");

		buttonsLayout.setSpacing(10);
		buttonsLayout.add(saveButton);
		buttonsLayout.add(cancelButton);

		// set the base layout
		baseLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		baseLayout.add(nameLayout);
		baseLayout.add(descriptionLayout);
		baseLayout.add(buttonsLayout);
		baseLayout.setSpacing(5);

		this.setWidget(baseLayout);
	}

	// ******* Handlers for the buttons *************

	private class SaveHandler implements ClickHandler
	{
		private GwtInspectionTechnique inspection = null;

		public SaveHandler(InspectionTechniqueDialog dialogPointer, GwtInspectionTechnique newInspection)
		{
			super();
			this.inspection = newInspection;
		}

		
		public void onClick(ClickEvent event)
		{
			errorLabel.setText("");
			String inspectionNameText = inspectionNameTextbox.getText();
			String inspectionDescriptionText = inspectionDescriptionTextArea.getText();

			if (inspectionNameText.length() <= 0 || inspectionDescriptionText.length() <= 0)
			{
				errorLabel.setText(messages.emptyInspectionNameDesc());
			}
			else
			{
				this.inspection.setTitle(inspectionNameTextbox.getText());
				this.inspection.setDescription(inspectionDescriptionTextArea.getText());

				for (GwtInspectionTechnique currentInspection : inspections)
				{
					if (this.inspection.getTitle().trim().equalsIgnoreCase(currentInspection.getTitle()) && 
						!(this.inspection.getInspectionTechniqueId().equals(currentInspection.getInspectionTechniqueId())))
					{
						Window.alert(messages.inspectionDuplicatedName());
						return;
					}
				}
				
				hide();
				
				if (!this.inspection.isSavedInDb())  //it's not in the DB, save a new one.
				{
					caller.addInspectionToTable(this.inspection);
				}
				else //update the old one.
				{
					caller.updateInspectionInTable(this.inspection);
				}
			}
		}
	}

	private class CancelHandler implements ClickHandler
	{
		private InspectionTechniqueDialog dialog = null;

		public CancelHandler(InspectionTechniqueDialog dialog)
		{
			super();
			this.dialog = dialog;
		}

		
		public void onClick(ClickEvent event)
		{
			this.dialog.hide(true);
		}
	}
}
