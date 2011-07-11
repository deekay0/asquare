package edu.cmu.square.client.ui.reviewAndFinalizeRequirements;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtSubGoal;

public class SubGoalDialogBox extends DialogBox
{

	private List<GwtSubGoal> oldSelectedSubGoals;
	private List<GwtSubGoal> allSubGoals;
	private List<GwtSubGoal> newSelectedSubGoals;
	
	final ReviewAndFinalizeRequirementsDetailPaneMessages messages = (ReviewAndFinalizeRequirementsDetailPaneMessages) GWT.create(ReviewAndFinalizeRequirementsDetailPaneMessages.class);

	public SubGoalDialogBox(final Command riksPane, List<GwtSubGoal> allSubGoals, List<GwtSubGoal> curretSelectedSubGoals)
		{

			// Set the dialog box's caption.
			setText(messages.associateGoals());

			this.allSubGoals = allSubGoals;
			this.oldSelectedSubGoals = curretSelectedSubGoals;

			final VerticalPanel checkBoxContainer = new VerticalPanel();
			checkBoxContainer.setSpacing(10);
			for (GwtSubGoal a : allSubGoals)
			{

				SubGoalCheckBox checkBox = new SubGoalCheckBox();
				checkBox.setText(a.getDescription());
				checkBox.setSubGoalID(a.getId());
				if (isCurrentSubGoalSelected(a.getId()))
				{
					checkBox.setValue(true);
				}
				checkBoxContainer.add(checkBox);
			}
			
			Button ok = new Button(messages.ok());
			Button cancel = new Button(messages.cancel());
			cancel.setWidth("60px");
			ok.setWidth("60px");
			
			FlexTable buttonsTable = new FlexTable();
			buttonsTable.setWidth("100%");
			buttonsTable.setCellPadding(3);
			buttonsTable.setWidget(0, 0, ok);
			buttonsTable.setWidget(0, 1, cancel);
			buttonsTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
			buttonsTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
			
			
			
			ok.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						loadSelectedSubGoals(checkBoxContainer);
						riksPane.execute();
						SubGoalDialogBox.this.hide();
					}
				});
		
			cancel.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{

						SubGoalDialogBox.this.hide();
					}
				});
	
			   checkBoxContainer.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			checkBoxContainer.add(buttonsTable);
			setWidget(checkBoxContainer);
		}

	private void loadSelectedSubGoals(VerticalPanel checkBoxContainer)
	{
		this.newSelectedSubGoals = new ArrayList<GwtSubGoal>();
		for (Widget w : checkBoxContainer)
		{
			if (w instanceof SubGoalCheckBox)
			{
				SubGoalCheckBox currentSubGoal = (SubGoalCheckBox) w;

				if (currentSubGoal.getValue() == true)// isChecked
				{
					GwtSubGoal gwtSelecteSubGoal = getSubGoalById(currentSubGoal.getSubGoalID());

					if (gwtSelecteSubGoal != null)
					{
						newSelectedSubGoals.add(gwtSelecteSubGoal);
					}
				}
			}
		}

	}

	private boolean isCurrentSubGoalSelected(int id)
	{

		for (GwtSubGoal a : this.oldSelectedSubGoals)
		{
			if (a.getId() == id)
			{
				return true;
			}
		}
		return false;
	}
	private GwtSubGoal getSubGoalById(int id)
	{

		for (GwtSubGoal a : allSubGoals)
		{
			if (a.getId() == id)
			{
				return a;
			}
		}
		return null;
	}

	public List<GwtSubGoal> getNewSelectedSubGoals()
	{
		return newSelectedSubGoals;
	}

	public List<GwtSubGoal> getOldSelectedSubGoals()
	{
		return oldSelectedSubGoals;
	}

	class SubGoalCheckBox extends CheckBox
	{
		private int SubGoalID;
		public void setSubGoalID(int SubGoalID)
		{
			this.SubGoalID = SubGoalID;
		}
		public int getSubGoalID()
		{
			return SubGoalID;
		}

	}

}
