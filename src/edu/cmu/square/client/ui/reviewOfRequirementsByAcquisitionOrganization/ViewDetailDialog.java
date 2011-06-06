package edu.cmu.square.client.ui.reviewOfRequirementsByAcquisitionOrganization;


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

import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.utils.SquareUtil;

	/**
	 * A dialog used to get information from the user for categories.
	 * 
	 * This dialog provides the dialog and talks back as used in the
	 * caller-dialog-talkback pattern used by Manage Site.
	 * 
	 */
	public class ViewDetailDialog extends DialogBox
	{
		private final TextBox requirementTextBox = new TextBox();
		private final TextArea requirementDefinitionTextBox = new TextArea();

		private ReviewOfRequirementsByAcquisitionPane updateRequirementCommand;
		
		private List<GwtRequirement> listOfRequirements = new ArrayList<GwtRequirement>();
		final ReviewOfRequirementsByAcquisitionMessages messages = (ReviewOfRequirementsByAcquisitionMessages) GWT.create(ReviewOfRequirementsByAcquisitionMessages.class);
		
		//private Button saveButton;
		
		/**
		 * Creates a new dialog box with a talk back pointer to the given category
		 * grid.
		 * 
		 * @param gridPointer
		 *            The category grid to which this dialog should talk back.
		 */
		public ViewDetailDialog(GwtRequirement currentRequirement, List<GwtRequirement> requirements, ReviewOfRequirementsByAcquisitionPane command)
		{
				super();
			

				this.listOfRequirements = requirements;
				this.updateRequirementCommand = command;

				//this.initializeDialog(currentRequirement);
		}

		
	
		/**
		 * Sets up the controls in the dialog
		 * 
		 * @param term
		 *            The category to be updated in this dialog.
		 */
		
		/*AAAAAAAA	
		private void initializeDialog(GwtRequirement requirement)
		{

			VerticalPanel baseLayout = new VerticalPanel();
			VerticalPanel nameLayout = new VerticalPanel();
			VerticalPanel descriptionLayout = new VerticalPanel();
			HorizontalPanel buttonsLayout = new HorizontalPanel();
			this.setText(messages.viewDetailRequirementDialogBoxTitle());
			//nameLayout.add(new Label(messages.createTermDialogBoxName()));
			nameLayout.add(this.requirementTextBox);
			//nameLayout.add(new Label(messages.createTermDialogBoxDefinition()));
			nameLayout.add(this.requirementDefinitionTextBox);

			this.requirementTextBox.setWidth("500px");
			this.requirementTextBox.setText(requirement.getRequirement());
			this.requirementDefinitionTextBox.setWidth("500px");
			this.requirementDefinitionTextBox.setSize("500px", "80px");
			this.requirementDefinitionTextBox.setText(requirement.getDefinition());

			// Set up the buttons
			//saveButton = new Button(messages.createTermDialogBoxSave(), new SaveHandler(this, term));
			//Button cancelButton = new Button(messages.createTermDialogBoxCancel(), new CancelHandler(this));

			this.requirmentTextBox.addKeyUpHandler(new KeyUpHandler()
			{
				public void onKeyUp(KeyUpEvent event)
				{
					 configureButton();
				}
				
			});
			
			this.requirmentTextBox.addChangeHandler(new ChangeHandler()
			{
				public void onChange(ChangeEvent event)
				{
					 configureButton();
					
				}
				
			});
			
			this.requirmentTextBox.addKeyDownHandler(new KeyDownHandler()
			{
				public void onKeyDown(KeyDownEvent event)
				{
					 configureButton();
					
				}
				
			});
			
			this.requirementDefinitionTextBox.addKeyUpHandler(new KeyUpHandler()
			{
				public void onKeyUp(KeyUpEvent event)
				{
					 configureButton();
					
				}
				
			});
			
			this.requirementDefinitionTextBox.addChangeHandler(new ChangeHandler()
			{
				public void onChange(ChangeEvent event)
				{
					 configureButton();
					
				}
				
			});
			
			this.requirementDefinitionTextBox.addKeyDownHandler(new KeyDownHandler()
			{
				public void onKeyDown(KeyDownEvent event)
				{
					 configureButton();
					
				}
				
			});
			
			
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
		private void configureButton()
		{
			if(termTextBox.getText().trim().equalsIgnoreCase("") || termDefinitionTextBox.getText().trim().equalsIgnoreCase(""))
			{
				saveButton.setEnabled(false);
			}
			else
			{
				saveButton.setEnabled(true);
			}
		}

		private class SaveHandler implements ClickHandler
		{
			private ViewDetailDialog dialog = null;
			private GwtRequirement locRequirementrm = null;
			String currentRequirement = "";
			public SaveHandler(ViewDetailDialog dialogPointer, GwtRequirement newRequirement)
				{
					super();
					this.dialog = dialogPointer;
					this.localterm = new GwtRequirement();
					this.localterm.setId(newRequirement.getId());
					this.localterm.setRequirement(newRequirement.getRequirement());
					currentRequirement = newRequirement.getRequirement();
					this.localterm.setDefinition(newRequirement.getDefinition());

				}

			public void onClick(ClickEvent event)
			{
				List<GwtRequirement> list = listOfRequirements;
				this.dialog.hide();

				localterm.setRequirement(SquareUtil.firstCharacterToUpperCase(termTextBox.getText().trim()));
				localterm.setDefinition(SquareUtil.firstCharacterToUpperCase(termDefinitionTextBox.getText().trim()));

				if (!currentRequirement.trim().equalsIgnoreCase(localterm.getRequirement()))
				{
					for (int i = 0; i < list.size(); i++)
					{
						if (termTextBox.getText().equalsIgnoreCase(list.get(i).getTerm()))
						{
						
							Window.alert(messages.createTermDialogBoxAlreadyExist());
							return;
						}
					}

				}
				updateRequirementCommand.upRequirementCommand(this.localterm);
			}
		}

		private class CancelHandler implements ClickHandler
		{
			private ViewDetailDialog dialog = null;

			public CancelHandler(ViewDetailDialog dialog)
				{
					super();
					this.dialog = dialog;
				}

			public void onClick(ClickEvent event)
			{
				this.dialog.hide(true);
			}
		}
		
		*/
}
