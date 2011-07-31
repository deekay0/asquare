package edu.cmu.square.client.ui.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;

/**
 * @author NickC
 */
public class YellowFadeHandler
{
	private final int transparent_color = 17;
	private static final String[] yftStyles = {"yft-00", "yft-01", "yft-02", "yft-03", "yft-04", "yft-05", "yft-06", "yft-07", "yft-08", "yft-09",
			"yft-10", "yft-11", "yft-12", "yft-13", "yft-14", "yft-15", "yft-16", "yft-transparent"};

	private List<FadeObject> fadeObjectList = new LinkedList<FadeObject>();
	private List<FadeFlexTableRow> fadeFlexList = new LinkedList<FadeFlexTableRow>();

	public YellowFadeHandler()
		{
			Timer t = new Timer()
				{
					public void run()
					{
						if (!fadeObjectList.isEmpty())
						{

							for (Iterator<FadeObject> iter = fadeObjectList.iterator(); iter.hasNext();)
							{
								FadeObject fObj = iter.next();
								fObj.nextColor();

								if (fObj.isTransparent()) // if it hits the
								// transparent color
								// then fading is
								// done so remove
								// it.
								{
									iter.remove();
									continue;
								}
							}
						}
						else if (!fadeFlexList.isEmpty())
						{
							for (Iterator<FadeFlexTableRow> iter = fadeFlexList.iterator(); iter.hasNext();)
							{
								FadeFlexTableRow fObj = iter.next();
								fObj.nextColor();

								if (fObj.isTransparent()) // if it hits the
								// transparent color
								// then fading is
								// done so remove
								// it.
								{
									iter.remove();
									continue;
								}
							}

						}
						else
						{
							return;
						}
					}
				};

			t.scheduleRepeating(60);
		}

	public void add(UIObject obj)
	{
		if (obj == null)
		{
			return;
		}

		FadeObject fo = new FadeObject(obj);
		fadeObjectList.add(0, fo);
	}
	public void add(FlexTable flexTable, int rowId)
	{
		if (flexTable == null)
		{
			return;
		}

		FadeFlexTableRow fadeFlex = new FadeFlexTableRow(flexTable, rowId);
		fadeFlexList.add(0, fadeFlex);
	}

	private class FadeObject
	{
		private UIObject uiObj = null;
		private int color = 0;

		public FadeObject(UIObject uiObj)
			{
				this.uiObj = uiObj;
				this.uiObj.addStyleName(yftStyles[color]);
			}

		public void nextColor()
		{
			if (color == transparent_color)
			{
				return;
			}

			uiObj.removeStyleName(yftStyles[color]);
			color++;
			uiObj.addStyleName(yftStyles[color]);
		}

		public int getColor()
		{
			return color;
		}

		public boolean isTransparent()
		{
			return (color == transparent_color);
		}
	}// end class FadeObject
	private class FadeFlexTableRow
	{
		private RowFormatter rowFormatter = null;
		private int color = 0;
		private int rowIndex;

		public FadeFlexTableRow(FlexTable flex, int rowIndex)
			{
				this.rowIndex = rowIndex;
				this.rowFormatter = flex.getRowFormatter();
				this.rowFormatter.addStyleName(this.rowIndex, yftStyles[color]);
			}

		public void nextColor()
		{
			if (color == transparent_color)
			{
				return;
			}

			this.rowFormatter.removeStyleName(this.rowIndex, yftStyles[color]);
			color++;
			this.rowFormatter.addStyleName(this.rowIndex, yftStyles[color]);
		}

		public int getColor()
		{
			return color;
		}

		public boolean isTransparent()
		{
			return (color == transparent_color);
		}
	}// end class FadeObject
}// end class YellowFadeHandler

