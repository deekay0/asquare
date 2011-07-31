package edu.cmu.square.client.ui.categorizeRequirements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.ui.core.SquareHyperlink;

public class CategoriesInbox
{

	private SquareHyperlink selectedCategoryLink;
	private List<GwtCategory> listOfCategories = new ArrayList<GwtCategory>();
	private List<GwtRequirement> listOfRequirements = new ArrayList<GwtRequirement>();
	private VerticalPanel outerCategoryInboxPanel;
	private int unassignedCount = 0;
	private int allCount = 0;
	private int currentSelectedCategory = -1;
	private GwtModesType currentMode;
	final CategorizePaneMessages messages = (CategorizePaneMessages) GWT.create(CategorizePaneMessages.class);
	
	public CategoriesInbox()
		{
			outerCategoryInboxPanel = new VerticalPanel();
		}

	public Widget getInboxWidget(List<GwtCategory> categories, List<GwtRequirement> requirements, GwtModesType currentMode)
	{
		this.currentMode = currentMode;
		listOfCategories = categories;
		listOfRequirements = requirements;

		updateCategoryCount();

		return buildInbox(currentSelectedCategory);
	}

	public boolean areAllRequirementsCategorized()
	{
		if (unassignedCount == 0)
		{
			return true;
		}

		return false;

	}
	public void clearWidget()
	{
		this.outerCategoryInboxPanel.clear();
	}

	private void updateCategoryCount()
	{
		allCount = listOfRequirements.size();
		
		for (GwtCategory cat : listOfCategories)
		{
			int count = 0;
			for (GwtRequirement req : listOfRequirements)
			{
				for (GwtCategory req_cat : req.getCategories())
				{
					if (req_cat.getId() == cat.getId())
					{
						count++;
						break;
					}
				}

			}
			cat.setCount(count);
		}
		
		this.unassignedCount = listOfRequirements.size();
		for (GwtRequirement requirement : listOfRequirements)
		{
			if (requirement.getCategories().size() != 0)
			{
				this.unassignedCount--;
			}
		}

	}

	private Widget buildInbox(int selectedCategory)
	{
		outerCategoryInboxPanel.clear();
		VerticalPanel categoryInboxPanel = new VerticalPanel();
		categoryInboxPanel.clear();

		categoryInboxPanel.setStyleName("square-Categorize-inbox");

		final DataElementHyperlink showAllLink = new DataElementHyperlink(messages.categoryInboxShowAllLink() + " (" + this.allCount + ")", -1);
		final DataElementHyperlink showUnassignedLink = new DataElementHyperlink(messages.categoryInboxUnassignedLink() + " (" + this.unassignedCount + ")", -2);

		showAllLink.setStyleName("square-hyperlink");
		showUnassignedLink.setStyleName("square-hyperlink");

		if (selectedCategory == -1)
		{
			showAllLink.setStyleName("square-Categorize-selected");
			selectedCategoryLink = showAllLink;
		}
		else if (selectedCategory == -2)
		{
			showUnassignedLink.setStyleName("square-Categorize-selected");
			selectedCategoryLink = showUnassignedLink;
		}

		GwtCategory showAllCategory = new GwtCategory();
		showAllCategory.setId(-1);

		GwtCategory showAllUnassigned = new GwtCategory();
		showAllUnassigned.setId(-2);

		showAllLink.addClickHandler(new LinkClickHandler(showAllLink, showAllCategory));
		showUnassignedLink.addClickHandler(new LinkClickHandler(showUnassignedLink, showAllUnassigned));

		categoryInboxPanel.add(showAllLink);
		categoryInboxPanel.add(showUnassignedLink);

		Collections.sort(listOfCategories);
		for (GwtCategory c : listOfCategories)
		{
			String text = c.getCategoryName() + " (" + c.getCount() + ")";
			final DataElementHyperlink categoryLink = new DataElementHyperlink(text, c.getId());

			if (selectedCategory == c.getId())
			{
				categoryLink.setStyleName("square-Categorize-selected");
				selectedCategoryLink = categoryLink;
			}
			else
			{
				categoryLink.setStyleName("square-hyperlink");
			}

			categoryLink.addClickHandler(new LinkClickHandler(categoryLink, c));
			categoryInboxPanel.add(categoryLink);

		}

		outerCategoryInboxPanel.add(categoryInboxPanel);

		return outerCategoryInboxPanel;
	}

	private class LinkClickHandler implements ClickHandler
	{
		private DataElementHyperlink link = null;
		private GwtCategory category = null;

		public LinkClickHandler(DataElementHyperlink link, GwtCategory newCategory)
			{
				super();
				this.link = link;
				this.category = newCategory;

			}

		public void onClick(ClickEvent event)
		{

			selectedCategoryLink.setStyleName("square-Categorize-hyperlink");
			link.setStyleName("square-Categorize-hyperlink-selected");
			selectedCategoryLink = link;
			currentSelectedCategory = link.getId();
			fireEvent(link.id);
		}
	}

	public int getSelectedCategory()
	{
		return currentSelectedCategory;
	}

	private class DataElementHyperlink extends SquareHyperlink
	{

		public DataElementHyperlink(String text, int id)
			{
				super(text);
				this.id = id;
				// TODO Auto-generated constructor stub
			}
		public void setId(int id)
		{
			this.id = id;
		}
		public int getId()
		{
			return id;
		}
		private int id;

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

	void fireEvent(int categoryId)
	{
		CategoryChangedEvent event = new CategoryChangedEvent(categoryId);
		fireEvent(event);
	}

	/**
	 * Fire an event.
	 */
	void fireEvent(GwtEvent<CategoryChangedEvent.IMyHandler> event)
	{

		handlerManager.fireEvent(event);
	}

}
