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

package com.liferay.headless.builder.test.util;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Luis Miguel Barcos
 */
public class HeadlessBuilderTestUtil {

	public static ObjectEntry addObjectEntry(
			ObjectDefinition objectDefinition, Map<String, Serializable> values)
		throws Exception {

		long groupId = 0;

		if (com.liferay.portal.kernel.util.StringUtil.equals(
				objectDefinition.getScope(),
				ObjectDefinitionConstants.SCOPE_SITE)) {

			groupId = TestPropsValues.getGroupId();
		}

		return ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), groupId,
			objectDefinition.getObjectDefinitionId(), values,
			ServiceContextTestUtil.getServiceContext());
	}

	public static String parseOpenAPIYaml(
		String openAPIYaml, Map<String, String> replacements) {

		Set<Map.Entry<String, String>> entries = replacements.entrySet();

		Iterator<Map.Entry<String, String>> iterator = entries.iterator();

		return _replace(iterator, iterator.next(), openAPIYaml);
	}

	public static ObjectDefinition publishObjectDefinition(
			List<ObjectField> objectFields, String scope, long userId)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				userId, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				scope, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				objectFields);

		return ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());
	}

	private static String _replace(
		Iterator<Map.Entry<String, String>> iterator,
		Map.Entry<String, String> entry, String text) {

		String key = entry.getKey();

		if (iterator.hasNext()) {
			return _replace(
				iterator, iterator.next(),
				StringUtil.replace(text, key, entry.getValue()));
		}

		return StringUtil.replace(text, key, entry.getValue());
	}

}