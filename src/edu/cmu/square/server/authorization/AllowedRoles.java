/**
 * 
 */
package edu.cmu.square.server.authorization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kaalpurush
 *
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AllowedRoles {
	Roles[] roles();
}
