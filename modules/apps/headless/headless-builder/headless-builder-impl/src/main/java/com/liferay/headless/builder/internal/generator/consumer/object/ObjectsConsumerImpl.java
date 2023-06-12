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

package com.liferay.headless.builder.internal.generator.consumer.object;

import com.liferay.headless.builder.internal.generator.application.ApiApplication;
import com.liferay.headless.builder.internal.generator.application.Operation;
import com.liferay.headless.builder.internal.generator.application.Schema;
import com.liferay.headless.builder.internal.generator.consumer.Consumer;
import com.liferay.headless.builder.internal.generator.consumer.object.model.ApiApplicationObjectModel;
import com.liferay.headless.builder.internal.generator.consumer.object.model.ApiEndpointsObjectModel;
import com.liferay.headless.builder.internal.generator.consumer.object.model.ApiSchemasObjectModel;
import com.liferay.headless.builder.internal.generator.consumer.object.model.ObjectModelsFactory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = Consumer.class)
public class ObjectsConsumerImpl implements Consumer<String> {

	@Override
	public ApiApplication getApiApplication(String apiApplicationERC)
		throws Exception {

		ApiApplicationObjectModel apiApplicationObjectModel =
			_objectModelsFactory.getObjectModel(
				apiApplicationERC, ApiApplicationObjectModel.class);

		return new ApiApplication(
			apiApplicationObjectModel.getBaseURL(),
			apiApplicationObjectModel.getCompanyId(),
			_getOperations(apiApplicationERC),
			apiApplicationObjectModel.getOsgiJaxRsName(),
			_getSchemas(apiApplicationERC));
	}

	private List<Operation> _getOperations(String apiApplicationERC)
		throws Exception {

		ApiEndpointsObjectModel apiEndpointsObjectModel =
			_objectModelsFactory.getObjectModel(
				apiApplicationERC, ApiEndpointsObjectModel.class);

		List<Operation> operations = new ArrayList<>();

		for (ApiEndpointsObjectModel.ApiEndpoint apiEndpoint :
				apiEndpointsObjectModel.getApiEndpoints()) {

			operations.add(
				new Operation(
					apiEndpoint.getMethod(), apiEndpoint.getPath(),
					apiEndpoint.getScope()));
		}

		return operations;
	}

	private List<Schema> _getSchemas(String apiApplicationERC)
		throws Exception {

		ApiSchemasObjectModel apiSchemasObjectModel =
			_objectModelsFactory.getObjectModel(
				apiApplicationERC, ApiSchemasObjectModel.class);

		List<Schema> schemas = new ArrayList<>();

		for (Schema apiSchema : apiSchemasObjectModel.getApiSchemaList()) {
			schemas.add(
				new Schema(
					apiSchema.getMainObjectDefinitionERC(), apiSchema.getName(),
					apiSchema.getProperties()));
		}

		return schemas;
	}

	@Reference
	private ObjectModelsFactory _objectModelsFactory;

}