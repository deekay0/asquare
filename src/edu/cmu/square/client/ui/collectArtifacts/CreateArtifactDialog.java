package edu.cmu.square.client.ui.collectArtifacts;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.model.GwtArtifact;
import edu.cmu.square.client.utils.SquareUtil;

/**
 * A dialog used to get information from the user for categories.
 * 
 * This dialog provides the dialog and talks back as used in the
 * caller-dialog-talkback pattern used by Manage Site.
 * 
 */
public class CreateArtifactDialog extends DialogBox
{
	private final TextBox artifactTextBox = new TextBox();
	private final TextArea artifactDescriptionTextBox = new TextArea();
	private final TextBox artifactRevisionTextBox = new TextBox();
	private final TextBox artifactLinkTextBox = new TextBox();

	private GwtArtifact newartifact = new GwtArtifact();
	private CollectArtifactsSummaryPane caller;
	
	private List<GwtArtifact> listOfArtifacts = new ArrayList<GwtArtifact>();
	
	final CollectArtifactsPaneMessages messages = (CollectArtifactsPaneMessages) GWT.create(CollectArtifactsPaneMessages.class);
	
	private Button saveButton;
	
	
	/**
	 * Creates a new dialog box with a talk back pointer to the given category
	 * grid.
	 * 
	 * @param gridPointer
	 *            The category grid to which this dialog should talk back.
	 */
	public CreateArtifactDialog(List<GwtArtifact> artifacts, CollectArtifactsSummaryPane caller)
	{
		super();

		this.listOfArtifacts = artifacts;
		this.caller = caller;
		this.initializeDialog(new GwtArtifact());
	}
	
	public CreateArtifactDialog(GwtArtifact artifactToEdit, List<GwtArtifact> artifacts, CollectArtifactsSummaryPane caller)
	{
		super();
		this.listOfArtifacts = artifacts;
		this.caller = caller;
		this.initializeDialog(artifactToEdit);
	}

	/**
	 * Sets up the controls in the dialog
	 * 
	 * @param artifact
	 *            The category to be updated in this dialog.
	 */
	private void initializeDialog(GwtArtifact artifact)
	{
		if (artifact.isInDatabase())
		{
			this.setText(messages.editArtifactDialogBoxTitle());
		}
		else
		{
			this.setText(messages.createArtifactDialogBoxTitle());
		}
		
		VerticalPanel nameLayout = new VerticalPanel();
		VerticalPanel descriptionLayout = new VerticalPanel();
		HorizontalPanel buttonsLayout = new HorizontalPanel();
		VerticalPanel bigRevisionLayout = new VerticalPanel();
		HorizontalPanel revisionLayout = new HorizontalPanel();
		VerticalPanel linkLayout = new VerticalPanel();
		
		VerticalPanel baseLayout = new VerticalPanel();
		baseLayout.setSpacing(10);
		baseLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		baseLayout.add(nameLayout);
		baseLayout.add(descriptionLayout);
		baseLayout.add(bigRevisionLayout);
		baseLayout.add(linkLayout);
		baseLayout.add(buttonsLayout);
		
		Label revisionNote = new Label(messages.revisionNote());
		revisionNote.setWidth("395px");
		revisionNote.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		revisionLayout.add(this.artifactRevisionTextBox);
		revisionLayout.add(revisionNote);
		revisionLayout.setWidth("500px");
		
		nameLayout.add(new Label(messages.artifactName()));
		nameLayout.add(this.artifactTextBox);
		
		descriptionLayout.add(new Label(messages.description()));
		descriptionLayout.add(this.artifactDescriptionTextBox);
		
		bigRevisionLayout.add(new Label(messages.revision()));
		bigRevisionLayout.add(revisionLayout);
		
		linkLayout.add(new Label(messages.link()));
		linkLayout.add(this.artifactLinkTextBox);

		this.artifactTextBox.setWidth("500px");
		this.artifactDescriptionTextBox.setSize("500px", "80px");
		this.artifactRevisionTextBox.setWidth("100px");
		this.artifactLinkTextBox.setWidth("500px");
	
		this.artifactTextBox.setText(artifact.getName());
		this.artifactDescriptionTextBox.setText(artifact.getDescription());
		this.artifactLinkTextBox.setText(artifact.getLink());
		this.artifactRevisionTextBox.setText(artifact.getRevision());

		saveButton = new Button(messages.save(), new SaveHandler(this, artifact));

		// Set up the buttons

		Button cancelButton = new Button(messages.cancel(), new CancelHandler(this));

		saveButton.setEnabled(false);
		this.artifactTextBox.addKeyUpHandler(new squareKeyUpHandler());
		this.artifactTextBox.addChangeHandler(new squareChangeHandler());
		this.artifactTextBox.addKeyDownHandler(new squareKeyDownHandler());
	
		this.artifactDescriptionTextBox.addKeyUpHandler(new squareKeyUpHandler());
		this.artifactDescriptionTextBox.addChangeHandler(new squareChangeHandler());
		this.artifactDescriptionTextBox.addKeyDownHandler(new squareKeyDownHandler());
		
		this.artifactRevisionTextBox.addKeyUpHandler(new squareKeyUpHandler());
		this.artifactRevisionTextBox.addChangeHandler(new squareChangeHandler());
		this.artifactRevisionTextBox.addKeyDownHandler(new squareKeyDownHandler());
		
		this.artifactLinkTextBox.addKeyUpHandler(new squareKeyUpHandler());
		this.artifactLinkTextBox.addChangeHandler(new squareChangeHandler());
		this.artifactLinkTextBox.addKeyDownHandler(new squareKeyDownHandler());


		saveButton.setWidth("100px");
		cancelButton.setWidth("100px");

		buttonsLayout.setSpacing(10);
		buttonsLayout.add(saveButton);
		buttonsLayout.add(cancelButton);

		// set the base layout
		
		this.setWidget(baseLayout);
	}
	private void configureButton()
	{
		
		String name=artifactTextBox.getText().trim();
		String description= artifactDescriptionTextBox.getText().trim();
		String revision=artifactRevisionTextBox.getText().trim();
		String link =artifactLinkTextBox.getText().trim();
		
		
		if (name.length()==0 ||description.length()==0||revision.length()==0||link.length()==0)
		{
			saveButton.setEnabled(false);
		}
		else
		{
			saveButton.setEnabled(true);
		}
	}
	// ******* Handlers for the buttons *************
	public void setNewCategory(GwtArtifact newartifact)
	{
		this.newartifact = newartifact;
	}

	public GwtArtifact getNewartifact()
	{
		return newartifact;
	}

	private class SaveHandler implements ClickHandler
	{
		private CreateArtifactDialog dialog = null;
		private GwtArtifact artifact = null;

		public SaveHandler(CreateArtifactDialog dialogPointer, GwtArtifact newartifact)
		{
			super();
			this.dialog = dialogPointer;
			this.artifact = newartifact;
		}

		public void onClick(ClickEvent event)
		{
			List<GwtArtifact> list = listOfArtifacts;

			artifact.setName(SquareUtil.firstCharacterToUpperCase(artifactTextBox.getText().trim()));
			artifact.setDescription(SquareUtil.firstCharacterToUpperCase(artifactDescriptionTextBox.getText().trim()));
			artifact.setRevision(artifactRevisionTextBox.getText().trim());
			artifact.setLink(artifactLinkTextBox.getText());

			for (int i = 0; i < list.size(); i++)
			{
				if (this.artifact.getName().equalsIgnoreCase(list.get(i).getName()) && this.artifact.getId() != list.get(i).getId())
				{
					Window.alert(messages.createArtifactDialogBoxAlreadyExist());
					return;
				}
				if (this.artifact.getLink().equalsIgnoreCase(list.get(i).getLink()) && this.artifact.getId() != list.get(i).getId())
				{
					Window.alert("Link already exists");
					return;
				}
			}

			if (this.artifact.isInDatabase())
			{
				caller.updateArtifact(this.artifact);
			}
			else
			{
				caller.createArtifact(this.artifact);
			}
			
			this.dialog.hide();
		}
	}
	


	private class CancelHandler implements ClickHandler
	{
		private CreateArtifactDialog dialog = null;

		public CancelHandler(CreateArtifactDialog dialog)
			{
				super();
				this.dialog = dialog;
			}

		public void onClick(ClickEvent event)
		{
			this.dialog.hide(true);
		}
	}


	private class squareKeyUpHandler implements KeyUpHandler
	{
		
		
		public void onKeyUp(KeyUpEvent event)
		{
			configureButton();

		}
	}
	
	private class squareChangeHandler implements ChangeHandler
	{
		
		
		public void onChange(ChangeEvent event)
		{
			configureButton();

		}
	}
	
	private class squareKeyDownHandler implements KeyDownHandler
	{
		
		public void onKeyDown(KeyDownEvent event)
		{
			configureButton();

		}
	}



	
}
