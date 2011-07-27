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

import edu.cmu.square.client.model.GwtPrioritization;

/**
 * UserAhp generated by hbm2java
 */
@Entity
@Table(name = "user_ahp")
public class UserAhp implements java.io.Serializable {

	private static final long serialVersionUID = 8805006384525820776L;
	private UserAhpId id;
	private Requirement requirementByRidB;
	private Requirement requirementByRidA;
	private User user;
	
	private double costA;
	private double costB;
	private double valueA;
	private double valueB;

	public UserAhp() {
		this.id = new UserAhpId();
	}

	public UserAhp(UserAhpId id, Requirement requirementByRidB,
			Requirement requirementByRidA, User user) {
		this.id = id;
		this.requirementByRidB = requirementByRidB;
		this.requirementByRidA = requirementByRidA;
		this.user = user;
	}

	public UserAhp(GwtPrioritization currentPrioritization)
		{
			this.id = new UserAhpId();
			this.setCostA(currentPrioritization.getCostA());
			this.setCostB(currentPrioritization.getCostB());
			
			this.setValueA(currentPrioritization.getValueA());
			this.setValueB(currentPrioritization.getValueB());
			
			this.requirementByRidA = new Requirement(currentPrioritization.getRequirementA());
			this.id.setRidA(requirementByRidA.getId());
			this.requirementByRidB = new Requirement(currentPrioritization.getRequirementB());
			this.id.setRidB(requirementByRidB.getId());
		}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "uid", column = @Column(name = "uid", nullable = false)),
			@AttributeOverride(name = "ridA", column = @Column(name = "ridA", nullable = false)),
			@AttributeOverride(name = "ridB", column = @Column(name = "ridB", nullable = false))})
	public UserAhpId getId() {
		return this.id;
	}

	public void setId(UserAhpId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ridB", nullable = false, insertable = false, updatable = false)
	public Requirement getRequirementByRidB() {
		return this.requirementByRidB;
	}

	public void setRequirementByRidB(Requirement requirementByRidB) {
		this.requirementByRidB = requirementByRidB;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ridA", nullable = false, insertable = false, updatable = false)
	public Requirement getRequirementByRidA() {
		return this.requirementByRidA;
	}

	public void setRequirementByRidA(Requirement requirementByRidA) {
		this.requirementByRidA = requirementByRidA;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
		this.id.setUid(user.getId());
	}

	@Override
	public String toString()
	{
		return "requirementA: " + getRequirementByRidA().getId() + 
		" requirementB: " + getRequirementByRidB().getId();
		
	}

	public GwtPrioritization createGwtPrioritization()
	{
		GwtPrioritization gp = new GwtPrioritization();
		gp.setCostA(this.getCostA());
		gp.setCostB(this.getCostB());
		gp.setValueA(this.getValueA());
		gp.setValueB(this.getValueB());
		gp.setRequirementA(this.requirementByRidA.createGwtRequirement());
		gp.setRequirementB(this.requirementByRidB.createGwtRequirement());
		return gp;
	}
	
	@Column(name = "costA", nullable = false)
	public double getCostA() {
		return this.costA;
	}

	public void setCostA(double costA) {
		this.costA = costA;
	}

	@Column(name = "valueA", nullable = false)
	public double getValueA() {
		return this.valueA;
	}

	public void setValueA(double valueA) {
		this.valueA = valueA;
	}

	
	@Column(name = "costB", nullable = false)
	public double getCostB() {
		return this.costB;
	}

	public void setCostB(double costB) {
		this.costB = costB;
	}

	@Column(name = "valueB", nullable = false)
	public double getValueB() {
		return this.valueB;
	}

	public void setValueB(double valueB) {
		this.valueB = valueB;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof UserAhp)) 
		{
			return false;
		}
		UserAhp other = (UserAhp)obj;
		return id.equals(other.id);
	}

	@Override
	public int hashCode()
	{
		return id.hashCode();
	}
}
