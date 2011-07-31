package edu.cmu.square.client.ui.ManageProject;

import java.util.List;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.utils.SquareUtil;



public class EditOrMergeCategoryDialog extends DialogBox
{
	final ManageProjectPaneMessages messages = (ManageProjectPaneMessages) GWT.create(ManageProjectPaneMessages.class);
	
	private final TextBox categoryNameTextBox = new TextBox();
	private final ListBox categoryMergeListBox = new ListBox();
	
	private final RadioButton editSelection = new RadioButton("group1",messages.editSelection());
	private final RadioButton mergeSelection = new RadioButton("group1",messages.mergeSelection());

	private CategoryGrid caller = null;
	

	/**
	 * Creates a new dialog box with a talk back pointer to the given category
	 * grid.
	 * 
	 * @param gridPointer
	 *            The category grid to which this dialog should talk back.
	 */
	public EditOrMergeCategoryDialog(CategoryGrid gridPointer)
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
	public EditOrMergeCategoryDialog(CategoryGrid gridPointer, GwtCategory category)
	{
		super();
		this.caller = gridPointer;
		this.initializeDialog(category);
	}
	
	
	
	private void initializeMergeListBox()
	{
		for(GwtCategory c: this.caller.getCategoryList())
		{
			if (this.caller.getCurrentCategoryID() != c.getId())
			{
				categoryMergeListBox.addItem(c.getCategoryName(), c.getId()+"");
			}
		}
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
		VerticalPanel editLayout = new VerticalPanel();
		HorizontalPanel buttonsLayout = new HorizontalPanel();
		editSelection.setValue(true);
		
		categoryNameTextBox.setEnabled(true);
		categoryMergeListBox.setEnabled(false);
		
		if (this.caller.getCategoryList().size() == 1)
		{
			mergeSelection.setVisible(false);
			categoryMergeListBox.setVisible(false);
		}
		
		editSelection.addClickHandler(new ClickHandler()
		{	
			public void onClick(ClickEvent event)
			{
				categoryNameTextBox.setEnabled(true);
				categoryMergeListBox.setEnabled(false);
				
			}});
		
		mergeSelection.addClickHandler(new ClickHandler()
		{	
			public void onClick(ClickEvent event)
			{
				categoryNameTextBox.setEnabled(false);
				categoryMergeListBox.setEnabled(true);
				
			}
		});


		editLayout.add(this.editSelection);
		editLayout.add(this.categoryNameTextBox);
		editLayout.add(new Label(" "));
		editLayout.add(this.mergeSelection);
		editLayout.add(this.categoryMergeListBox);
		
		initializeMergeListBox();
	
		
		this.categoryNameTextBox.setWidth("500px");
		this.categoryNameTextBox.setText(category.getCategoryName());


		// Set up the buttons
		final Button saveButton = new Button(messages.save(), new SaveHandler(this, category));
		Button cancelButton = new Button(messages.cancel(), new CancelHandler(this));

		saveButton.setWidth("100px");
		cancelButton.setWidth("100px");

		buttonsLayout.setSpacing(10);
		buttonsLayout.add(saveButton);
		buttonsLayout.add(cancelButton);
		
		categoryNameTextBox.addKeyUpHandler(new KeyUpHandler()
	    {
			public void onKeyUp(KeyUpEvent event)
			{
				if(mergeSelection.getValue()==false)
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
			}
	    });
	    
		categoryNameTextBox.addKeyDownHandler(new KeyDownHandler()
	    {
			public void onKeyDown(KeyDownEvent event)
			{
				if(mergeSelection.getValue()==false)
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
			}
	    });

		// set the base layout
		baseLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		baseLayout.add(editLayout);
		baseLayout.add(buttonsLayout);
		baseLayout.setSpacing(5);

		this.setWidget(baseLayout);
	}

	// ******* Handlers for the buttons *************

	private class SaveHandler implements ClickHandler
	{
		
		private EditOrMergeCategoryDialog dialog = null;
		private GwtCategory category = null;

		public SaveHandler(EditOrMergeCategoryDialog dialogPointer, GwtCategory newCategory)
			{
				super();
				this.dialog = dialogPointer;
				this.category = newCategory;
			}

	
		public void onClick(ClickEvent event)
		{	
			    List<GwtCategory> list = caller.getCategoryList();
				String original = this.category.getCategoryName();
			    this.dialog.hide();
				this.category.setCategoryName(SquareUtil.firstCharacterToUpperCase(categoryNameTextBox.getText().trim()));
				
				if (!this.category.isSavedInDb()) // create a new category,
				// pass it back to the
				// caller
				{
					caller.addCategoryToTable(this.category);
				}
				else
				// udpate the old category, pass it back to the caller
				{
					
					if(mergeSelection.getValue()==true)
					{
						int selectedIndex=categoryMergeListBox.getSelectedIndex();
						int mergeToId = Integer.parseInt(categoryMergeListBox.getValue(selectedIndex));
						String targetCategory=categoryMergeListBox.getItemText(selectedIndex);
					
						boolean response = Window.confirm(messages.confirmMerge(this.category.getCategoryName(),targetCategory));
						if (response)
						{
	
						
						caller.mergeCategoryInTable(this.category.getId(), mergeToId);
						}
					}
					else
					{
		                this.category.setCategoryName(original);

						for(int i=0;i<list.size();i++)
						{
							if(categoryNameTextBox.getText().trim().equalsIgnoreCase(list.get(i).getCategoryName()))
							{
								this.category.setCategoryName(original);
								Window.alert(messages.duplicateCategoryError());
								return;
							}
						}
						this.category.setCategoryName(categoryNameTextBox.getText());
						caller.updateCategoryInTable(this.category);
					}
					
				}
			
		}
	}

	private class CancelHandler implements ClickHandler
	{
		private EditOrMergeCategoryDialog dialog = null;

		public CancelHandler(EditOrMergeCategoryDialog dialog)
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
