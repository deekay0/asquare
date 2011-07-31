/**
 * 
 */
package edu.cmu.square.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author kaalpurush
 *
 */
public class GwtStepVerficationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4581743510999176026L;
	private Integer id;
	private List<String> message = new ArrayList<String>();
	private GwtStep step;
	private boolean hasWarning=false;
	
	public void setMessages(List<String> message)
	{
		this.message = message;
	}
	public List<String> getMessages()
	{
		return message;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getId()
	{
		return id;
	}
	public void setHasWarning(boolean hasWarning)
	{
		this.hasWarning = hasWarning;
	}
	public boolean hasWarning()
	{
		return hasWarning;
	}
	/**
	 * @param step the step to set
	 */
	public void setStep(GwtStep step)
	{
		this.step = step;
	}
	/**
	 * @return the step
	 */
	public GwtStep getStep()
	{
		return step;
	}
	
}
