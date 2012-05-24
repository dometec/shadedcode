package org.example.demo.service;

import java.lang.reflect.Field;
import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContext;

import org.apache.catalina.Realm;
import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.ApplicationContextFacade;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.realm.GenericPrincipal;
import org.example.demo.auth.DemoPrincipal;

@Singleton
public class TomcatRealmUserStore implements RealmUserStore {

	private Realm realm;

	@Inject
	public TomcatRealmUserStore(@Named("ServletContext") ServletContext servletContext) {

		try {

			ApplicationContextFacade acf = (ApplicationContextFacade) servletContext;
			Field privateField = ApplicationContextFacade.class.getDeclaredField("context");
			privateField.setAccessible(true);
			ApplicationContext appContext = (ApplicationContext) privateField.get(acf);

			Field privateField2 = ApplicationContext.class.getDeclaredField("context");
			privateField2.setAccessible(true);
			StandardContext stdContext = (StandardContext) privateField2.get(appContext);

			realm = stdContext.getRealm();

		} catch (Exception e) {
			// maybe not in Tomcat?
			// mBeanServer.getAttribute(new
			// ObjectName("Catalina:type=Engine"), "realm")
		}
	}

	@Override
	public DemoPrincipal authenticate(String user, String password) {

		try {

			Principal principal = realm.authenticate(user, password);
			if (principal == null)
				return null;

			GenericPrincipal genericPrincipal = (GenericPrincipal) principal;
			return new DemoPrincipal(principal, genericPrincipal.getRoles());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public DemoPrincipal toDemoPrincipal(Principal principal) {

		if (principal instanceof GenericPrincipal) {
			GenericPrincipal gprincipal = (GenericPrincipal) principal;
			return new DemoPrincipal(gprincipal, gprincipal.getRoles());
		}

		return new DemoPrincipal(principal);

	}

}
