package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageTradeoffreason;

/**
 * @author Nan
 *
 */
public interface TradeoffReasonDao extends AbstractDao<ProjectPackageTradeoffreason, Integer> 
{	
	
	List<GwtTradeoffReason> getAllTradeoffReasons(Project project);
	void setTradeoffReason(int projectID, int packageId, String tradeoffreason);
	String getTradeoffReason(int projectID, int packageId);
	void updateTradeoffReason(int projectID, int packageId, String tradeoffreason);
	void setPriority(int projectID, int packageId,int priority);
	Integer getPriority(int projectID, int packageId);
	List<ProjectPackageTradeoffreason> getAllTradeoffReasonsNoGwt(Project project);
}
