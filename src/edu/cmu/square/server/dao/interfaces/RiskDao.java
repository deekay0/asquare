package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Risk;

public interface RiskDao extends AbstractDao<Risk, Integer> {

	public List<Risk> getRisksByProject(Project project);

}
