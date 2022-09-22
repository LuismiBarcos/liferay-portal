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

package com.liferay.object.rest.internal.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.internal.resource.v1_0.test.util.HTTPTestUtils;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectEntryTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Miguel Barcos
 */
@RunWith(Arquillian.class)
public class ObjectEntryResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME, false)));

		_objectEntry = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition, _OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE);
	}

	@Test
	public void testGetCustomObjectRelatedCustomObjects() throws Exception {
		String objectFieldName = "x" + RandomTestUtil.randomString();
		String objectFieldValue = RandomTestUtil.randomString();

		ObjectDefinition relatedObjectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null,
						RandomTestUtil.randomString(), objectFieldName,
						false)));

		ObjectEntry relatedObjectEntry = ObjectEntryTestUtil.addObjectEntry(
			relatedObjectDefinition, objectFieldName, objectFieldValue);

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition, relatedObjectDefinition,
				TestPropsValues.getUserId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectRelationshipLocalServiceUtil.
			addObjectRelationshipMappingTableValues(
				TestPropsValues.getUserId(),
				objectRelationship.getObjectRelationshipId(),
				_objectEntry.getPrimaryKey(),
				relatedObjectEntry.getPrimaryKey(),
				ServiceContextTestUtil.getServiceContext());

		JSONObject jsonObject = HTTPTestUtils.invoke(
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), StringPool.SLASH,
				_objectEntry.getPrimaryKey(), StringPool.SLASH,
				objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			objectFieldValue, itemJSONObject.getString(objectFieldName));

		ObjectRelationshipLocalServiceUtil.
			deleteObjectRelationshipMappingTableValues(
				objectRelationship.getObjectRelationshipId(),
				_objectEntry.getPrimaryKey(),
				relatedObjectEntry.getPrimaryKey());

		ObjectRelationshipLocalServiceUtil.deleteObjectRelationship(
			objectRelationship);

		_objectDefinitionLocalService.deleteObjectDefinition(
			relatedObjectDefinition);
	}

	@Test
	public void testPutCustomObjectRelatedCustomObject() throws Exception {
		String objectFieldName = "x" + RandomTestUtil.randomString();
		String objectFieldValue = RandomTestUtil.randomString();

		ObjectDefinition relatedObjectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null,
						RandomTestUtil.randomString(), objectFieldName,
						false)));

		ObjectEntry relatedObjectEntry = ObjectEntryTestUtil.addObjectEntry(
			relatedObjectDefinition, objectFieldName, objectFieldValue);

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition, relatedObjectDefinition,
				TestPropsValues.getUserId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = HTTPTestUtils.invoke(
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), StringPool.SLASH,
				_objectEntry.getPrimaryKey(), StringPool.SLASH,
				objectRelationship.getName(), StringPool.SLASH,
				relatedObjectEntry.getPrimaryKey()),
			Http.Method.PUT);

		Assert.assertEquals(
			objectFieldValue, jsonObject.getString(objectFieldName));

		ObjectRelationshipLocalServiceUtil.
			deleteObjectRelationshipMappingTableValues(
				objectRelationship.getObjectRelationshipId(),
				_objectEntry.getPrimaryKey(),
				relatedObjectEntry.getPrimaryKey());

		ObjectRelationshipLocalServiceUtil.deleteObjectRelationship(
			objectRelationship);

		_objectDefinitionLocalService.deleteObjectDefinition(
			relatedObjectDefinition);
	}

	private static final String _OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE =
		RandomTestUtil.randomString();

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@DeleteAfterTestRun
	private ObjectEntry _objectEntry;

}