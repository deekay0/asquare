package edu.cmu.square.client.ui.categorizeRequirements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.ui.categorizeRequirements.CategoryChangedEvent.Action;
import edu.cmu.square.client.ui.core.SquareHyperlink;
import edu.cmu.square.client.ui.core.YellowFadeHandler;

public class CategorizeRequirementsTable implements Command
{

	private ListBox actionListbox;
	private ListBox categoryListbox;
	List<GwtRequirement> listOfRequirements;
	List<GwtRequirement> listOfFilteredRequirements;
	List<GwtRequirement> listOfPreviouseCheckedRequirements = new ArrayList<GwtRequirement>();
	List<GwtCategory> listOfCategories;
	private FlexTable categoryTable;
	int projectId;
	private int currentSelectedCategory = -1;
	private int currentSelectedAction = 0;
	private CreateCategoryDialog createCategoryDialog;
	private GwtModesType currentMode;
	private YellowFadeHandler yellowFadeHandler = new YellowFadeHandler();

	final CategorizePaneMessages messages = (CategorizePaneMessages) GWT.create(CategorizePaneMessages.class);

	public CategorizeRequirementsTable()
		{

			categoryTable = new FlexTable();

		}
	public GwtModesType getCurrentMode()
	{
		return currentMode;
	}

	public void setCurrentMode(GwtModesType currentMode)
	{
		this.currentMode = currentMode;
	}

	/**
	 * Initializes the category grid, putting the button at the top and
	 * inputting the initial set of categories from the database.
	 */

	public Widget getRequirementsCategoryWidget(List<GwtRequirement> requirements, List<GwtCategory> categories, int categoryId,
			GwtModesType currentMode)
	{
	
		this.currentMode = currentMode;
		currentSelectedCategory = categoryId;
		listOfRequirements = requirements;
		listOfCategories = categories;
		listOfFilteredRequirements = new ArrayList<GwtRequirement>();
		actionListbox = new ListBox();
		categoryListbox = new ListBox();
		actionListbox.addChangeHandler(new ChangeHandler()
			{

				public void onChange(ChangeEvent event)
				{
					currentSelectedAction = actionListbox.getSelectedIndex();
					loadCategoryListbox();

				}
			});

		categoryListbox.addChangeHandler(new ChangeHandler()
			{
				public void onChange(ChangeEvent event)
				{
					int index = actionListbox.getSelectedIndex();
					String action = actionListbox.getItemText(index);

					index = categoryListbox.getSelectedIndex();
					String categoryValue = categoryListbox.getValue(index);

					if (atLeastOneCheckedValidator())
					{
						handleAction(action, categoryValue);
					}
					else
					{
						Window.alert(messages.noRequirementSelectedAlert());
						categoryListbox.setSelectedIndex(0);
					}

				}
			});

		FlexCellFormatter formatter = categoryTable.getFlexCellFormatter();
		categoryTable.setWidth("600px");
		categoryTable.setCellSpacing(0);
	
		
		categoryTable.setWidget(0, 0,loadHeaderPaneRow() );

		formatter.setStyleName(0, 0, "square-Categorize-TableHeader");
		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);

		loadFilteredRequirements();

		int rowNumber = 1;
		for (GwtRequirement requirement : listOfFilteredRequirements)
		{

			DataRowItemPane rowPane = new DataRowItemPane(rowNumber, requirement, this);
			categoryTable.setWidget(rowNumber, 0, rowPane);
			categoryTable.getFlexCellFormatter().setStyleName(rowNumber, 0, "square-Categorize-flex");
			if(listOfPreviouseCheckedRequirements.contains(requirement))
			{
				//rowPane.setChecked(true);
				yellowFadeHandler.add(rowPane);
			
				loadCategoryListbox();
			}
			rowNumber++;

		}
		listOfPreviouseCheckedRequirements.clear();
		if(categoryTable.getRowCount()==1)
		{
			
			DisclosurePanel diclosure= new DisclosurePanel();
		
			Label noRequirement = new Label(messages.noRequirementsLabel());
			noRequirement.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			diclosure.add(noRequirement);
			categoryTable.setWidget(rowNumber, 0, diclosure);
			categoryTable.getCellFormatter().setStyleName(rowNumber, 0, "square-Categorize-flex");
			categoryTable.getCellFormatter().setHorizontalAlignment(rowNumber, 0, HasHorizontalAlignment.ALIGN_LEFT);
			diclosure.setAnimationEnabled(true);
			diclosure.setOpen(true);

		}
	
		return categoryTable;

	}

	public void loadFilteredRequirements()
	{
		this.listOfFilteredRequirements.clear();
		for (GwtRequirement requirement : this.listOfRequirements)
		{
			if (isRequirementAssociateToCategory(currentSelectedCategory, requirement))
			{
				if (requirement.getId() >0)
				{
					this.listOfFilteredRequirements.add(requirement);
				}

			}
		}
	}

	public void handleAction(String action, String categoryValue)
	{
		if (action.equalsIgnoreCase(messages.assignActionListBox()) && categoryValue.trim().equalsIgnoreCase("create"))
		{
			createCategoryDialog = new CreateCategoryDialog(listOfCategories, this);
			createCategoryDialog.setText(messages.createCategoryDialogBoxTitle());
			createCategoryDialog.center();
			createCategoryDialog.setAnimationEnabled(true);
			createCategoryDialog.show();
		}
		else if (action.equalsIgnoreCase(messages.assignActionListBox()) && categoryValue.trim().length() > 0)
		{
			int catId = Integer.parseInt(categoryValue);
			listOfPreviouseCheckedRequirements=this.getCheckedRequirements();
			fireEvent(catId, listOfPreviouseCheckedRequirements, Action.assign);

		}
		else if (action.equalsIgnoreCase(messages.removeActionListBox()) && categoryValue.trim().length() > 0)
		{
			int catId = Integer.parseInt(categoryValue);
			listOfPreviouseCheckedRequirements=this.getCheckedRequirements();
			fireEvent(catId, listOfPreviouseCheckedRequirements, Action.remove);

		}

	}

	public List<GwtRequirement> getCheckedRequirements()
	{
		List<GwtRequirement> requirements = new ArrayList<GwtRequirement>();
		for (int i = 1; i < categoryTable.getRowCount(); i++)
		{

			DataRowItemPane item = (DataRowItemPane) categoryTable.getWidget(i, 0);
			if (item != null)
			{
				if (item.isChecked())
				{
					int requirementId = item.getDataId();
					requirements.add(getRequirementById(requirementId));
				}
			}
		}
		return requirements;

	}
	public boolean isRequirementChecked(int requirementId)
	{

		for (int i = 1; i < categoryTable.getRowCount(); i++)
		{
			DataRowItemPane item = (DataRowItemPane) categoryTable.getWidget(i, 0);
			if (item != null)
			{
				if (item.getDataId() == requirementId)
				{
					if (item.isChecked())
					{
						return true;
					}
				}
			}
		}
		return false;

	}

	public boolean isRequirementAssociateToCategory(int categoryId, GwtRequirement requirement)
	{
		if (categoryId == -1)// Show all
		{

			return true;
		}
		else if (categoryId == -2 && requirement.getCategories().size() == 0)// Unassigned
		{

			return true;
		}
		else
		{
			for (GwtCategory cat : requirement.getCategories())
			{
				if (cat.getId() == categoryId)
				{
					return true;
				}
			}
		}
		return false;
	}

	public HorizontalPanel loadHeaderPaneRow()
	{
		HorizontalPanel headerPane = new HorizontalPanel();
		headerPane.clear();
		headerPane.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		headerPane.setStyleName("inner-table");
		headerPane.setSpacing(2);

		if (this.getCurrentMode().equals(GwtModesType.ReadWrite))
		{
			loadActionListBox();
			loadCategoryListbox();
			// populate the flex table with default values

			headerPane.add(actionListbox);
			headerPane.add(categoryListbox);
			headerPane.add(new Label(" "));
			headerPane.add(new Label(messages.selectLable()));

			SquareHyperlink showAll = new SquareHyperlink(messages.allLink());
			SquareHyperlink none = new SquareHyperlink(messages.noneLink());

			showAll.addClickHandler(new ClickHandler()
				{

					public void onClick(ClickEvent event)
					{
						selectAllRequirements();
					}
				});

			none.addClickHandler(new ClickHandler()
				{

					public void onClick(ClickEvent event)
					{
						uncheckAllRequirements();
					}
				});
			showAll.setStyleName("square-hyperlink");
			showAll.addStyleName("square-categorize-header-hyperlink");
			
			
			none.setStyleName("square-hyperlink");
			none.addStyleName("square-categorize-header-hyperlink");
			headerPane.add(showAll);
			headerPane.add(new Label(", "));
			headerPane.add(none);

		}
		else
		{
			headerPane.add(new Label(messages.requiremntTableTitle()));
		}
		return headerPane;
		
	}

	public void loadActionListBox()
	{
		actionListbox.clear();
		actionListbox.setWidth("100px");
		actionListbox.addItem(messages.assignActionListBox(), "a");
		actionListbox.addItem(messages.removeActionListBox(), "r");
		actionListbox.setSelectedIndex(currentSelectedAction);

	}

	public void loadCategoryListbox()
	{

		List<GwtCategory> categories;
		int index = actionListbox.getSelectedIndex();
		String text = actionListbox.getItemText(index);

		if (text.trim().equalsIgnoreCase(messages.assignActionListBox()))
		{
			categories = getCategoriesToAssign();
		}
		else
		{
			categories = getCategoriesToRemove();
		}
		Collections.sort(categories);
		categoryListbox.clear();
		categoryListbox.setWidth("200px");
		categoryListbox.addItem(messages.chooseCategoryListBox(), " ");

		for (GwtCategory c : categories)
		{

			if (text.trim().equalsIgnoreCase(messages.assignActionListBox()))
			{
				if (currentSelectedCategory != c.getId())
				{
					categoryListbox.addItem("  " + c.getCategoryName(), c.getId() + "");
				}
			}
			else
			{
				categoryListbox.addItem("  " + c.getCategoryName(), c.getId() + "");

			}
		}
		
		//Show the New option when the action is assign and there is at least on requirement selected
		if (text.trim().equalsIgnoreCase(messages.assignActionListBox())&&atLeastOneCheckedValidator())
		{
			categoryListbox.addItem(messages.newActionListBox(), "create");
		}

	}

	private void selectAllRequirements()
	{
		for (int i = 1; i < categoryTable.getRowCount(); i++)
		{

			DataRowItemPane item = (DataRowItemPane) categoryTable.getWidget(i, 0);
			if (item != null)
			{
				item.setChecked(true);
			}

		}
		loadCategoryListbox();
	}

	private void uncheckAllRequirements()
	{
		for (int i = 1; i < categoryTable.getRowCount(); i++)
		{
			DataRowItemPane item = (DataRowItemPane) categoryTable.getWidget(i, 0);
			if (item != null)
			{
				item.setChecked(false);
			}
		}
		loadCategoryListbox();
	}

	

	public void clearWidget()
	{
		categoryTable = new FlexTable();
		
	}

	public int getCurrentSelectedCategory()
	{
		return currentSelectedCategory;
	}

	public boolean atLeastOneCheckedValidator()
	{
		for (int i = 1; i < categoryTable.getRowCount(); i++)
		{
			DataRowItemPane item = (DataRowItemPane) categoryTable.getWidget(i, 0);
			if (item != null)
			{
				if (item.isChecked())
				{
					return true;
				}
			}

		}
		return false;

	}

	/**
	 * Filters the category list with the following rules 1) Takes to account
	 * the requirements checked in the table 2) Do an aggregate of the
	 * categories and determine which are the categories allow to assign
	 * 
	 */
	public List<GwtCategory> getCategoriesToAssign()
	{
		List<GwtCategory> categoriesToRemoveList = new ArrayList<GwtCategory>();

		for (GwtRequirement requirement : listOfRequirements)
		{
			if (isRequirementChecked(requirement.getId()))
			{
				if (isRequirementAssociateToCategory(currentSelectedCategory, requirement))
				{
					for (GwtCategory cat : listOfCategories)
					{
						if (!requirement.getCategories().contains(cat))
						{

							if (!categoriesToRemoveList.contains(cat))
							{
								categoriesToRemoveList.add(cat);
							}
						}

					}

				}
			}
		}
		return categoriesToRemoveList;
	}

	/**
	 * Filters the category list with the following rules 1) Takes to account
	 * the requirements checked in the table 2) Returns the categories that have
	 * been selected and do and agregate
	 * 
	 */
	public List<GwtCategory> getCategoriesToRemove()
	{
		List<GwtCategory> categoriesToRemoveList = new ArrayList<GwtCategory>();

		for (GwtRequirement requirement : listOfFilteredRequirements)
		{
			if (isRequirementChecked(requirement.getId()))
			{
				for (GwtCategory cat : requirement.getCategories())
				{

					if (!categoriesToRemoveList.contains(cat))
					{
						categoriesToRemoveList.add(cat);
					}
				}

			}
		}
		return categoriesToRemoveList;
	}

	public GwtRequirement getRequirementById(int id)
	{
		for (GwtRequirement requirement : listOfRequirements)
		{
			if (requirement.getId() == id)
			{
				return requirement;
			}
		}
		return null;
	}

	private HandlerManager handlerManager;

	/**
	 * Adds this handler to the widget.
	 * 
	 * @param the
	 *            type of handler to add
	 * @param type
	 *            the event type
	 * @param handler
	 *            the handler
	 * @return {@link HandlerRegistration} used to remove the handler
	 */
	protected final HandlerRegistration addHandler(GwtEvent.Type<CategoryChangedEvent.IMyHandler> type, final CategoryChangedEvent.IMyHandler handler)
	{
		return ensureHandlers().addHandler(type, handler);
	}

	/**
	 * Ensures the existence of the handler manager.
	 * 
	 * @return the handler manager
	 * */
	private HandlerManager ensureHandlers()
	{
		return handlerManager == null ? handlerManager = new HandlerManager(this) : handlerManager;
	}

	/**
	 * Register a component for the specified event.
	 */
	public void register(final CategoryChangedEvent.ITakesMyEvent component)
	{

		addHandler(CategoryChangedEvent.getType(), new CategoryChangedEvent.IMyHandler()
			{
				public void onLoad(CategoryChangedEvent event)
				{
					component.onCategoryChanged(event);
				}
			});
	}

	void fireEvent(GwtCategory category, List<GwtRequirement> requirements)
	{
		CategoryChangedEvent event = new CategoryChangedEvent(category, requirements);
		fireEvent(event);
	}
	void fireEvent(int categoryId, List<GwtRequirement> requirements, CategoryChangedEvent.Action command)
	{
		CategoryChangedEvent event = new CategoryChangedEvent(requirements, categoryId, command);
		fireEvent(event);
	}

	/**
	 * Fire an event.
	 */
	void fireEvent(GwtEvent<CategoryChangedEvent.IMyHandler> event)
	{

		handlerManager.fireEvent(event);
	}

	
	public void execute()
	{
		listOfPreviouseCheckedRequirements=this.getCheckedRequirements();
		this.fireEvent(createCategoryDialog.getNewCategory(),listOfPreviouseCheckedRequirements );

	}

}
