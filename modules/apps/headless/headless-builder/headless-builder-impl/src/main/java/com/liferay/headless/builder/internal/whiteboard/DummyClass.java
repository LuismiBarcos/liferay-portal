package com.liferay.headless.builder.internal.whiteboard;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = DummyClass.class, immediate = true)
public class DummyClass {

	@Activate
	protected void activate() {
		_whiteboard.dummyMethod();
	}

	@Reference
	private Whiteboard _whiteboard;
}
