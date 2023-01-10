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

package com.liferay.object.rest.internal.util;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Collections;
import java.util.List;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectFieldTestUtil {

	public static List<ObjectField> createDefaultObjectFields(
			ObjectDefinition objectDefinition, String objectFieldName)
		throws Exception {

		return Collections.singletonList(
			ObjectFieldLocalServiceUtil.addCustomObjectField(
				null, TestPropsValues.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
				ObjectFieldConstants.DB_TYPE_INTEGER, null, true, false, null,
				LocalizedMapUtil.getLocalizedMap(objectFieldName),
				objectFieldName, false, false, Collections.emptyList()));
	}

	public static List<ObjectField> getDefaultObjectFields() {
		return getDefaultObjectFields("x" + RandomTestUtil.randomString());
	}

	public static List<ObjectField> getDefaultObjectFields(
		String objectFieldName) {

		return Collections.singletonList(
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
				ObjectFieldConstants.DB_TYPE_INTEGER, true, false, null,
				objectFieldName, objectFieldName, false));
	}

}