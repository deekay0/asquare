package edu.cmu.square.client.navigation;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

public class StateHandler
{
	protected HandlerManager handlerManager;

	protected final HandlerRegistration addHandler(GwtEvent.Type<SquareStateChangedEvent.IMyHandler> type,
			final SquareStateChangedEvent.IMyHandler handler)
	{
		return ensureHandlers().addHandler(type, handler);
	}
	protected HandlerManager ensureHandlers()
	{
		return handlerManager == null ? handlerManager = new HandlerManager(this) : handlerManager;
	}

	/**
	 * Register a component for the specified event.
	 */
	public void register(final SquareStateChangedEvent.ITakesMyEvent component)
	{
		addHandler(SquareStateChangedEvent.getType(), new SquareStateChangedEvent.IMyHandler()
			{
				public void onLoad(SquareStateChangedEvent event)
				{
					component.onSquareStateChanged(event);
				}
			});
	}

	protected void fireEvent(String key, Object value)
	{
		SquareStateChangedEvent event = new SquareStateChangedEvent(key, value);
		fireEvent(event);
	}

	/**
	 * Fire an event.
	 */
	protected void fireEvent(GwtEvent<SquareStateChangedEvent.IMyHandler> event)
	{
		handlerManager.fireEvent(event);
	}
}
