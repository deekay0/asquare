/**
 * 
 */
package edu.cmu.square.client.remoteService.step.interfaces;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtBusinessGoal;
import edu.cmu.square.client.model.GwtSubGoal;


/**
 * @author mlengarc
 *
 */
@RemoteServiceRelativePath("identifyGoalAssetService.rpc")
public interface IdentifyGoalsAssetsService extends RemoteService {
	
	/**
	 * Allows to Log In to the system, this method will keep track of the failed attempts
	 * and will lock the system after 5 
	 * @param userName 
	 * @param password 
	 * @return GWTLogIn this object contain all the information related to Log In
	 * such as sessionID, whether the account is lock and so on.
	 */
	GwtBusinessGoal loadBusinessGoalInfo(int projectId) throws  Exception;
	
	void setBusinessGoalInfo(int projectId, GwtBusinessGoal gwtBusinessGoal) throws  Exception;
	
	Integer addSubGoal(int projectId, GwtSubGoal gwtSubGoal)throws  Exception;
	
	void removeSubGoal(int projectId, GwtSubGoal gwtSubGoal)throws  Exception;
	
	void updateSubGoal(int projectId, GwtSubGoal gwtSubGoal)throws  Exception;
	
	Integer addAsset(int projectId, GwtAsset gwtAsset)throws  Exception;
	
	void removeAsset(int projectId, GwtAsset gwtAsset)throws  Exception;
	
	void updateAsset(int projectId, GwtAsset gwtAsset)throws  Exception;
	
	void associateSubGoalAndAsset(int projectId, GwtSubGoal gwtSubGoal, GwtAsset gwtAsset)throws  Exception;
	
	void removeAssociationGoalAsset(int projectId, GwtSubGoal gwtSubGoal, GwtAsset gwtAsset)throws  Exception;
	
}
