package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtRating;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.remoteService.step.interfaces.ReviewPackagesService;
import edu.cmu.square.server.business.step.interfaces.ReviewPackagesBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

@Service
public class ReviewPackagesServiceImpl extends SquareRemoteServiceServlet implements ReviewPackagesService
{

	private static final long serialVersionUID = -5725843620206249786L;

	@Resource
	private ReviewPackagesBusiness reviewPackagesBusiness;
	
	public GwtQualityAttribute addQualityAttribute(GwtProject gwtProject, GwtQualityAttribute gwtElicitQualityAttribute) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			this.reviewPackagesBusiness.addQualityAttribute(gwtProject, gwtElicitQualityAttribute);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
		
		return gwtElicitQualityAttribute;
	}

	
	public GwtSoftwarePackage addSoftwarePackage(GwtProject gwtProject, GwtSoftwarePackage gwtElicititationSoftwarePackage) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();

			this.reviewPackagesBusiness.addSoftwarePackage(gwtProject, gwtElicititationSoftwarePackage);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
		
		return gwtElicititationSoftwarePackage;
	}

	
	public List<GwtQualityAttribute> getQualityAttributes(GwtProject gwtProject, StepStatus stepStatus) throws SquareException
	{
		List<GwtQualityAttribute> evaluationList = null;
		
		try
		{
			this.setValuesForAuthorization();
			evaluationList = reviewPackagesBusiness.getQualityAttributes(gwtProject, stepStatus);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}

		return evaluationList;
	}

	
	public List<GwtSoftwarePackage> getSoftwarePackages(GwtProject gwtProject, StepStatus stepStatus) throws SquareException
	{
		List<GwtSoftwarePackage> techniques = null;
		
		try
		{
			this.setValuesForAuthorization();
			techniques = reviewPackagesBusiness.getSoftwarePackages(gwtProject, stepStatus);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}

		return techniques;
	}

	
	public void removeQualityAttribute(GwtQualityAttribute evaluation) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			this.reviewPackagesBusiness.removeQualityAttribute(evaluation);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
	}

	
	public void removeSoftwarePackage(GwtSoftwarePackage technique, Integer projectId) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			this.reviewPackagesBusiness.removeSoftwarePackage(technique, projectId);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
	}

	
	public void updateQualityAttribute(GwtQualityAttribute elicitation, GwtProject gwtProject) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			this.reviewPackagesBusiness.updateQualityAttribute(elicitation, gwtProject);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
	}

	
	public void updateSoftwarePackage(GwtSoftwarePackage gwtElicitationSoftwarePackage, GwtProject gwtProject) throws SquareException
	{
		try
		{
			this.setValuesForAuthorization();
			reviewPackagesBusiness.updateSoftwarePackage(gwtElicitationSoftwarePackage, gwtProject);
		}
		catch (SquareException ex)
		{
			throw ex;
		}
		catch (Throwable t)
		{
			throw new SquareException(t.getMessage());
		}
	}

	
	public List<GwtRating> getRateValues(int projectID) throws SquareException
	{

		return reviewPackagesBusiness.getRateValues(projectID);
	}

	
	public void setRateValue(int projectID, int techniqueID, int evaluationID, int value) throws SquareException
	{
		reviewPackagesBusiness.setRateValue(projectID, techniqueID, evaluationID, value);

	}

	
	public void setValuesForAuthorization()
	{
		setValuesForAuthorization(reviewPackagesBusiness);
	}

}
