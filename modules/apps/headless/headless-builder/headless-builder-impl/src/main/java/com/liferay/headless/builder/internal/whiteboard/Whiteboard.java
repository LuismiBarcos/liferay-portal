package com.liferay.headless.builder.internal.whiteboard;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;
import org.osgi.util.tracker.ServiceTracker;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.osgi.service.http.whiteboard.HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = Whiteboard.class, immediate = true)
public class Whiteboard {

	@Activate
	@Modified
	protected void activate(BundleContext bundleContext) throws Exception {
		System.out.println("Activated");
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configTracker =
			new ServiceTracker<>(
				bundleContext, ConfigurationAdmin.class, null);

		CountDownLatch addedCountLatch = new CountDownLatch(1);
		CountDownLatch removedCountLatch = new CountDownLatch(1);

		ServiceTracker<?, ?> serviceTracker = new ServiceTracker
			<JaxrsServiceRuntime, JaxrsServiceRuntime>(
			bundleContext, JaxrsServiceRuntime.class, null) {

			@Override
			public JaxrsServiceRuntime addingService(
				ServiceReference<JaxrsServiceRuntime> reference) {

				if ("/new-whiteboard".equals(
					reference.getProperty(
						HTTP_WHITEBOARD_SERVLET_PATTERN))) {

					addedCountLatch.countDown();

					JaxrsServiceRuntime service =
						bundleContext.getService(reference);

					RuntimeDTO runtimeDTO = service.getRuntimeDTO();

					return super.addingService(reference);
				}

				return null;
			}

			@Override
			public void removedService(
				ServiceReference<JaxrsServiceRuntime> reference,
				JaxrsServiceRuntime service) {

				removedCountLatch.countDown();
			}
		};

		configTracker.open();

		serviceTracker.open();

		ConfigurationAdmin admin = configTracker.waitForService(5000);

		Configuration configuration = admin.createFactoryConfiguration(
			"org.apache.aries.jax.rs.whiteboard", "?");

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/new-whiteboard");
		properties.put(Constants.SERVICE_RANKING, 1000);

		configuration.update(properties);

		addedCountLatch.await(1, TimeUnit.MINUTES);

		configuration.delete();

		removedCountLatch.await(1, TimeUnit.MINUTES);

		configTracker.close();

		serviceTracker.close();

		System.out.println("Bye");
	}
}
