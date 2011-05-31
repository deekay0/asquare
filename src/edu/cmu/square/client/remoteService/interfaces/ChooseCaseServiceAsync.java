package edu.cmu.square.client.remoteService.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.model.GwtAsquareCase;


public interface ChooseCaseServiceAsync
{
	/**
	 * This would retrieve the list of Cases that this user is a member of. 
	 * @return A list of cases and additional information for the user.
	 */
	void getCasesForUser(AsyncCallback<List<GwtAsquareCase>> callback);
}
