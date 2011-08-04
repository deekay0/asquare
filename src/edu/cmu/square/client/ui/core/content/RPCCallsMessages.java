package edu.cmu.square.client.ui.core.content;

import com.google.gwt.i18n.client.Messages;
/**
 * This interface binds with the corresponding properties file
 * This is intended to handle the messaging of the site
 * instead of hardcoding text in the UI, we should use this pattern.
 * 
 */
public interface RPCCallsMessages extends Messages {


	String errorAction(String actionName);
	String errorAuthorization(String actionName);
	String errorGeneral(String actionName);
	String errorDupiliatedName(String actionName);


}
