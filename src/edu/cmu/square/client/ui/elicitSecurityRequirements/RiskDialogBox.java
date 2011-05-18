package edu.cmu.square.client.ui.elicitSecurityRequirements;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtRisk;

public class RiskDialogBox extends DialogBox

{

	private List<GwtRisk> oldSelectedRisks;
	private List<GwtRisk> allRisks;
	private List<GwtRisk> newSelectedRisks;
	
	final ElicitSecurityRequirementPaneMessages messages = (ElicitSecurityRequirementPaneMessages) GWT.create(ElicitSecurityRequirementPaneMessages.class);

	public RiskDialogBox(final Command riksPane, List<GwtRisk> allRisks, List<GwtRisk> curretSelectedRisks)
		{

			// Set the dialog box's caption.
			setText(messages.associateRisks());

			this.allRisks = allRisks;
			this.oldSelectedRisks = curretSelectedRisks;

			final VerticalPanel checkBoxContainer = new VerticalPanel();
			int count=0;
			checkBoxContainer.setSpacing(10);
			for (GwtRisk a : allRisks)
			{

				RiskCheckBox checkBox = new RiskCheckBox();
				checkBox.setText(a.getRiskTitle());
				checkBox.setRiskID(a.getId());
				if (isCurrentRiskSelected(a.getId()))
				{
					checkBox.setValue(true);
				}
				checkBoxContainer.add(checkBox);
				count++;
			}
			if(count==0)
			{
				checkBoxContainer.add(new Label(messages.associateSubGoalFirst()));
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
						loadSelectedRisks(checkBoxContainer);
						riksPane.execute();
						RiskDialogBox.this.hide();
					}
				});
			
			cancel.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{

						RiskDialogBox.this.hide();
					}
				});
			
			
			   checkBoxContainer.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			checkBoxContainer.add(buttonsTable);
			setWidget(checkBoxContainer);
		}

	private void loadSelectedRisks(VerticalPanel checkBoxContainer)
	{
		this.newSelectedRisks = new ArrayList<GwtRisk>();
		for (Widget w : checkBoxContainer)
		{
			if (w instanceof RiskCheckBox)
			{
				RiskCheckBox currentRisk = (RiskCheckBox) w;

				if (currentRisk.getValue() == true)// isChecked
				{
					GwtRisk gwtSelecteRisk = getRiskById(currentRisk.getRiskID());

					if (gwtSelecteRisk != null)
					{
						newSelectedRisks.add(gwtSelecteRisk);
					}
				}
			}
		}

	}

	private boolean isCurrentRiskSelected(int id)
	{

		for (GwtRisk a : this.oldSelectedRisks)
		{
			if (a.getId() == id)
			{
				return true;
			}
		}
		return false;
	}
	private GwtRisk getRiskById(int id)
	{

		for (GwtRisk a : allRisks)
		{
			if (a.getId() == id)
			{
				return a;
			}
		}
		return null;
	}

	public List<GwtRisk> getNewSelectedRisks()
	{
		return newSelectedRisks;
	}

	public List<GwtRisk> getOldSelectedRisks()
	{
		return oldSelectedRisks;
	}

	class RiskCheckBox extends CheckBox
	{
		private int RiskID;
		public void setRiskID(int RiskID)
		{
			this.RiskID = RiskID;
		}
		public int getRiskID()
		{
			return RiskID;
		}

	}

}
