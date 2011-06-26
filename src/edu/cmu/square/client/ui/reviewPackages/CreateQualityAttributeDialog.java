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
public class CreateQualityAttributeDialog extends DialogBox
{
	private final TextBox termTextBox = new TextBox();
	private final TextArea termDefinitionTextBox = new TextArea();

	

	private GwtQualityAttribute newTerm=new GwtQualityAttribute();
	private ReviewPackagesPane createQualityAttributeCommand;
	private List<GwtQualityAttribute> listOfQualityAttributes=new ArrayList<GwtQualityAttribute>();
	final ReviewPackagesPaneMessages messages = (ReviewPackagesPaneMessages) GWT.create(ReviewPackagesPaneMessages.class);
	private Button saveButton;
	/**
	 * Creates a new dialog box with a talk back pointer to the given category
	 * grid.
	 * 
	 * @param gridPointer
	 *            The category grid to which this dialog should talk back.
	 */
	public CreateQualityAttributeDialog(List<GwtQualityAttribute>  categories, ReviewPackagesPane command)
		{
			super();
		
			this.listOfQualityAttributes=categories;
			this.createQualityAttributeCommand=command;
			this.initializeDialog(new GwtQualityAttribute());
		}


	/**
	 * Sets up the controls in the dialog
	 * 
	 * @param QA
	 *            The category to be updated in this dialog.
	 */
	private void initializeDialog(GwtQualityAttribute QA)
	{

		VerticalPanel baseLayout = new VerticalPanel();
		VerticalPanel nameLayout = new VerticalPanel();
		VerticalPanel descriptionLayout = new VerticalPanel();
		HorizontalPanel buttonsLayout = new HorizontalPanel();
		this.setText(messages.createQualityAttributeDialogBoxTitle());
		nameLayout.add(new Label(messages.createQualityAttributeDialogBoxName()));
		nameLayout.add(this.termTextBox);
		nameLayout.add(new Label(messages.createQualityAttributeDialogBoxDefinition()));
		nameLayout.add(this.termDefinitionTextBox);
		this.termTextBox.setWidth("500px");
		this.termTextBox.setText(QA.getName());
		this.termDefinitionTextBox.setWidth("500px");
		this.termDefinitionTextBox.setSize("500px", "80px");
		this.termDefinitionTextBox.setText(QA.getDescription());
		saveButton = new Button(messages.createQualityAttributeDialogBoxSave(), new SaveHandler(this, QA));


		// Set up the buttons
	
		Button cancelButton = new Button(messages.createQualityAttributeDialogBoxCancel(), new CancelHandler(this));

		saveButton.setEnabled(false);
		this.termTextBox.addKeyUpHandler(new KeyUpHandler()
		{
			public void onKeyUp(KeyUpEvent event)
			{
				 configureButton();
			}
			
		});
		
		this.termTextBox.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.termTextBox.addKeyDownHandler(new KeyDownHandler()
		{
			public void onKeyDown(KeyDownEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.termDefinitionTextBox.addKeyUpHandler(new KeyUpHandler()
		{
			public void onKeyUp(KeyUpEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.termDefinitionTextBox.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.termDefinitionTextBox.addKeyDownHandler(new KeyDownHandler()
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

	// ******* Handlers for the buttons *************

	public void setNewCategory(GwtQualityAttribute newTerm)
	{
		this.newTerm = newTerm;
	}

	public GwtQualityAttribute getNewTerm()
	{
		return newTerm;
	}

	private class SaveHandler implements ClickHandler
	{
		private CreateQualityAttributeDialog dialog = null;
		private GwtQualityAttribute qualityAttribute = null;

		public SaveHandler(CreateQualityAttributeDialog dialogPointer, GwtQualityAttribute newTerm)
			{
				super();
				this.dialog = dialogPointer;
				this.qualityAttribute = newTerm;
			}

		
		public void onClick(ClickEvent event)
		{	
				List<GwtQualityAttribute> list = listOfQualityAttributes;
			    this.dialog.hide();

			    qualityAttribute.setName(SquareUtil.firstCharacterToUpperCase(termTextBox.getText().trim()));
			    qualityAttribute.setDescription(SquareUtil.firstCharacterToUpperCase(termDefinitionTextBox.getText().trim()));
			 
				for(int i=0;i<list.size();i++)
				{
					if(this.qualityAttribute.getName().equalsIgnoreCase(list.get(i).getName()))
					{
						Window.alert(messages.createQualityAttributeDialogBoxAlreadyExist());
						return;
					}
				}
				
				createQualityAttributeCommand.createCommand(this.qualityAttribute);
		}
	}

	private class CancelHandler implements ClickHandler
	{
		private CreateQualityAttributeDialog dialog = null;

		public CancelHandler(CreateQualityAttributeDialog dialog)
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
