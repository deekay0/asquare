package edu.cmu.square.client.ui.core;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;

public class SquareWaterMarkTextBox extends TextBox implements BlurHandler, FocusHandler
{
	    String watermark;  
	    HandlerRegistration blurHandler;  
	    HandlerRegistration focusHandler;  
	  
	    
 
	public SquareWaterMarkTextBox(String waterMarkText)
	{
		super();
		this.watermark=waterMarkText;
		setText(waterMarkText);
		setStyleName("textInput-watermark ");
		 
		blurHandler = addBlurHandler(this);  
        focusHandler = addFocusHandler(this);  
		
	}
	

	public void onBlur(BlurEvent event)
	{
		
		if (this.getText().trim().length() == 0)
		{
			this.setStyleName("textInput-watermark ");
			this.setText(this.watermark);
		}

	}


	public void onFocus(FocusEvent event)
	{
	
		if (this.getText().trim().equalsIgnoreCase(this.watermark))
		{
			this.setStyleName("textInput");
			this.setText("");
		}
		
	}
	
}
