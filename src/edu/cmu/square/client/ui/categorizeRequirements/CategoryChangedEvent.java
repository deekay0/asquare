package edu.cmu.square.client.ui.categorizeRequirements;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtRequirement;

public class CategoryChangedEvent extends GwtEvent<CategoryChangedEvent.IMyHandler>
{
	private int categoryId;
	
	public  enum Action {assign, remove, create};
	
	private Action command;
	
	private GwtCategory newCategory;
	
	private List<GwtRequirement> requirementsToProcess = new ArrayList<GwtRequirement>();

	public CategoryChangedEvent(int categoryId)
		{
			this.categoryId = categoryId;

		}
	public CategoryChangedEvent(List<GwtRequirement> requirements,int categoryId, Action command)
	{
		this.categoryId = categoryId;
		this.command = command;
		this.requirementsToProcess = requirements;
	}
	public CategoryChangedEvent(GwtCategory newCategory,List<GwtRequirement> requirements)
	{
		this.setNewCategory(newCategory);
		this.command =Action.create;
		this.requirementsToProcess = requirements;
	}
	
	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}

	public int getCategoryId()
	{
		return categoryId;
	}

	// marker for handler logic in registration method
	public interface IMyHandler extends EventHandler
	{
		void onLoad(CategoryChangedEvent event);
	}
	// marker on calling
	public interface ITakesMyEvent extends EventHandler
	{
		void onCategoryChanged(CategoryChangedEvent event);
	}

	private static final GwtEvent.Type<CategoryChangedEvent.IMyHandler> TYPE = new GwtEvent.Type<CategoryChangedEvent.IMyHandler>();

	public static GwtEvent.Type<CategoryChangedEvent.IMyHandler> getType()
	{
		return TYPE;
	}

	public GwtEvent.Type<CategoryChangedEvent.IMyHandler> getAssociatedType()
	{
		return TYPE;
	}

	protected void dispatch(CategoryChangedEvent.IMyHandler handler)
	{
		handler.onLoad(this);

	}

	public void setRequirementsToProcess(List<GwtRequirement> requirementsToProcess)
	{
		this.requirementsToProcess = requirementsToProcess;
	}

	public List<GwtRequirement> getRequirementsToProcess()
	{
		return requirementsToProcess;
	}

	public void setCommand(Action command)
	{
		this.command = command;
	}

	public Action getCommand()
	{
		return command;
	}
	public void setNewCategory(GwtCategory newCategory)
	{
		this.newCategory = newCategory;
	}
	public GwtCategory getNewCategory()
	{
		return newCategory;
	}

	
}