package edu.cmu.square.server.business.step.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtProjectPackageAttributeRating;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtRating;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.step.interfaces.ReviewPackagesBusiness;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.ProjectPackageAttributeRatingDao;
import edu.cmu.square.server.dao.interfaces.QualityAttributeDao;
import edu.cmu.square.server.dao.interfaces.SoftwarePackageDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageAttributeRating;
import edu.cmu.square.server.dao.model.ProjectPackageAttributeRatingId;
import edu.cmu.square.server.dao.model.QualityAttribute;
import edu.cmu.square.server.dao.model.SoftwarePackage;

@Service
@Scope("prototype")
public class ReviewPackagesBusinessImpl extends BaseBusinessImpl implements ReviewPackagesBusiness
{
	@Resource
	private SoftwarePackageDao softwarePackageDao;
	@Resource
	private ProjectDao projectDao;
	@Resource
	private QualityAttributeDao qualityAttributeDao;
	@Resource
	private ProjectPackageAttributeRatingDao projectPackageAttributeRatingDao;

	
//	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer,Roles.Administrator})
//	public void addQualityAttribute(GwtProject gwtProject, GwtQualityAttribute gwtEvaluation) throws SquareException
//	{
//		ProjectPackageAttributeRating ppar = new ProjectPackageAttributeRating();
//		ppar.setProject(new Project(gwtProject));
//		ppar.set
//		Project currentProject = new Project(gwtProject);
//		QualityAttribute evaluationToAdd = new QualityAttribute(gwtEvaluation);
//		//TODO: see if this works
////		evaluationToAdd.setProject(currentProject);
//
//		// get the evaluation by name and check for duplicates
//		List<QualityAttribute> evaluations = this.qualityAttributeDao.getQualityAttributesByNameAndProject(evaluationToAdd.getName(), gwtProject.getId());
//		if (!evaluations.isEmpty())
//		{
//			SquareException se = new SquareException("Already exists");
//			se.setType(ExceptionType.duplicateName);
//			throw se;
//		}
//		this.qualityAttributeDao.create(evaluationToAdd);
//		gwtEvaluation.setId(evaluationToAdd.getId());
//
//	}
	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer,Roles.Administrator})
	public void addQualityAttribute(GwtProject gwtProject, GwtQualityAttribute gwtQualityAttribute) throws SquareException
	{
		Project currentProject = new Project(gwtProject);
		QualityAttribute qualityAttribute = new QualityAttribute(gwtQualityAttribute);

		// get the technique by name and check for duplicates
		List<QualityAttribute> qas = this.qualityAttributeDao.getQualityAttributesByNameAndProject(qualityAttribute.getName(), gwtProject.getId());
		if (!qas.isEmpty())
		{
			SquareException se = new SquareException("Already exists");
			se.setType(ExceptionType.duplicateName);
			throw se;
		}
		
		this.qualityAttributeDao.create(qualityAttribute);
		gwtQualityAttribute.setId(qualityAttribute.getId());
		
		List<SoftwarePackage> SPs = softwarePackageDao.getSoftwarePackagesByProject(currentProject);
		
		if(SPs.isEmpty())
		{
			System.out.println("We're here");
			SoftwarePackage sp = new SoftwarePackage();
			sp.setName("Unnamed");
			sp.setDescription("No description");
			sp.setId(0);
			
			this.softwarePackageDao.create(sp);
			this.projectPackageAttributeRatingDao.create(new ProjectPackageAttributeRating(new ProjectPackageAttributeRatingId(currentProject.getId(), sp.getId(), qualityAttribute.getId()), sp, currentProject, qualityAttribute, 0));
			System.out.println("Done");
		}
		else
		{
			for(SoftwarePackage sp : SPs)
			{
				this.projectPackageAttributeRatingDao.create(new ProjectPackageAttributeRating(new ProjectPackageAttributeRatingId(currentProject.getId(), sp.getId(), qualityAttribute.getId()), sp, currentProject, qualityAttribute, 0));
			}
		}
	}

	
//	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer,Roles.Administrator})
//	public void addSoftwarePackage(GwtProject gwtProject, GwtSoftwarePackage gwtElicitationTechnique) throws SquareException
//	{
//		Project currentProject = new Project(gwtProject);
//		SoftwarePackage techniqueToAdd = new SoftwarePackage(gwtElicitationTechnique);
//
//		System.out.println("before");
//		// get the technique by name and check for duplicates
//		List<SoftwarePackage> techniques = this.softwarePackageDao.getSoftwarePackagesByNameAndProject(techniqueToAdd.getName(), gwtProject.getId());
//		System.out.println("after");
//		if (!techniques.isEmpty())
//		{
//			SquareException se = new SquareException("Already exists");
//			se.setType(ExceptionType.duplicateName);
//			throw se;
//		}
//
//		
//		this.softwarePackageDao.addSoftwarePackageToProject(currentProject, techniqueToAdd);
//		gwtElicitationTechnique.setId(techniqueToAdd.getId());
//	}

	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer,Roles.Administrator})
	public void addSoftwarePackage(GwtProject gwtProject, GwtSoftwarePackage gwtSoftwarePackage) throws SquareException
	{
		Project currentProject = new Project(gwtProject);
		SoftwarePackage softwarePackage = new SoftwarePackage(gwtSoftwarePackage);

		// get the technique by name and check for duplicates
		List<SoftwarePackage> techniques = this.softwarePackageDao.getSoftwarePackagesByNameAndProject(softwarePackage.getName(), gwtProject.getId());
		if (!techniques.isEmpty())
		{
			SquareException se = new SquareException("Already exists");
			se.setType(ExceptionType.duplicateName);
			throw se;
		}
		
		this.softwarePackageDao.create(softwarePackage);
		gwtSoftwarePackage.setId(softwarePackage.getId());
		
		List<QualityAttribute> QAs = qualityAttributeDao.getQualityAttributesByProject(currentProject);
		 
		

		if(QAs.isEmpty())
		{
			System.out.println("We're here");
			QualityAttribute qa = new QualityAttribute();
			qa.setName("Unnamed");
			qa.setDescription("No description");
			qa.setId(0);
			
			this.qualityAttributeDao.create(qa);
			this.projectPackageAttributeRatingDao.create(new ProjectPackageAttributeRating(new ProjectPackageAttributeRatingId(currentProject.getId(), gwtSoftwarePackage.getId(), qa.getId()), softwarePackage, currentProject, qa, 0));
			System.out.println("Done");
		}
		else
		{
			for(QualityAttribute qa : QAs)
			{
				this.projectPackageAttributeRatingDao.create(new ProjectPackageAttributeRating(new ProjectPackageAttributeRatingId(currentProject.getId(), gwtSoftwarePackage.getId(), qa.getId()), softwarePackage, currentProject, qa, 0));
			}
		}
	}
	
	@AllowedRoles(roles = {Roles.All})
	public List<GwtQualityAttribute> getQualityAttributes(GwtProject gwtProject, StepStatus stepStatus) throws SquareException
	{
		List<GwtQualityAttribute> evaluationList = new ArrayList<GwtQualityAttribute>();
		Project project = new Project(gwtProject);
		List<QualityAttribute> evaluations = qualityAttributeDao.getQualityAttributesByProject(project);

		if (evaluations == null)
		{
			return evaluationList;
		}
		
		for (QualityAttribute t : evaluations)
		{
			evaluationList.add(t.createGwtQualityAttribute());
		}

		return evaluationList;
	}

	@AllowedRoles(roles = {Roles.All})
	public List<GwtSoftwarePackage> getSoftwarePackages(GwtProject gwtProject, StepStatus stepStatus) throws SquareException
	{
		List<GwtSoftwarePackage> softwarePackageList = new ArrayList<GwtSoftwarePackage>();
		Project project = new Project(gwtProject);
		List<SoftwarePackage> packages = softwarePackageDao.getSoftwarePackagesByProject(project);

		if (packages == null)
		{
			return softwarePackageList;
		}
		
		for (SoftwarePackage t : packages)
		{
			softwarePackageList.add(t.createGwtSoftwarePackage());
		}

		return softwarePackageList;
	}


	
//	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer})
//	public void removeSoftwarePackage(GwtSoftwarePackage gwtElicitTechnique, GwtProject project) throws SquareException
//	{
//
//		Project p = projectDao.fetch(project.getId());
//		if (p.getSecurityTechnique() != null && p.getSecurityTechnique().getId().equals(gwtElicitTechnique.getId()))
//		{
//			SquareException se = new SquareException();
//			se.setType(ExceptionType.constraintViolated);
//			throw se;
//		}
//		
//
////		if (p.getTechniques().size() == 1)
////		{
////			SquareException se = new SquareException();
////			se.setType(ExceptionType.onlyOneTechnique);
////			throw se;
////		}
////		
//
//		try
//		{
//			projectPackageAttributeRatingDao.get
//			softwarePackageDao.deleteEntity(gwtElicitTechnique.getId());
//		}
//		catch (Throwable t)
//		{
//			throw new SquareException(t);
//		}
//	}

	
	@AllowedRoles(roles = {Roles.Security_Specialist})
	public void removeSoftwarePackage(GwtSoftwarePackage gwtSoftwarePackage, GwtProject project) throws SquareException
	{

		
		List<GwtProjectPackageAttributeRating> pparlist = projectPackageAttributeRatingDao.getAllRatingsForProject(new Project(project));
		for(GwtProjectPackageAttributeRating it : pparlist)
			if(it.getPackage().getId() == gwtSoftwarePackage.getId())
				projectPackageAttributeRatingDao.deleteEntity(new ProjectPackageAttributeRating(it));
		
		int noOfSPs = 0;
		//Make sure to take the package out of the SP DB if necessary
		pparlist = projectPackageAttributeRatingDao.getAllRatings(new Project(project));
		for(GwtProjectPackageAttributeRating it : pparlist)
			if(it.getPackage().getId() == gwtSoftwarePackage.getId())
				++noOfSPs;
		
		if(noOfSPs == 0)
			softwarePackageDao.deleteEntity(softwarePackageDao.fetch(gwtSoftwarePackage.getId())); 

	}
	
	@AllowedRoles(roles = {Roles.Security_Specialist})
	public void removeQualityAttribute(GwtQualityAttribute gwtQualityAttribute, GwtProject project) throws SquareException
	{
		List<GwtProjectPackageAttributeRating> pparlist = projectPackageAttributeRatingDao.getAllRatingsForProject(new Project(project));
		for(GwtProjectPackageAttributeRating it : pparlist)
			if(it.getAttribute().getId() == gwtQualityAttribute.getId())
				projectPackageAttributeRatingDao.deleteEntity(new ProjectPackageAttributeRating(it));
		
		int noOfSPs = 0;
		//Make sure to take the package out of the SP DB if necessary
		pparlist = projectPackageAttributeRatingDao.getAllRatings(new Project(project));
		for(GwtProjectPackageAttributeRating it : pparlist)
			if(it.getPackage().getId() == gwtQualityAttribute.getId())
				++noOfSPs;
		
		if(noOfSPs == 0)
		{
			qualityAttributeDao.deleteEntity(qualityAttributeDao.fetch(gwtQualityAttribute.getId())); 
			
			SoftwarePackage sp = softwarePackageDao.fetch(1);
			QualityAttribute qa = new QualityAttribute();
			
			qa.setName("Unnamed");
			qa.setDescription("No description");
			qa.setId(0);
			qualityAttributeDao.create(qa);
			
			ProjectPackageAttributeRating ppar = new ProjectPackageAttributeRating(new ProjectPackageAttributeRatingId(project.getId(), sp.getId(), qa.getId()), sp, new Project(project), qa, 0);
			projectPackageAttributeRatingDao.create(ppar);
		}

	}

	
	@AllowedRoles(roles = {Roles.Security_Specialist})
	public void updateQualityAttribute(GwtQualityAttribute gwtEvaluation, GwtProject gwtProject) throws SquareException
	{
		if (gwtEvaluation == null)
		{
			throw new SquareException("gwtEvaluation should not be null.");
		}

		if (gwtEvaluation.getId() <= 0)
		{
			throw new SquareException("gwtEvaluation must have an id.");
		}

		//try
		{

//			// get the evaluation by name and check for duplicates
//			List<QualityAttribute> evaluations = this.qualityAttributeDao.getQualityAttributesByNameAndProject(gwtEvaluation.getName(), gwtProject.getId());
//			if (evaluations.size() > 0)
//			{
//				if (!evaluations.get(0).getId().equals(gwtEvaluation.getId()))
//				{
//					SquareException se = new SquareException("Already exists");
//					se.setType(ExceptionType.duplicateName);
//					throw se;
//				}
//			}
			QualityAttribute currentEvaluation = new QualityAttribute(gwtEvaluation);
			//TODO: see if this works
//			currentEvaluation.setProject(new Project(gwtProject));
			qualityAttributeDao.update(currentEvaluation);
		}
	}

	
	@AllowedRoles(roles = {Roles.Security_Specialist})
	public void updateSoftwarePackage(GwtSoftwarePackage gwtElicitationTechnique, GwtProject gwtProject) throws SquareException
	{
		if (gwtElicitationTechnique == null)
		{
			throw new SquareException("should not be null.");
		}

		if (gwtElicitationTechnique.getId() <= 0)
		{
			throw new SquareException("must have an id.");
		}

//		try
//		{
//			// get the technique by name and check for duplicates
//			List<SoftwarePackage> techniques = this.softwarePackageDao.getSoftwarePackagesByNameAndProject(gwtElicitationTechnique.getName(), gwtProject.getId());
//			if (techniques.size() > 0)
//			{
//				if (!techniques.get(0).getId().equals(gwtElicitationTechnique.getId()))
//				{
//					SquareException se = new SquareException("Already exists");
//					se.setType(ExceptionType.duplicateName);
//					throw se;
//				}
//			}

			SoftwarePackage currentTechnique = new SoftwarePackage(gwtElicitationTechnique);
			
			//TODO:...
//			currentTechnique.setProject(new Project(gwtProject));
			softwarePackageDao.update(currentTechnique);
//		}
//		catch (SquareException ex)
//		{
//			throw ex;
//		}
//		catch (Throwable t)
//		{
//			throw new SquareException("updating technique failed", t);
//		}

	}

	@AllowedRoles(roles = {Roles.All})
	public List<GwtRating> getRateValues(int projectID) throws SquareException
	{
		Project project = new Project();
		project.setId(projectID);

//		List<GwtRating> list = qualityAttributeDao.getAllRatings(project);
//
//		for (ProjectPackageAttributeRating item : hbnList)
//		{
//			GwtRating gwtItem = new GwtRating();
//			gwtItem.setAttributeId(item.getId().getEid());
//			gwtItem.setPackageId(item.getId().getTid());
//			gwtItem.setValue(item.getValue());
//
//			list.add(gwtItem);
//		}

		return qualityAttributeDao.getAllRatings(project);
	}

	
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer, Roles.Requirements_Engineer})
	public void setRateValue(int projectID, int techniqueID, int evaluationCriteriaID, int value) throws SquareException
	{
		
		
		if( qualityAttributeDao.getRating(projectID, techniqueID, evaluationCriteriaID) == -1)
			qualityAttributeDao.setRating(projectID, techniqueID, evaluationCriteriaID, value);
		else
		{
			qualityAttributeDao.updateRating(projectID, techniqueID, evaluationCriteriaID, value);
		}

	}

	@AllowedRoles(roles = {Roles.All})
	public int getRateValue(int projectID, int packageId, int attributeId) throws SquareException
	{
//TODO
//		int result = -1;
//
//		TechniqueTechniqueEvaluationId id = new TechniqueTechniqueEvaluationId();
//		id.setTid(techniqueID);
//		id.setEid(evaluationCriteriaID);
//		id.setPid(projectID);
//
//		ProjectPackageAttributeRating tecEva = qualityAttributeDao.fetch(id);
//
//		if (tecEva == null)
//		{
//			result = -1;
//
//		}
//		else
//		{
//			result = tecEva.getValue();
//		}

		return qualityAttributeDao.getRating(projectID, packageId, attributeId);

	}
	
	public void loadDefaultTechniques(int projectId, List<GwtSoftwarePackage> techniques) throws SquareException
	{
		for (GwtSoftwarePackage technique : techniques)
		{
			this.addSoftwarePackage(new GwtProject(projectId), technique);
		}

	}
	
	public void loadDefaultEvaluations(int projectId, List<GwtQualityAttribute> evaluations) throws SquareException
	{
		for (GwtQualityAttribute evaluation : evaluations)
		{
			this.addQualityAttribute(new GwtProject(projectId), evaluation);
		}
	}


	@Override
	public StepEnum getStepDescription() throws SquareException
	{
		return StepEnum.Select_Elicitation_Technique;
	}


	@Override
	public GwtStepVerficationResult verifyStep(Project project) throws SquareException
	{
		GwtStepVerficationResult result = new GwtStepVerficationResult();

		

		

		if (project.isSecurity())
		{
			if (project.getSecurityTechnique() == null)
			{
				result.getMessages().add("The security requirements elicitation technique has not been selected!");
				result.setHasWarning(true);

			}
		}
		return result;
	}
	
}
