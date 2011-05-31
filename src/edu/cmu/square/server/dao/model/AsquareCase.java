package edu.cmu.square.server.dao.model;

// Nan

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.cmu.square.client.model.GwtAsquareCase;

/**
 * Nan
 */
@Entity
@Table(name = "asquare_case")
public class AsquareCase implements java.io.Serializable {
	 
	private static final long serialVersionUID = -3551908241213631843L;
	private Integer id;
	private String name;
	private String description;
	
	private Set<Project> projects = new HashSet<Project>();

	public AsquareCase() {
	}


	public AsquareCase(GwtAsquareCase gwtCase) {
		this.id = gwtCase.getId();
		this.name = gwtCase.getName();
		this.description = gwtCase.getDescription();
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

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = false, length = 250)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
	*/
	
	public GwtAsquareCase createGwtCase() {
		GwtAsquareCase gwtCase = new GwtAsquareCase();
		gwtCase.setDescription(this.description);
		gwtCase.setName(this.name);
		gwtCase.setId(this.id);
		return gwtCase;
	}
	
	public String toString() {
		return this.name;
	}

	
}




