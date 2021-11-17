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

package com.liferay.headless.webhooks.internal.graphql.query.v1_0;

import com.liferay.headless.webhooks.dto.v1_0.Webhook;
import com.liferay.headless.webhooks.resource.v1_0.WebhookResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotEmpty;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Luis Miguel Barcos
 * @generated
 */
@Generated("")
public class Query {

	public static void setWebhookResourceComponentServiceObjects(
		ComponentServiceObjects<WebhookResource>
			webhookResourceComponentServiceObjects) {

		_webhookResourceComponentServiceObjects =
			webhookResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {webhooks(siteKey: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a Site's webhooks.")
	public WebhookPage webhooks(
			@GraphQLName("siteKey") @NotEmpty String siteKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_webhookResourceComponentServiceObjects,
			this::_populateResourceContext,
			webhookResource -> new WebhookPage(
				webhookResource.getSiteWebhooksPage(Long.valueOf(siteKey))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {webhook(webhookId: ___){url, apiKey}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a Webhook.")
	public Webhook webhook(@GraphQLName("webhookId") String webhookId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webhookResourceComponentServiceObjects,
			this::_populateResourceContext,
			webhookResource -> webhookResource.getWebhook(webhookId));
	}

	@GraphQLName("WebhookPage")
	public class WebhookPage {

		public WebhookPage(Page webhookPage) {
			actions = webhookPage.getActions();

			items = webhookPage.getItems();
			lastPage = webhookPage.getLastPage();
			page = webhookPage.getPage();
			pageSize = webhookPage.getPageSize();
			totalCount = webhookPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Webhook> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(WebhookResource webhookResource)
		throws Exception {

		webhookResource.setContextAcceptLanguage(_acceptLanguage);
		webhookResource.setContextCompany(_company);
		webhookResource.setContextHttpServletRequest(_httpServletRequest);
		webhookResource.setContextHttpServletResponse(_httpServletResponse);
		webhookResource.setContextUriInfo(_uriInfo);
		webhookResource.setContextUser(_user);
		webhookResource.setGroupLocalService(_groupLocalService);
		webhookResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<WebhookResource>
		_webhookResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}