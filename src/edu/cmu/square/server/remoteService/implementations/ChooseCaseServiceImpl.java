package edu.cmu.square.server.remoteService.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtAsquareCase;
import edu.cmu.square.client.remoteService.interfaces.ChooseCaseService;
import edu.cmu.square.server.business.interfaces.ChooseCaseBusiness;
@Service
public class ChooseCaseServiceImpl extends SquareRemoteServiceServlet implements ChooseCaseService
{

	private static final long serialVersionUID = 9025261545222202L;
	
	@Resource
	private ChooseCaseBusiness chooseCase;

	@Override
	public void setValuesForAuthorization()
	{
		chooseCase = getLogic(chooseCase, ChooseCaseBusiness.class);
		setValuesForAuthorization(chooseCase);

	}

	@Override
	public List<GwtAsquareCase> getCasesForUser() throws SquareException
	{
		return chooseCase.getAsquareCases();
	}

}
