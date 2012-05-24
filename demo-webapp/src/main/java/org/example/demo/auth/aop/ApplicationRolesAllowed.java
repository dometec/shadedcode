package org.example.demo.auth.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Annotation for filter access based on roles assigned by internal authentication (application managed security)
 * 
 * @author dometec
 * @since 09/05/2012
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface ApplicationRolesAllowed {
	public String[] value() default "*";
}
