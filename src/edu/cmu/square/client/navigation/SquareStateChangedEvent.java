package edu.cmu.square.client.navigation;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SquareStateChangedEvent extends GwtEvent<SquareStateChangedEvent.IMyHandler>
{

	public SquareStateChangedEvent(String propertyName, Object propertyValue)
		{
			key = propertyName;
			value = propertyValue;

		}
	// marker for handler logic in registration method
	public interface IMyHandler extends EventHandler
	{
		void onLoad(SquareStateChangedEvent event);
	}

	// marker on calling
	public interface ITakesMyEvent extends EventHandler
	{
		void onSquareStateChanged(SquareStateChangedEvent event);
	}

	private static final GwtEvent.Type<SquareStateChangedEvent.IMyHandler> TYPE = new GwtEvent.Type<SquareStateChangedEvent.IMyHandler>();

	public static GwtEvent.Type<SquareStateChangedEvent.IMyHandler> getType()
	{
		return TYPE;
	}

	private Object value;
	private String key;

	public GwtEvent.Type<SquareStateChangedEvent.IMyHandler> getAssociatedType()
	{
		return TYPE;
	}

	protected void dispatch(SquareStateChangedEvent.IMyHandler handler)
	{
		handler.onLoad(this);

	}

	public Object getValue()
	{
		return value;
	}

	public String getKey()
	{
		return key;
	}

}