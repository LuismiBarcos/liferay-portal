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

package com.liferay.portal.vulcan.extension;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Luis Miguel Barcos
 */
public class EntityExtensionContext {

	public EntityExtensionContext(
		Class<?> entityClass, Map<String, Serializable> extendedProperties) {
		this.extendedProperties = extendedProperties;
		this.entityClass = entityClass;
	}

	public Map<String, Serializable> getExtendedProperties() {
		return extendedProperties;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	private final Map<String, Serializable> extendedProperties;
	private final Class<?> entityClass;
}
