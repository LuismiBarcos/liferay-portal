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

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_ENTRY);

		return _webhookLocalService.addWebhook(apiKey, userId, webhookURL, serviceContext);
	}

	@Override
	public Webhook deleteWebhook(long webhookId) throws PortalException {

		_blogsEntryModelResourcePermission.check(
			getPermissionChecker(), webhookId, ActionKeys.DELETE);

		return _webhookLocalService.deleteWebhook(webhookId);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.webhooks.model.Webhook)"
	)
	private volatile ModelResourcePermission<Webhook>
		_blogsEntryModelResourcePermission;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(resource.name=" + WebhooksConstants.RESOURCE_NAME + ")"
	)
	private volatile PortletResourcePermission _portletResourcePermission;

	@Reference
	private WebhookLocalService _webhookLocalService;
}