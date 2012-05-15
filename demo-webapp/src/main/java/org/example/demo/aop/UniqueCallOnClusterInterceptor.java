package org.example.demo.aop;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.TransactionManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.example.demo.service.cluster.KeyCallOnClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

public class UniqueCallOnClusterInterceptor implements MethodInterceptor {

	@Inject
	private KeyCallOnClusterService keyCallOnClusterService;

	public Object invoke(MethodInvocation invocation) throws Throwable {

		Logger logger = LoggerFactory.getLogger(this.getClass());

		String classname = invocation.getMethod().getDeclaringClass().getSimpleName();
		String methodName = invocation.getMethod().getName();
		String key = classname + "_" + methodName + "_" + extractParameterValue(invocation);

		TransactionManager tm = keyCallOnClusterService.getTransactionManager();

		try {

			if (tm != null) {
				tm.begin();
				boolean success = keyCallOnClusterService.lock(key);

				if (!success) {
					logger.info("Non posso effettuare il lock sul cluster per la chiave {}.", key);
					return Response.status(Status.CONFLICT).entity("Another call with same parameter is in progress.").build();
				}
			}

			String runningServer = (String) keyCallOnClusterService.get(key);
			if (runningServer != null) {
				logger.info("Chiamata già in corso, server {}.", runningServer);
				return Response.status(Status.CONFLICT).entity("Another call with same parameter is in progress.").build();
			}

			keyCallOnClusterService.put(key, "todo-hostname");

		} catch (Exception e) {

			logger.error("Errore quando tento di fare il lock per {}: {}.", key, e.getMessage());

		} finally {
			if (tm != null)
				tm.commit();
		}

		try {

			return invocation.proceed();

		} finally {

			keyCallOnClusterService.remove(key);

		}

	}

	public String extractParameterValue(MethodInvocation invocation) {

		List<String> list = new ArrayList<String>();
		Annotation[][] parameterAnnotations = invocation.getMethod().getParameterAnnotations();

		for (int i = 0; i < parameterAnnotations.length; i++) {
			Annotation[] annotations = parameterAnnotations[i];
			for (int j = 0; j < annotations.length; j++) {
				Annotation annotation = annotations[j];
				if (annotation instanceof KeyParameter) {
					list.add(String.valueOf(invocation.getArguments()[i]));
					break;
				}
			}
		}

		if (list.size() == 0)
			return Joiner.on(", ").useForNull("null").join(invocation.getArguments());
		return Joiner.on(", ").useForNull("null").join(list);

	}
}