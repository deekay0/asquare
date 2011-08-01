package edu.cmu.square.client.ui.editUserProfile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.ManageSiteService;
import edu.cmu.square.client.remoteService.interfaces.ManageSiteServiceAsync;
import edu.cmu.square.client.ui.chooseProject.HomePilot;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.utils.SquareUtil;

public class EditUserProfilePane extends BasePane
{
	private ManageSiteServiceAsync manageSiteService = GWT.create(ManageSiteService.class);

	private final EditUserProfilePaneMessages messages = (EditUserProfilePaneMessages) GWT.create(EditUserProfilePaneMessages.class);

	private GwtUser currentUser;

	private FlexTable profileTable = new FlexTable();
	private FlexTable passwordTable = new FlexTable();

	private final TextBox fullNameTextBox = new TextBox();
	private final TextBox emailTextBox = new TextBox();
	private final TextBox phoneNumberTextBox = new TextBox();
	private final TextBox organizationTextBox = new TextBox();
	private final TextBox departmentTextBox = new TextBox();
	private final TextBox locationTextBox = new TextBox();
	private final PasswordTextBox newPasswordTextBox = new PasswordTextBox();
	private final PasswordTextBox confirmPasswordTextBox = new PasswordTextBox();
	private final CheckBox skipTeachStepCheckBox = new CheckBox();

	private VerticalPanel vPaneTable = new VerticalPanel();
	private VerticalPanel vPanePasswordWarning = new VerticalPanel();
	private VerticalPanel vPaneSaveWarniing = new VerticalPanel();

	private final int MAX_NAME_LENGTH = 48;
	private final int MAX_EMAIL_LENGTH = 48;
	private final int MAX_PHONE_LENGTH = 25;
	private final int MAX_ORGNAME_LENGTH = 48;
	private final int MAX_DEPTNAME_LENGTH = 48;
	private final int MAX_LOCATION_LENGTH = 48;
	private final int MAX_PASSWORD_LENGTH = 25;
	private final int MAX_ROWCHECK_NUM = 3;

	public EditUserProfilePane(State stateInfo)
	{
		super(stateInfo);
		ServiceDefTarget endpoint = (ServiceDefTarget) manageSiteService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageSite.rpc");
		this.showLoadingStatusBar();
		this.loadUserProfile(this.getCurrentState().getUserId());
	}
	
	
	private void PaneInitialization()
	{
		this.hideStatusBar();

		this.vPaneTable.setStyleName("square-edit-user-pane");

		// this.profileTable.setBorderWidth(1);
		this.profileTable.setSize("360px", "250px");
		this.passwordTable.setSize("485px", "150px");
		Button saveButton;
		Button cancelButton;
		saveButton = new Button(messages.buttonSave());
		cancelButton = new Button(messages.buttonCancel());
		saveButton.setPixelSize(100, 50);
		cancelButton.setPixelSize(100, 50);

		this.vPanePasswordWarning.setStyleName("square-error");
		this.vPaneSaveWarniing.setStyleName("square-error");

		HorizontalPanel buttons = new HorizontalPanel();
		buttons.setSpacing(10);
		buttons.add(saveButton);
		buttons.add(cancelButton);
		passwordTable.setWidget(4, 0, buttons);

		FlexCellFormatter formatter = profileTable.getFlexCellFormatter();
		FlexCellFormatter formatter2 = passwordTable.getFlexCellFormatter();
		formatter2.setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_CENTER);
		formatter2.setColSpan(4, 0, 2);

		formatter.setColSpan(0, 0, 2);
		profileTable.setWidget(0, 0, this.vPaneSaveWarniing);
		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		passwordTable.setWidget(3, 1, this.vPanePasswordWarning);
		formatter2.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_LEFT);

		this.fullNameTextBox.setMaxLength(MAX_NAME_LENGTH);
		this.emailTextBox.setMaxLength(MAX_EMAIL_LENGTH);
		this.phoneNumberTextBox.setMaxLength(MAX_PHONE_LENGTH);
		this.organizationTextBox.setMaxLength(MAX_ORGNAME_LENGTH);
		this.departmentTextBox.setMaxLength(MAX_DEPTNAME_LENGTH);
		this.locationTextBox.setMaxLength(MAX_LOCATION_LENGTH);
		this.newPasswordTextBox.setMaxLength(MAX_PASSWORD_LENGTH);
		this.confirmPasswordTextBox.setMaxLength(MAX_PASSWORD_LENGTH);

		this.newPasswordTextBox.addFocusHandler(new FocusHandler()
			{
				public void onFocus(FocusEvent event)
				{
					vPanePasswordWarning.clear();
				}

			});

		this.confirmPasswordTextBox.addFocusHandler(new FocusHandler()
			{
				public void onFocus(FocusEvent event)
				{
					vPanePasswordWarning.clear();
				}

			});

		// add handler
		saveButton.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					boolean savePassword = false;
					boolean saveProfile = false;

					// Check password
					vPaneSaveWarniing.clear();
					vPanePasswordWarning.clear();

					if (!newPasswordTextBox.getText().equals(confirmPasswordTextBox.getText()))
					{
						showWarnings(messages.notMatchPassword());
					}
					else
					{
						savePassword = true;
					}

					saveProfile = validateRequiredFields();

					// Save to DB
					if (savePassword && saveProfile)
					{
						updateUserProfile(getEditUserValues(), newPasswordTextBox.getText().trim());
					}
					else
					{
						showStatusBarWithoutAnimation(messages.notSaved());
					}

				}
			});

		cancelButton.addClickHandler(new ClickHandler()
			{

				public void onClick(ClickEvent event)
				{
					showStatusBarWithoutAnimation(messages.notSaved());
					History.newItem(HomePilot.generateNavigationId(HomePilot.PageId.home));
				}
			});

		renderUserProfileTable();
		this.vPaneTable.add(profileTable);
		this.vPaneTable.add(passwordTable);
		this.getContent().add(vPaneTable);

	}

	private void setValidationStyle(Widget w, String textValue)
	{
		if (textValue.length() == 0)
		{
			w.setStyleName("square-RequiredField");
		}
		else
		{
			w.setStyleName("square-long-textBox");
		}

	}
	private boolean validateRequiredFields()
	{
		for (int j = 1; j < MAX_ROWCHECK_NUM; j++)
		{
			Widget w = profileTable.getWidget(j, 1);
			String textValue = "";
			if (w instanceof TextBox)
			{
				TextBox field = (TextBox) w;
				textValue = field.getText().trim();
				setValidationStyle(field, textValue);
			}

			if (textValue.length() == 0)
			{
				showWarnings(messages.requireField());
				return false;
			}

			if (w.equals(emailTextBox) && !textValue.matches(SquareUtil.getEmailRegex()))
			{
				w.setStyleName("square-RequiredField");
				showWarnings(messages.emailCheck());
				return false;
			}
		}
		return true;
	}

	private void showWarnings(String saveStatus)
	{
		DisclosurePanel disclosure = new DisclosurePanel();
		Label invalidLabel = new Label(saveStatus);

		if (saveStatus.equals(messages.requireField()))
		{
			disclosure.add(invalidLabel);
			this.vPaneSaveWarniing.add(disclosure);
		}
		else if (saveStatus.equals(messages.emailCheck()))
		{
			this.emailTextBox.setStyleName("square-RequiredField");
			disclosure.add(invalidLabel);
			this.vPaneSaveWarniing.add(disclosure);
		}
		else if (saveStatus.equals(messages.duplicateEmail()))
		{
			this.emailTextBox.setStyleName("square-RequiredField");
			disclosure.add(invalidLabel);
			this.vPaneSaveWarniing.add(disclosure);
		}
		else
		{
			disclosure.add(invalidLabel);
			this.vPanePasswordWarning.add(disclosure);
		}
		disclosure.setAnimationEnabled(true);
		disclosure.setOpen(true);

	}

	private void renderUserProfileTable()
	{
		this.fullNameTextBox.setWidth("300px");
		this.emailTextBox.setWidth("300px");
		this.phoneNumberTextBox.setWidth("300px");
		this.organizationTextBox.setWidth("300px");
		this.departmentTextBox.setWidth("300px");
		this.locationTextBox.setWidth("300px");
		this.newPasswordTextBox.setWidth("300px");
		this.confirmPasswordTextBox.setWidth("300px");
		

		profileTable.setCellSpacing(2);
		profileTable.setCellPadding(5);
		passwordTable.setCellSpacing(2);
		passwordTable.setCellPadding(5);
		FlexCellFormatter formatter = profileTable.getFlexCellFormatter();
		FlexCellFormatter formatter2 = passwordTable.getFlexCellFormatter();

		formatter.setWidth(1, 0, "300px");
		formatter.setWidth(1, 1, "300px");
		
		profileTable.setWidget(1, 0, new Label(messages.editNameLabel()));
		profileTable.setWidget(1, 1, this.fullNameTextBox);
		formatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT);

		profileTable.setWidget(2, 0, new Label(messages.editEmailLabel()));
		profileTable.setWidget(2, 1, this.emailTextBox);
		formatter.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_LEFT);

		profileTable.setWidget(3, 0, new Label(messages.editPhoneLabel()));
		profileTable.setWidget(3, 1, this.phoneNumberTextBox);
		formatter.setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_LEFT);

		profileTable.setWidget(4, 0, new Label(messages.editCompanyLabel()));
		profileTable.setWidget(4, 1, this.organizationTextBox);
		formatter.setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_LEFT);

		profileTable.setWidget(5, 0, new Label(messages.editDepartmentLabel()));
		profileTable.setWidget(5, 1, this.departmentTextBox);
		formatter.setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_LEFT);

		profileTable.setWidget(6, 0, new Label(messages.editLocationLabel()));
		profileTable.setWidget(6, 1, this.locationTextBox);
		formatter.setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_LEFT);

		passwordTable.setWidget(0, 1, new Label(messages.passwordInstructions()));
		passwordTable.setWidget(1, 0, new Label(messages.newPasswordLabel()));
		passwordTable.setWidget(1, 1, this.newPasswordTextBox);
		formatter2.setWidth(1, 0, "300px");
		formatter2.setWidth(1, 1, "300px");
		formatter2.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter2.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT);

		passwordTable.setWidget(2, 0, new Label(messages.confirmPasswordLabel()));
		passwordTable.setWidget(2, 1, this.confirmPasswordTextBox);
		formatter2.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter2.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_LEFT);
		
		passwordTable.setWidget(3, 0, new Label(messages.editSkipTeachStep()));
		passwordTable.setWidget(3, 1, this.skipTeachStepCheckBox);
		formatter2.setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter2.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_LEFT);

	}

	private GwtUser getEditUserValues()
	{
		GwtUser gwtUser = new GwtUser();
		gwtUser.setUserId(this.getCurrentState().getUserId());
		gwtUser.setUserName(this.currentUser.getUserName());
		gwtUser.setIsAdmin(this.currentUser.getIsAdmin());
		gwtUser.setIsLocked(this.currentUser.isLocked());
		gwtUser.setFullName(this.fullNameTextBox.getText().trim());
		gwtUser.setEmailAddress(this.emailTextBox.getText().trim());
		gwtUser.setPhone(this.phoneNumberTextBox.getText().trim());
		gwtUser.setOrganization(this.organizationTextBox.getText().trim());
		gwtUser.setDepartment(this.departmentTextBox.getText().trim());
		gwtUser.setLocation(this.locationTextBox.getText().trim());
		gwtUser.setSkipTeachStep(this.skipTeachStepCheckBox.getValue());

		return gwtUser;
	}

	private void setFormUserValues(GwtUser editUser)
	{
		this.fullNameTextBox.setText(editUser.getFullName());
		this.emailTextBox.setText(editUser.getEmailAddress());
		this.phoneNumberTextBox.setText(editUser.getPhone());
		this.organizationTextBox.setText(editUser.getOrganization());
		this.departmentTextBox.setText(editUser.getDepartment());
		this.locationTextBox.setText(editUser.getLocation());
		this.skipTeachStepCheckBox.setValue(editUser.isSkipTeachStep());
	}

	private void loadUserProfile(int userID)
	{
		manageSiteService.getUserInfo(userID, new AsyncCallback<GwtUser>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.loadSessionTimeOut());
				}

				public void onSuccess(GwtUser result)
				{
					currentUser = result;
					PaneInitialization();
					setFormUserValues(result);
				}

			});
	}

	private void updateUserProfile(final GwtUser gwtUser, String password)
	{
		manageSiteService.updateUserProfile(gwtUser, password, new AsyncCallback<Void>()
		{
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.updateSessionTimeOut());
			}

			public void onSuccess(Void result)
			{
				currentState.setSkipTeachStep(gwtUser.isSkipTeachStep());//Updates the state variable of teach step.
				showStatusBarWithoutAnimation(messages.profileSaved());
			}
		});
	}

}
