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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.InspectionStatus;

/**
 * Project generated by hbm2java
 */
@Entity
@Table(name = "project")
public class Project implements java.io.Serializable
{
	private static final long serialVersionUID = 6547930377955826706L;

	private Set<User> users = new HashSet<User>();
	private Set<Role> roles = new HashSet<Role>();
	private Integer id;
	private Technique securityTechnique;
	private InspectionTechnique inspectionTechnique;
	private String inspectionStatus;
	private User leadRequirementsEngineer;
	private Role currentRole;
	private Technique privacyTechnique;
	private String name;
	private boolean lite;
	private boolean privacy;
	private boolean security;
	private String privacyTechniqueRationale;
	private String securityTechniqueRationale;
	private Date dateCreated;
	private Date dateModified;
	private AsquareCase cases;
	private Set<Term> terms = new HashSet<Term>(0);
	private Set<Technique> techniques = new HashSet<Technique>(0);
	private Set<InspectionTechnique> inspectionTechniques = new HashSet<InspectionTechnique>(0);
	private Set<EvaluationCriteria> techniqueEvaluations = new HashSet<EvaluationCriteria>(0);
	private Set<Risk> risks = new HashSet<Risk>(0);
	private Set<Step> steps = new HashSet<Step>(0);
	private Set<Artifact> artifacts = new HashSet<Artifact>(0);
	private Set<Goal> goals = new HashSet<Goal>(0);
	private Set<Asset> assets = new HashSet<Asset>(0);

	private Set<Category> categories = new HashSet<Category>(0);
	private Set<Requirement> requirements = new HashSet<Requirement>(0);

	public Project()
		{

		}
	public Project(GwtProject project)
		{
			this.id = project.getId();
			this.name = project.getName();
			this.lite = project.isLite();
			this.security = project.isSecurity();
			this.privacy = project.isPrivacy();
			
			this.cases = new AsquareCase(project.getCases());
			
			if (project.getCurrentRole() != null)
			{
				this.currentRole = new Role(project.getCurrentRole());
			}
			if (project.getLeadRequirementEngineer() != null)
			{
				this.leadRequirementsEngineer = new User(project.getLeadRequirementEngineer(), "");
			}		
		}
	

	public Project(int projectId)
		{
			this.id = projectId;
		}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId()
	{
		return this.id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stid", nullable = true)
	public Technique getSecurityTechnique()
	{
		return this.securityTechnique;
	}

	public void setSecurityTechnique(Technique techniqueByStid)
	{
		this.securityTechnique = techniqueByStid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspectionId", nullable = true)
	public InspectionTechnique getInspectionTechnique()
	{
		return this.inspectionTechnique;
	}

	public void setInspectionTechnique(InspectionTechnique inspectionTechnique)
	{
		this.inspectionTechnique = inspectionTechnique;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_requirements_engineer", nullable = false)
	public User getLeadRequirementEngineer()
	{
		return this.leadRequirementsEngineer;
	}
	public void setLeadRequirementEngineer(User user)
	{
		this.leadRequirementsEngineer = user;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cases", nullable = false)
	public AsquareCase getCases()
	{
		return this.cases;
	}

	public void setCases(AsquareCase cases)
	{
		this.cases = cases;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ptid", nullable = true)
	public Technique getPrivacyTechnique()
	{
		return this.privacyTechnique;
	}

	public void setPrivacyTechnique(Technique privacyTechnique)
	{
		this.privacyTechnique = privacyTechnique;
	}

	@Column(name = "name", nullable = false, length = 250)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "lite", nullable = false)
	public boolean isLite()
	{
		return this.lite;
	}

	public void setLite(boolean lite)
	{
		this.lite = lite;
	}

	@Column(name = "privacy", nullable = false)
	public boolean isPrivacy()
	{
		return this.privacy;
	}

	public void setPrivacy(boolean privacy)
	{
		this.privacy = privacy;
	}

	@Column(name = "security", nullable = false)
	public boolean isSecurity()
	{
		return this.security;
	}

	public void setSecurity(boolean security)
	{
		this.security = security;
	}

	@Column(name = "privacyTechniqueRationale", nullable = true, length = 65535)
	public String getPrivacyTechniqueRationale()
	{
		return this.privacyTechniqueRationale;
	}

	public void setPrivacyTechniqueRationale(String privacyTechniqueRationale)
	{
		this.privacyTechniqueRationale = privacyTechniqueRationale;
	}

	@Column(name = "securityTechniqueRationale", nullable = true, length = 65535)
	public String getSecurityTechniqueRationale()
	{
		return this.securityTechniqueRationale;
	}

	public void setSecurityTechniqueRationale(String securityTechniqueRationale)
	{
		this.securityTechniqueRationale = securityTechniqueRationale;
	}

	public void setInspectionStatus(String inspectionStatusId)
	{
		this.inspectionStatus = inspectionStatusId;
	}

	@Column(name = "inspectionStatus", nullable = true)
	public String getInspectionStatus()
	{
		return inspectionStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = true, length = 19)
	public Date getDateCreated()
	{
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", nullable = false, length = 19)
	public Date getDateModified()
	{
		return this.dateModified;
	}

	public void setDateModified(Date dateModified)
	{
		this.dateModified = dateModified;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Term> getTerms()
	{
		return this.terms;
	}

	public void setTerms(Set<Term> terms)
	{
		this.terms = terms;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<EvaluationCriteria> getTechniqueEvaluations()
	{
		return this.techniqueEvaluations;
	}

	public void setTechniqueEvaluations(Set<EvaluationCriteria> techniqueEvaluations)
	{
		this.techniqueEvaluations = techniqueEvaluations;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Risk> getRisks()
	{
		return this.risks;
	}

	public void setRisks(Set<Risk> risks)
	{
		this.risks = risks;
	}

	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	// public Set<ProjectStep> getProjectSteps()
	// {
	// return this.projectSteps;
	// }
	//
	// public void setProjectSteps(Set<ProjectStep> projectSteps)
	// {
	// this.projectSteps = projectSteps;
	// }

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Artifact> getArtifacts()
	{
		return this.artifacts;
	}

	public void setArtifacts(Set<Artifact> artifacts)
	{
		this.artifacts = artifacts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Goal> getGoals()
	{
		return this.goals;
	}

	public void setGoals(Set<Goal> goals)
	{
		this.goals = goals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Asset> getAssets()
	{
		return this.assets;
	}

	public void setAssets(Set<Asset> assets)
	{
		this.assets = assets;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Category> getCategories()
	{
		return this.categories;
	}

	public void setcategories(Set<Category> categories)
	{
		this.categories = categories;
	}

	@ManyToMany
	@JoinTable(name = "user_project_role", joinColumns = {@JoinColumn(name = "project_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
	public Set<User> getUsers()
	{
		return users;
	}

	public void setUsers(Set<User> users)
	{
		this.users = users;
	}

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "projects", targetEntity = Role.class)
	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Technique> getTechniques()
	{
		return this.techniques;
	}

	public void setTechniques(Set<Technique> techniques)
	{
		this.techniques = techniques;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<InspectionTechnique> getInspectionTechniques()
	{
		return this.inspectionTechniques;
	}

	public void setInspectionTechniques(Set<InspectionTechnique> value)
	{
		this.inspectionTechniques = value;
	}

	@ManyToMany
	@JoinTable(name = "project_step", joinColumns = {@JoinColumn(name = "projectId")}, inverseJoinColumns = {@JoinColumn(name = "stepId")})
	public Set<Step> getSteps()
	{
		return steps;
	}

	public void setSteps(Set<Step> steps)
	{
		this.steps = steps;
	}

	/**
	 * @param requirements
	 *            the requirements to set
	 */
	public void setRequirements(Set<Requirement> requirements)
	{
		this.requirements = requirements;
	}

	/**
	 * @return the requirements
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Requirement> getRequirements()
	{
		return requirements;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof Project))
		{
			return false;
		}
		Project other = (Project) obj;
		if (this.id == null || this.id == 0)
		{
			return false;
		}
		return this.id.equals(other.id);
	}

	public int hashCode()
	{
		if (this.id == null)
		{
			return super.hashCode();
		}
		return this.id.hashCode();
	}

	public Role currentRole()
	{
		return currentRole;
	}

	public void setCurrentRole(Role currentRole)
	{
		this.currentRole = currentRole;
	}
	
	public GwtProject createGwtProject()
	{
		GwtProject gwtProject = new GwtProject();
		gwtProject.setId(this.id);
		gwtProject.setLite(this.lite);
		gwtProject.setName(this.name);
		gwtProject.setPrivacy(this.privacy);
		gwtProject.setSecurity(this.security);
		
		
		if (this.leadRequirementsEngineer != null)
		{
			gwtProject.setLeadRequirementEngineer(this.leadRequirementsEngineer.createGwtUser());
		}
		gwtProject.setCases(this.cases.createGwtCase());
		
		if (this.currentRole != null)
		{
			gwtProject.setCurrentRole(this.currentRole.createGwtRole());
		}
		
		
		
		//Elicitation Technique
		if (this.getSecurityTechnique() != null)
		{
			gwtProject.setSecurityTechniqueID(this.getSecurityTechnique().getId());
		}
		gwtProject.setSecurityRational(this.getSecurityTechniqueRationale());
		
		//Inspection Technique
		if (this.getInspectionTechnique() != null)
		{
			gwtProject.setInspectionTechniqueID(this.getInspectionTechnique().createGwtInspectionTechnique());
		}
		
		if (this.inspectionStatus != null)
		{
			for (InspectionStatus i: InspectionStatus.values())
			{
				if (i.getLabel().equals(this.inspectionStatus))
				{
					gwtProject.setInspectionStatus(i);		
				}
			}
			
		}
		
		if (this.steps != null)
		{
			for (Step s: steps)
			{
				gwtProject.getSteps().add(s.createGwtStep(this.id));
			}
		}
		return gwtProject;
	}


}
