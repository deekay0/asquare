/**
 * 
 */
package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.model.GwtArtifact;


/**
 * @author kaalpurush
 *
 */
public interface CollectArtifactsServiceAsync 
{
	
	void getAllArtifacts(int projectId,AsyncCallback<List<GwtArtifact>> callback);
	
	/**
	 * Creates an artifact
	 * @param newArtifact
	 * @param projectId
	 * @return a GwtArtifact
	 * @
	 */
	void createArtifact(GwtArtifact newArtifact, int projectId, AsyncCallback<GwtArtifact> callback) ;
	
	/**
	 * Updates an artifact's name, description, revision, link
	 * @param artifactToUpdate
	 * @
	 */
	void updateArtifact(GwtArtifact artifactToUpdate, AsyncCallback<Void> callback) ;
	
	/**
	 * Delete an artifact from the project and database.
	 * @param artifactId
	 * @
	 */
	void deleteArtifact(int artifactId, AsyncCallback<Void> callback) ;
}
