package edu.cmu.square.client.model;

import java.io.Serializable;

public class GwtProjectPackageAttributeRating implements Serializable {

	
	private static final long serialVersionUID = -6144620742917868842L;
	private GwtProject project;
	private GwtSoftwarePackage softwarePackage;
	private GwtQualityAttribute qualityAttribute;
	private Integer value = -1;
	
	public GwtProject getProject()
	{
		return project;
	}
	public void setProject(GwtProject projectId)
	{
		this.project = projectId;
	}
	public GwtSoftwarePackage getPackage()
	{
		return softwarePackage;
	}
	public void setPackage(GwtSoftwarePackage packageId)
	{
		this.softwarePackage = packageId;
	}
	public GwtQualityAttribute getAttribute()
	{
		return qualityAttribute;
	}
	public void setAttribute(GwtQualityAttribute requirementId)
	{
		this.qualityAttribute = requirementId;
	}
	public Integer getValue()
	{
		return value;
	}
	public void setValue(Integer value)
	{
		this.value = value;
	}
	
	
}
