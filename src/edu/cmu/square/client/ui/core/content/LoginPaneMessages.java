package edu.cmu.square.client.ui.core.content;

import com.google.gwt.i18n.client.Messages;
/**
 * This interface binds with the corresponding properties file
 * This is intended to handle the messaging of the site
 * instead of hardcoding text in the UI, we should use this pattern.
 * 
 */
public interface LoginPaneMessages extends Messages {


	String panelTitle();
	String loginButton();
	String userNameLabel();
	String passwordLabel();
	String loginFailed();
	String accountLocked();
	String emptyValidator();
	String passwordIllegalError();
	String duplicateUsernameError();
	String generalLoginError();
	String instructions();
	

}
