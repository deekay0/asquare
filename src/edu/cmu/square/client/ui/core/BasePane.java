package edu.cmu.square.client.ui.core;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.core.content.CommonMessages;

public abstract class BasePane extends Composite
{
	private VerticalPanel basePaneLayout = new VerticalPanel();
	private HorizontalPanel statusBarpane = new HorizontalPanel();
	private VerticalPanel content = new VerticalPanel();
	private Label statusLabel = new Label();
	private Image imageLoader = new Image("images//spinner_orange_on_white.gif");
	private boolean showDisclosurePanel = false;
	final CommonMessages messages = (CommonMessages) GWT.create(CommonMessages.class);

	protected State currentState;
	public YellowFadeHandler yellowFadeHandler = new YellowFadeHandler();

	public BasePane(final State stateInfo)
		{
			this.currentState = stateInfo;

			this.initializeStatusBar();

			this.basePaneLayout.setSpacing(0);

			this.basePaneLayout.setWidth("100%");
			this.basePaneLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

			this.content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			this.content.setSpacing(2);
			this.content.setWidth("100%");

			this.basePaneLayout.add(statusBarpane);
			this.basePaneLayout.add(content);
			this.initWidget(basePaneLayout);
		}

	public Panel getContent()
	{
		return this.content;
	}

	public State getCurrentState()
	{
		return this.currentState;
	}

	public void showLoadingStatusBar()
	{
		this.showStatusBar(messages.loading());
	}

	public void showStatusBar(String action)
	{
		this.statusLabel.setStyleName("square-status-message1");
		this.statusLabel.setText(action);
		this.imageLoader.setVisible(true);
		this.statusLabel.setVisible(true);
	}

	public void showStatusBarWithoutAnimation(String action)
	{
		autoHide();
		this.statusLabel.setStyleName("square-status-message2");
		this.statusLabel.setText(action);
		this.imageLoader.setVisible(false);
		this.statusLabel.setVisible(true);
	}

	private void autoHide()
	{
		Timer t = new Timer()
			{
				boolean first = false;

				public void run()
				{

					if (first)
					{
						statusLabel.setVisible(false);
						imageLoader.setVisible(false);
						return;
					}
					else
					{

						first = true;
					}
				}

			};
		t.scheduleRepeating(3000);

	}

	public void hideStatusBar()
	{
		this.statusLabel.setVisible(false);
		this.imageLoader.setVisible(false);
	}

	public HashMap<String, String> getQueryString()
	{
		HashMap<String, String> params = new HashMap<String, String>();
		String historyToken = History.getToken();
		// skip if there is no question mark
		if (!historyToken.contains("?"))
		{
			return params;
		}

		// ? position
		int questionMarkIndex = historyToken.indexOf("?") + 1;

		String[] arStr = historyToken.substring(questionMarkIndex, historyToken.length()).split("&");

		for (int i = 0; i < arStr.length; i++)
		{
			String[] substr = arStr[i].split("=");
			if (substr.length == 2)
			{
				params.put(substr[0], substr[1]);
			}
		}

		return params;
	}

	/**
	 * This methods cleans the project context when the project area is abandon.
	 * Everytime the admin enters a adminstrative areas as managestie or choose
	 * project. this wil be called.
	 */
	protected void cleanProjectContext()
	{
		this.currentState.setUserProjectRole(ProjectRole.None);
		this.currentState.setProjectName("none");
	}

	protected void initWidget(Widget pane)
	{
		super.initWidget(pane);
	}

	private void initializeStatusBar()
	{
		this.statusBarpane.setSpacing(2);
		this.statusBarpane.setHeight("20px");
		this.statusBarpane.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

		this.statusLabel.setStyleName("square-status-message");
		this.statusLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.imageLoader.setSize("16px", "16px");
		this.statusBarpane.add(imageLoader);
		this.statusBarpane.add(statusLabel);
		this.statusLabel.setVisible(false);
		this.imageLoader.setVisible(false);
	}

	protected void createExportPanel(Panel statusPanel, final Integer projectId)
	{
		final DisclosurePanel exportPanel = new DisclosurePanel();
		exportPanel.setAnimationEnabled(true);
		SquareHyperlink exportLink = new SquareHyperlink(messages.exportRequirements());

		final HorizontalPanel exportOptionsPanel = new HorizontalPanel();
		exportOptionsPanel.setSpacing(5);
		Button exportButton = new Button(messages.export());

		final ListBox exportOptions = new ListBox();

		exportOptions.addItem(""); // index 0
		exportOptions.addItem("XML");
		exportOptions.addItem("CSV");
		
		exportOptions.addItem("HTML");
		exportOptions.addItem("RequisitePro-CSV");

		exportButton.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					int index = exportOptions.getSelectedIndex();

					switch (index)
					{
						case 1 :
						{
							String link = GWT.getModuleBaseURL() + "export?format=XML&projectId=" + projectId + "&sessionId="
									+ currentState.getSessionID();
							Window.open(link, "_blank", "");

							break;
						}
						case 2 :
						{
							String link = GWT.getModuleBaseURL() + "export?format=CSV&projectId=" + projectId + "&sessionId="
									+ currentState.getSessionID();
							Window.open(link, "_blank", "");
							break;
						}
						case 3 :
						{
							String link = GWT.getModuleBaseURL() + "export?format=HTML&projectId=" + projectId + "&sessionId="
									+ currentState.getSessionID();
							Window.open(link, "_blank", "");

							break;
						}
						case 4 :
						{
							String link = GWT.getModuleBaseURL() + "export?format=RequisitePro-CSV&projectId=" + projectId + "&sessionId="
									+ currentState.getSessionID();
							Window.open(link, "_blank", "");

							break;
						}
						default :
					}

				}

			});

		exportOptionsPanel.add(exportOptions);
		exportOptionsPanel.add(exportButton);
		exportPanel.setContent(exportOptionsPanel);
		exportPanel.setOpen(false);
		exportLink.addClickHandler(new ClickHandler()
			{

				public void onClick(ClickEvent event)
				{
					hideStatusBar();
					showDisclosurePanel = !showDisclosurePanel;
					if (showDisclosurePanel)
					{
						exportPanel.setOpen(true);
					}
					else
					{
						exportPanel.setOpen(false);
					}
				}

			});

		statusPanel.add(exportLink);
		statusPanel.add(exportPanel);
	}

	protected String getProjectFocus(GwtProject p)
	{
		String projectFocus = null;
		if (p.isSecurity() && p.isPrivacy())
		{
			projectFocus = messages.securityAndPrivacy();
		}
		else if (p.isSecurity())
		{
			projectFocus = messages.securityOnly();
		}
		else if (p.isPrivacy())
		{
			projectFocus = messages.privacyOnly();
		}
		return projectFocus;
	}

	
}
