/**
 * 
 */
package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtArtifact;


/**
 * @author kaalpurush
 *
 */
@RemoteServiceRelativePath("collectArtifacts.rpc")
public interface CollectArtifactsService extends RemoteService {
	
	 List<GwtArtifact> getAllArtifacts(int projectId) throws SquareException;
	 
		/**
		 * Creates an artifact
		 * @param newArtifact
		 * @param projectId
		 * @return a GwtArtifact
		 * @throws SquareException
		 */
		GwtArtifact createArtifact(GwtArtifact newArtifact, int projectId) throws SquareException;
		
		/**
		 * Updates an artifact's name, description, revision, link
		 * @param artifactToUpdate
		 * @throws SquareException
		 */
		void updateArtifact(GwtArtifact artifactToUpdate) throws SquareException;
		
		/**
		 * Delete an artifact from the project and database.
		 * @param artifactId
		 * @throws SquareException
		 */
		void deleteArtifact(int artifactId) throws SquareException;
}
