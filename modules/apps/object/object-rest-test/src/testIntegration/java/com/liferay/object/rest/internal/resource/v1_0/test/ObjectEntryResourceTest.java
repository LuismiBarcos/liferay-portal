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
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.internal.util.FilterURLCreatorUtil;
import com.liferay.object.rest.internal.util.HTTPTestUtil;
import com.liferay.object.rest.internal.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.internal.util.ObjectEntryTestUtil;
import com.liferay.object.rest.internal.util.ObjectFieldTestUtil;
import com.liferay.object.rest.internal.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import javax.ws.rs.NotSupportedException;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-153117", "true"
			).build());
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-164801", "true"
			).build());
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-176651", "true"
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-153117", "false"
			).build());
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-164801", "false"
			).build());
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-176651", "false"
			).build());
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectFieldTestUtil.createDefaultObjectFieldList(
				_OBJECT_FIELD_NAME_1));
		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectFieldTestUtil.createDefaultObjectFieldList(
				_OBJECT_FIELD_NAME_2));

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

		_siteScopedObjectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectFieldTestUtil.createDefaultObjectFieldList(
					_OBJECT_FIELD_NAME_2),
				ObjectDefinitionConstants.SCOPE_SITE);

		_siteScopedObjectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_siteScopedObjectDefinition1, _OBJECT_FIELD_NAME_1,
			_OBJECT_FIELD_VALUE_1);
	}

	@After
	public void tearDown() throws Exception {
		if (_objectRelationship != null) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship);
		}

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition1);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition2);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_siteScopedObjectDefinition1);
	}

	@Test
	public void testFilterObjectEntriesByRelatedObjectEntries()
		throws Exception {

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "true"
			).build());

		_testFilterObjectEntriesByRelatedObjectEntries();

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "false"
			).build());
	}

	@Test
	public void testFilterWithComparisonOperatorObjectEntriesByRelatedObjectEntries()
		throws Exception {

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "true"
			).build());

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		for (FilterURLCreatorUtil.FilterOperator.ComparisonOperator
				comparisonOperator :
					FilterURLCreatorUtil.FilterOperator.ComparisonOperator.
						values()) {

			_testFilterWithComparisonOperator(
				comparisonOperator, _objectRelationship);
		}

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		for (FilterURLCreatorUtil.FilterOperator.ComparisonOperator
				comparisonOperator :
					FilterURLCreatorUtil.FilterOperator.ComparisonOperator.
						values()) {

			_testFilterWithComparisonOperator(
				comparisonOperator, _objectRelationship);
		}

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "false"
			).build());
	}

	@Test
	public void testFilterWithLogicalOperatorObjectEntriesByRelatedObjectEntries()
		throws Exception {

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "true"
			).build());

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		for (FilterURLCreatorUtil.FilterOperator.LogicalOperator
				logicalOperator :
					FilterURLCreatorUtil.FilterOperator.LogicalOperator.
						values()) {

			_testFilterWithLogicalOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				logicalOperator, _objectRelationship);

			_testFilterWithLogicalOperatorObjectEntriesByRelatedSystemFieldsInBothSidesOfRelationship(
				logicalOperator, _objectRelationship);
		}

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		for (FilterURLCreatorUtil.FilterOperator.LogicalOperator
				logicalOperator :
					FilterURLCreatorUtil.FilterOperator.LogicalOperator.
						values()) {

			_testFilterWithLogicalOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				logicalOperator, _objectRelationship);

			_testFilterWithLogicalOperatorObjectEntriesByRelatedSystemFieldsInBothSidesOfRelationship(
				logicalOperator, _objectRelationship);
		}

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "false"
			).build());
	}

	@Test
	public void testFilterWithStringFunctionsObjectEntriesByRelatedObjectEntries()
		throws Exception {

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "true"
			).build());

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1,
			String.valueOf(_OBJECT_FIELD_VALUE_1));

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2,
			String.valueOf(_OBJECT_FIELD_VALUE_2));

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		for (FilterURLCreatorUtil.FilterOperator.StringOperator stringOperator :
				FilterURLCreatorUtil.FilterOperator.StringOperator.values()) {

			_testFilterWithStringOperator(stringOperator, _objectRelationship);
		}

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		for (FilterURLCreatorUtil.FilterOperator.StringOperator stringOperator :
				FilterURLCreatorUtil.FilterOperator.StringOperator.values()) {

			_testFilterWithStringOperator(stringOperator, _objectRelationship);
		}

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "false"
			).build());
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
	public void testGetObjectEntryFilteredByKeywords() throws Exception {
		_postObjectEntryWithKeywords("tag1");
		_postObjectEntryWithKeywords("tag1", "tag2");
		_postObjectEntryWithKeywords("tag1", "tag2", "tag3");

		_assertFilteredObjectEntries(3, "keywords/any(k:k eq 'tag1')");
		_assertFilteredObjectEntries(2, "keywords/any(k:k eq 'tag2')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k eq 'tag3')");
		_assertFilteredObjectEntries(0, "keywords/any(k:k eq '1234')");

		_assertFilteredObjectEntries(2, "keywords/any(k:k ne 'tag1')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k ne 'tag2')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k ne 'tag3')");

		_assertFilteredObjectEntries(2, "keywords/any(k:k gt 'tag1')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k gt 'tag2')");
		_assertFilteredObjectEntries(0, "keywords/any(k:k gt 'tag3')");

		_assertFilteredObjectEntries(3, "keywords/any(k:k ge 'tag1')");
		_assertFilteredObjectEntries(2, "keywords/any(k:k ge 'tag2')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k ge 'tag3')");

		_assertFilteredObjectEntries(0, "keywords/any(k:k lt 'tag1')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k lt 'tag2')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k lt 'tag3')");

		_assertFilteredObjectEntries(3, "keywords/any(k:k le 'tag1')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k le 'tag2')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k le 'tag3')");

		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'t'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'ta'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'tag'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'tag1'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:startswith(k,'tag2'))");
		_assertFilteredObjectEntries(1, "keywords/any(k:startswith(k,'tag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:startswith(k,'1234'))");

		_assertFilteredObjectEntries(3, "keywords/any(k:contains(k,'tag'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:contains(k,'ag1'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:contains(k,'ag2'))");
		_assertFilteredObjectEntries(1, "keywords/any(k:contains(k,'ag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:contains(k,'1234'))");

		_assertFilteredObjectEntries(3, "keywords/any(k:k in ('tag1','tag2'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:k in ('tag2','tag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:k in ('1234','5678'))");
	}

	@Test
	public void testGetObjectEntryWithKeywords() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"keywords", JSONUtil.putAll("tag1", "tag2")
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		jsonObject = HTTPTestUtil.invoke(
			null,
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.GET);

		JSONArray keywordsJSONArray = jsonObject.getJSONArray("keywords");

		Assert.assertEquals("tag1", keywordsJSONArray.get(0));
		Assert.assertEquals("tag2", keywordsJSONArray.get(1));

		jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				"keywords", JSONUtil.putAll("tag1", "tag2", "tag3")
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		jsonObject = HTTPTestUtil.invoke(
			null,
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.GET);

		keywordsJSONArray = jsonObject.getJSONArray("keywords");

		Assert.assertEquals("tag1", keywordsJSONArray.get(0));
		Assert.assertEquals("tag2", keywordsJSONArray.get(1));
		Assert.assertEquals("tag3", keywordsJSONArray.get(2));
	}

	@Test
	public void testGetObjectRelationshipERCFieldNameInOneToManyRelationship()
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
	public void testGetObjectRelationshipERCFieldNameInOneToManyRelationshipFromRelatedObjectEntry()
		throws Exception {

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-161364", "true"
			).build());

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), "?nestedFields=",
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		JSONArray relationshipJSONArray = itemJSONObject.getJSONArray(
			_objectRelationship.getName());

		Assert.assertEquals(1, relationshipJSONArray.length());

		JSONObject relatedObjectEntryJSONObject =
			relationshipJSONArray.getJSONObject(0);

		Assert.assertEquals(
			relatedObjectEntryJSONObject.getString(
				_objectRelationship.getName() + "ERC"),
			_objectEntry1.getExternalReferenceCode());

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-161364", "false"
			).build());
	}

	@Test
	public void testGetScopeScopeKeyObjectEntriesPage() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			JSONObject jsonObject = HTTPTestUtil.invoke(
				null,
				_siteScopedObjectDefinition1.getRESTContextPath() + "/scopes/" +
					RandomTestUtil.randomLong(),
				Http.Method.GET);

			Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));
		}

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			_siteScopedObjectDefinition1.getRESTContextPath() + "/scopes/" +
				TestPropsValues.getGroupId(),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			itemJSONObject.getLong("id"),
			_siteScopedObjectEntry1.getObjectEntryId());
	}

	@Test
	public void testPatchObjectEntryWithKeywords() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"keywords", JSONUtil.putAll("tag1", "tag2")
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		HTTPTestUtil.invoke(
			JSONUtil.put(
				"keywords", JSONUtil.putAll("tag1", "tag2", "tag3")
			).toString(),
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.PATCH);

		jsonObject = HTTPTestUtil.invoke(
			null,
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.GET);

		JSONArray keywordsJSONArray = jsonObject.getJSONArray("keywords");

		Assert.assertEquals("tag1", keywordsJSONArray.get(0));
		Assert.assertEquals("tag2", keywordsJSONArray.get(1));
		Assert.assertEquals("tag3", keywordsJSONArray.get(2));
	}

	@Test
	public void testPatchSiteScopedObject() throws Exception {
		String newObjectFieldValue = RandomTestUtil.randomString();

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_OBJECT_FIELD_NAME_1, newObjectFieldValue);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			StringBundler.concat(
				_siteScopedObjectDefinition1.getRESTContextPath(), "/scopes/",
				String.valueOf(TestPropsValues.getGroupId()),
				"/by-external-reference-code/",
				_siteScopedObjectEntry1.getExternalReferenceCode()),
			Http.Method.PATCH);

		Assert.assertEquals(
			jsonObject.getString(_OBJECT_FIELD_NAME_1), newObjectFieldValue);
	}

	@Test
	public void testPostCustomObjectEntryWithInvalidNestedCustomObjectEntries()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.WARN)) {

			_objectRelationship =
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectDefinition1, _objectDefinition2,
					TestPropsValues.getUserId(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToManyRelationship(
				_objectDefinition1.getRESTContextPath(), _objectRelationship);

			_objectRelationship =
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectDefinition1, _objectDefinition2,
					TestPropsValues.getUserId(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToOneRelationship(
				_objectDefinition2.getRESTContextPath(), _objectRelationship);

			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInOneToManyRelationship(
				_objectDefinition1.getRESTContextPath(), _objectRelationship);
		}
	}

	@Test
	public void testPostCustomObjectEntryWithNestedCustomObjectEntriesInManyToManyRelationship()
		throws Exception {

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		String objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=", _objectRelationship.getName()),
			Http.Method.GET);

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);
	}

	@Test
	public void testPostCustomObjectEntryWithNestedCustomObjectEntriesInManyToOneRelationship()
		throws Exception {

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition2.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		String objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=",
				StringBundler.concat(
					"r_", _objectRelationship.getName(), "_",
					StringUtil.replaceLast(
						_objectDefinition1.getPKObjectFieldName(), "Id", ""))),
			Http.Method.GET);

		_assertObjectEntryField(
			jsonObject.getJSONObject(
				StringBundler.concat(
					"r_", _objectRelationship.getName(), "_",
					StringUtil.replaceLast(
						_objectDefinition1.getPKObjectFieldName(), "Id", ""))),
			_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1);
	}

	@Test
	public void testPostCustomObjectEntryWithNestedCustomObjectEntriesInOneToManyRelationship()
		throws Exception {

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		String objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=", _objectRelationship.getName()),
			Http.Method.GET);

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);
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

	@Test
	public void testPutCustomObjectEntryWithNestedCustomObjectEntriesInManyToManyRelationship()
		throws Exception {

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				}));

		HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			newObjectEntryJSONObject.toString(),
			com.liferay.petra.string.StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		jsonObject = HTTPTestUtil.invoke(
			null,
			com.liferay.petra.string.StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), "?nestedFields=",
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);
	}

	@Test
	public void testPutCustomObjectEntryWithNestedCustomObjectEntriesInManyToOneRelationship()
		throws Exception {

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition2.getRESTContextPath(), Http.Method.POST);

		String objectEntryId = jsonObject.getString("id");

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString()));

		jsonObject = HTTPTestUtil.invoke(
			newObjectEntryJSONObject.toString(),
			com.liferay.petra.string.StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				objectEntryId),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=",
				StringBundler.concat(
					"r_", _objectRelationship.getName(), "_",
					StringUtil.replaceLast(
						_objectDefinition1.getPKObjectFieldName(), "Id", ""))),
			Http.Method.GET);

		_assertObjectEntryField(
			jsonObject.getJSONObject(
				StringBundler.concat(
					"r_", _objectRelationship.getName(), "_",
					StringUtil.replaceLast(
						_objectDefinition1.getPKObjectFieldName(), "Id", ""))),
			_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1);
	}

	@Test
	public void testPutCustomObjectEntryWithNestedCustomObjectEntriesInOneToManyRelationship()
		throws Exception {

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				}));

		HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			newObjectEntryJSONObject.toString(),
			com.liferay.petra.string.StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		jsonObject = HTTPTestUtil.invoke(
			null,
			com.liferay.petra.string.StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), "?nestedFields=",
				_objectRelationship.getName()),
			Http.Method.GET);

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);
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

	private void _assertFilteredObjectEntries(
			int expectedObjectEntryCount, String filter)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			_objectDefinition1.getRESTContextPath() + "?filter=" +
				URLCodec.encodeURL(filter),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(expectedObjectEntryCount, itemsJSONArray.length());
	}

	private void _assertObjectEntryField(
		JSONObject objectEntryJSONObject, String objectFieldName,
		String objectFieldValue) {

		int objectEntryId = objectEntryJSONObject.getInt("id");

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			objectEntryId);

		Assert.assertEquals(
			MapUtil.getString(objectEntry.getValues(), objectFieldName),
			objectFieldValue);
	}

	private String _buildRelationshipsPropertyNameSyntax(
		ObjectRelationship objectRelationship, String propertyName) {

		return StringBundler.concat(
			objectRelationship.getName(), StringPool.SLASH, propertyName);
	}

	private <T> String _createFilter(
		FilterURLCreatorUtil.FilterOperator filterOperator, String leftFilter,
		String propertyName, T propertyValue, String rightFilter) {

		if (filterOperator instanceof
				FilterURLCreatorUtil.FilterOperator.ComparisonOperator) {

			return FilterURLCreatorUtil.createFilterWithComparisonOperator(
				(FilterURLCreatorUtil.FilterOperator.ComparisonOperator)
					filterOperator,
				propertyName, propertyValue);
		}
		else if (filterOperator instanceof
					FilterURLCreatorUtil.FilterOperator.LambdaOperator) {

			return "";
		}
		else if (filterOperator instanceof
					FilterURLCreatorUtil.FilterOperator.ListOperator) {

			return FilterURLCreatorUtil.createFilterWithListOperator(
				(FilterURLCreatorUtil.FilterOperator.ListOperator)
					filterOperator,
				propertyName, propertyValue);
		}
		else if (filterOperator instanceof
					FilterURLCreatorUtil.FilterOperator.LogicalOperator) {

			return FilterURLCreatorUtil.createFilterWithLogicalOperators(
				leftFilter,
				(FilterURLCreatorUtil.FilterOperator.LogicalOperator)
					filterOperator,
				rightFilter);
		}
		else if (filterOperator instanceof
					FilterURLCreatorUtil.FilterOperator.StringOperator) {

			return FilterURLCreatorUtil.createFilterWithStringOperator(
				propertyName, (String)propertyValue,
				(FilterURLCreatorUtil.FilterOperator.StringOperator)
					filterOperator);
		}

		throw new NotSupportedException(
			"Filter " + filterOperator.toString() + " is not supported");
	}

	private JSONArray _createObjectEntriesJSONArray(
			String[] externalReferenceCodeValues, String objectFieldName,
			String[] objectFieldValues)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < objectFieldValues.length; i++) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					objectFieldName, objectFieldValues[i]
				).put(
					"externalReferenceCode", externalReferenceCodeValues[i]
				).toString());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private String _escape(String string) {
		return URLCodec.encodeURL(string);
	}

	private void _postObjectEntryWithKeywords(String... keywords)
		throws Exception {

		HTTPTestUtil.invoke(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"keywords", JSONUtil.putAll(keywords)
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);
	}

	private <T> void _testFilterByRelatedObjectDefinitionSystemObjectField(
			String expectedObjectFieldName, T expectedObjectFieldValue,
			FilterURLCreatorUtil.FilterOperator filterOperator,
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship, T relatedObjectEntryValue,
			String systemPropertyName)
		throws Exception {

		_testFilterByRelatedObjectDefinitionSystemObjectField(
			expectedObjectFieldName, expectedObjectFieldValue, filterOperator,
			StringPool.BLANK, objectDefinition, objectRelationship,
			relatedObjectEntryValue, StringPool.BLANK, systemPropertyName);
	}

	private <T> void _testFilterByRelatedObjectDefinitionSystemObjectField(
			String expectedObjectFieldName, T expectedObjectFieldValue,
			FilterURLCreatorUtil.FilterOperator filterOperator,
			String leftFilter, ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship, T relatedObjectEntryValue,
			String rightFilter, String systemPropertyName)
		throws Exception {

		_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
			expectedObjectFieldName, expectedObjectFieldValue, filterOperator,
			_buildRelationshipsPropertyNameSyntax(
				objectRelationship, systemPropertyName),
			leftFilter, objectDefinition, relatedObjectEntryValue, rightFilter);
	}

	private void _testFilterObjectEntriesByRelatedObjectEntries()
		throws Exception {

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		for (FilterURLCreatorUtil.FilterOperator filterOperator :
				FilterURLCreatorUtil.getFilterOperators()) {

			_testFilterWithComparisonOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				_objectRelationship, filterOperator, 0);
		}

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);

		_objectRelationship = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		for (FilterURLCreatorUtil.FilterOperator filterOperator :
				FilterURLCreatorUtil.getFilterOperators()) {

			_testFilterWithComparisonOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				_objectRelationship, filterOperator, 0);
		}
	}

	private <T> void
			_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
				String endpoint, String expectedObjectFieldName,
				T expectedObjectFieldValue)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, endpoint, Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			expectedObjectFieldValue,
			itemJSONObject.getInt(expectedObjectFieldName));
	}

	private <T extends Serializable> void
			_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
				String expectedObjectFieldName, T expectedObjectFieldValue,
				FilterURLCreatorUtil.FilterOperator filterOperator,
				ObjectDefinition objectDefinition,
				ObjectRelationship objectRelationship,
				String relatedObjectFieldName, T relatedObjectFieldValue)
		throws Exception {

		_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
			expectedObjectFieldName, expectedObjectFieldValue, filterOperator,
			StringPool.BLANK, objectDefinition, objectRelationship,
			relatedObjectFieldName, StringPool.BLANK, relatedObjectFieldValue);
	}

	private <T extends Serializable> void
			_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
				String expectedObjectFieldName, T expectedObjectFieldValue,
				FilterURLCreatorUtil.FilterOperator filterOperator,
				String leftFilter, ObjectDefinition objectDefinition,
				ObjectRelationship objectRelationship,
				String relatedObjectFieldName, String rightFilter,
				T relatedObjectFieldValue)
		throws Exception {

		_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
			expectedObjectFieldName, expectedObjectFieldValue, filterOperator,
			_buildRelationshipsPropertyNameSyntax(
				objectRelationship, relatedObjectFieldName),
			leftFilter, objectDefinition, relatedObjectFieldValue, rightFilter);
	}

	private <T> void
			_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
				String expectedObjectFieldName, T expectedObjectFieldValue,
				FilterURLCreatorUtil.FilterOperator filterOperator,
				String filterPropertyName, String leftFilter,
				ObjectDefinition objectDefinition, T relatedObjectEntryValue,
				String rightFilter)
		throws Exception {

		String filter = _escape(
			_createFilter(
				filterOperator, leftFilter, filterPropertyName,
				relatedObjectEntryValue, rightFilter));

		String endpoint = StringBundler.concat(
			objectDefinition.getRESTContextPath(), "?filter=", filter);

		_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
			endpoint, expectedObjectFieldName, expectedObjectFieldValue);
	}

	private void _testFilterWithComparisonOperator(
			FilterURLCreatorUtil.FilterOperator.ComparisonOperator
				comparisonOperator,
			ObjectRelationship objectRelationship)
		throws Exception {

		if ((comparisonOperator ==
				FilterURLCreatorUtil.FilterOperator.ComparisonOperator.EQ) ||
			(comparisonOperator ==
				FilterURLCreatorUtil.FilterOperator.ComparisonOperator.GE) ||
			(comparisonOperator ==
				FilterURLCreatorUtil.FilterOperator.ComparisonOperator.LE)) {

			_testFilterWithComparisonOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				objectRelationship, comparisonOperator, 0);

			_testFilterWithComparisonOperatorObjectsEntriesByRelatedObjectEntriesSystemObjectFieldInBothSidesOfRelationship(
				comparisonOperator, objectRelationship, 0);
		}
		else if ((comparisonOperator ==
					FilterURLCreatorUtil.FilterOperator.ComparisonOperator.
						GT) ||
				 (comparisonOperator ==
					 FilterURLCreatorUtil.FilterOperator.ComparisonOperator.
						 NE)) {

			_testFilterWithComparisonOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				objectRelationship, comparisonOperator, -1);

			_testFilterWithComparisonOperatorObjectsEntriesByRelatedObjectEntriesSystemObjectFieldInBothSidesOfRelationship(
				comparisonOperator, objectRelationship, -1);
		}
		else if (comparisonOperator ==
					FilterURLCreatorUtil.FilterOperator.ComparisonOperator.LT) {

			_testFilterWithComparisonOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				objectRelationship, comparisonOperator, 1);

			_testFilterWithComparisonOperatorObjectsEntriesByRelatedObjectEntriesSystemObjectFieldInBothSidesOfRelationship(
				comparisonOperator, objectRelationship, 1);
		}
		else {
			throw new IllegalStateException(
				"Unexpected value: " + comparisonOperator);
		}
	}

	private void
			_testFilterWithComparisonOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				ObjectRelationship objectRelationship,
				FilterURLCreatorUtil.FilterOperator filterOperator,
				int addToValue)
		throws Exception {

		_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1, filterOperator,
			_objectDefinition1, objectRelationship, _OBJECT_FIELD_NAME_2,
			_OBJECT_FIELD_VALUE_2 + addToValue);
		_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2, filterOperator,
			_objectDefinition2, objectRelationship, _OBJECT_FIELD_NAME_1,
			_OBJECT_FIELD_VALUE_1 + addToValue);
	}

	private void
			_testFilterWithComparisonOperatorObjectsEntriesByRelatedObjectEntriesSystemObjectFieldInBothSidesOfRelationship(
				FilterURLCreatorUtil.FilterOperator.ComparisonOperator
					filterOperator,
				ObjectRelationship objectRelationship, int addToValue)
		throws Exception {

		_testFilterByRelatedObjectDefinitionSystemObjectField(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1, filterOperator,
			_objectDefinition1, objectRelationship,
			_objectEntry2.getObjectEntryId() + addToValue, "id");

		_testFilterByRelatedObjectDefinitionSystemObjectField(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2, filterOperator,
			_objectDefinition2, objectRelationship,
			_objectEntry1.getObjectEntryId() + addToValue, "id");
	}

	private void
			_testFilterWithLogicalOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				FilterURLCreatorUtil.FilterOperator.LogicalOperator
					logicalOperator,
				ObjectRelationship objectRelationship)
		throws Exception {

		String leftFilter =
			FilterURLCreatorUtil.createFilterWithComparisonOperator(
				FilterURLCreatorUtil.FilterOperator.ComparisonOperator.EQ,
				_buildRelationshipsPropertyNameSyntax(
					objectRelationship, _OBJECT_FIELD_NAME_2),
				_OBJECT_FIELD_VALUE_2);

		String rightFilter =
			FilterURLCreatorUtil.createFilterWithComparisonOperator(
				FilterURLCreatorUtil.FilterOperator.ComparisonOperator.LE,
				_buildRelationshipsPropertyNameSyntax(
					objectRelationship, _OBJECT_FIELD_NAME_2),
				_OBJECT_FIELD_VALUE_2);

		if (logicalOperator ==
				FilterURLCreatorUtil.FilterOperator.LogicalOperator.NOT) {

			leftFilter =
				FilterURLCreatorUtil.createFilterWithComparisonOperator(
					FilterURLCreatorUtil.FilterOperator.ComparisonOperator.LT,
					_buildRelationshipsPropertyNameSyntax(
						objectRelationship, _OBJECT_FIELD_NAME_2),
					_OBJECT_FIELD_VALUE_2);
		}

		_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1, logicalOperator,
			leftFilter, _objectDefinition1, objectRelationship,
			_OBJECT_FIELD_NAME_2, rightFilter, _OBJECT_FIELD_VALUE_2);

		leftFilter = FilterURLCreatorUtil.createFilterWithComparisonOperator(
			FilterURLCreatorUtil.FilterOperator.ComparisonOperator.EQ,
			_buildRelationshipsPropertyNameSyntax(
				objectRelationship, _OBJECT_FIELD_NAME_1),
			_OBJECT_FIELD_VALUE_1);

		rightFilter = FilterURLCreatorUtil.createFilterWithComparisonOperator(
			FilterURLCreatorUtil.FilterOperator.ComparisonOperator.LE,
			_buildRelationshipsPropertyNameSyntax(
				objectRelationship, _OBJECT_FIELD_NAME_1),
			_OBJECT_FIELD_VALUE_1);

		if (logicalOperator ==
				FilterURLCreatorUtil.FilterOperator.LogicalOperator.NOT) {

			leftFilter =
				FilterURLCreatorUtil.createFilterWithComparisonOperator(
					FilterURLCreatorUtil.FilterOperator.ComparisonOperator.LT,
					_buildRelationshipsPropertyNameSyntax(
						objectRelationship, _OBJECT_FIELD_NAME_1),
					_OBJECT_FIELD_VALUE_1);
		}

		_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2, logicalOperator,
			leftFilter, _objectDefinition2, objectRelationship,
			_OBJECT_FIELD_NAME_1, rightFilter, _OBJECT_FIELD_VALUE_1);
	}

	private void
			_testFilterWithLogicalOperatorObjectEntriesByRelatedSystemFieldsInBothSidesOfRelationship(
				FilterURLCreatorUtil.FilterOperator.LogicalOperator
					logicalOperator,
				ObjectRelationship objectRelationship)
		throws Exception {

		String leftFilter =
			FilterURLCreatorUtil.createFilterWithComparisonOperator(
				FilterURLCreatorUtil.FilterOperator.ComparisonOperator.EQ,
				_buildRelationshipsPropertyNameSyntax(
					objectRelationship, _OBJECT_FIELD_NAME_2),
				_OBJECT_FIELD_VALUE_2);

		String rightFilter =
			FilterURLCreatorUtil.createFilterWithComparisonOperator(
				FilterURLCreatorUtil.FilterOperator.ComparisonOperator.LE,
				_buildRelationshipsPropertyNameSyntax(
					objectRelationship, _OBJECT_FIELD_NAME_2),
				_OBJECT_FIELD_VALUE_2);

		if (logicalOperator ==
				FilterURLCreatorUtil.FilterOperator.LogicalOperator.NOT) {

			leftFilter =
				FilterURLCreatorUtil.createFilterWithComparisonOperator(
					FilterURLCreatorUtil.FilterOperator.ComparisonOperator.LT,
					_buildRelationshipsPropertyNameSyntax(
						objectRelationship, _OBJECT_FIELD_NAME_2),
					_OBJECT_FIELD_VALUE_2);
		}

		_testFilterByRelatedObjectDefinitionSystemObjectField(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1, logicalOperator,
			leftFilter, _objectDefinition1, objectRelationship,
			_OBJECT_FIELD_NAME_2, rightFilter, "id");

		leftFilter = FilterURLCreatorUtil.createFilterWithComparisonOperator(
			FilterURLCreatorUtil.FilterOperator.ComparisonOperator.EQ,
			_buildRelationshipsPropertyNameSyntax(
				objectRelationship, _OBJECT_FIELD_NAME_1),
			_OBJECT_FIELD_VALUE_1);

		rightFilter = FilterURLCreatorUtil.createFilterWithComparisonOperator(
			FilterURLCreatorUtil.FilterOperator.ComparisonOperator.LE,
			_buildRelationshipsPropertyNameSyntax(
				objectRelationship, _OBJECT_FIELD_NAME_1),
			_OBJECT_FIELD_VALUE_1);

		if (logicalOperator ==
				FilterURLCreatorUtil.FilterOperator.LogicalOperator.NOT) {

			leftFilter =
				FilterURLCreatorUtil.createFilterWithComparisonOperator(
					FilterURLCreatorUtil.FilterOperator.ComparisonOperator.LT,
					_buildRelationshipsPropertyNameSyntax(
						objectRelationship, _OBJECT_FIELD_NAME_1),
					_OBJECT_FIELD_VALUE_1);
		}

		_testFilterByRelatedObjectDefinitionSystemObjectField(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2, logicalOperator,
			leftFilter, _objectDefinition2, objectRelationship,
			_OBJECT_FIELD_NAME_1, rightFilter, "id");
	}

	private void _testFilterWithStringOperator(
			FilterURLCreatorUtil.FilterOperator.StringOperator stringOperator,
			ObjectRelationship objectRelationship)
		throws Exception {

		if (stringOperator ==
				FilterURLCreatorUtil.FilterOperator.StringOperator.CONTAINS) {

			_testFilterWithStringOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				1, 2, stringOperator, objectRelationship);

			_testFilterWithStringOperatorObjectsEntriesByRelatedObjectEntriesSystemObjectFieldInBothSidesOfRelationship(
				1, 2, stringOperator, objectRelationship);
		}
		else if (stringOperator ==
					FilterURLCreatorUtil.FilterOperator.StringOperator.
						STARTSWITH) {

			_testFilterWithStringOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				0, 1, stringOperator, objectRelationship);

			_testFilterWithStringOperatorObjectsEntriesByRelatedObjectEntriesSystemObjectFieldInBothSidesOfRelationship(
				0, 1, stringOperator, objectRelationship);
		}
		else {
			throw new IllegalStateException(
				"Unexpected value: " + stringOperator);
		}
	}

	private void
			_testFilterWithStringOperatorObjectEntriesByRelatedObjectEntriesInBothSidesOfRelationship(
				int beginIndex, int endIndex,
				FilterURLCreatorUtil.FilterOperator filterOperator,
				ObjectRelationship objectRelationship)
		throws Exception {

		String relatedObjectFieldStringValue = String.valueOf(
			_OBJECT_FIELD_VALUE_2);

		_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1, filterOperator,
			_objectDefinition1, objectRelationship, _OBJECT_FIELD_NAME_2,
			relatedObjectFieldStringValue.substring(beginIndex, endIndex));

		relatedObjectFieldStringValue = String.valueOf(_OBJECT_FIELD_VALUE_1);

		_testFilterObjectEntriesByRelatedObjectEntriesUsingAFilterOperator(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2, filterOperator,
			_objectDefinition2, objectRelationship, _OBJECT_FIELD_NAME_1,
			relatedObjectFieldStringValue.substring(beginIndex, endIndex));
	}

	private void
			_testFilterWithStringOperatorObjectsEntriesByRelatedObjectEntriesSystemObjectFieldInBothSidesOfRelationship(
				int beginIndex, int endIndex,
				FilterURLCreatorUtil.FilterOperator.StringOperator
					filterOperator,
				ObjectRelationship objectRelationship)
		throws Exception {

		String relatedObjectFieldStringValue =
			_objectEntry2.getExternalReferenceCode();

		_testFilterByRelatedObjectDefinitionSystemObjectField(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1, filterOperator,
			_objectDefinition1, objectRelationship,
			relatedObjectFieldStringValue.substring(beginIndex, endIndex),
			"externalReferenceCode");

		relatedObjectFieldStringValue =
			_objectEntry1.getExternalReferenceCode();

		_testFilterByRelatedObjectDefinitionSystemObjectField(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2, filterOperator,
			_objectDefinition2, objectRelationship,
			relatedObjectFieldStringValue.substring(beginIndex, endIndex),
			"externalReferenceCode");
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

	private void
			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToManyRelationship(
				String objectDefinitionRESTContextPath,
				ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			objectRelationship.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2
				).put(
					"externalReferenceCode", _ERC_VALUE_2
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(), objectDefinitionRESTContextPath,
			Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
	}

	private void
			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToOneRelationship(
				String objectDefinitionRESTContextPath,
				ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			objectRelationship.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_1,
				new String[] {
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				}));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(), objectDefinitionRESTContextPath,
			Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
	}

	private void
			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInOneToManyRelationship(
				String objectDefinitionRESTContextPath,
				ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			objectRelationship.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2
				).put(
					"externalReferenceCode", _ERC_VALUE_2
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(), objectDefinitionRESTContextPath,
			Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
	}

	private static final String _ERC_VALUE_1 = RandomTestUtil.randomString();

	private static final String _ERC_VALUE_2 = RandomTestUtil.randomString();

	private static final String _NEW_OBJECT_FIELD_VALUE_1 =
		RandomTestUtil.randomString();

	private static final String _NEW_OBJECT_FIELD_VALUE_2 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_1 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_2 =
		"x" + RandomTestUtil.randomString();

	private static final int _OBJECT_FIELD_VALUE_1 = RandomTestUtil.randomInt();

	private static final int _OBJECT_FIELD_VALUE_2 = RandomTestUtil.randomInt();

	private ObjectDefinition _objectDefinition1;
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntry _objectEntry1;
	private ObjectEntry _objectEntry2;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ObjectRelationship _objectRelationship;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private ObjectDefinition _siteScopedObjectDefinition1;
	private ObjectEntry _siteScopedObjectEntry1;

}