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

package com.liferay.headless.builder.internal.generator.consumer.object.model;

import com.liferay.headless.builder.internal.generator.application.Property;
import com.liferay.headless.builder.internal.generator.application.Schema;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Miguel Barcos
 */
public class ApiSchemasObjectModel extends ObjectModel {

	public ApiSchemasObjectModel(
			String apiApplicationERC, long companyId,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectEntryLocalService objectEntryLocalService,
			ObjectEntryManager objectEntryManager,
			PermissionCheckerFactory permissionCheckerFactory,
			UserLocalService userLocalService)
		throws Exception {

		super(
			companyId, objectDefinitionLocalService, objectEntryLocalService,
			objectEntryManager, permissionCheckerFactory, apiApplicationERC,
			userLocalService);
	}

	public List<Schema> getApiSchemaList() {
		return _apiSchemaList;
	}

	@Override
	protected void initObjectModel() throws Exception {
		Page<ObjectEntry> objectEntriesPage = getObjectEntries(
			String.format(
				"%s/externalReferenceCode eq '%s'",
				_APPLICATION_SCHEMA_OBJECT_RELATIONSHIP_NAME,
				relatedObjectEntryERC),
			_OBJECT_DEFINITION_ERC);

		_apiSchemaList = new ArrayList<>();

		for (ObjectEntry schemaObjectEntry : objectEntriesPage.getItems()) {
			_apiSchemaList.add(
				new Schema(
					(String)getObjectEntryPropertyValue(
						schemaObjectEntry,
						_MAIN_OBJECT_DEFINITION_ERC_PROPERTY_VALUE),
					(String)getObjectEntryPropertyValue(
						schemaObjectEntry, _NAME_PROPERTY_VALUE),
					_getSchemaProperties(
						schemaObjectEntry.getExternalReferenceCode())));
		}
	}

	private List<Property> _getSchemaProperties(String schemaERC)
		throws Exception {

		return new ApiPropertyObjectModel(
			schemaERC, companyId, objectDefinitionLocalService,
			objectEntryLocalService, objectEntryManager,
			permissionCheckerFactory, userLocalService
		).getProperties();
	}

	private static final String _APPLICATION_SCHEMA_OBJECT_RELATIONSHIP_NAME =
		"applicationSchemas";

	private static final String _MAIN_OBJECT_DEFINITION_ERC_PROPERTY_VALUE =
		"mainObjectDefinitionERC";

	private static final String _NAME_PROPERTY_VALUE = "name";

	private static final String _OBJECT_DEFINITION_ERC = "MSOD_API_SCHEMA";

	private List<Schema> _apiSchemaList;

}