/**
 * 
 */
package edu.cmu.square.client.remoteService.step.interfaces;


import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtBusinessGoal;
import edu.cmu.square.client.model.GwtSubGoal;

          
public interface IdentifyGoalsAssetsServiceAsync
{
	 
		void loadBusinessGoalInfo(int projectId, AsyncCallback<GwtBusinessGoal> result);
		
		void setBusinessGoalInfo(int projectId, GwtBusinessGoal gwtBusinessGoal, AsyncCallback<Void> result);
	
		void addSubGoal(int projectId, GwtSubGoal gwtSubGoal,AsyncCallback<Integer> asyncCallback);
		
		void removeSubGoal(int projectId, GwtSubGoal gwtSubGoal ,AsyncCallback<Void> result);
		
		void updateSubGoal(int projectId, GwtSubGoal gwtSubGoal,AsyncCallback<Void> result);
		
		void addAsset(int projectId, GwtAsset gwtAsset,AsyncCallback<Integer> asyncCallback);
		
		void removeAsset(int projectId, GwtAsset gwtAsset,AsyncCallback<Void> result);
		
		void updateAsset(int projectId, GwtAsset gwtAsset,AsyncCallback<Void> result);
		
		void associateSubGoalAndAsset(int projectId, GwtSubGoal gwtSubGoal, GwtAsset gwtAsset,AsyncCallback<Void> result);
		
		void removeAssociationGoalAsset(int projectId, GwtSubGoal gwtSubGoal, GwtAsset gwtAsset, AsyncCallback<Void> result)throws  Exception;
		
}
