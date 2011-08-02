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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtAsset;
public class AssetDialogBox extends DialogBox

{

	private List<GwtAsset> oldSelectedAssets;
	private List<GwtAsset> allAssets;
	private List<GwtAsset> newSelectedAssets;
	private String value;
	
	final RiskAssessmentPaneMessages messages = (RiskAssessmentPaneMessages) GWT.create(RiskAssessmentPaneMessages.class);

	public AssetDialogBox(final Command riksPane, List<GwtAsset> allAssets,
			List<GwtAsset> curretSelectedAssets)
		{

			// Set the dialog box's caption.
			setText(messages.associateAssets());
			this.allAssets = allAssets;
			this.oldSelectedAssets = curretSelectedAssets;

			final VerticalPanel checkBoxContainer = new VerticalPanel();
			   checkBoxContainer.setSpacing(7);
			for (GwtAsset a : allAssets)
			{
				AssetCheckBox checkBox = new AssetCheckBox();
				checkBox.setText(a.getDescription());
				checkBox.setAssetID(a.getId());
				if (isCurrentAssetSelected(a.getId()))
				{
					checkBox.setValue(true);
				}
				checkBoxContainer.add(checkBox);
			}
			
			 
			
			
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			horizontalPanel.setSpacing(5);
			horizontalPanel.setWidth("200px");
			Button ok = new Button(messages.ok());
			Button cancel = new Button(messages.cancel());
			ok.setWidth("80px");
			cancel.setWidth("80px");
			ok.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						loadSelectedAssets(checkBoxContainer);
						riksPane.execute();
						AssetDialogBox.this.hide();
					}
				});
			
			
			cancel.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						AssetDialogBox.this.hide();
					}
				});
			
			horizontalPanel.add(ok);
			horizontalPanel.add(cancel);
			checkBoxContainer.add(horizontalPanel);
			setWidget(checkBoxContainer);
		}

	private boolean isCurrentAssetSelected(int id)
	{

		for (GwtAsset a : this.oldSelectedAssets)
		{
			if (a.getId() == id)
			{
				return true;
			}
		}
		return false;
	}

	private void loadSelectedAssets(VerticalPanel checkBoxContainer)
	{
		this.newSelectedAssets = new ArrayList<GwtAsset>();
		for (Widget w : checkBoxContainer)
		{
			if (w instanceof AssetCheckBox)
			{
				AssetCheckBox currentAsset = (AssetCheckBox) w;

				if (currentAsset.getValue() == true)// isChecked
				{
					GwtAsset gwtSelecteAsset = getAssetById(currentAsset
							.getAssetID());

					if (gwtSelecteAsset != null)
					{
						newSelectedAssets.add(gwtSelecteAsset);
					}
				}
			}
		}

	}

	private GwtAsset getAssetById(int id)
	{

		for (GwtAsset a : allAssets)
		{
			if (a.getId() == id)
			{
				return a;
			}
		}
		return null;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	public List<GwtAsset> getNewSelectedAssets()
	{
		return newSelectedAssets;
	}

	public List<GwtAsset> getOldSelectedAssets()
	{
		return oldSelectedAssets;
	}

	class AssetCheckBox extends CheckBox
	{
		private int assetID;

		public void setAssetID(int assetID)
		{
			this.assetID = assetID;
		}

		public int getAssetID()
		{
			return assetID;
		}

	}

}
