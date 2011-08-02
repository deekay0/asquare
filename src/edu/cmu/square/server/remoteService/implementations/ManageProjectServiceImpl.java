/**
 * 
 */
package edu.cmu.square.server.remoteService.implementations;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtInspectionTechnique;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRole;
import edu.cmu.square.client.model.GwtStep;
import edu.cmu.square.client.model.GwtTechnique;
import edu.cmu.square.client.model.GwtTerm;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectService;
import edu.cmu.square.server.business.interfaces.ManageProjectBusiness;
import edu.cmu.square.server.dao.model.Role;

/**
 * @author kaalpurush
 * 
 */
@Service
public class ManageProjectServiceImpl extends SquareRemoteServiceServlet implements ManageProjectService
{

	@Resource
	private ManageProjectBusiness mpb;
	
	private static final long serialVersionUID = 4328727584625839784L;


	
	public GwtProject createProject(GwtProject newProject) throws SquareException
	{
		
		List<GwtTerm> terms=null;
		InputStream defaultValueStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("defaultProjectValues.xml");
		
		Document projectDefaulValuesXMLDocument =getProjectDefaultValuesXMLDocument(defaultValueStream);
		
		
		terms=mpb.findDefaultTerms(projectDefaulValuesXMLDocument);
		System.out.println("termterm............"+terms.size()+"  "+terms.get(0).getDefinition());
		
		return mpb.createProject(newProject, terms);
	
	}
	
	public GwtProject copyProject(GwtProject originalProject) throws SquareException
	{
		
		InputStream defaultValueStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("defaultProjectValues.xml");
		Document projectDefaulValuesXMLDocument = getProjectDefaultValuesXMLDocument(defaultValueStream);
				
		return mpb.copyProject(originalProject);
	
	}



	public void deleteProject(int projectId) throws SquareException
	{
		 mpb.deleteProject(projectId);
		
	}
	

	public List<GwtProject> getAllProjects() throws SquareException
	{
		return mpb.getAllProjects();
	}
	public void addUserToProject(GwtProject gwtProject, GwtUser gwtUser, GwtRole gwtRole) throws SquareException
	{

		mpb.addUserToProject(gwtProject, gwtUser, gwtRole);
	}



	public void editRole(GwtUser user, GwtProject gwtProject) throws SquareException
	{

		mpb.editRole(user, gwtProject);

	}

	public List<GwtRole> getAllRoles() throws SquareException
	{

		List<GwtRole> gwtRoles = new ArrayList<GwtRole>();
		List<Role> roles = mpb.getAllRoles();
		for (Role r : roles)
		{

			GwtRole gwtRole = new GwtRole();
			gwtRole.setId(r.getId());
			gwtRole.setName(r.getName());
			gwtRole.setDescription(r.getDescription());
			gwtRoles.add(gwtRole);
			System.out.println("getAllRoles"+gwtRole.toString());

		}

		return gwtRoles;
	}




	public List<GwtStep> getSteps(GwtProject project) throws SquareException
	{

		List<GwtStep> gwtSteps = new ArrayList<GwtStep>();

		gwtSteps = mpb.getSteps(project);

		return gwtSteps;
	}

	public List<GwtUser> getUserList(GwtProject gwtProject) throws SquareException
	{

		if (gwtProject.getId() == null || gwtProject.getId() <= 0)
		{
			throw new SquareException("Project id is required");
		}

		return mpb.getUserList(gwtProject);
	}


	public void removeUserFromProject(GwtProject project, GwtUser user) throws SquareException
	{

		mpb.removeUserFromProject(project, user);

	}

	public void updateProjectName(GwtProject project) throws SquareException
	{

		mpb.updateProjectName(project);
		storeProjectName(project.getName());

	}

	@Override
	public GwtProject updateProject(int projectId, int leadRequiremntId, String projectName) throws SquareException
	{
		return mpb.updateProject(projectId, leadRequiremntId, projectName);
	
	}

	

	public GwtProject getProject(Integer projectId) throws SquareException
	{
		GwtProject gp = this.mpb.getProject(projectId);
		storeProjectName(gp.getName());
		return mpb.getProject(projectId);
	}

//	public void updateProjectTechnique(Integer projectId, Integer techniqueID, String rationale) throws SquareException
//	{
//		mpb.setTechniqueToProject(projectId, techniqueID, rationale);
//	}
//
//	public void updateProjectInspectionTechnique(Integer projectId, Integer inspectionTechniqueId) throws SquareException
//	{
//		mpb.setInspectionTechniqueToProject(projectId, inspectionTechniqueId);
//	}
//
//
//	public void updateProjectInspectionStatus(Integer projectId, String inspectionTechniqueStatus) throws SquareException
//	{
//		mpb.setInspectionTechniqueStatusToProject(projectId, inspectionTechniqueStatus);
//		
//	}
	public void setValuesForAuthorization()
	{
		setValuesForAuthorization(mpb);

	}

	private Document getProjectDefaultValuesXMLDocument(InputStream defaultValueStream)
	{

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
	
		Document doc=null;
		try
		{	db = dbf.newDocumentBuilder();
		
			doc = db.parse(defaultValueStream);
			return doc;
		
		}
		catch (ParserConfigurationException e)
		{  
			e.printStackTrace();
			return null;
		}
		catch (SAXException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}








	
}
