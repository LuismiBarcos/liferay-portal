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

import com.liferay.headless.builder.internal.contracts.PropertyInfo;
import com.liferay.headless.builder.internal.contracts.SourceInformationBridge;
import com.liferay.headless.builder.internal.operation.Operation;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Carlos Correa
 */
public class HeadlessBuilderUtil {

	public static Map<String, Object> getEntity(
		Map<String, String> pathParameters, Operation.Response response,
		SourceInformationBridge _sourceInformationBridge,
		HttpServletRequest httpServletRequest, UriInfo uriInfo)
		throws Exception {
		Map<String, Object> entity = new HashMap<>();

		Map<String, PropertyInfo> objectProperty =
			response.getObjectPropertiesInfo();

		for (Map.Entry<String, PropertyInfo> objectPropertyEntry : objectProperty.entrySet()) {
			entity.put(objectPropertyEntry.getKey(),
				_sourceInformationBridge.getValue(
					objectPropertyEntry.getValue(),
					GetterUtil.getLong(pathParameters.get("id")), httpServletRequest, uriInfo));
		}

		return entity;
	}

}