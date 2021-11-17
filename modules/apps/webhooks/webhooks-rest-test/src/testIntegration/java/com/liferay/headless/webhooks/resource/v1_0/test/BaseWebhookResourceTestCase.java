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

import com.liferay.headless.webhooks.client.dto.v1_0.Webhook;
import com.liferay.headless.webhooks.client.http.HttpInvoker;
import com.liferay.headless.webhooks.client.pagination.Page;
import com.liferay.headless.webhooks.client.resource.v1_0.WebhookResource;
import com.liferay.headless.webhooks.client.serdes.v1_0.WebhookSerDes;
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
public abstract class BaseWebhookResourceTestCase {

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

		_webhookResource.setContextCompany(testCompany);

		WebhookResource.Builder builder = WebhookResource.builder();

		webhookResource = builder.authentication(
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

		Webhook webhook1 = randomWebhook();

		String json = objectMapper.writeValueAsString(webhook1);

		Webhook webhook2 = WebhookSerDes.toDTO(json);

		Assert.assertTrue(equals(webhook1, webhook2));
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

		Webhook webhook = randomWebhook();

		String json1 = objectMapper.writeValueAsString(webhook);
		String json2 = WebhookSerDes.toJSON(webhook);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Webhook webhook = randomWebhook();

		webhook.setApiKey(regex);
		webhook.setUrl(regex);

		String json = WebhookSerDes.toJSON(webhook);

		Assert.assertFalse(json.contains(regex));

		webhook = WebhookSerDes.toDTO(json);

		Assert.assertEquals(regex, webhook.getApiKey());
		Assert.assertEquals(regex, webhook.getUrl());
	}

	@Test
	public void testGetSiteWebhooksPage() throws Exception {
		Long siteId = testGetSiteWebhooksPage_getSiteId();
		Long irrelevantSiteId = testGetSiteWebhooksPage_getIrrelevantSiteId();

		Page<Webhook> page = webhookResource.getSiteWebhooksPage(siteId);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantSiteId != null) {
			Webhook irrelevantWebhook = testGetSiteWebhooksPage_addWebhook(
				irrelevantSiteId, randomIrrelevantWebhook());

			page = webhookResource.getSiteWebhooksPage(irrelevantSiteId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWebhook),
				(List<Webhook>)page.getItems());
			assertValid(page);
		}

		Webhook webhook1 = testGetSiteWebhooksPage_addWebhook(
			siteId, randomWebhook());

		Webhook webhook2 = testGetSiteWebhooksPage_addWebhook(
			siteId, randomWebhook());

		page = webhookResource.getSiteWebhooksPage(siteId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(webhook1, webhook2), (List<Webhook>)page.getItems());
		assertValid(page);
	}

	protected Webhook testGetSiteWebhooksPage_addWebhook(
			Long siteId, Webhook webhook)
		throws Exception {

		return webhookResource.postSiteWebhook(siteId, webhook);
	}

	protected Long testGetSiteWebhooksPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteWebhooksPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteWebhooksPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testPostSiteWebhook() throws Exception {
		Webhook randomWebhook = randomWebhook();

		Webhook postWebhook = testPostSiteWebhook_addWebhook(randomWebhook);

		assertEquals(randomWebhook, postWebhook);
		assertValid(postWebhook);
	}

	protected Webhook testPostSiteWebhook_addWebhook(Webhook webhook)
		throws Exception {

		return webhookResource.postSiteWebhook(
			testGetSiteWebhooksPage_getSiteId(), webhook);
	}

	@Test
	public void testGraphQLPostSiteWebhook() throws Exception {
		Webhook randomWebhook = randomWebhook();

		Webhook webhook = testGraphQLWebhook_addWebhook(randomWebhook);

		Assert.assertTrue(equals(randomWebhook, webhook));
	}

	@Test
	public void testDeleteWebhook() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeleteWebhook() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetWebhook() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetWebhook() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetWebhookNotFound() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertContains(Webhook webhook, List<Webhook> webhooks) {
		boolean contains = false;

		for (Webhook item : webhooks) {
			if (equals(webhook, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(webhooks + " does not contain " + webhook, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Webhook webhook1, Webhook webhook2) {
		Assert.assertTrue(
			webhook1 + " does not equal " + webhook2,
			equals(webhook1, webhook2));
	}

	protected void assertEquals(
		List<Webhook> webhooks1, List<Webhook> webhooks2) {

		Assert.assertEquals(webhooks1.size(), webhooks2.size());

		for (int i = 0; i < webhooks1.size(); i++) {
			Webhook webhook1 = webhooks1.get(i);
			Webhook webhook2 = webhooks2.get(i);

			assertEquals(webhook1, webhook2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Webhook> webhooks1, List<Webhook> webhooks2) {

		Assert.assertEquals(webhooks1.size(), webhooks2.size());

		for (Webhook webhook1 : webhooks1) {
			boolean contains = false;

			for (Webhook webhook2 : webhooks2) {
				if (equals(webhook1, webhook2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				webhooks2 + " does not contain " + webhook1, contains);
		}
	}

	protected void assertValid(Webhook webhook) throws Exception {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("apiKey", additionalAssertFieldName)) {
				if (webhook.getApiKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("url", additionalAssertFieldName)) {
				if (webhook.getUrl() == null) {
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

	protected void assertValid(Page<Webhook> page) {
		boolean valid = false;

		java.util.Collection<Webhook> webhooks = page.getItems();

		int size = webhooks.size();

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
					com.liferay.headless.webhooks.dto.v1_0.Webhook.class)) {

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

	protected boolean equals(Webhook webhook1, Webhook webhook2) {
		if (webhook1 == webhook2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("apiKey", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						webhook1.getApiKey(), webhook2.getApiKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("url", additionalAssertFieldName)) {
				if (!Objects.deepEquals(webhook1.getUrl(), webhook2.getUrl())) {
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

		if (!(_webhookResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_webhookResource;

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
		EntityField entityField, String operator, Webhook webhook) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("apiKey")) {
			sb.append("'");
			sb.append(String.valueOf(webhook.getApiKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("url")) {
			sb.append("'");
			sb.append(String.valueOf(webhook.getUrl()));
			sb.append("'");

			return sb.toString();
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

	protected Webhook randomWebhook() throws Exception {
		return new Webhook() {
			{
				apiKey = StringUtil.toLowerCase(RandomTestUtil.randomString());
				url = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected Webhook randomIrrelevantWebhook() throws Exception {
		Webhook randomIrrelevantWebhook = randomWebhook();

		return randomIrrelevantWebhook;
	}

	protected Webhook randomPatchWebhook() throws Exception {
		return randomWebhook();
	}

	protected WebhookResource webhookResource;
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
		LogFactoryUtil.getLog(BaseWebhookResourceTestCase.class);

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
	private com.liferay.headless.webhooks.resource.v1_0.WebhookResource
		_webhookResource;

}