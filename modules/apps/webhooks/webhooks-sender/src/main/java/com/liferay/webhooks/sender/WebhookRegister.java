/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.webhooks.sender;

import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = WebhookRegister.class)
public class WebhookRegister {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
		_stringServiceRegistrationMap = new HashMap<>();
	}
	
	public void register(String entityClassName, DefaultDTOConverterContext defaultDTOConverterContext) {
		List<Class<?>> classes = _findClass(entityClassName);
		ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				ModelListener.class.getName(),
				new WebhooksModelListener(classes.get(0), defaultDTOConverterContext, _dtoConverterRegistry), null);
		_stringServiceRegistrationMap.put(entityClassName,serviceRegistration);
	}
	
	public void unregister(Class clazz) {
		ServiceRegistration serviceRegistration =
			_stringServiceRegistrationMap.get(clazz.getName());
		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	@Deactivate
	public void deactivate() {
		_stringServiceRegistrationMap.forEach(
			(classname, serviceRegistration) -> serviceRegistration.unregister());
	}

	private List<Class<?>> _findClass(String name) {
		List<Class<?>> result = new ArrayList<>();
		for (Bundle b : _bundleContext.getBundles()) {
			try {
				Class<?> c = b.loadClass(name);
				result.add(c);
			} catch (ClassNotFoundException e) {
				// No problem, this bundle doesn't have the class
			}
		}
		return result;
	}

	private Map<String,ServiceRegistration<?>> _stringServiceRegistrationMap;
	
	private BundleContext _bundleContext;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;
}
