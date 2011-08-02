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

import edu.cmu.square.client.model.GwtEvaluation;

/**
 * A dialog used to get information from the user for evaluation criteria.
 * 
 * This dialog provides the dialog and talks back as used in the
 * caller-dialog-talkback pattern used by Manage Site.
 * 
 */
public class ElicitationEvaluationDialog extends DialogBox
{
	private final TextBox evaluationNameTextbox = new TextBox();
	private final TextArea evaluationDescriptionTextArea = new TextArea();
	private final Label errorLabel = new Label();

	private EvaluationGrid caller = null;
	private List<GwtEvaluation> evaluations = null;
	
	final ManageProjectPaneMessages messages = (ManageProjectPaneMessages) GWT.create(ManageProjectPaneMessages.class);

	
	
	/**
	 * Creates a new dialog box with a talk back pointer to the given technique
	 * grid.
	 * 
	 * @param gridPointer
	 *            The technique grid to which this dialog should talk back.
	 */
	public ElicitationEvaluationDialog(EvaluationGrid gridPointer, List<GwtEvaluation> evaluations)
	{
		super();
		this.caller = gridPointer;
		this.evaluations = evaluations;
		this.initializeDialog(new GwtEvaluation());
	}

	/**
	 * Creates a new dialog box with a talk back to the pointer to the given
	 * technique grid meant for editing the given technique.
	 * 
	 * @param gridPointer
	 *            The technique grid to which this dialog should talk back.
	 * @param evaluation
	 *            The evaluation to be edited.
	 */
	public ElicitationEvaluationDialog(EvaluationGrid gridPointer, GwtEvaluation evaluation, List<GwtEvaluation> evaluations)
	{
		super();
		this.caller = gridPointer;
		this.evaluations = evaluations;
		this.initializeDialog(evaluation);
	}

	/**
	 * Sets up the controls in the dialog
	 * 
	 * @param evaluation
	 *            The evaluation to be updated in this dialog.
	 */
	private void initializeDialog(GwtEvaluation evaluation)
	{
		VerticalPanel baseLayout = new VerticalPanel();
		VerticalPanel nameLayout = new VerticalPanel();
		VerticalPanel descriptionLayout = new VerticalPanel();
		HorizontalPanel buttonsLayout = new HorizontalPanel();

		nameLayout.add(new Label(this.messages.evaluationName()));
		nameLayout.add(this.evaluationNameTextbox);
		this.evaluationNameTextbox.setWidth("500px");
		this.evaluationNameTextbox.setText(evaluation.getTitle());

		descriptionLayout.add(new Label(this.messages.evaluationDescription()));
		descriptionLayout.add(this.evaluationDescriptionTextArea);
		descriptionLayout.add(this.errorLabel);
		this.evaluationDescriptionTextArea.setWidth("500px");
		this.evaluationDescriptionTextArea.setHeight("100px");
		this.evaluationDescriptionTextArea.setText(evaluation.getDescription());

		// Set up the buttons
		Button saveButton = new Button(messages.save(), new SaveHandler(this, evaluation));
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
		private GwtEvaluation evaluation = null;

		public SaveHandler(ElicitationEvaluationDialog dialogPointer, GwtEvaluation newEvaluation)
		{
			super();
			this.evaluation = newEvaluation;
		}

		
		public void onClick(ClickEvent event)
		{
			errorLabel.setText("");
			String evaluationNameText = evaluationNameTextbox.getText();
			String evaluationDescriptionText = evaluationDescriptionTextArea.getText();
			
			for (GwtEvaluation currentEvaluation : evaluations)
			{
				if (evaluationNameText.trim().equalsIgnoreCase(currentEvaluation.getTitle()) && 
					this.evaluation.getEvaluationId() != currentEvaluation.getEvaluationId())
				{
					Window.alert(messages.evaluationDuplicatedName());
					return;
				}	
			}
			
			if (evaluationNameText.length() <= 0 || evaluationDescriptionText.length() <= 0)
			{
				errorLabel.setText(messages.emptyEvaluationNameDesc());
			}
			else
			{
				hide();

				this.evaluation.setTitle(evaluationNameTextbox.getText());
				this.evaluation.setDescription(evaluationDescriptionTextArea.getText());

				if (!this.evaluation.isSavedInDb())
				{
					caller.addEvaluationToTable(this.evaluation);
				}
				else // udpate the old evaluation, pass it back to the caller
				{
					caller.updateEvaluationInTable(this.evaluation);
				}
			}
		}
	}

	private class CancelHandler implements ClickHandler
	{
		private ElicitationEvaluationDialog dialog = null;

		public CancelHandler(ElicitationEvaluationDialog dialog)
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
