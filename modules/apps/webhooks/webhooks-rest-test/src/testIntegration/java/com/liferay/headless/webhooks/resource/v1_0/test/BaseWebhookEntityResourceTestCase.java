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

package com.liferay.headless.webhooks.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.webhooks.client.dto.v1_0.WebhookEntity;
import com.liferay.headless.webhooks.client.http.HttpInvoker;
import com.liferay.headless.webhooks.client.pagination.Page;
import com.liferay.headless.webhooks.client.resource.v1_0.WebhookEntityResource;
import com.liferay.headless.webhooks.client.serdes.v1_0.WebhookEntitySerDes;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Luis Miguel Barcos
 * @generated
 */
@Generated("")
public abstract class BaseWebhookEntityResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_webhookEntityResource.setContextCompany(testCompany);

		WebhookEntityResource.Builder builder = WebhookEntityResource.builder();

		webhookEntityResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		WebhookEntity webhookEntity1 = randomWebhookEntity();

		String json = objectMapper.writeValueAsString(webhookEntity1);

		WebhookEntity webhookEntity2 = WebhookEntitySerDes.toDTO(json);

		Assert.assertTrue(equals(webhookEntity1, webhookEntity2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		WebhookEntity webhookEntity = randomWebhookEntity();

		String json1 = objectMapper.writeValueAsString(webhookEntity);
		String json2 = WebhookEntitySerDes.toJSON(webhookEntity);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		WebhookEntity webhookEntity = randomWebhookEntity();

		webhookEntity.setEntity(regex);

		String json = WebhookEntitySerDes.toJSON(webhookEntity);

		Assert.assertFalse(json.contains(regex));

		webhookEntity = WebhookEntitySerDes.toDTO(json);

		Assert.assertEquals(regex, webhookEntity.getEntity());
	}

	@Test
	public void testGetSiteWebhooksEntitiesPage() throws Exception {
		Long siteId = testGetSiteWebhooksEntitiesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteWebhooksEntitiesPage_getIrrelevantSiteId();

		Page<WebhookEntity> page =
			webhookEntityResource.getSiteWebhooksEntitiesPage(siteId);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantSiteId != null) {
			WebhookEntity irrelevantWebhookEntity =
				testGetSiteWebhooksEntitiesPage_addWebhookEntity(
					irrelevantSiteId, randomIrrelevantWebhookEntity());

			page = webhookEntityResource.getSiteWebhooksEntitiesPage(
				irrelevantSiteId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWebhookEntity),
				(List<WebhookEntity>)page.getItems());
			assertValid(page);
		}

		WebhookEntity webhookEntity1 =
			testGetSiteWebhooksEntitiesPage_addWebhookEntity(
				siteId, randomWebhookEntity());

		WebhookEntity webhookEntity2 =
			testGetSiteWebhooksEntitiesPage_addWebhookEntity(
				siteId, randomWebhookEntity());

		page = webhookEntityResource.getSiteWebhooksEntitiesPage(siteId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(webhookEntity1, webhookEntity2),
			(List<WebhookEntity>)page.getItems());
		assertValid(page);
	}

	protected WebhookEntity testGetSiteWebhooksEntitiesPage_addWebhookEntity(
			Long siteId, WebhookEntity webhookEntity)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteWebhooksEntitiesPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteWebhooksEntitiesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testPostSiteWebhooksEntity() throws Exception {
		WebhookEntity randomWebhookEntity = randomWebhookEntity();

		WebhookEntity postWebhookEntity =
			testPostSiteWebhooksEntity_addWebhookEntity(randomWebhookEntity);

		assertEquals(randomWebhookEntity, postWebhookEntity);
		assertValid(postWebhookEntity);
	}

	protected WebhookEntity testPostSiteWebhooksEntity_addWebhookEntity(
			WebhookEntity webhookEntity)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteWebhookEntity() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeleteWebhookEntity() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetWebhookEntity() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetWebhookEntity() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetWebhookEntityNotFound() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertContains(
		WebhookEntity webhookEntity, List<WebhookEntity> webhookEntities) {

		boolean contains = false;

		for (WebhookEntity item : webhookEntities) {
			if (equals(webhookEntity, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			webhookEntities + " does not contain " + webhookEntity, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		WebhookEntity webhookEntity1, WebhookEntity webhookEntity2) {

		Assert.assertTrue(
			webhookEntity1 + " does not equal " + webhookEntity2,
			equals(webhookEntity1, webhookEntity2));
	}

	protected void assertEquals(
		List<WebhookEntity> webhookEntities1,
		List<WebhookEntity> webhookEntities2) {

		Assert.assertEquals(webhookEntities1.size(), webhookEntities2.size());

		for (int i = 0; i < webhookEntities1.size(); i++) {
			WebhookEntity webhookEntity1 = webhookEntities1.get(i);
			WebhookEntity webhookEntity2 = webhookEntities2.get(i);

			assertEquals(webhookEntity1, webhookEntity2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WebhookEntity> webhookEntities1,
		List<WebhookEntity> webhookEntities2) {

		Assert.assertEquals(webhookEntities1.size(), webhookEntities2.size());

		for (WebhookEntity webhookEntity1 : webhookEntities1) {
			boolean contains = false;

			for (WebhookEntity webhookEntity2 : webhookEntities2) {
				if (equals(webhookEntity1, webhookEntity2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				webhookEntities2 + " does not contain " + webhookEntity1,
				contains);
		}
	}

	protected void assertValid(WebhookEntity webhookEntity) throws Exception {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("entity", additionalAssertFieldName)) {
				if (webhookEntity.getEntity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("webhookId", additionalAssertFieldName)) {
				if (webhookEntity.getWebhookId() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<WebhookEntity> page) {
		boolean valid = false;

		java.util.Collection<WebhookEntity> webhookEntities = page.getItems();

		int size = webhookEntities.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.webhooks.dto.v1_0.WebhookEntity.
						class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		WebhookEntity webhookEntity1, WebhookEntity webhookEntity2) {

		if (webhookEntity1 == webhookEntity2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("entity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						webhookEntity1.getEntity(),
						webhookEntity2.getEntity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("webhookId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						webhookEntity1.getWebhookId(),
						webhookEntity2.getWebhookId())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_webhookEntityResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_webhookEntityResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, WebhookEntity webhookEntity) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("entity")) {
			sb.append("'");
			sb.append(String.valueOf(webhookEntity.getEntity()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("webhookId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected WebhookEntity randomWebhookEntity() throws Exception {
		return new WebhookEntity() {
			{
				entity = StringUtil.toLowerCase(RandomTestUtil.randomString());
				webhookId = RandomTestUtil.randomLong();
			}
		};
	}

	protected WebhookEntity randomIrrelevantWebhookEntity() throws Exception {
		WebhookEntity randomIrrelevantWebhookEntity = randomWebhookEntity();

		return randomIrrelevantWebhookEntity;
	}

	protected WebhookEntity randomPatchWebhookEntity() throws Exception {
		return randomWebhookEntity();
	}

	protected WebhookEntityResource webhookEntityResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseWebhookEntityResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.webhooks.resource.v1_0.WebhookEntityResource
		_webhookEntityResource;

}