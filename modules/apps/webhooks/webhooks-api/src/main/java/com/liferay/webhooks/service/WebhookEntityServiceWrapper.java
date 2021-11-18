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

package com.liferay.webhooks.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link WebhookEntityService}.
 *
 * @author Brian Wing Shun Chan
 * @see WebhookEntityService
 * @generated
 */
public class WebhookEntityServiceWrapper
	implements ServiceWrapper<WebhookEntityService>, WebhookEntityService {

	public WebhookEntityServiceWrapper(
		WebhookEntityService webhookEntityService) {

		_webhookEntityService = webhookEntityService;
	}

	@Override
	public com.liferay.webhooks.model.WebhookEntity addWebhookEntity(
			String entityClassName, long userId, long webhookId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookEntityService.addWebhookEntity(
			entityClassName, userId, webhookId, serviceContext);
	}

	@Override
	public com.liferay.webhooks.model.WebhookEntity deleteWebhookEntity(
			long webhookEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookEntityService.deleteWebhookEntity(webhookEntityId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _webhookEntityService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.webhooks.model.WebhookEntity>
		getSiteWebhookEntities(long groupId) {

		return _webhookEntityService.getSiteWebhookEntities(groupId);
	}

	@Override
	public WebhookEntityService getWrappedService() {
		return _webhookEntityService;
	}

	@Override
	public void setWrappedService(WebhookEntityService webhookEntityService) {
		_webhookEntityService = webhookEntityService;
	}

	private WebhookEntityService _webhookEntityService;

}