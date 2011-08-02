/**
 * 
 */
package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Asset;
import edu.cmu.square.server.dao.model.Project;
/**
 * This interface lists out the data access operations on the User table
 *
 */

public interface AssetDao extends AbstractDao<Asset, Integer> {

	 List<Asset> getAssetByProject(Project project);
	 


}
