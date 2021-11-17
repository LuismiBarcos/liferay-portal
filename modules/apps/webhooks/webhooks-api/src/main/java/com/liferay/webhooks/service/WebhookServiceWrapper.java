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
 * Provides a wrapper for {@link WebhookService}.
 *
 * @author Brian Wing Shun Chan
 * @see WebhookService
 * @generated
 */
public class WebhookServiceWrapper
	implements ServiceWrapper<WebhookService>, WebhookService {

	public WebhookServiceWrapper(WebhookService webhookService) {
		_webhookService = webhookService;
	}

	@Override
	public com.liferay.webhooks.model.Webhook addWebhook(
			String apiKey, long userId, String webhookURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookService.addWebhook(
			apiKey, userId, webhookURL, serviceContext);
	}

	@Override
	public com.liferay.webhooks.model.Webhook deleteWebhook(long webhookId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookService.deleteWebhook(webhookId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _webhookService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.webhooks.model.Webhook> getSiteWebhooks(
		long groupId) {

		return _webhookService.getSiteWebhooks(groupId);
	}

	@Override
	public com.liferay.webhooks.model.Webhook getWebhook(long webhookId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookService.getWebhook(webhookId);
	}

	@Override
	public com.liferay.webhooks.model.Webhook getWebhook(
			long groupId, String webhookURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookService.getWebhook(groupId, webhookURL);
	}

	@Override
	public WebhookService getWrappedService() {
		return _webhookService;
	}

	@Override
	public void setWrappedService(WebhookService webhookService) {
		_webhookService = webhookService;
	}

	private WebhookService _webhookService;

}