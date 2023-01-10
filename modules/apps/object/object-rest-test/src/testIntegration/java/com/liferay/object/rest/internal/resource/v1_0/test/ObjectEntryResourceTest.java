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
import com.liferay.object.rest.internal.util.HTTPTestUtil;
import com.liferay.object.rest.internal.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.internal.util.ObjectEntryTestUtil;
import com.liferay.object.rest.internal.util.ObjectFieldTestUtil;
import com.liferay.object.rest.internal.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;

import org.junit.After;
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
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectFieldTestUtil.getDefaultObjectFields(_OBJECT_FIELD_NAME_1));
		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectFieldTestUtil.getDefaultObjectFields(_OBJECT_FIELD_NAME_2));

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1,
			String.valueOf(_OBJECT_FIELD_VALUE_1));

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2,
			String.valueOf(_OBJECT_FIELD_VALUE_2));
	}

	@After
	public void tearDown() throws Exception {
		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);

		for (ObjectDefinition objectDefinition : _objectDefinitions) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	@Test
	public void testDeleteCustomObjectDefinition1WithCustomObjectDefinition2()
		throws Exception {

		Long irrelevantCurrentObjectId = RandomTestUtil.randomLong();

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2(
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2(
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2NotFound(
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				irrelevantCurrentObjectId, StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				irrelevantCurrentObjectId),
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2NotFound(
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				irrelevantCurrentObjectId, StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				irrelevantCurrentObjectId),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2(
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2NotFound(
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				irrelevantCurrentObjectId, StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				irrelevantCurrentObjectId),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2NotFound(
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				irrelevantCurrentObjectId, StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				_objectEntry2.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				irrelevantCurrentObjectId),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), StringPool.SLASH,
				_objectRelationship.getName()));
	}

	@Test
	public void testFilterInObjectsWithFieldsAndRelationshipCreatedAfterPublish()
		throws Exception {

		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectFieldTestUtil.getDefaultObjectFields());

		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition1,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition1, _OBJECT_FIELD_NAME_1));

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectFieldTestUtil.getDefaultObjectFields());

		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition2,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition2, _OBJECT_FIELD_NAME_2));

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			objectDefinition1, objectDefinition2,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testFiltersGetFilteredObjectEntriesByRelatedObjectEntries();
	}

	@Test
	public void testFilterInObjectsWithFieldsCreatedAfterPublish()
		throws Exception {

		ObjectDefinition objectDefinition1 = _createDefaultObjectDefinition();

		ObjectDefinition objectDefinition2 = _createDefaultObjectDefinition();

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			objectDefinition1, objectDefinition2,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_publishObjectsDefinitions(objectDefinition1, objectDefinition2);

		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition1,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition1, _OBJECT_FIELD_NAME_1));
		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition2,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition2, _OBJECT_FIELD_NAME_2));

		_testFiltersGetFilteredObjectEntriesByRelatedObjectEntries();
	}

	@Test
	public void testFilterInObjectWithFieldsAndRelationshipCreatedAfterPublishAndObjectWithFieldsCreatedAfterPublish()
		throws Exception {

		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectFieldTestUtil.getDefaultObjectFields());

		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition1,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition1, _OBJECT_FIELD_NAME_1));

		ObjectDefinition objectDefinition2 = _createDefaultObjectDefinition();

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			objectDefinition1, objectDefinition2,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectDefinitionTestUtil.publishObjectDefinition(objectDefinition2);
		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition2,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition2, _OBJECT_FIELD_NAME_2));

		_testFiltersGetFilteredObjectEntriesByRelatedObjectEntries();
	}

	@Test
	public void testFilterInObjectWithFieldsAndRelationshipCreatedAfterPublishAndObjectWithFieldsCreatedBeforePublish()
		throws Exception {

		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectFieldTestUtil.getDefaultObjectFields());

		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition1,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition1, _OBJECT_FIELD_NAME_1));

		ObjectDefinition objectDefinition2 = _createDefaultObjectDefinition();

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			objectDefinition1, objectDefinition2,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectDefinitionTestUtil.publishObjectDefinition(objectDefinition2);

		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition2,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition2, _OBJECT_FIELD_NAME_2));

		_testFiltersGetFilteredObjectEntriesByRelatedObjectEntries();
	}

	@Test
	public void testFilterInObjectWithFieldsAndRelationshipCreatedBeforePublish()
		throws Exception {

		ObjectDefinition objectDefinition1 =
			_createObjectDefinitionWithObjectField(_OBJECT_FIELD_NAME_1);

		ObjectDefinition objectDefinition2 =
			_createObjectDefinitionWithObjectField(_OBJECT_FIELD_NAME_2);

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			objectDefinition1, objectDefinition2,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_publishObjectsDefinitions(objectDefinition1, objectDefinition2);

		_testFiltersGetFilteredObjectEntriesByRelatedObjectEntries();
	}

	@Test
	public void testFilterInObjectWithFieldsAndRelationshipCreatedBeforePublishAndObjectWithFieldsAndRelationshipCreatedAfterPublish()
		throws Exception {

		ObjectDefinition objectDefinition1 = _createDefaultObjectDefinition();

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectFieldTestUtil.getDefaultObjectFields(
					_OBJECT_FIELD_NAME_2));

		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition1,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition1, _OBJECT_FIELD_NAME_1));

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			objectDefinition1, objectDefinition2,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_publishObjectsDefinitions(objectDefinition1);

		_testFiltersGetFilteredObjectEntriesByRelatedObjectEntries();
	}

	@Test
	public void testFilterInObjectWithFieldsAndRelationshipCreatedBeforePublishAndObjectWithFieldsCreatedAfterPublish()
		throws Exception {

		ObjectDefinition objectDefinition1 =
			_createObjectDefinitionWithObjectField(_OBJECT_FIELD_NAME_1);

		ObjectDefinition objectDefinition2 = _createDefaultObjectDefinition();

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			objectDefinition1, objectDefinition2,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_publishObjectsDefinitions(objectDefinition1, objectDefinition2);

		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition2,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition2, _OBJECT_FIELD_NAME_2));

		_testFiltersGetFilteredObjectEntriesByRelatedObjectEntries();
	}

	@Test
	public void testFilterInObjectWithFieldsCreatedAfterPublishAndObjectWithFieldsAndRelationshipCreatedAfterPublish()
		throws Exception {

		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.createObjectDefinition(
				ObjectFieldTestUtil.getDefaultObjectFields());

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectFieldTestUtil.getDefaultObjectFields(
					_OBJECT_FIELD_NAME_2));

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			objectDefinition1, objectDefinition2,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectDefinitionTestUtil.publishObjectDefinition(objectDefinition1);

		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition1,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition1, _OBJECT_FIELD_NAME_1));

		_testFiltersGetFilteredObjectEntriesByRelatedObjectEntries();
	}

	@Test
	public void testFilterInObjectWithFieldsCreatedAfterPublishAndObjectWithFieldsAndRelationshipCreatedBeforePublish()
		throws Exception {

		ObjectDefinition objectDefinition1 = _createDefaultObjectDefinition();

		ObjectDefinition objectDefinition2 =
			_createObjectDefinitionWithObjectField(_OBJECT_FIELD_NAME_2);

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			objectDefinition1, objectDefinition2,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_publishObjectsDefinitions(objectDefinition1, objectDefinition2);

		ObjectDefinitionTestUtil.addObjectFields(
			objectDefinition1,
			ObjectFieldTestUtil.createDefaultObjectFields(
				objectDefinition1, _OBJECT_FIELD_NAME_1));

		_testFiltersGetFilteredObjectEntriesByRelatedObjectEntries();
	}

	@Test
	public void testGetFilteredObjectEntriesByOneToManyRelatedObjectEntries()
		throws Exception {

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testFiltersGetFilteredObjectEntriesByRelatedObjectEntries();
	}

	@Test
	public void testGetNestedFieldDetailsInOneToManyRelationships()
		throws Exception {

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-161364", "true"
			).build());

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testGetNestedFieldDetailsInOneToManyRelationships(
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), "?nestedFields=r_",
				_objectRelationship.getName(), "_",
				_objectDefinition1.getPKObjectFieldName()),
			StringBundler.concat(
				"r_", _objectRelationship.getName(), "_",
				StringUtil.replaceLast(
					_objectDefinition1.getPKObjectFieldName(), "Id", "")));

		_testGetNestedFieldDetailsInOneToManyRelationships(
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), "?nestedFields=",
				_objectRelationship.getName()),
			_objectRelationship.getName());

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-161364", "false"
			).build());
	}

	@Test
	public void testGetRelationshipERCFieldInOneToManyRelationship()
		throws Exception {

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-161364", "true"
			).build());

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, _objectDefinition2.getRESTContextPath(), Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			itemJSONObject.getString(_objectRelationship.getName() + "ERC"),
			_objectEntry1.getExternalReferenceCode());

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-161364", "false"
			).build());
	}

	@Test
	public void testPutByExternalReferenceCodeManyToManyRelationship()
		throws Exception {

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry1.getExternalReferenceCode(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry2.getExternalReferenceCode()),
			Http.Method.PUT);

		Assert.assertEquals(
			_objectEntry2.getExternalReferenceCode(),
			jsonObject.getString("externalReferenceCode"));
		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_2, jsonObject.getInt(_OBJECT_FIELD_NAME_2));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry2.getExternalReferenceCode(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				_objectEntry1.getExternalReferenceCode()),
			Http.Method.PUT);

		Assert.assertEquals(
			_objectEntry1.getExternalReferenceCode(),
			jsonObject.getString("externalReferenceCode"));
		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_1, jsonObject.getInt(_OBJECT_FIELD_NAME_1));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry2.getExternalReferenceCode(), StringPool.SLASH,
				_objectRelationship.getName(), StringPool.SLASH,
				RandomTestUtil.randomString()),
			Http.Method.PUT);

		Assert.assertThat(
			jsonObject.getString("title"),
			CoreMatchers.containsString("No ObjectEntry exists with the key"));
	}

	private ObjectRelationship _addObjectRelationshipAndRelateObjectsEntries(
			String type)
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition1, _objectDefinition2,
				TestPropsValues.getUserId(), type);

		ObjectRelationshipTestUtil.relateObjectEntries(
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			objectRelationship, TestPropsValues.getUserId());

		return objectRelationship;
	}

	private void _createAndRelateObjectsEntries(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2)
		throws Exception {

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition1, _OBJECT_FIELD_NAME_1,
			String.valueOf(_OBJECT_FIELD_VALUE_1));

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition2, _OBJECT_FIELD_NAME_2,
			String.valueOf(_OBJECT_FIELD_VALUE_2));

		ObjectRelationshipTestUtil.relateObjectEntries(
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			_objectRelationship, TestPropsValues.getUserId());
	}

	private ObjectDefinition _createDefaultObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.createDefaultObjectDefinition();

		_objectDefinitions.add(objectDefinition);

		return objectDefinition;
	}

	private ObjectDefinition _createObjectDefinitionWithObjectField(
			String objectFieldName)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.createDefaultObjectDefinition(
				objectFieldName);

		_objectDefinitions.add(objectDefinition);

		return objectDefinition;
	}

	private void _publishObjectsDefinitions(
			ObjectDefinition... objectDefinitions)
		throws Exception {

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			ObjectDefinitionTestUtil.publishObjectDefinition(objectDefinition);
		}
	}

	private void _testDeleteCustomObjectDefinition1WithCustomObjectDefinition2(
			String deleteEndpoint, String getEndpoint)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, getEndpoint, Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		HTTPTestUtil.invoke(null, deleteEndpoint, Http.Method.DELETE);

		jsonObject = HTTPTestUtil.invoke(null, getEndpoint, Http.Method.GET);

		itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, itemsJSONArray.length());
	}

	private void
			_testDeleteCustomObjectDefinition1WithCustomObjectDefinition2NotFound(
				String deleteEndpoint1, String deleteEndpoint2,
				String getEndpoint)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, deleteEndpoint1, Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = HTTPTestUtil.invoke(
			null, deleteEndpoint2, Http.Method.DELETE);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		jsonObject = HTTPTestUtil.invoke(null, getEndpoint, Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());
	}

	private void _testFiltersGetFilteredObjectEntriesByRelatedObjectEntries()
		throws Exception {

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "true"
			).build());

		ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.getObjectDefinition(
				_objectRelationship.getObjectDefinitionId1());

		ObjectDefinition objectDefinition2 =
			_objectDefinitionLocalService.getObjectDefinition(
				_objectRelationship.getObjectDefinitionId2());

		_createAndRelateObjectsEntries(objectDefinition1, objectDefinition2);

		for (Operation operation : Operation.values()) {
			if ((operation == Operation.eq) || (operation == Operation.ge) ||
				(operation == Operation.le)) {

				_testGetFilteredObjectEntriesByRelatedObjectEntries(
					objectDefinition1, objectDefinition2, operation, 0);
			}
			else if ((operation == Operation.gt) ||
					 (operation == Operation.ne)) {

				_testGetFilteredObjectEntriesByRelatedObjectEntries(
					objectDefinition1, objectDefinition2, operation, -1);
			}
			else if (operation == Operation.lt) {
				_testGetFilteredObjectEntriesByRelatedObjectEntries(
					objectDefinition1, objectDefinition2, operation, 1);
			}
			else {
				throw new Exception(
					"Operation " + operation.name() + " not supported");
			}
		}

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "false"
			).build());
	}

	private void _testGetFilteredObjectEntriesByRelatedObjectEntries(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, Operation operation,
			int addToValue)
		throws Exception {

		_testGetFilteredObjectEntriesByRelatedObjectEntries(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1, objectDefinition1,
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 + addToValue,
			operation);

		_testGetFilteredObjectEntriesByRelatedObjectEntries(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2, objectDefinition2,
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + addToValue,
			operation);
	}

	private void _testGetFilteredObjectEntriesByRelatedObjectEntries(
			String expectedObjectFieldName, int expectedObjectFieldValue,
			ObjectDefinition objectDefinition, String objectFieldName,
			int objectFieldValue, Operation operation)
		throws Exception {

		String endpoint = StringBundler.concat(
			objectDefinition.getRESTContextPath(), "?filter=",
			_objectRelationship.getName(), StringPool.SLASH, objectFieldName,
			"%20", operation.name(), "%20", objectFieldValue);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, endpoint, Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			expectedObjectFieldValue,
			itemJSONObject.getInt(expectedObjectFieldName));
	}

	private void _testGetNestedFieldDetailsInOneToManyRelationships(
			String endpoint, String expectedFieldName)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, endpoint, Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_2, itemJSONObject.getInt(_OBJECT_FIELD_NAME_2));

		JSONObject relatedObjectJSONObject = itemJSONObject.getJSONObject(
			expectedFieldName);

		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_1,
			relatedObjectJSONObject.getInt(_OBJECT_FIELD_NAME_1));
	}

	private static final String _OBJECT_FIELD_NAME_1 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_2 =
		"x" + RandomTestUtil.randomString();

	private static final int _OBJECT_FIELD_VALUE_1 = RandomTestUtil.randomInt();

	private static final int _OBJECT_FIELD_VALUE_2 = RandomTestUtil.randomInt();

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final List<ObjectDefinition> _objectDefinitions = new ArrayList<>();

	@DeleteAfterTestRun
	private ObjectEntry _objectEntry1;

	@DeleteAfterTestRun
	private ObjectEntry _objectEntry2;

	private ObjectRelationship _objectRelationship;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private enum Operation {

		eq, ge, gt, le, lt, ne

	}

}