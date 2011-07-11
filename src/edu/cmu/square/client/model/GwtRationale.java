package edu.cmu.square.client.model;

import java.io.Serializable;

public class GwtRationale implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private GwtProject project = null;
	private GwtSoftwarePackage spackage = null;
	private String tradeoffreason = "";
	
	
	public GwtProject getProject()
	{
		return project;
	}

	public void setProject(GwtProject project)
	{
		this.project = project;
	}
	public GwtSoftwarePackage getPackage()
	{
		return spackage;
	}
	public void setPackage(GwtSoftwarePackage spackage)
	{
		this.spackage = spackage;
	}
	public String getRationale()
	{
		return tradeoffreason;
	}
	public void setRationale(String tradeoffreason)
	{
		this.tradeoffreason = tradeoffreason;
	}
}
