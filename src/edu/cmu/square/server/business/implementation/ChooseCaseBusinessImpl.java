package edu.cmu.square.server.business.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtAsquareCase;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.interfaces.ChooseCaseBusiness;
import edu.cmu.square.server.dao.interfaces.AsquareCaseDao;
import edu.cmu.square.server.dao.model.AsquareCase;

@Service
public class ChooseCaseBusinessImpl extends BaseBusinessImpl implements ChooseCaseBusiness
{
	private static Logger logger = Logger.getLogger(ChooseProjectBusinessImpl.class);
	
	@Resource
	private AsquareCaseDao caseDao;

	@Override
	@SuppressWarnings("unchecked")
	@AllowedRoles(roles = {Roles.All})
	public List<GwtAsquareCase> getAsquareCases() throws SquareException
	{
		logger.info("Calling getAsquareCases");
		
		List<GwtAsquareCase> casesGwt = new ArrayList<GwtAsquareCase>();
		
		
		List<AsquareCase> cases = caseDao.getAllCases();
		System.out.println("ttttttttttasdfasdf   "+cases.size());
		
		for (AsquareCase a: cases)
		{
			casesGwt.add (a.createGwtCase());
		}
		
		System.out.println("gwtgwt   "+cases.size());
		
		// TODO Auto-generated method stub
		return casesGwt;
	}

}
