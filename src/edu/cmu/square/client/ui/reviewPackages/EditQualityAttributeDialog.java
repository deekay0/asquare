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

import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.utils.SquareUtil;

/**
 * A dialog used to get information from the user for categories.
 * 
 * This dialog provides the dialog and talks back as used in the
 * caller-dialog-talkback pattern used by Manage Site.
 * 
 */
public class EditQualityAttributeDialog extends DialogBox
{
	private final TextBox QualityAttributeTextBox = new TextBox();
	private final TextArea QualityAttributeDefinitionTextBox = new TextArea();

	private ReviewPackagesPane updateQualityAttributeCommand;
	private List<GwtQualityAttribute> listOfQualityAttributes = new ArrayList<GwtQualityAttribute>();
	final ReviewPackagesPaneMessages messages = (ReviewPackagesPaneMessages) GWT.create(ReviewPackagesPaneMessages.class);
	private Button saveButton;
	
	/**
	 * Creates a new dialog box with a talk back pointer to the given category
	 * grid.
	 * 
	 * @param gridPointer
	 *            The category grid to which this dialog should talk back.
	 */
	public EditQualityAttributeDialog(GwtQualityAttribute currentQualityAttribute, List<GwtQualityAttribute> QualityAttributes, ReviewPackagesPane command)
		{
			super();
		

			this.listOfQualityAttributes = QualityAttributes;
			this.updateQualityAttributeCommand = command;

			this.initializeDialog(currentQualityAttribute);
		}

	/**
	 * Sets up the controls in the dialog
	 * 
	 * @param QualityAttribute
	 *            The category to be updated in this dialog.
	 */
	private void initializeDialog(GwtQualityAttribute QualityAttribute)
	{

		VerticalPanel baseLayout = new VerticalPanel();
		VerticalPanel nameLayout = new VerticalPanel();
		VerticalPanel descriptionLayout = new VerticalPanel();
		HorizontalPanel buttonsLayout = new HorizontalPanel();
		this.setText(messages.editQualityAttributeDialogBoxTitle());
		nameLayout.add(new Label(messages.createQualityAttributeDialogBoxName()));
		nameLayout.add(this.QualityAttributeTextBox);
		nameLayout.add(new Label(messages.createQualityAttributeDialogBoxDefinition()));
		nameLayout.add(this.QualityAttributeDefinitionTextBox);

		this.QualityAttributeTextBox.setWidth("500px");
		this.QualityAttributeTextBox.setText(QualityAttribute.getName());
		this.QualityAttributeDefinitionTextBox.setWidth("500px");
		this.QualityAttributeDefinitionTextBox.setSize("500px", "80px");
		this.QualityAttributeDefinitionTextBox.setText(QualityAttribute.getDescription());

		// Set up the buttons
		saveButton = new Button(messages.createQualityAttributeDialogBoxSave(), new SaveHandler(this, QualityAttribute));
		Button cancelButton = new Button(messages.createQualityAttributeDialogBoxCancel(), new CancelHandler(this));

		this.QualityAttributeTextBox.addKeyUpHandler(new KeyUpHandler()
		{
			public void onKeyUp(KeyUpEvent event)
			{
				 configureButton();
			}
			
		});
		
		this.QualityAttributeTextBox.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.QualityAttributeTextBox.addKeyDownHandler(new KeyDownHandler()
		{
			public void onKeyDown(KeyDownEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.QualityAttributeDefinitionTextBox.addKeyUpHandler(new KeyUpHandler()
		{
			public void onKeyUp(KeyUpEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.QualityAttributeDefinitionTextBox.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.QualityAttributeDefinitionTextBox.addKeyDownHandler(new KeyDownHandler()
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
		if(QualityAttributeTextBox.getText().trim().equalsIgnoreCase("") || QualityAttributeDefinitionTextBox.getText().trim().equalsIgnoreCase(""))
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
		private EditQualityAttributeDialog dialog = null;
		private GwtQualityAttribute localQualityAttribute = null;
		String currentQualityAttribute = "";
		public SaveHandler(EditQualityAttributeDialog dialogPointer, GwtQualityAttribute newQualityAttribute)
			{
				super();
				this.dialog = dialogPointer;
				this.localQualityAttribute = new GwtQualityAttribute();
				this.localQualityAttribute.setId(newQualityAttribute.getId());
				this.localQualityAttribute.setName(newQualityAttribute.getName());
				currentQualityAttribute = newQualityAttribute.getName();
				this.localQualityAttribute.setDescription(newQualityAttribute.getDescription());

			}

		public void onClick(ClickEvent event)
		{
			List<GwtQualityAttribute> list = listOfQualityAttributes;
			this.dialog.hide();

			localQualityAttribute.setName(SquareUtil.firstCharacterToUpperCase(QualityAttributeTextBox.getText().trim()));
			localQualityAttribute.setDescription(SquareUtil.firstCharacterToUpperCase(QualityAttributeDefinitionTextBox.getText().trim()));

			if (!currentQualityAttribute.trim().equalsIgnoreCase(localQualityAttribute.getName()))
			{
				for (int i = 0; i < list.size(); i++)
				{
					if (QualityAttributeTextBox.getText().equalsIgnoreCase(list.get(i).getName()))
					{
					
						Window.alert(messages.createQualityAttributeDialogBoxAlreadyExist());
						return;
					}
				}

			}
			updateQualityAttributeCommand.updateCommand(this.localQualityAttribute);
		}
	}

	private class CancelHandler implements ClickHandler
	{
		private EditQualityAttributeDialog dialog = null;

		public CancelHandler(EditQualityAttributeDialog dialog)
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
