package edu.cmu.square.client.ui.risksAssessment;

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

import edu.cmu.square.client.model.GwtArtifact;

public class ArtifactDialogBox extends DialogBox
{
	private List<GwtArtifact> oldSelectedArtifacts;
	private List<GwtArtifact> allArtifacts;
	private List<GwtArtifact> newSelectedArtifacts;
	
	final RiskAssessmentPaneMessages messages = (RiskAssessmentPaneMessages) GWT.create(RiskAssessmentPaneMessages.class);

	public ArtifactDialogBox(final Command riksPane, List<GwtArtifact> allArtifacts, List<GwtArtifact> curretSelectedArtifacts, String dialogTitle)
	{

			// Set the dialog box's caption.
			setText(dialogTitle);

			this.allArtifacts = allArtifacts;
			this.oldSelectedArtifacts = curretSelectedArtifacts;

			final VerticalPanel checkBoxContainer = new VerticalPanel();
			checkBoxContainer.setSpacing(7);
			for (GwtArtifact a : allArtifacts)
			{
				ArtifactCheckBox checkBox = new ArtifactCheckBox();
				checkBox.setText(a.getName());
				checkBox.setArtifactID(a.getId());
				if (isCurrentArtifactSelected(a.getId()))
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
						loadSelectedArtifacts(checkBoxContainer);
						riksPane.execute();
						ArtifactDialogBox.this.hide();
					}
				});

			cancel.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{

						ArtifactDialogBox.this.hide();
					}
				});

			checkBoxContainer.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

			checkBoxContainer.add(buttonsTable);
			setWidget(checkBoxContainer);
		}

	private void loadSelectedArtifacts(VerticalPanel checkBoxContainer)
	{
		this.newSelectedArtifacts = new ArrayList<GwtArtifact>();
		for (Widget w : checkBoxContainer)
		{
			if (w instanceof ArtifactCheckBox)
			{
				ArtifactCheckBox currentArtifact = (ArtifactCheckBox) w;

				if (currentArtifact.getValue() == true)// isChecked
				{
					GwtArtifact gwtSelecteArtifact = getArtifactById(currentArtifact.getArtifactID());

					if (gwtSelecteArtifact != null)
					{
						newSelectedArtifacts.add(gwtSelecteArtifact);
					}
				}
			}
		}

	}

	private boolean isCurrentArtifactSelected(int id)
	{

		for (GwtArtifact a : this.oldSelectedArtifacts)
		{
			if (a.getId() == id)
			{
				return true;
			}
		}
		return false;
	}
	private GwtArtifact getArtifactById(int id)
	{

		for (GwtArtifact a : allArtifacts)
		{
			if (a.getId() == id)
			{
				return a;
			}
		}
		return null;
	}

	public List<GwtArtifact> getNewSelectedArtifacts()
	{
		return newSelectedArtifacts;
	}

	public List<GwtArtifact> getOldSelectedArtifacts()
	{
		return oldSelectedArtifacts;
	}

	class ArtifactCheckBox extends CheckBox
	{
		private int ArtifactID;
		public void setArtifactID(int ArtifactID)
		{
			this.ArtifactID = ArtifactID;
		}
		public int getArtifactID()
		{
			return ArtifactID;
		}

	}

}
