package org.example.demo.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 
 * Intercetta le chiamate provenienti dall'esterno ed esegue un trim su 
 * tutti i parametri stringa e se la stringa è vuota imposta il valore a null
 * 
 * @author dometec
 * @since 16/04/2011
 * 
 */
public class TrimAndNullInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {

		for (int i = 0; i < invocation.getArguments().length; i++) {

			if (invocation.getArguments()[i] != null && invocation.getArguments()[i] instanceof String) {
				String sparam = (String) invocation.getArguments()[i];

				String trim = sparam.trim();
				if (trim.isEmpty())
					invocation.getArguments()[i] = null;
				else
					invocation.getArguments()[i] = trim;
			}

		}

		return invocation.proceed();

	}
}