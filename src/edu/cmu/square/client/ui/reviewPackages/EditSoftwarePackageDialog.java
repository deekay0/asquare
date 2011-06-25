package edu.cmu.square.client.ui.reviewPackages;

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

import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.GwtTerm;
import edu.cmu.square.client.utils.SquareUtil;

/**
 * A dialog used to get information from the user for categories.
 * 
 * This dialog provides the dialog and talks back as used in the
 * caller-dialog-talkback pattern used by Manage Site.
 * 
 */
public class EditSoftwarePackageDialog extends DialogBox
{
	private final TextBox SoftwarePackageTextBox = new TextBox();
	private final TextArea SoftwarePackageDefinitionTextBox = new TextArea();

	private GwtSoftwarePackage current;
	private ReviewPackagesPane updateSoftwarePackageCommand;
	private List<GwtSoftwarePackage> listOfSoftwarePackages = new ArrayList<GwtSoftwarePackage>();
	final ReviewPackagesPaneMessages messages = (ReviewPackagesPaneMessages) GWT.create(ReviewPackagesPaneMessages.class);
	private Button saveButton;
	
	/**
	 * Creates a new dialog box with a talk back pointer to the given category
	 * grid.
	 * 
	 * @param gridPointer
	 *            The category grid to which this dialog should talk back.
	 */
	public EditSoftwarePackageDialog(GwtSoftwarePackage currentSoftwarePackage, List<GwtSoftwarePackage> SoftwarePackages, ReviewPackagesPane command)
		{
			super();
		
current = currentSoftwarePackage;
			this.listOfSoftwarePackages = SoftwarePackages;
			this.updateSoftwarePackageCommand = command;

			this.initializeDialog(currentSoftwarePackage);
		}

	/**
	 * Sets up the controls in the dialog
	 * 
	 * @param SoftwarePackage
	 *            The category to be updated in this dialog.
	 */
	private void initializeDialog(GwtSoftwarePackage SoftwarePackage)
	{

		VerticalPanel baseLayout = new VerticalPanel();
		VerticalPanel nameLayout = new VerticalPanel();
		VerticalPanel descriptionLayout = new VerticalPanel();
		HorizontalPanel buttonsLayout = new HorizontalPanel();
		this.setText(messages.editSoftwarePackageDialogBoxTitle());
		nameLayout.add(new Label(messages.createSoftwarePackageDialogBoxName()));
		nameLayout.add(this.SoftwarePackageTextBox);
		nameLayout.add(new Label(messages.createSoftwarePackageDialogBoxDefinition()));
		nameLayout.add(this.SoftwarePackageDefinitionTextBox);

		this.SoftwarePackageTextBox.setWidth("500px");
		this.SoftwarePackageTextBox.setText(SoftwarePackage.getName());
		this.SoftwarePackageDefinitionTextBox.setWidth("500px");
		this.SoftwarePackageDefinitionTextBox.setSize("500px", "80px");
		this.SoftwarePackageDefinitionTextBox.setText(SoftwarePackage.getDescription());

		// Set up the buttons
		saveButton = new Button(messages.createSoftwarePackageDialogBoxSave(), new SaveHandler(this, SoftwarePackage));
		Button cancelButton = new Button(messages.createSoftwarePackageDialogBoxCancel(), new CancelHandler(this));
		Button deleteButton = new Button(messages.createSoftwarePackageDialogBoxDelete(), new DeleteHandler(this));
		this.SoftwarePackageTextBox.addKeyUpHandler(new KeyUpHandler()
		{
			public void onKeyUp(KeyUpEvent event)
			{
				 configureButton();
			}
			
		});
		
		this.SoftwarePackageTextBox.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.SoftwarePackageTextBox.addKeyDownHandler(new KeyDownHandler()
		{
			public void onKeyDown(KeyDownEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.SoftwarePackageDefinitionTextBox.addKeyUpHandler(new KeyUpHandler()
		{
			public void onKeyUp(KeyUpEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.SoftwarePackageDefinitionTextBox.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.SoftwarePackageDefinitionTextBox.addKeyDownHandler(new KeyDownHandler()
		{
			public void onKeyDown(KeyDownEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		
		saveButton.setWidth("100px");
		cancelButton.setWidth("100px");
		deleteButton.setWidth("100px");
		
		buttonsLayout.setSpacing(10);
		buttonsLayout.add(saveButton);
		buttonsLayout.add(cancelButton);
		buttonsLayout.add(deleteButton);

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
		if(SoftwarePackageTextBox.getText().trim().equalsIgnoreCase("") || SoftwarePackageDefinitionTextBox.getText().trim().equalsIgnoreCase(""))
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
		private EditSoftwarePackageDialog dialog = null;
		private GwtSoftwarePackage localSoftwarePackage = null;
		String currentSoftwarePackage = "";
		public SaveHandler(EditSoftwarePackageDialog dialogPointer, GwtSoftwarePackage newSoftwarePackage)
			{
				super();
				this.dialog = dialogPointer;
				this.localSoftwarePackage = new GwtSoftwarePackage();
				this.localSoftwarePackage.setId(newSoftwarePackage.getId());
				this.localSoftwarePackage.setName(newSoftwarePackage.getName());
				currentSoftwarePackage = newSoftwarePackage.getName();
				this.localSoftwarePackage.setDescription(newSoftwarePackage.getDescription());

			}

		public void onClick(ClickEvent event)
		{
			List<GwtSoftwarePackage> list = listOfSoftwarePackages;
			this.dialog.hide();

			localSoftwarePackage.setName(SquareUtil.firstCharacterToUpperCase(SoftwarePackageTextBox.getText().trim()));
			localSoftwarePackage.setDescription(SquareUtil.firstCharacterToUpperCase(SoftwarePackageDefinitionTextBox.getText().trim()));

			if (!currentSoftwarePackage.trim().equalsIgnoreCase(localSoftwarePackage.getName()))
			{
				for (int i = 0; i < list.size(); i++)
				{
					if (SoftwarePackageTextBox.getText().equalsIgnoreCase(list.get(i).getName()))
					{
					
						Window.alert(messages.createSoftwarePackageDialogBoxAlreadyExist());
						return;
					}
				}

			}
			updateSoftwarePackageCommand.updateCommand(this.localSoftwarePackage);
		}
	}

	private class CancelHandler implements ClickHandler
	{
		private EditSoftwarePackageDialog dialog = null;

		public CancelHandler(EditSoftwarePackageDialog dialog)
			{
				super();
				this.dialog = dialog;
			}

		public void onClick(ClickEvent event)
		{
			this.dialog.hide(true);
		}
	}
	
	private class DeleteHandler implements ClickHandler
	{
		private EditSoftwarePackageDialog dialog = null;

		public DeleteHandler(EditSoftwarePackageDialog dialog)
			{
				super();
				this.dialog = dialog;
			}

		public void onClick(ClickEvent event)
		{

					boolean response = Window.confirm(messages.confirmDelete() + "?");
					if (response)
						updateSoftwarePackageCommand.removeSoftwarePackage(current);
		}
	}
}
