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

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.extension.OpenAPIEndpointsExtension;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;
import javafx.util.Pair;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = OpenAPIEndpointsExtension.class)
public class OpenAPIRelationshipsExtendedEndpoints
	implements OpenAPIEndpointsExtension {

	@Override
	public Map<String, PathItem> getExtendedEndpoints(UriInfo uriInfo)
		throws Exception {

		Map<String, PathItem> pathItemMap = new HashMap<>();

		List<ObjectDefinition> systemObjectDefinitions =
			_objectDefinitionLocalService.getSystemObjectDefinitions();

		String basePath = uriInfo.getBaseUri().getPath();

		Map<ObjectDefinition, SystemObjectDefinitionMetadata> objectDefinitionSystemObjectDefinitionMetadataMap =
			new HashMap<>(Collections.emptyMap());

		for (ObjectDefinition systemObjectDefinition : systemObjectDefinitions) {
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
				_getSystemObjectDefinitionMetadata(systemObjectDefinition);
			if (basePath.contains(_getSystemObjectBasePath(
				systemObjectDefinitionMetadata.getRESTContextPath()))) {
				objectDefinitionSystemObjectDefinitionMetadataMap.put(
					systemObjectDefinition, systemObjectDefinitionMetadata);
			}
		}

		for (Map.Entry<ObjectDefinition, SystemObjectDefinitionMetadata> objectDefinitionSystemObjectDefinitionMetadataEntry : objectDefinitionSystemObjectDefinitionMetadataMap.entrySet()) {
			ObjectDefinition currentObjectDefinition =
				objectDefinitionSystemObjectDefinitionMetadataEntry.getKey();
			SystemObjectDefinitionMetadata currentSystemObjectDefinitionMetadata =
				objectDefinitionSystemObjectDefinitionMetadataEntry.getValue();
			List<ObjectRelationship> systemObjectRelationships =
				currentSystemObjectDefinitionMetadata.getSystemObjectRelationships();

			for (ObjectRelationship systemObjectRelationship : systemObjectRelationships) {
				String path = StringUtil.lowerCaseFirstLetter(uriInfo.getPath().split(StringPool.SLASH)[0] + StringPool.SLASH +
				  StringUtil.lowerCaseFirstLetter(currentSystemObjectDefinitionMetadata.getRESTContextPath().split(StringPool.SLASH)[2]) +
					StringPool.SLASH +
					"{" + StringUtil.lowerCaseFirstLetter(
						currentObjectDefinition.getName()) + "Id}" +
					StringPool.SLASH + systemObjectRelationship.getName());
				System.out.println(path);
				pathItemMap.put(path, _createPathItem());
			}
		}

		return pathItemMap;
	}

	private PathItem _createPathItem() {
		return new PathItem(){{
			get(_getOperation());
		}};
	}

	private Operation _getOperation() {
		Map<String, Parameter> parameters = new HashMap<>();

		parameters.put("userId",
			new Parameter(){
				{
					name("userId");
					in("query");
					required(true);
				}
			}
		);
		return new Operation(){{
			operationId("operationId");
			parameters(new ArrayList<>(parameters.values()));
			tags(Collections.singletonList("UserAccount"));
		}};
	}

	private String _getSystemObjectBasePath(
		String systemObjectRESTContextPath) {
		return systemObjectRESTContextPath.split("/")[0];
	}

	private SystemObjectDefinitionMetadata _getSystemObjectDefinitionMetadata(
		ObjectDefinition objectDefinition) {
		return _systemObjectDefinitionMetadataTracker.getSystemObjectDefinitionMetadata(
			objectDefinition.getName());
	}

	@Reference
	private SystemObjectDefinitionMetadataTracker
		_systemObjectDefinitionMetadataTracker;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}