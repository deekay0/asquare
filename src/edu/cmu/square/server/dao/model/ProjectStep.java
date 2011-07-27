package edu.cmu.square.server.dao.model;

// Generated May 17, 2009 5:07:01 PM by Hibernate Tools 3.2.4.GA

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ProjectStep generated by hbm2java
 */
@Entity
@Table(name = "project_step", uniqueConstraints = @UniqueConstraint(columnNames = {
		"stepId", "projectId" }))
public class ProjectStep implements java.io.Serializable {

	private static final long serialVersionUID = -629686553194531930L;
	private ProjectStepId id;
	private Step step;
	private Project project;
	private String status;
	
	public ProjectStep() {
	}

	public ProjectStep(ProjectStepId id, Step step, Project project) {
		this.id = id;
		this.step = step;
		this.project = project;                              
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "stepId", column = @Column(name = "stepId", nullable = false)),
			@AttributeOverride(name = "projectId", column = @Column(name = "projectId", nullable = false))
		})
	public ProjectStepId getId() {
		return this.id;
	}

	public void setId(ProjectStepId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stepId", nullable = false, insertable = false, updatable = false)
	public Step getStep() {
		return this.step;
	}
	
	public void setStep(Step step) {
		this.step = step;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectId", nullable = false, insertable = false, updatable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "status", nullable = false)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
