package edu.cmu.square.server.business.step.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.GwtTerm;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.step.interfaces.AgreeOnDefinitionsBusiness;
import edu.cmu.square.server.dao.interfaces.TermDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Term;


@Service
@Scope("prototype")
public class AgreeOnDefinitionsBusinessImpl extends BaseBusinessImpl implements AgreeOnDefinitionsBusiness
{
	@Resource
	private TermDao termDao;

	@AllowedRoles(roles = {Roles.All})
	public GwtTerm addTerm(GwtProject gwtProject, GwtTerm gwtTerm) throws SquareException
	{
		Project project = new Project(gwtProject.getId());
		Term term = new Term(gwtTerm);
		term.setProject(project);

		termDao.create(term);

		return term.createGwtTerm();
	}

	@AllowedRoles(roles = {Roles.All})
	public List<GwtTerm> getTerms(GwtProject gwtProject) throws SquareException
	{
		List<GwtTerm> termList = new ArrayList<GwtTerm>();
		Project project = new Project(gwtProject.getId());
		List<Term> terms = termDao.getTermByProject(project);

		for (Term t : terms)
		{
			termList.add(t.createGwtTerm());
		}

		return termList;

	}

	@AllowedRoles(roles = {Roles.All})
	public void removeTerm(GwtTerm gwtTerm) throws SquareException
	{
		termDao.deleteById(gwtTerm.getId());
	}
	
	@AllowedRoles(roles = {Roles.All})
	public void updateTerm(GwtProject gwtProject, GwtTerm gwtTerm) throws SquareException
	{
		Term term = new Term(gwtTerm);
		term.setProject(new Project(gwtProject.getId()));
		termDao.update(term);
	}
	
	

	@Override
	public void loadDefaultTerms(int projectId, List<GwtTerm> defaultTerms) throws SquareException
	{
		GwtProject project = new GwtProject(projectId);
		for (GwtTerm term : defaultTerms)
		{

			this.addTerm(project, term);	

		}
	}

	@Override
	public StepEnum getStepDescription() throws SquareException
	{
		return StepEnum.Agree_On_Definitions;
	}

	@Override
	public GwtStepVerficationResult verifyStep(Project project) throws SquareException
	{
		GwtStepVerficationResult result = new GwtStepVerficationResult();

		
		List<GwtTerm> terms = getTerms(project.createGwtProject());

		if (terms.size() == 0)
		{
			result.getMessages().add("There are no terms defined yet!");
			result.setHasWarning(true);
		}

		return result;
	}
	
	
	
}
