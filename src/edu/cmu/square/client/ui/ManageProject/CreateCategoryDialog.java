package edu.cmu.square.client.ui.ManageProject;

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.utils.SquareUtil;

/**
 * A dialog used to get information from the user for categories.
 * 
 * This dialog provides the dialog and talks back as used in the
 * caller-dialog-talkback pattern used by Manage Site.
 * 
 */
public class CreateCategoryDialog extends DialogBox
{
	private final TextBox categoryNameTextBox = new TextBox();

	private CategoryGrid caller = null;
	final ManageProjectPaneMessages messages = (ManageProjectPaneMessages) GWT.create(ManageProjectPaneMessages.class);

	/**
	 * Creates a new dialog box with a talk back pointer to the given category
	 * grid.
	 * 
	 * @param gridPointer
	 *            The category grid to which this dialog should talk back.
	 */
	public CreateCategoryDialog(CategoryGrid gridPointer)
		{
			super();
			this.caller = gridPointer;
			this.initializeDialog(new GwtCategory());
		}

	/**
	 * Creates a new dialog box with a talk back to the pointer to the given
	 * category grid meant for editing the given category.
	 * 
	 * @param gridPointer
	 *            The category grid to which this dialog should talk back.
	 * @param category
	 *            The category to be edited.
	 */
	public CreateCategoryDialog(CategoryGrid gridPointer, GwtCategory category)
		{
			super();
			this.caller = gridPointer;
			this.initializeDialog(category);
		}

	/**
	 * Sets up the controls in the dialog
	 * 
	 * @param category
	 *            The category to be updated in this dialog.
	 */
	private void initializeDialog(GwtCategory category)
	{

		VerticalPanel baseLayout = new VerticalPanel();
		VerticalPanel nameLayout = new VerticalPanel();
		VerticalPanel descriptionLayout = new VerticalPanel();
		HorizontalPanel buttonsLayout = new HorizontalPanel();

		nameLayout.add(new Label(this.messages.categoryName()));
		nameLayout.add(this.categoryNameTextBox);
		this.categoryNameTextBox.setWidth("500px");
		this.categoryNameTextBox.setText(category.getCategoryName());


		// Set up the buttons
		final Button saveButton = new Button(messages.save(), new SaveHandler(this, category));
		Button cancelButton = new Button(messages.cancel(), new CancelHandler(this));

		saveButton.setEnabled(false);
		this.categoryNameTextBox.addKeyUpHandler(new KeyUpHandler()
		{
			public void onKeyUp(KeyUpEvent event)
			{
				if(categoryNameTextBox.getText().trim().equalsIgnoreCase(""))
				{
					saveButton.setEnabled(false);
				}
				else
				{
					saveButton.setEnabled(true);
				}
				
			}
			
		});
		
		this.categoryNameTextBox.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				if(categoryNameTextBox.getText().trim().equalsIgnoreCase(""))
				{
					saveButton.setEnabled(false);
				}
				else
				{
					saveButton.setEnabled(true);
				}
				
			}
			
		});
		
		this.categoryNameTextBox.addKeyDownHandler(new KeyDownHandler()
		{
			public void onKeyDown(KeyDownEvent event)
			{
				if(categoryNameTextBox.getText().trim().equalsIgnoreCase(""))
				{
					saveButton.setEnabled(false);
				}
				else
				{
					saveButton.setEnabled(true);
				}
				
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

	// ******* Handlers for the buttons *************

	private class SaveHandler implements ClickHandler
	{
		private CreateCategoryDialog dialog = null;
		private GwtCategory category = null;

		public SaveHandler(CreateCategoryDialog dialogPointer, GwtCategory newCategory)
		{
			super();
			this.dialog = dialogPointer;
			this.category = newCategory;
		}

		
		public void onClick(ClickEvent event)
		{	
			List<GwtCategory> list = caller.getCategoryList();
		    this.dialog.hide();

		    this.category.setCategoryName(SquareUtil.firstCharacterToUpperCase(categoryNameTextBox.getText().trim()));
			 
			for(int i=0;i<list.size();i++)
			{
				if(this.category.getCategoryName().equalsIgnoreCase(list.get(i).getCategoryName()))
				{
					Window.alert(messages.duplicateCategoryError());
					return;
				}
			}

			if (!this.category.isSavedInDb()) // create a new category, pass it back to the caller
			{
				caller.addCategoryToTable(this.category);
			}
			else // udpate the old category, pass it back to the caller
			{
				caller.updateCategoryInTable(this.category);
			}
		}
	}

	private class CancelHandler implements ClickHandler
	{
		private CreateCategoryDialog dialog = null;

		public CancelHandler(CreateCategoryDialog dialog)
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
