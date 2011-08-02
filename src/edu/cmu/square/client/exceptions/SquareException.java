package edu.cmu.square.client.exceptions;

public class SquareException extends Exception
{
	private static final long serialVersionUID = 3048988601697375001L;
	private ExceptionType type = ExceptionType.other;

	public SquareException()
		{
			super();
			System.out.println("1");
			
		}

	public SquareException(String msg, Throwable cause)
		{
			super(msg, cause);
			System.out.println("2");
			
		}

	public SquareException(String msg)
		{
			super(msg);
			System.out.println("3/"+msg);
			

		}

	public SquareException(Throwable t)
		{
			super(t);
			System.out.println("4");
			
		}

	public ExceptionType getType()
	{
		System.out.println("5/"+type.toString());
		
		return type;
	}

	public void setType(ExceptionType type)
	{
		System.out.println("6/"+type.toString());
		
		this.type = type;
	}

}
