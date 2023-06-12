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

package com.liferay.headless.builder.internal.generator.application;

import java.util.List;

/**
 * @author Luis Miguel Barcos
 */
public class ApiApplication {

	public ApiApplication(
		String baseURL, long companyId, List<Operation> operations,
		String osgiJaxRsName, List<Schema> schemas) {

		_baseURL = baseURL;
		_companyId = companyId;
		_operations = operations;
		_osgiJaxRsName = osgiJaxRsName;
		_schemas = schemas;
	}

	public String getBaseURL() {
		return _baseURL;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public List<Operation> getOperations() {
		return _operations;
	}

	public String getOsgiJaxRsName() {
		return _osgiJaxRsName;
	}

	public List<Schema> getSchemas() {
		return _schemas;
	}

	private final String _baseURL;
	private final long _companyId;
	private final List<Operation> _operations;
	private final String _osgiJaxRsName;
	private final List<Schema> _schemas;

}