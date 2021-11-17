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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.webhooks.model.Webhook;
import com.liferay.webhooks.service.base.WebhookLocalServiceBaseImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.webhooks.model.Webhook",
	service = AopService.class
)
public class WebhookLocalServiceImpl extends WebhookLocalServiceBaseImpl {

	@Override
	public Webhook addWebhook(
		String apiKey, long userId, String webhookURL,
		ServiceContext serviceContext) throws PortalException {

		if (Validator.isNotNull(webhookURL)) {
			webhookURL = _validateWebhookURL(webhookURL);
		}

		User user = _userLocalService.getUser(userId);
		long groupId = serviceContext.getScopeGroupId();
		long webhookId = counterLocalService.increment();

		Webhook webhook = webhookPersistence.create(webhookId);
		webhook.setUuid(serviceContext.getUuid());
		webhook.setGroupId(groupId);
		webhook.setCompanyId(user.getCompanyId());
		webhook.setUserId(user.getUserId());
		webhook.setUserName(user.getFullName());
		webhook.setWebhookURL(webhookURL);
		webhook.setApiKey(apiKey);

		return webhookPersistence.update(webhook);
	}

	@Override
	public Webhook getWebhook(long groupId, String webhookURL)
		throws PortalException {
		return webhookPersistence.findByG_WU(groupId, webhookURL);
	}

	private String _validateWebhookURL(String webhookURL)
		throws PortalException {
		try {
			new URL(webhookURL);
		} catch (MalformedURLException malformedURLException) {
			throw new PortalException(malformedURLException.getMessage());
		}
		return webhookURL;
	}

	@Reference
	private UserLocalService _userLocalService;
}