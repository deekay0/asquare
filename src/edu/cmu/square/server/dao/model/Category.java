package edu.cmu.square.server.dao.model;

// Generated May 17, 2009 5:07:01 PM by Hibernate Tools 3.2.4.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.cmu.square.client.model.GwtCategory;

/**
 * Category generated by hbm2java
 */
@Entity
@Table(name = "category")
public class Category implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1399590737257584691L;
	private Integer id;
	private String label;
	private Project project;

	private Date dateCreated;
	private Date dateModified;
	private Set<Requirement> requirements = new HashSet<Requirement>(
			0);
//	private Set<Project> projects = new HashSet<Project>(0);

	public Category() {
	}
	
	public Category(GwtCategory gwtCategory)
	{
		this.id = gwtCategory.getId();
		this.label = gwtCategory.getCategoryName();
	}

	

	

	public Category(Integer id)
		{
			this.id = id;
		}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "label", nullable = false)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 19)
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", nullable = false, length = 19)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public GwtCategory createGwtCategory()
	{
		GwtCategory newCategory = new GwtCategory();
		newCategory.setCategoryName(label);
		newCategory.setId(this.id!=null?this.id:0);

		return newCategory;
	}

	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirements(Set<Requirement> requirements)
	{
		this.requirements = requirements;
	}

	/**
	 * @return the requirements
	 */
	@ManyToMany(
	        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
	        mappedBy = "categories",
	        targetEntity = Requirement.class
	    )
	public Set<Requirement> getRequirements()
	{
		return requirements;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Category)) {
			return super.equals(obj);
		}
		
		Category other = (Category)obj;
		if(this.id==null || other.id == null) {
			return false;
		}
		return this.id.equals(other.id);
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	public int hashCode()
	{
		return this.id.hashCode();
	}

	
	public String toString()
	{
		String toString = "Label: " + this.label;
		return toString;
	}

}
