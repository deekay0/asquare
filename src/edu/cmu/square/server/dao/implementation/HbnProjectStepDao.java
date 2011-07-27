package edu.cmu.square.server.dao.implementation;

import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.ProjectStepDao;
import edu.cmu.square.server.dao.model.ProjectStep;
import edu.cmu.square.server.dao.model.ProjectStepId;
@Repository
public class HbnProjectStepDao extends
		HbnAbstractDao<ProjectStep, ProjectStepId> implements ProjectStepDao {
	

}
