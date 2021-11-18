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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.webhooks.model.WebhookEntity;
import com.liferay.webhooks.service.WebhookEntityLocalService;
import com.liferay.webhooks.service.base.WebhookEntityServiceBaseImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=webhooks",
		"json.web.service.context.path=WebhookEntity"
	},
	service = AopService.class
)
public class WebhookEntityServiceImpl extends WebhookEntityServiceBaseImpl {

	@Override
	public WebhookEntity deleteWebhookEntity(long webhookEntityId)
		throws PortalException {
		return _webhookEntityLocalService.deleteWebhookEntity(webhookEntityId);
	}

	@Override
	public List<WebhookEntity> getSiteWebhookEntities(long groupId) {
		return webhookEntityPersistence.findByGroupId(groupId);
	}

	@Override
	public WebhookEntity addWebhookEntity(
		String entityClassName, long userId, long webhookId,
		ServiceContext serviceContext) throws PortalException {
		return _webhookEntityLocalService.addWebhookEntity(
			entityClassName, userId, webhookId, serviceContext);
	}
	
	@Reference
	private WebhookEntityLocalService _webhookEntityLocalService;
}