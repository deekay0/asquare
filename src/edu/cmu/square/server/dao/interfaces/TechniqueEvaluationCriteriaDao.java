package edu.cmu.square.server.dao.interfaces;


import java.util.List;

import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.TechniqueEvaluationCriteria;
import edu.cmu.square.server.dao.model.TechniqueTechniqueEvaluationId;

public interface  TechniqueEvaluationCriteriaDao extends AbstractDao<TechniqueEvaluationCriteria, TechniqueTechniqueEvaluationId>{

	public List<TechniqueEvaluationCriteria> getAllValues(Project project) ;
}
