package org.example.demo.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

public class LogCall implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {

		Logger logger = LoggerFactory.getLogger(invocation.getThis().getClass());

		String arg = Joiner.on(", ").useForNull("null").join(invocation.getArguments());
		logger.debug("{} ({}).", invocation.getMethod().getName(), arg);

		Object result = invocation.proceed();

		logger.trace("Output: {}.", result);

		return result;

	}

}