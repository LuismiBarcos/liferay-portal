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

package com.liferay.headless.builder.internal.sourcer.object;

import com.liferay.headless.builder.internal.sourcer.api.PropertyInfo;
import com.liferay.headless.builder.internal.sourcer.api.Sourcer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.yaml.openapi.FieldDefinition;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = Sourcer.class)
public class ObjectSourcerImpl implements Sourcer {

	@Override
	public Map<String, PropertyInfo> getPropertiesInfo(
		String entityName, Map<String, Schema> propertySchemas) {

		Map<String, PropertyInfo> stringObjectPropertyMap = new HashMap<>();

		for (Map.Entry<String, Schema> schemaEntry :
				propertySchemas.entrySet()) {

			Schema value = schemaEntry.getValue();

			FieldDefinition fieldDefinition = value.getFieldDefinition();

			String objectFieldName = fieldDefinition.getName();

			stringObjectPropertyMap.put(
				schemaEntry.getKey(),
				new PropertyInfo(
					schemaEntry.getKey(), objectFieldName, entityName));
		}

		return stringObjectPropertyMap;
	}

	@Override
	public Object getValue(
			PropertyInfo propertyInfo, Object pathParameterValue,
			HttpServletRequest httpServletRequest, UriInfo uriInfo)
		throws Exception {

		long objectEntryId = 0;

		if (pathParameterValue instanceof Long) {
			objectEntryId = (long)pathParameterValue;
		}

		String internalClass = propertyInfo.getInternalClass();

		long objectDefinitionId = GetterUtil.getLong(
			internalClass.split("#")[1]);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				objectDefinition.getStorageType());

		return _getFieldValue(
			objectEntryManager.getObjectEntry(
				_getDTOConverterContext(
					objectEntryId, objectDefinition, uriInfo,
					httpServletRequest),
				objectDefinition, objectEntryId),
			propertyInfo.getInternalName());
	}

	private Object _getCustomFieldValue(
		Map<String, Object> values, String name) {

		return values.get(name);
	}

	private DTOConverterContext _getDTOConverterContext(
			long objectEntryId, ObjectDefinition objectDefinition,
			UriInfo uriInfo, HttpServletRequest httpServletRequest)
		throws Exception {

		return new DefaultDTOConverterContext(
			false, null, _dtoConverterRegistry, httpServletRequest,
			objectEntryId,
			LocaleUtil.fromLanguageId(objectDefinition.getDefaultLanguageId()),
			uriInfo, _userLocalService.getUser(objectDefinition.getUserId()));
	}

	private Object _getFieldValue(ObjectEntry objectEntry, String name) {
		Object systemField = _getSystemFieldValue(objectEntry, name);

		if (systemField == null) {
			return _getCustomFieldValue(objectEntry.getProperties(), name);
		}

		return systemField;
	}

	private Object _getSystemFieldValue(ObjectEntry objectEntry, String name) {
		if (name.equals("createDate")) {
			return objectEntry.getDateCreated();
		}

		return null;
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private UserLocalService _userLocalService;

}