package edu.cmu.square.client.exceptions;

public class SquareException extends Exception
{
	private static final long serialVersionUID = 3048988601697375001L;
	private ExceptionType type = ExceptionType.other;

	public SquareException()
		{
			super();
		}

	public SquareException(String msg, Throwable cause)
		{
			super(msg, cause);

		}

	public SquareException(String msg)
		{
			super(msg);

		}

	public SquareException(Throwable t)
		{
			super(t);
		}

	public ExceptionType getType()
	{
		return type;
	}

	public void setType(ExceptionType type)
	{
		this.type = type;
	}

}
