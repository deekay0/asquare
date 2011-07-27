package edu.cmu.square.server.remoteService.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.remoteService.interfaces.ChooseProjectService;
import edu.cmu.square.server.business.interfaces.ChooseProjectBusiness;
@Service
public class ChooseProjectServiceImpl extends SquareRemoteServiceServlet implements ChooseProjectService
{

	private static final long serialVersionUID = 9025261545222201L;
	
	@Resource
	private ChooseProjectBusiness chooseProject;

	@Override
	public void setValuesForAuthorization()
	{
		chooseProject = getLogic(chooseProject, ChooseProjectBusiness.class);
		setValuesForAuthorization(chooseProject);

	}

	@Override
	public List<GwtProject>[] getProjectsForUser(Integer userId, Integer caseId) throws SquareException
	{
		return chooseProject.getProjectsForUser(userId, caseId);
	}

}
