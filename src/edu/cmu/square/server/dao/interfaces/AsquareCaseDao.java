package edu.cmu.square.server.dao.interfaces;

import java.util.List;
import edu.cmu.square.server.dao.model.AsquareCase;

/**
 * @author Nan
 *
 */


public interface AsquareCaseDao extends AbstractDao<AsquareCase, Integer>
{
	List<AsquareCase> getAllCases();

}
