/**
 * 
 */
package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtArtifact;
import edu.cmu.square.client.remoteService.step.interfaces.CollectArtifactsService;
import edu.cmu.square.server.business.step.interfaces.CollectArtifactsBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

/**
 * @author kaalpurush
 * 
 */
@Service
public class CollectArtifactsServiceImpl extends SquareRemoteServiceServlet implements
		CollectArtifactsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -717209333881321918L;
	
	@Resource
	private CollectArtifactsBusiness businessLogic;


	/**
	 * Returns a list of
	 * 
	 * @param project
	 * @return
	 */

	public List<GwtArtifact> getAllArtifacts(int projectId) throws SquareException {
			return businessLogic.getArtifactsForProject(projectId);
	}

	public void setValuesForAuthorization() 
	{
		setValuesForAuthorization(businessLogic);
	}

	@Override
	public GwtArtifact createArtifact(GwtArtifact newArtifact, int projectId) throws SquareException
	{
		businessLogic.createArtifact(newArtifact, projectId);
		return newArtifact;
	}

	@Override
	public void deleteArtifact(int artifactId) throws SquareException
	{

		businessLogic.deleteArtifact(artifactId);
		
	}

	@Override
	public void updateArtifact(GwtArtifact artifactToUpdate) throws SquareException
	{
		businessLogic.updateArtifact(artifactToUpdate);
		
	}
	
}
