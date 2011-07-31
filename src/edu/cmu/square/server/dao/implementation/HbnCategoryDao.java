/**
 * 
 */
package edu.cmu.square.server.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.cmu.square.server.dao.interfaces.CategoryDao;
import edu.cmu.square.server.dao.model.Category;
import edu.cmu.square.server.dao.model.Project;

/**
 * @author yirul
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class HbnCategoryDao extends HbnAbstractDao<Category, Integer> implements CategoryDao
{

	/* (non-Javadoc)
	 * @see edu.cmu.square.server.dao.interfaces.CategoryDao#addCategoryToProject(edu.cmu.square.server.dao.model.Project, edu.cmu.square.server.dao.model.Category)
	 */
	
	public void addCategoryToProject(Project project, Category category)
	{
		category.setProject(project);
		super.create(category);

	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.server.dao.interfaces.CategoryDao#getCategoriesByNameAndProject(java.lang.String, java.lang.Integer)
	 */
	
	public List<Category> getCategoriesByNameAndProject(String label, Integer projectID)
	{
		Session session = getSession();
		String query = "Select c from Category c where c.label=:categoryLabel and c.project.id=:projectId";
		Query q = session.createQuery(query);
		q.setParameter("categoryLabel", label);
		q.setParameter("projectId", projectID);
		
		return q.list();
	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.server.dao.interfaces.CategoryDao#getCategoriesByProject(edu.cmu.square.server.dao.model.Project)
	 */
	
	public List<Category> getCategoriesByProject(Project currentProject)
	{
		Session session = getSession();
		String query = "Select p.categories from Project p where p=:myProject";
		Query q = session.createQuery(query);
		q.setParameter("myProject", currentProject);
		return q.list();
	}

	
	public List<Object[]> getCategoriesWithRequirementCount(Integer projectId)
	{
		Session session = getSession();
		String query = "Select cat as category, count(req.id) as count " +
				" from Project p inner join p.requirements req " +
				"inner join req.categories cat where p.id=:projectId group by req";
		Query q = session.createQuery(query);
		q.setParameter("projectId", projectId);
		List lst = q.list();
		return (List<Object[]>)(lst);
	}

	


}
