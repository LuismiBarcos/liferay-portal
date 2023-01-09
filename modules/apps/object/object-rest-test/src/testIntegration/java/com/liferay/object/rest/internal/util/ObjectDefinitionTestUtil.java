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

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.List;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectDefinitionTestUtil {

	public static void addObjectFields(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields) {

		objectFields.forEach(
			objectField -> objectField.setObjectDefinitionId(
				objectDefinition.getObjectDefinitionId()));
	}

	public static ObjectDefinition createDefaultObjectDefinition()
		throws Exception {

		return createDefaultObjectDefinition(
			"x" + RandomTestUtil.randomString());
	}

	public static ObjectDefinition createDefaultObjectDefinition(
			String objectFieldName)
		throws Exception {

		return createObjectDefinition(
			ObjectFieldTestUtil.getDefaultObjectFields(objectFieldName));
	}

	public static ObjectDefinition createObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		return createObjectDefinition(
			objectFields, TestPropsValues.getUserId());
	}

	public static ObjectDefinition createObjectDefinition(
			List<ObjectField> objectFields, long userId)
		throws Exception {

		return ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
			userId, false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"A" + RandomTestUtil.randomString(), null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);
	}

	public static ObjectDefinition publishObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		return publishObjectDefinition(
			objectFields, TestPropsValues.getUserId());
	}

	public static ObjectDefinition publishObjectDefinition(
			List<ObjectField> objectFields, long userId)
		throws Exception {

		return publishObjectDefinition(
			createObjectDefinition(objectFields, userId), userId);
	}

	public static ObjectDefinition publishObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return publishObjectDefinition(
			objectDefinition, TestPropsValues.getUserId());
	}

	public static ObjectDefinition publishObjectDefinition(
			ObjectDefinition objectDefinition, long userId)
		throws Exception {

		return ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());
	}

}