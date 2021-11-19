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

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Reference;

/**
 * @author luismiguelbarcos
 */
public class WebhooksModelListener extends BaseModelListener {


	public WebhooksModelListener(Class<?> modelClass, DefaultDTOConverterContext defaultDTOConverterContext, DTOConverterRegistry dtoConverterRegistry) {
		_modelClass = modelClass;
		_defaultDTOConverterContext = defaultDTOConverterContext;
		_dtoConverterRegistry = dtoConverterRegistry;
	}

	@Override
	public Class<?> getModelClass() {
		return _modelClass;
	}

	@Override
	public void onAfterCreate(BaseModel model) throws ModelListenerException {
		System.out.println("onAfterCreate -> " + model.getModelClassName());
		DTOConverter dtoConverter =
			_dtoConverterRegistry.getDTOConverter(model.getModelClassName());
		try {
			Object o = dtoConverter.toDTO(_defaultDTOConverterContext, model);
			WebhookSender sender = new WebhookSender();
			sender.sendEventInformation(o);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onAfterRemove(BaseModel model) throws ModelListenerException {
		System.out.println("onAfterRemove -> " + model.getModelClassName());
	}

	private final DTOConverterRegistry _dtoConverterRegistry;

	private final Class<?> _modelClass;

	private final DefaultDTOConverterContext _defaultDTOConverterContext;
}