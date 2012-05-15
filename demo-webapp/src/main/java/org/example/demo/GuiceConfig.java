package org.example.demo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.example.demo.aop.LogCall;
import org.example.demo.aop.TrimAndNullInterceptor;
import org.example.demo.aop.UniqueCallOnCluster;
import org.example.demo.aop.UniqueCallOnClusterInterceptor;
import org.example.demo.auth.aop.ApplicationRolesAllowed;
import org.example.demo.auth.aop.ApplicationRolesAllowedInterceptor;
import org.example.demo.persistence.aop.NoTransactional;
import org.example.demo.persistence.aop.TransactionInterceptor;
import org.example.demo.persistence.aop.Transactional;
import org.example.demo.service.JettyRealmUserStore;
import org.example.demo.service.RealmUserStore;
import org.example.demo.service.TomcatRealmUserStore;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class GuiceConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {

		return Guice.createInjector(new JerseyServletModule() {
			@Override
			protected void configureServlets() {

				Map<String, String> params = new HashMap<String, String>();
				params.put("com.sun.jersey.config.feature.ImplicitViewables", "false");
				params.put("com.sun.jersey.config.feature.Redirect", "true");
				params.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
				params.put("com.sun.jersey.config.property.packages", "org.example.demo");
				params.put(ServletContainer.PROPERTY_WEB_PAGE_CONTENT_REGEX, ".*\\.(htm|html|css|js|jsp|png|jpeg|jpg|gif)$");
				params.put("com.sun.jersey.spi.container.ResourceFilters",
						"com.sun.jersey.api.container.filter.RolesAllowedResourceFilterFactory");

				filter("/*").through(GuiceContainer.class, params);

				install(new Module() {

					public void configure(Binder binder) {

						bind(ServletContext.class).annotatedWith(Names.named("ServletContext")).toInstance(getServletContext());

						try {
							// try tomcat
							Class.forName("org.apache.catalina.Realm");
							bind(RealmUserStore.class).to(TomcatRealmUserStore.class);
						} catch (ClassNotFoundException e) {
							// try jetty
							bind(RealmUserStore.class).to(JettyRealmUserStore.class);
						}

						TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
						requestInjection(transactionInterceptor);

						bindInterceptor(Matchers.annotatedWith(Transactional.class),
								Matchers.not(Matchers.annotatedWith(NoTransactional.class)), transactionInterceptor);

						ApplicationRolesAllowedInterceptor rolesInt = new ApplicationRolesAllowedInterceptor();
						requestInjection(rolesInt);

						bindInterceptor(Matchers.any(), Matchers.annotatedWith(ApplicationRolesAllowed.class), rolesInt);
						bindInterceptor(Matchers.annotatedWith(ApplicationRolesAllowed.class), Matchers.any(), rolesInt);

						UniqueCallOnClusterInterceptor uniqueCallOnClusterInterceptor = new UniqueCallOnClusterInterceptor();
						requestInjection(uniqueCallOnClusterInterceptor);
						bindInterceptor(Matchers.any(), Matchers.annotatedWith(UniqueCallOnCluster.class), uniqueCallOnClusterInterceptor);

						LogCall logCall = new LogCall();
						TrimAndNullInterceptor trimAndNullableInterceptor = new TrimAndNullInterceptor();

						bindInterceptor(
								Matchers.annotatedWith(Path.class),
								Matchers.annotatedWith(GET.class).or(Matchers.annotatedWith(POST.class))
										.or(Matchers.annotatedWith(PUT.class)).or(Matchers.annotatedWith(DELETE.class)),
								trimAndNullableInterceptor, logCall);

					}
				});

			}
		});

	}
}
