package edu.cmu.square.client.ui.core;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.model.GWTAppProperties;
import edu.cmu.square.client.navigation.State;

public class FooterPane extends Composite
{	
	public FooterPane(GWTAppProperties properties)
	{
	
		VerticalPanel footerPanel = new VerticalPanel();
		footerPanel.setSize("100%", "100%");
		footerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		footerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		footerPanel.setStyleName("gwt-footer");
		
		//This has to be an HTML snippet becuase we don't know how to open things up in a new window without hitting our navigator
		String organizationLink = "<a href='" + properties.getFooterOrganizationURL() + "' target='_blank' class=\"gwt-footer\">" + properties.getFooterLinkOrganizationName() + "</a>";  

		
		Label title = new Label(properties.getFooterTitle());
		title.setStyleName("gwt-footer");
		Label copyright = new Label(properties.getFooterLabelCopyRight());
		copyright.setStyleName("gwt-footer");
		footerPanel.add(new Label(" "));
		footerPanel.add(title);
		footerPanel.add(new HTML(organizationLink));
		footerPanel.add(copyright);
		this.initWidget(footerPanel);
	}



	
}
