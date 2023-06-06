package com.liferay.headless.builder.internal.whiteboard;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.util.tracker.ServiceTracker;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;

import static org.osgi.service.http.runtime.HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT;
import static org.osgi.service.http.whiteboard.HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN;
import static org.osgi.service.http.whiteboard.HttpWhiteboardConstants.HTTP_WHITEBOARD_TARGET;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = Whiteboard.class, immediate = true)
public class Whiteboard {

	private Configuration _configuration;

	private ServiceTracker<JaxrsServiceRuntime, JaxrsServiceRuntime> _serviceTracker;
	private ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> _configTracker;

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		CountDownLatch addedCountLatch = new CountDownLatch(1);

		_configTracker =
			new ServiceTracker<>(
				bundleContext, ConfigurationAdmin.class, null);

		 _runtimeTracker = new ServiceTracker<>(
			 bundleContext, JaxrsServiceRuntime.class, null);

		_serviceTracker = new ServiceTracker
			<JaxrsServiceRuntime, JaxrsServiceRuntime>(
			bundleContext, JaxrsServiceRuntime.class, null) {

			@Override
			public JaxrsServiceRuntime addingService(
				ServiceReference<JaxrsServiceRuntime> reference) {

				if ("/new-whiteboard".equals(
					reference.getProperty(
						HTTP_WHITEBOARD_SERVLET_PATTERN))) {

					addedCountLatch.countDown();

					return super.addingService(reference);
				}

				return null;
			}
		};

		_configTracker.open();

		_serviceTracker.open();

		_runtimeTracker.open();

		ConfigurationAdmin admin = _configTracker.getService();

		_configuration = admin.createFactoryConfiguration(
			"org.apache.aries.jax.rs.whiteboard", "mywhiteboard");

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("enabled", true);
		properties.put("default.application.base", "/new-whiteboard");
		properties.put("default.application.base", "/new-whiteboard");


//		properties.put(
//			HTTP_WHITEBOARD_SERVLET_PATTERN,
//			"/new-whiteboard");
//		properties.put(Constants.SERVICE_RANKING, 1000);
//		properties.put("default.application.base", "/new-whiteboard");
//		properties.put("osgi.jaxrs.endpoint", "/new-whiteboard");
//		properties.put("service.pid", "/new-whiteboard");
//		properties.put("application.ready.service.filter", "(liferay.jaxrs.whiteboard.ready=true)");
//		properties.put(HTTP_WHITEBOARD_TARGET, "(osgi.http.endpoint=/new-whiteboard)");
//		properties.put(HTTP_SERVICE_ENDPOINT,"/new-whiteboard");

		_configuration.update(properties);
	}
	
	public void dummyMethod() {
		System.out.println("Dummy method");
	}

	@Deactivate
	protected void deactivate() throws Exception {
		_configuration.delete();

		_configTracker.close();
		_runtimeTracker.close();
		_serviceTracker.close();

		_configTracker = null;
		_runtimeTracker = null;
		_serviceTracker = null;
	}

	private ServiceTracker<JaxrsServiceRuntime, JaxrsServiceRuntime> _runtimeTracker;

	@Reference
	private JaxrsServiceRuntime _jaxrsServiceRuntime;
}
