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
import com.liferay.webhooks.model.WebhookEntity;
import com.liferay.webhooks.service.base.WebhookEntityLocalServiceBaseImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.webhooks.model.WebhookEntity",
	service = AopService.class
)
public class WebhookEntityLocalServiceImpl
	extends WebhookEntityLocalServiceBaseImpl {

	@Override
	public WebhookEntity addWebhookEntity(
		String entityClassName, long userId, long webhookId,
		ServiceContext serviceContext) throws PortalException {

		User user = _userLocalService.getUser(userId);
		long groupId = serviceContext.getScopeGroupId();
		long webhookEntityId = counterLocalService.increment();

		WebhookEntity webhookEntity = webhookEntityPersistence.create(webhookEntityId);
		webhookEntity.setUuid(serviceContext.getUuid());
		webhookEntity.setGroupId(groupId);
		webhookEntity.setCompanyId(user.getCompanyId());
		webhookEntity.setUserId(user.getUserId());
		webhookEntity.setUserName(user.getFullName());
		webhookEntity.setEntityClassName(entityClassName);
		webhookEntity.setWebhookId(webhookId);

		return webhookEntityPersistence.update(webhookEntity);
	}

	@Reference
	private UserLocalService _userLocalService;
}