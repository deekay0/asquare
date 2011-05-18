package edu.cmu.square.server.dao.interfaces;

import java.util.List;

import edu.cmu.square.server.dao.model.InspectionTechnique;
import edu.cmu.square.server.dao.model.Project;


public interface InspectionTechniqueDao extends AbstractDao<InspectionTechnique, Integer>
{
	List<InspectionTechnique> getInspectionTechniquesByProject(Project testProject);

	int hasTechnique(String name, Project project);

	InspectionTechnique getInspectionTechniquesByNameAndProject(String title, int projectId);
	
}
