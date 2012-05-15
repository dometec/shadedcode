package org.example.demo.auth.aop;

import java.lang.reflect.Field;

import javax.inject.Inject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.example.demo.auth.AuthenticatedUser;
import org.example.demo.auth.DemoPrincipal;
import org.example.demo.auth.NotAuthenticatedException;
import org.example.demo.auth.NotAuthorizedException;
import org.example.demo.service.UserStore;

public class ApplicationRolesAllowedInterceptor implements MethodInterceptor {

	@Inject
	private UserStore userStore;

	public Object invoke(MethodInvocation invocation) throws Throwable {

		String[] roles = null;
		if (invocation.getMethod().isAnnotationPresent(ApplicationRolesAllowed.class))
			roles = invocation.getMethod().getAnnotation(ApplicationRolesAllowed.class).value();
		else
			roles = invocation.getThis().getClass().getSuperclass().getAnnotation(ApplicationRolesAllowed.class).value();

		Field[] fields = invocation.getThis().getClass().getSuperclass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(AuthenticatedUser.class)) {
				field.setAccessible(true);

				if (field.get(invocation.getThis()) == null)
					throw new NotAuthenticatedException();

				DemoPrincipal principal = (DemoPrincipal) field.get(invocation.getThis());

				if (!userStore.isUserInRole(principal, roles))
					throw new NotAuthorizedException();

				return invocation.proceed();
			}
		}

		throw new RuntimeException("There isn't annotation to inject AutenticatedUser!");

	}
}
