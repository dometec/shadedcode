package org.example.demo.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Inject the current user to DemoPrincipal field, null if nobody is logged
 * 
 * @author dometec
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticatedUser {

}
