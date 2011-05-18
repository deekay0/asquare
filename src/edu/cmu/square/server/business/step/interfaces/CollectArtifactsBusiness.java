/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtArtifact;


public interface CollectArtifactsBusiness extends StepBusinessInterface
{
	/**
	 * @param projectId The project that the user selected.
	 * @return List<GwtArtifact>  Return the list of artifacts for the project.
	 * @throws SquareException  This may indicate database errors, and/or business logic errors
	 * 
	 */
	List<GwtArtifact> getArtifactsForProject(int projectId) throws SquareException;
	
	GwtArtifact createArtifact(GwtArtifact newArtifact, int projectId) throws SquareException;
	
	void updateArtifact(GwtArtifact artifactToUpdate) throws SquareException;
	
	void deleteArtifact(int artifactId) throws SquareException;
	
	
	

}
