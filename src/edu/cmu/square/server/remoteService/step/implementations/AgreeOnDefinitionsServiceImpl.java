/**
 * 
 */
package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtTerm;
import edu.cmu.square.client.remoteService.step.interfaces.AgreeOnDefinitionsService;
import edu.cmu.square.server.business.step.interfaces.AgreeOnDefinitionsBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

/**
 * @author yirul
 * 
 */
@Service
public class AgreeOnDefinitionsServiceImpl extends SquareRemoteServiceServlet implements AgreeOnDefinitionsService
{	
	
	@Resource
	private AgreeOnDefinitionsBusiness agreeOnDefinitionsBusiness;

	private static final long serialVersionUID = -2353832112072071414L;


	public void setValuesForAuthorization()
	{
		setValuesForAuthorization(agreeOnDefinitionsBusiness);
		
	}


	public GwtTerm addTerm(GwtProject gwtProject, GwtTerm gwtTerm) throws SquareException
	{
	 return	agreeOnDefinitionsBusiness.addTerm(gwtProject, gwtTerm);
	}


	public List<GwtTerm> getTerms(GwtProject gwtProject) throws SquareException
	{
		
		return	agreeOnDefinitionsBusiness.getTerms(gwtProject);
	}


	public void removeTerm(GwtTerm gwtTerm) throws SquareException
	{
			agreeOnDefinitionsBusiness.removeTerm(gwtTerm);
		
	}


	public void updateTerm(GwtProject gwtProject, GwtTerm gwtTerm) throws SquareException
	{
		agreeOnDefinitionsBusiness.updateTerm(gwtProject, gwtTerm);
		
	}


}
