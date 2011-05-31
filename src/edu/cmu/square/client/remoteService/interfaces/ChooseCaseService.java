package edu.cmu.square.client.remoteService.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtAsquareCase;

@RemoteServiceRelativePath("chooseCase.rpc")
public interface ChooseCaseService extends RemoteService
{

	/**
	 * This would retrieve the list of Cases that this user is a member of. 
	 * @return A list of cases and additional information for the user.
	 */
	List<GwtAsquareCase> getCasesForUser() throws SquareException;
}
