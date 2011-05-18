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

import edu.cmu.square.client.model.GwtTechnique;

/**
 * A dialog used to get information from the user for elicitation techniques.
 * 
 * This dialog provides the dialog and talks back as used in the
 * caller-dialog-talkback pattern used by Manage Site.
 * 
 */
public class ElicitationTechniqueDialog extends DialogBox
{
	private static final String CROSS_SITE_SCRIPTING_REGEXP = ".*<[/]?script[>]?.*";
	
	private final TextBox techniqueNameTextBox = new TextBox();
	private final TextArea techniqueDescriptionTextArea = new TextArea();
	private final Label errorLabel = new Label();

	private TechniqueGrid caller = null;
	private List<GwtTechnique> techniques = null;
	
	final ManageProjectPaneMessages messages = (ManageProjectPaneMessages) GWT.create(ManageProjectPaneMessages.class);

	/**
	 * Creates a new dialog box with a talk back pointer to the given technique
	 * grid.
	 * 
	 * @param gridPointer
	 *            The technique grid to which this dialog should talk back.
	 */
	public ElicitationTechniqueDialog(TechniqueGrid gridPointer, List<GwtTechnique> techniques) 
	{
		super();
		this.caller = gridPointer;
		this.techniques = techniques;
		this.initializeDialog(new GwtTechnique());
	}

	/**
	 * Creates a new dialog box with a talk back to the pointer to the given
	 * technique grid meant for editing the given technique.
	 * 
	 * @param gridPointer
	 *            The technique grid to which this dialog should talk back.
	 * @param technique
	 *            The technique to be edited.
	 */
	public ElicitationTechniqueDialog(TechniqueGrid gridPointer, GwtTechnique technique, List<GwtTechnique> techniques)
	{
		super();
		this.caller = gridPointer;
		this.techniques = techniques;
		this.initializeDialog(technique);
	}

	/**
	 * Sets up the controls in the dialog
	 * 
	 * @param technique
	 *            The technique to be updated in this dialog.
	 */
	private void initializeDialog(GwtTechnique technique)
	{
		VerticalPanel baseLayout = new VerticalPanel();
		VerticalPanel nameLayout = new VerticalPanel();
		VerticalPanel descriptionLayout = new VerticalPanel();
		HorizontalPanel buttonsLayout = new HorizontalPanel();

		nameLayout.add(new Label(this.messages.techniqueName()));
		nameLayout.add(this.techniqueNameTextBox);
		this.techniqueNameTextBox.setWidth("500px");
		this.techniqueNameTextBox.setText(technique.getTitle());

		descriptionLayout.add(new Label(this.messages.techniqueDescription()));
		descriptionLayout.add(this.techniqueDescriptionTextArea);
		descriptionLayout.add(this.errorLabel);
		this.techniqueDescriptionTextArea.setWidth("500px");
		this.techniqueDescriptionTextArea.setHeight("100px");
		this.techniqueDescriptionTextArea.setText(technique.getDescription());

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
		private GwtTechnique technique = null;

		public SaveHandler(ElicitationTechniqueDialog dialogPointer, GwtTechnique newTechnique)
		{
			super();
			this.technique = newTechnique;
		}

		
		public void onClick(ClickEvent event)
		{
			String techniqueNameText = techniqueNameTextBox.getText();
			String techniqueDescriptionText = techniqueDescriptionTextArea.getText();

			for (GwtTechnique currentTechnique : techniques)
			{
				if (techniqueNameText.trim().equalsIgnoreCase(currentTechnique.getTitle()) && 
					this.technique.getTechniqueId() != currentTechnique.getTechniqueId())
				{
					Window.alert(messages.techniqueDuplicateName());
					return;
				}	
			}
			
			if (techniqueDescriptionText.matches(CROSS_SITE_SCRIPTING_REGEXP) || techniqueNameText.matches(CROSS_SITE_SCRIPTING_REGEXP))
			{
		    	errorLabel.setText(messages.invalidTechniqueDesc());
		    }
		    else if (techniqueNameText.length() <= 0 || techniqueDescriptionText.length() <= 0)
			{
				errorLabel.setText(messages.emptyTechniqueNameDesc());
			}
		    else
		    {
				hide();

				this.technique.setTitle(techniqueNameText);
				this.technique.setDescription(techniqueDescriptionText);

				if (!this.technique.isSavedInDb()) 
				{
					caller.addTechniqueToTable(this.technique);
				}
				else // udpate the old technique, pass it back to the caller
				{
					caller.updateTechniqueInTable(this.technique);
				}
			}
		}
	}

	private class CancelHandler implements ClickHandler
	{
		private ElicitationTechniqueDialog dialog = null;

		public CancelHandler(ElicitationTechniqueDialog dialog)
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
