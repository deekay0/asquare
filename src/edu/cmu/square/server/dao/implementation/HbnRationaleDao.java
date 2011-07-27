/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.RationaleDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.ProjectPackageRationale;
import edu.cmu.square.server.dao.model.ProjectPackageRationaleId;
import edu.cmu.square.server.dao.model.ProjectPackageTradeoffreason;
import edu.cmu.square.server.dao.model.SoftwarePackage;

/**
 * @author DK
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class HbnRationaleDao extends HbnAbstractDao<ProjectPackageRationale, ProjectPackageRationaleId> implements RationaleDao 
{
	private void unsetRationale(int projectID)
	{
		String query = "Update ProjectPackageRationale r set r.rationale='' where r.project.id=:projectId";
		Query q = getSession().createQuery(query);
		q.setParameter("projectId", projectID);
		q.executeUpdate();
	}
	
	public ProjectPackageRationaleId getIdForProject(Project project)
	{
		String query = "Select s.id from ProjectPackageRationale s where s.project.id=:projectId)";
		
		Query q = getSession().createQuery(query);
		
		q.setParameter("projectId", project.getId());
		
		List<ProjectPackageRationaleId> list = q.list(); 
		
		if(!list.isEmpty())
		{
			System.out.println("The id is: "+list.get(0));
			return list.get(0);
		}
		else 
		{
			System.out.println("No final selection has been made yet");
			return null;
		}
	
	}
	
	@Override
	public void setRationale(ProjectPackageRationale rationale)
	{
		this.deleteById(new ProjectPackageRationaleId(rationale.getProject().getId(), rationale.getSoftwarePackage().getId()));
		String query = "I ProjectPackageRationale r set r.rationale=:rationale where r.project.id=:projectId and r.softwarePackage.id=:packageId";
		Query q = getSession().createQuery(query);
		q.setParameter("projectId", rationale.getProject().getId());
		q.setParameter("packageId", rationale.getSoftwarePackage().getId());
		q.setParameter("rationale", rationale.getRationale());
		q.executeUpdate();
	}

	@Override
	public ProjectPackageRationale getRationale(Project projectID)
	{
		String query = "Select s from ProjectPackageRationale s where s.project.id=:projectId)";
		
		Query q = getSession().createQuery(query);
		
		q.setParameter("projectId", projectID.getId());
		
		List<ProjectPackageRationale> list = q.list(); 
		if(!list.isEmpty())
			return list.get(0);
		else return null;
		
	}

	@Override
	public void updateRationale(int projectID, int packageId, String tradeoffreason)
	{
		System.out.println("update here...."+projectID+"   "+packageId+"  "+tradeoffreason);
			
		String query = "Update ProjectPackageTradeoffreason r set r.tradeoffreason=:tradeoffreason where r.project.id=:projectId and r.softwarePackage.id=:packageId";
		Query q = getSession().createQuery(query);
		q.setParameter("projectId", projectID);
		q.setParameter("packageId", packageId);
		q.setParameter("tradeoffreason", tradeoffreason);
		q.executeUpdate();
	}

	
}
