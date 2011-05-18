package edu.cmu.square.server.business.step.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtArtifact;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.step.interfaces.CollectArtifactsBusiness;
import edu.cmu.square.server.dao.interfaces.ArtifactDao;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.model.Artifact;
import edu.cmu.square.server.dao.model.Project;

@Service
@Scope("prototype")
public class CollectArtifactsBusinessImpl extends BaseBusinessImpl implements CollectArtifactsBusiness
{
	@Resource
	private ProjectDao projectDao;

	@Resource
	private ArtifactDao artifactDao;

	@AllowedRoles(roles = {Roles.All})
	public List<GwtArtifact> getArtifactsForProject(int projectId) throws SquareException
	{
		Project project = projectDao.fetch(projectId);

		List<Artifact> artifacts = artifactDao.getArtifactsByProject(project);
		List<GwtArtifact> gwtArtifactsList = new ArrayList<GwtArtifact>();

		for (Artifact a : artifacts)
		{
			gwtArtifactsList.add(a.createGwtArtifact());
		}
		return gwtArtifactsList;
	}

	@AllowedRoles(roles = {Roles.All})
	public GwtArtifact createArtifact(GwtArtifact newArtifact, int projectId) throws SquareException
	{
		try
		{
			if (containsArtifact(projectId, newArtifact))
			{
				SquareException ex = new SquareException();
				ex.setType(ExceptionType.duplicateName);
				throw ex;
			}

			Artifact artifact = new Artifact(newArtifact);
			artifact.setProject(new Project(projectId));
			this.artifactDao.create(artifact);
			newArtifact.setId(artifact.getId());
			return newArtifact;
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable ex)
		{
			SquareException exSquare = new SquareException(ex.getMessage());
			exSquare.setType(ExceptionType.other);
			throw exSquare;
		}
	}

	@AllowedRoles(roles = {Roles.All})
	public void deleteArtifact(int artifactId) throws SquareException
	{
		if (artifactId <=0)
		{
			SquareException se = new SquareException("Artifact id must be greater than 0");
			se.setType(ExceptionType.other);
			throw se;
		}	
		this.artifactDao.deleteById(artifactId);
	}

	@AllowedRoles(roles = {Roles.All})
	public void updateArtifact(GwtArtifact artifactToUpdate) throws SquareException
	{
		try
		{
			int projectId = this.getProjectId(artifactToUpdate);
			if (containsArtifact(projectId, artifactToUpdate))
			{
				SquareException ex = new SquareException();
				ex.setType(ExceptionType.duplicateName);
				throw ex;
			}

			Artifact artifact = new Artifact(artifactToUpdate);
			artifact.setProject(new Project(projectId));
			this.artifactDao.update(artifact);

		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable ex)
		{
			SquareException exSquare = new SquareException(ex.getMessage());
			exSquare.setType(ExceptionType.other);
			throw exSquare;
		}
	}

	private boolean containsArtifact(int projectId, GwtArtifact artifact)
	{
		List<Artifact> artifacts = this.artifactDao.getArtifactsByProject(new Project(projectId));

		if (artifacts.size() > 0)
		{
			for (Artifact a : artifacts)
			{
				if (a.getName().equalsIgnoreCase(artifact.getName()) && artifact.getId() != a.getId().intValue())
				{
					return true;
				}
			}

		}

		return false;
	}

	private int getProjectId(GwtArtifact artifact) 
	{
		Artifact newArtifact = this.artifactDao.fetch(artifact.getId());
		if (newArtifact != null)
		{
			return newArtifact.getProject().getId();
		}

		return -1;
	}

	@Override
	public StepEnum getStepDescription() throws SquareException
	{
		return StepEnum.Collect_Artifacts;
	}

	@Override
	public GwtStepVerficationResult verifyStep(Project project) throws SquareException
	{
		GwtStepVerficationResult result = new GwtStepVerficationResult();

		List<GwtArtifact> artifacts = getArtifactsForProject(project.getId());

		if (artifacts.size() == 0)
		{
			result.getMessages().add("There are no artifacts collected!");
			result.setHasWarning(true);
		}

		return result;
	}

}
