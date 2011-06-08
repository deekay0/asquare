package edu.cmu.square.server.business.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtAsquareCase;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.interfaces.ChooseCaseBusiness;

public class ChooseCaseBusinessImplTest extends AbstractSpringBase
{
	@Resource
	private ChooseCaseBusiness chooseBusiness;
	
	@Test
	public void getAsquareCases()
	{
		try
		{
			List<GwtAsquareCase> cases = chooseBusiness.getAsquareCases();
			assertTrue(cases.size()==3);
		}
		catch (SquareException e)
		{
			e.printStackTrace();
			fail("Error " + e);
		}
		 
	}

}
