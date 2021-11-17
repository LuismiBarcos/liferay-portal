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

package com.liferay.webhooks.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.webhooks.constants.WebhooksConstants;
import com.liferay.webhooks.model.Webhook;
import com.liferay.webhooks.service.WebhookLocalService;
import com.liferay.webhooks.service.base.WebhookServiceBaseImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=webhooks",
		"json.web.service.context.path=Webhook"
	},
	service = AopService.class
)
public class WebhookServiceImpl extends WebhookServiceBaseImpl {

	@Override
	public Webhook addWebhook(
		String apiKey, long userId, String webhookURL,
		ServiceContext serviceContext) throws PortalException {

		return _webhookLocalService.addWebhook(apiKey, userId, webhookURL, serviceContext);
	}

	@Override
	public Webhook deleteWebhook(long webhookId) throws PortalException {

		return _webhookLocalService.deleteWebhook(webhookId);
	}

	@Override
	public Webhook getWebhook(long groupId, String webhookURL)
		throws PortalException {
		Webhook webhook = _webhookLocalService.getWebhook(groupId, webhookURL);

		return webhook;
	}

	@Override
	public Webhook getWebhook(long webhookId) throws PortalException {
		Webhook webhook = _webhookLocalService.getWebhook(webhookId);

		return webhook;
	}

	@Override
	public List<Webhook> getSiteWebhooks(long groupId) {
		return webhookPersistence.findByGroupId(groupId);
	}

	@Reference
	private WebhookLocalService _webhookLocalService;
}