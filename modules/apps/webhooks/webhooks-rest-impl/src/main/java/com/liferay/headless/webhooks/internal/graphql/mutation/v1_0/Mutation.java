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

package com.liferay.headless.webhooks.internal.graphql.mutation.v1_0;

import com.liferay.headless.webhooks.dto.v1_0.Webhook;
import com.liferay.headless.webhooks.resource.v1_0.WebhookResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotEmpty;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Luis Miguel Barcos
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setWebhookResourceComponentServiceObjects(
		ComponentServiceObjects<WebhookResource>
			webhookResourceComponentServiceObjects) {

		_webhookResourceComponentServiceObjects =
			webhookResourceComponentServiceObjects;
	}

	@GraphQLField(description = "Creates a new Webhook in a Site.")
	public Webhook createSiteWebhook(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("webhook") Webhook webhook)
		throws Exception {

		return _applyComponentServiceObjects(
			_webhookResourceComponentServiceObjects,
			this::_populateResourceContext,
			webhookResource -> webhookResource.postSiteWebhook(
				Long.valueOf(siteKey), webhook));
	}

	@GraphQLField
	public Response createSiteWebhookBatch(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_webhookResourceComponentServiceObjects,
			this::_populateResourceContext,
			webhookResource -> webhookResource.postSiteWebhookBatch(
				Long.valueOf(siteKey), callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes the webhook and return a 204 if the operation succeeds."
	)
	public boolean deleteWebhook(@GraphQLName("webhookId") String webhookId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_webhookResourceComponentServiceObjects,
			this::_populateResourceContext,
			webhookResource -> webhookResource.deleteWebhook(webhookId));

		return true;
	}

	@GraphQLField
	public Response deleteWebhookBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_webhookResourceComponentServiceObjects,
			this::_populateResourceContext,
			webhookResource -> webhookResource.deleteWebhookBatch(
				callbackURL, object));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
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
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}