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

package com.liferay.object.rest.internal.jaxrs.extension.relationships;

import com.liferay.object.rest.extension.relationships.CustomObjectRelationshipsResource;
import com.liferay.object.rest.extension.relationships.ObjectRelationships;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(
	factory = "com.liferay.object.rest.extension.relationships.CustomObjectRelationshipsResource",
	property = {"api.version=v1.0", "osgi.jaxrs.resource=true"},
	service = CustomObjectRelationshipsResource.class
)
@Path("")
public class CustomObjectRelationshipsResourceImpl
	implements CustomObjectRelationshipsResource {

	@GET
	@Override
	@Parameters(
		{
			@Parameter(in = ParameterIn.PATH, name = "previousPath"),
			@Parameter(
				in = ParameterIn.PATH, name = "currentCustomObjectEntryId"
			),
			@Parameter(
				in = ParameterIn.PATH, name = "customObjectRelationshipName"
			),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/{previousPath: [a-zA-Z0-9-]+}/{currentCustomObjectEntryId: \\d+}/{customObjectRelationshipName: [a-zA-Z0-9-]+}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags({@Tag(name = "ObjectEntry")})
	public Page<Object> getCustomObjectRelatedObjectsPage(
			Long currentCustomObjectEntryId,
			String customObjectRelationshipName, Pagination pagination)
		throws Exception {

		return _objectRelationships.getObjectRelatedObjectsPage(
			currentCustomObjectEntryId, customObjectRelationshipName,
			pagination, _uriInfo);
	}

	@Reference
	private ObjectRelationships _objectRelationships;

	@Context
	private UriInfo _uriInfo;

}