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

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author luismiguelbarcos
 */
@Component(
	immediate = true,
	service = ModelListener.class
)
public class WebhooksModelListener extends BaseModelListener {

	public WebhooksModelListener(){
		_modelClass = BlogsEntry.class;
	}

//	@Activate
//	protected void activate(BundleContext bundleContext) throws Exception {
//		_serviceRegistration = bundleContext.registerService(
//			ModelListener.class.getName(),
//			new WebhooksModelListener(BlogsEntry.class),
//			null);
//	}
//
//	@Deactivate
//	protected void deactivate() throws Exception {
//		_serviceRegistration.unregister();
//	}

	public WebhooksModelListener(Class<?> modelClass) {
		_modelClass = modelClass;
	}

	@Override
	public Class<?> getModelClass() {
		return _modelClass;
	}

	@Override
	public void onAfterCreate(BaseModel model) throws ModelListenerException {
		System.out.println("onAfterCreate");
	}

	@Override
	public void onAfterRemove(BaseModel model) throws ModelListenerException {
		System.out.println("onAfterRemove");
	}

	private final Class<?> _modelClass;

	private ServiceRegistration<?> _serviceRegistration;
}