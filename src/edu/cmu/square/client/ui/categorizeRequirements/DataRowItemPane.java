package edu.cmu.square.client.ui.categorizeRequirements;

import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.ui.core.SquareHyperlink;

public class DataRowItemPane extends Composite
{

	private int row = -1;
	private int dataId = -1;
	private List<GwtCategory> categories;
	private GwtRequirement requirement;
	private CategorizeRequirementsTable parenTable;
	final CategorizePaneMessages messages = (CategorizePaneMessages) GWT.create(CategorizePaneMessages.class);

	public DataRowItemPane(int rowId, GwtRequirement requirement,final CategorizeRequirementsTable parenTable)
		{
			this.row = rowId;
			this.parenTable=parenTable;
			requirementCheckBox = new CheckBox();
			requirementCheckBox.addClickHandler(new ClickHandler(){

				
				public void onClick(ClickEvent event)
				{
					
					parenTable.loadCategoryListbox();
					
				}});
			initWidget(getDataItemPaneRow(requirement));
			

		}

	private CheckBox requirementCheckBox;

	public Widget getDataItemPaneRow(GwtRequirement requirement)
	{

		VerticalPanel verticalRowPane = new VerticalPanel();
		verticalRowPane.setHeight("50px");
		verticalRowPane.setStyleName("inner-table");
		final SquareHyperlink viewLink = new SquareHyperlink(messages.view());
		
		Label requirementReadOnly = new Label(requirement.getTitle());
		
		requirementCheckBox.setText(requirement.getTitle());
		requirementCheckBox.setValue(false);
		
		FlexTable topPart = new FlexTable();
		topPart.setBorderWidth(0);
		topPart.setHeight("10px");
		topPart.setWidth("580px");
		if(this.parenTable.getCurrentMode().equals(GwtModesType.ReadWrite))
		{
		topPart.setWidget(0, 0, requirementCheckBox);
		}
		else
		{
			topPart.setWidget(0, 0, requirementReadOnly);
		}
		topPart.setWidget(0, 1, viewLink);
		topPart.getFlexCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		topPart.getFlexCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);

		this.dataId = requirement.getId();
		this.categories = requirement.getCategories();

		Label categoryLabel = new Label("");
		categoryLabel.setStyleName("square-category-label");

		String text = "";
		int count = 0;
		List<GwtCategory> listOfCategories= requirement.getCategories();
		Collections.sort(listOfCategories);
		for (GwtCategory cat : listOfCategories)
		{
			if (requirement.getCategories().size() - 1 == count)
			{
				text = text + cat.getCategoryName();
			}
			else
			{
				text = text + cat.getCategoryName() + ", ";
			}
			count++;
		}

		if (text.trim().length() > 0)
		{
			categoryLabel.setText(text);
			topPart.setWidget(1, 0, categoryLabel);
			topPart.setWidget(1, 1, new Label(" "));
		}

		final DisclosurePanel bottomPart = new DisclosurePanel();
		bottomPart.setAnimationEnabled(true);
		bottomPart.setContent(new Label(requirement.getDescription()));
		bottomPart.setHeight("1px");

		viewLink.addClickHandler(new ClickHandler()
			{

				
				public void onClick(ClickEvent event)
				{

					if (bottomPart.isOpen())
					{
						viewLink.setText(messages.view());
						bottomPart.setOpen(false);
					}
					else
					{
						viewLink.setText(messages.hide());
						bottomPart.setOpen(true);
					}

				}
			});

		verticalRowPane.setStyleName("inner-table");
		verticalRowPane.setSpacing(2);

		verticalRowPane.add(topPart);
		verticalRowPane.add(bottomPart);

		return verticalRowPane;

	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public int getRow()
	{
		return row;
	}

	public void setDataId(int dataId)
	{
		this.dataId = dataId;
	}

	public int getDataId()
	{
		return dataId;
	}

	public void setChecked(boolean isChecked)
	{

		requirementCheckBox.setValue(isChecked);

	}

	public boolean isChecked()
	{
		return requirementCheckBox.getValue();
	}

	public void setCategories(List<GwtCategory> categories)
	{
		this.categories = categories;
	}

	public List<GwtCategory> getCategories()
	{
		return categories;
	}

	public void setRequirement(GwtRequirement requirement)
	{
		this.requirement = requirement;
	}

	public GwtRequirement getRequirement()
	{
		return requirement;
	}

}
