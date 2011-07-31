package edu.cmu.square.client.ui.core.content;

import com.google.gwt.i18n.client.Messages;
/**
 * This interface binds with the corresponding properties file
 * This is intended to handle the messaging of the site
 * instead of hardcoding text in the UI, we should use this pattern.
 * 
 */
public interface HeaderPaneMessages extends Messages
{

	String panelTitle1();
	String panelTitle2();
	String headerLogo();
	String linkTitle1();
	String linkTitle2();
	String linkTitle3();
	String adminLinkText();
	String delimiter();
	
}
