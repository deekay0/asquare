package edu.cmu.square.client.ui.core;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.chooseCase.ChooseCasePilot;
import edu.cmu.square.client.ui.core.content.SignOutPaneMessages;


public class SignOutPane extends BasePane
{
	final SignOutPaneMessages messages = (SignOutPaneMessages)GWT.create(SignOutPaneMessages.class);
	
	public SignOutPane(final State stateInfo)
	{
		super(stateInfo);
		//The stateInfo.isAuthenticated()) placed to protect that the user tries to hit refresh and this pane 
		//will try to setAuthenticated false twice.
	
		if (stateInfo.isAuthenticated())
		{
			stateInfo.setAuthenticated(false);
			stateInfo.clear();
		}
		
		
		if(this.getQueryString().get("timeout")!=null)
		{
			if(this.getQueryString().get("timeout").equalsIgnoreCase("1"))
			{
				this.showStatusBarWithoutAnimation("Session time out!");
			}
		}
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("square-login");
		verticalPanel.setSize("400", "150");
		FlexTable flexTable= new FlexTable();
		
	
		HTML s = new HTML(messages.signOutMessage());
		verticalPanel.add(s);
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		flexTable.setWidget(2, 1, s);
		flexTable.setWidget(3, 1, new Hyperlink(messages.signInMessage(), ChooseCasePilot.generateNavigationId(ChooseCasePilot.PageId.login)));
		
		verticalPanel.add(flexTable);
	
		this.getContent().add(verticalPanel);
	}

}

