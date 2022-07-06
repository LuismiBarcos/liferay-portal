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
package com.liferay.object.rest.internal.jaxrs.extension;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.extension.RelationshipsResource;
import com.liferay.object.rest.resource.v1_0.ObjectEntryResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import org.osgi.service.component.annotations.Component;

import java.util.Collections;

/**
 * @author Luis Miguel Barcos
 */
@Component(
	factory = "com.liferay.object.rest.extension.RelationshipsResource",
	properties = "OSGI-INF/liferay/rest/v1_0/object-entry.properties",
	service = RelationshipsResource.class
)
public class RelationshipsResourceImpl implements RelationshipsResource {

	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "currentObjectEntryId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "objectRelationshipName"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "page"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "pageSize"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "ObjectEntry")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/{currentObjectEntryId}/{objectRelationshipName}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<ObjectEntry> getCurrentObjectEntriesObjectRelationshipNamePage(
		@io.swagger.v3.oas.annotations.Parameter(hidden = true)
		@javax.validation.constraints.NotNull
		@javax.ws.rs.PathParam("currentObjectEntryId")
			Long currentObjectEntryId,
		@io.swagger.v3.oas.annotations.Parameter(hidden = true)
		@javax.validation.constraints.NotNull
		@javax.ws.rs.PathParam("objectRelationshipName")
			String objectRelationshipName,
		@javax.ws.rs.core.Context Pagination pagination)
		throws Exception {
		System.out.println("Hello world");
		return Page.of(Collections.emptyList());
	}

	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "StructuredContent")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/hello")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public String getHello() throws Exception {
		// http://localhost:8080/o/headless-delivery/hello
		System.out.println("Hello world");
		return "hello";
	}
}
