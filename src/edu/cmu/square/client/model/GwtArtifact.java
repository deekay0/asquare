package edu.cmu.square.client.model;

import java.io.Serializable;

public class GwtArtifact implements Serializable
{
	private static final long serialVersionUID = -7389327860978043496L;
	private int id = -1;
	private String revision;
	private String link;
	private String name;
	private String description;

	public GwtArtifact()
	{

	}

	public GwtArtifact(String name, String description, String revision, String link)
	{
		this.name = name;
		this.description = description;
		this.revision = revision;
		this.link = link;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getRevision()
	{
		return revision;
	}

	public void setRevision(String artifactVersion)
	{
		this.revision = artifactVersion;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	
	public boolean isInDatabase()
	{
		return this.id != -1;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		
		return ((GwtArtifact)obj).id == this.id;
	}

}
