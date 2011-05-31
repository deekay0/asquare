package edu.cmu.square.server.business.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.interfaces.ChooseProjectBusiness;

import edu.cmu.square.server.dao.interfaces.AsquareCaseDao;

import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.model.AsquareCase;
import edu.cmu.square.server.dao.model.Project;
@Service
public class ChooseProjectBusinessImpl extends BaseBusinessImpl implements ChooseProjectBusiness
{

	private static Logger logger = Logger.getLogger(ChooseProjectBusinessImpl.class);
	
	@Resource
	private ProjectDao projectDao;
	
	@Resource
	private AsquareCaseDao caseDao;
	
	
	@Override
	@SuppressWarnings("unchecked")
	@AllowedRoles(roles = {Roles.All})
	public List<GwtProject>[] getProjectsForUser(Integer userId, Integer caseId) throws SquareException
	{
		logger.info("Calling getProjectsForUser");
		
		List<GwtProject>[] result = new List[2];
		//get in progress projects
		List<GwtProject> projectsInProgressGwt = new ArrayList<GwtProject>();
		
		System.out.println("choose project business........... case id ...."+ caseId);
		List<Project> projectsInProgress = projectDao.getIncompleteProjectsForUser(userId, caseId);
		
		
		List<AsquareCase> temptemp = caseDao.getAllCases();
		System.out.println("tttttttttt   "+temptemp.size());
		
		
		
		
		for (Project p: projectsInProgress)
		{
			projectsInProgressGwt.add (p.createGwtProject());
		}
		//get completed projects
		List<GwtProject> projectsCompletedGwt = new ArrayList<GwtProject>();
		
		List<Project> projectsCompleted = projectDao.getCompletedProjectsForUser(userId, caseId);
		
		for (Project p: projectsCompleted)
		{
			projectsCompletedGwt.add (p.createGwtProject());
		}
		result[0] = projectsInProgressGwt;
		result[1] = projectsCompletedGwt;
		return result;
	}

}
