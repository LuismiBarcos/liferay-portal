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

package com.liferay.headless.builder.internal.util;

import com.liferay.headless.builder.internal.objects.ObjectProperty;
import com.liferay.headless.builder.internal.objects.ObjectsIntegrationImpl;
import com.liferay.headless.builder.internal.operation.Operation;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carlos Correa
 */
public class HeadlessBuilderUtil {

	public static Map<String, Object> getEntity(Map<String, String> pathParameters, Operation.Response response, ObjectsIntegrationImpl objectsIntegration)
		throws PortalException {
		Map<String, Object> entity = new HashMap<>();

		Map<String, ObjectProperty> objectProperty =
			response.getObjectProperty();

		for (Map.Entry<String, ObjectProperty> objectPropertyEntry : objectProperty.entrySet()) {
			entity.put(objectPropertyEntry.getKey(),
				objectsIntegration.getValue(
					objectPropertyEntry.getValue(),
					GetterUtil.getLong(pathParameters.get("id"))));
		}

		return entity;
	}

}