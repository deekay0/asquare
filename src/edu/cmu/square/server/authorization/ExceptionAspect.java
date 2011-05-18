package edu.cmu.square.server.authorization;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
/**
 * The objective of this class is to handle exceptions in a centralized way for remote service classes.
 * @author kaalpurush
 */
@Component
@Aspect
public class ExceptionAspect {
	private static Logger logger = Logger.getLogger(ExceptionAspect.class);
	@Pointcut("execution(* edu.cmu.square.server.remoteService..*.*(..))")
	public void anyServiceOperation() {
	}

	@AfterThrowing(pointcut = "anyServiceOperation()", throwing = "t")
	public void handleException(Throwable t) throws SquareException {
		SquareException se = null;
		logger.error("Handling serviceException " + t);
		if (t instanceof SquareException) {
			se = (SquareException)t;
			
		}	
		else {
			se = new SquareException(t.getMessage());
			if (t instanceof ConstraintViolationException)
			{
				se.setType(ExceptionType.constraintViolated);
			}
			
		}
		
		throw se;
	}
}
