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

package com.liferay.headless.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.builder.application.HeadlessBuilderApplication;
import com.liferay.headless.builder.application.HeadlessBuilderApplicationFactory;
import com.liferay.headless.builder.test.info.item.provider.TestEntryInfoItemFieldValuesProvider;
import com.liferay.headless.builder.test.info.item.provider.TestEntryInfoItemFormProvider;
import com.liferay.headless.builder.test.info.item.provider.TestEntryInfoItemObjectProvider;
import com.liferay.headless.builder.test.model.TestEntry;
import com.liferay.headless.builder.test.util.HeadlessBuilderTestUtil;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.yaml.YAMLUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class HeadlessBuilderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(HeadlessBuilderTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_infoItemFieldValuesProviderServiceRegistration =
			bundleContext.registerService(
				InfoItemFieldValuesProvider.class,
				new TestEntryInfoItemFieldValuesProvider(), null);
		_infoItemFormProviderServiceRegistration =
			bundleContext.registerService(
				InfoItemFormProvider.class, new TestEntryInfoItemFormProvider(),
				null);
		_infoItemObjectProviderServiceRegistration =
			bundleContext.registerService(
				InfoItemObjectProvider.class,
				new TestEntryInfoItemObjectProvider(), null);

		_objectDefinition = HeadlessBuilderTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME, false)),
			ObjectDefinitionConstants.SCOPE_COMPANY,
			TestPropsValues.getUserId());

		_objectEntry = HeadlessBuilderTestUtil.addObjectEntry(
			_objectDefinition,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE
			).build());

		if (_finalOpenAPI.isEmpty()) {
			_finalOpenAPI = _parseOpenAPI("/rest-openapi.yaml");
		}
	}

	@After
	public void tearDown() throws Exception {
		_infoItemFieldValuesProviderServiceRegistration.unregister();
		_infoItemFormProviderServiceRegistration.unregister();
		_infoItemObjectProviderServiceRegistration.unregister();

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());
	}

	@FeatureFlags("LPS-171047")
	@Test
	public void testHeadlessBuilderApplication() throws Exception {
		_withHeadlessBuilderApplication(
			TestPropsValues.getCompanyId(),
			() -> {
				JSONObject jsonObject = _invoke(
					String.format(
						"headless-builder/v1.0/%s/%d",
						_objectDefinition.getPluralLabel(
							LocaleUtil.fromLanguageId(
								_objectDefinition.getDefaultLanguageId())),
						_objectEntry.getObjectEntryId()),
					Http.Method.GET);

				JSONAssert.assertEquals(
					JSONUtil.put(
						"date", _formatDate(_objectEntry.getCreateDate())
					).put(
						"objectField", _OBJECT_FIELD_VALUE
					).toString(),
					jsonObject.toString(), true);
			});
	}

	@FeatureFlags("LPS-171047")
	@Test
	public void testHeadlessBuilderApplicationOnADifferentCompany()
		throws Exception {

		_withHeadlessBuilderApplication(
			0,
			() -> {
				JSONObject jsonObject = _invoke(
					String.format(
						"headless-builder/v1.0/%s/%d",
						_objectDefinition.getPluralLabel(
							LocaleUtil.fromLanguageId(
								_objectDefinition.getDefaultLanguageId())),
						_objectEntry.getObjectEntryId()),
					Http.Method.GET);

				JSONAssert.assertEquals(
					JSONUtil.put(
						"status", "NOT_FOUND"
					).put(
						"title", "The operation could not be found."
					).toString(),
					jsonObject.toString(), true);
			});
	}

	@Test
	public void testHeadlessBuilderApplicationWithoutFeatureFlag()
		throws Exception {

		HttpURLConnection httpURLConnection = _createHttpURLConnection(
			String.format(
				"headless-builder/v1.0/%s/%d",
				_objectDefinition.getPluralLabel(
					LocaleUtil.fromLanguageId(
						_objectDefinition.getDefaultLanguageId())),
				_objectEntry.getObjectEntryId()),
			Http.Method.GET);

		httpURLConnection.connect();

		Assert.assertEquals(404, httpURLConnection.getResponseCode());
	}

	@FeatureFlags("LPS-171047")
	@Test
	public void testMissingHeadlessBuilderApplication() throws Exception {
		JSONObject jsonObject = _invoke(
			String.format(
				"headless-builder/v1.0/%s/%d",
				_objectDefinition.getPluralLabel(
					LocaleUtil.fromLanguageId(
						_objectDefinition.getDefaultLanguageId())),
				_objectEntry.getObjectEntryId()),
			Http.Method.GET);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "NOT_FOUND"
			).put(
				"title", "The operation could not be found."
			).toString(),
			jsonObject.toString(), true);
	}

	private HttpURLConnection _createHttpURLConnection(
			String endpoint, Http.Method method)
		throws Exception {

		URL url = new URL("http://localhost:8080/o/" + endpoint);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setRequestProperty(HttpHeaders.ACCEPT, "*/*");

		httpURLConnection.setRequestProperty(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		String encodedUserNameAndPassword = Base64.encode(
			"test@liferay.com:test".getBytes(StandardCharsets.UTF_8));

		httpURLConnection.setRequestProperty(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		httpURLConnection.setRequestMethod(method.toString());

		return httpURLConnection;
	}

	private String _formatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		return simpleDateFormat.format(date);
	}

	private JSONObject _invoke(String endpoint, Http.Method method)
		throws Exception {

		HttpURLConnection httpURLConnection = _createHttpURLConnection(
			endpoint, method);

		httpURLConnection.connect();

		try {
			return JSONFactoryUtil.createJSONObject(
				StringUtil.read(httpURLConnection.getInputStream()));
		}
		catch (FileNotFoundException fileNotFoundException) {
			return JSONFactoryUtil.createJSONObject(
				StringUtil.read(httpURLConnection.getErrorStream()));
		}
	}

	private String _parseOpenAPI(String openAPIFile) {
		InputStream inputStream = getClass().getResourceAsStream(openAPIFile);

		Scanner scanner = new Scanner(inputStream);

		while (scanner.hasNextLine()) {
			_finalOpenAPI = StringBundler.concat(
				_finalOpenAPI, scanner.nextLine(), "\n");
		}

		return HeadlessBuilderTestUtil.parseOpenAPIYaml(
			_finalOpenAPI,
			HashMapBuilder.put(
				HeadlessBuilderTestUtil.ParserConstants.OBJECT_DEFINITION_ID,
				String.valueOf(_objectDefinition.getObjectDefinitionId())
			).put(
				HeadlessBuilderTestUtil.ParserConstants.OBJECT_DEFINITION_NAME,
				_objectDefinition.getShortName()
			).put(
				HeadlessBuilderTestUtil.ParserConstants.
					OBJECT_DEFINITION_PLURAL_NAME,
				_objectDefinition.getPluralLabel(
					LocaleUtil.fromLanguageId(
						_objectDefinition.getDefaultLanguageId()))
			).put(
				HeadlessBuilderTestUtil.ParserConstants.OBJECT_FIELD_NAME,
				_OBJECT_FIELD_NAME
			).build());
	}

	private void _withHeadlessBuilderApplication(
			long companyId, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		HeadlessBuilderApplication headlessBuilderApplication =
			_headlessBuilderApplicationFactory.getHeadlessBuilderApplication(
				companyId, YAMLUtil.loadOpenAPIYAML(_finalOpenAPI));

		HeadlessBuilderApplication.Handle handle =
			headlessBuilderApplication.deploy();

		try {
			unsafeRunnable.run();
		}
		finally {
			handle.undeploy();
		}
	}

	private static final String _OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE =
		RandomTestUtil.randomString();

	private static String _finalOpenAPI = "";
	private static ObjectDefinition _objectDefinition;

	@Inject
	private HeadlessBuilderApplicationFactory
		_headlessBuilderApplicationFactory;

	private ServiceRegistration<?>
		_infoItemFieldValuesProviderServiceRegistration;
	private ServiceRegistration<?> _infoItemFormProviderServiceRegistration;
	private ServiceRegistration<?> _infoItemObjectProviderServiceRegistration;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntry _objectEntry;
	private final TestEntry _testEntry = TestEntry.INSTANCE;

}