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

package com.liferay.headless.builder.internal.operation.handler;

import com.liferay.headless.builder.internal.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.objects.ObjectsIntegrationImpl;
import com.liferay.headless.builder.internal.operation.Operation;
import com.liferay.headless.builder.internal.util.HeadlessBuilderUtil;
import com.liferay.headless.builder.internal.util.URLUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * @author Carlos Correa
 */
@Component(
	property = HeadlessBuilderConstants.OPERATION_NAME + "=getByPrimaryKey",
	service = OperationHandler.class
)
public class GetByPrimaryKeyOperationHandler implements OperationHandler {

	@Override
	public Response handle(
			HttpServletRequest httpServletRequest, Operation operation)
		throws Exception {

		Operation.Response response = operation.getResponse(
			httpServletRequest.getHeader(HttpHeaders.ACCEPT),
			Response.Status.OK.getStatusCode());


		Map<String, String> pathParameters = URLUtil.getPathParameters(
			httpServletRequest.getRequestURI(),
			operation.getPathConfiguration());

		return Response.status(
			Response.Status.OK
		).entity(
			HeadlessBuilderUtil.getEntity(pathParameters,
				response, _objectsIntegration)
		).build();

	}

	@Reference
	private ObjectsIntegrationImpl _objectsIntegration;
}