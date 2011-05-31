package edu.cmu.square.server.business.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtAsquareCase;

public interface ChooseCaseBusiness extends BaseBusinessInterface
{
	List<GwtAsquareCase> getAsquareCases() throws SquareException;

}
