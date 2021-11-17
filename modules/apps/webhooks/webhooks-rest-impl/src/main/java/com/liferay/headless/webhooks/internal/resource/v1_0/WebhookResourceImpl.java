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

package com.liferay.headless.webhooks.internal.resource.v1_0;

import com.liferay.headless.webhooks.dto.v1_0.Webhook;
import com.liferay.headless.webhooks.internal.dto.v1_0.converter.WebhookDTOConverter;
import com.liferay.headless.webhooks.resource.v1_0.WebhookResource;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.webhooks.service.WebhookService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import java.util.Collections;

/**
 * @author Luis Miguel Barcos
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/webhook.properties",
	scope = ServiceScope.PROTOTYPE, service = WebhookResource.class
)
public class WebhookResourceImpl extends BaseWebhookResourceImpl {

	@Override
	public Webhook getWebhook(String webhookId) throws Exception {
		return _toWebhook(
			_webhookService.getWebhook(GetterUtil.getLong(webhookId)));
	}

	@Override
	public Page<Webhook> getSiteWebhooksPage(
		Long siteId) throws Exception {
		return Page.of(
			transform(
				_webhookService.getSiteWebhooks(siteId),
				this::_toWebhook
			)
		);
	}

	@Override
	public Webhook postSiteWebhook(
		Long siteId, Webhook webhook) throws Exception {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setScopeGroupId(siteId);
		return _toWebhook(_webhookService.addWebhook(webhook.getApiKey(),
			contextUser.getUserId(), webhook.getUrl(), serviceContext));
	}

	@Override
	public void deleteWebhook(String webhookId) throws Exception {
		_webhookService.deleteWebhook(GetterUtil.getLong(webhookId));
	}

	private Webhook _toWebhook(com.liferay.webhooks.model.Webhook webhook) {
		return _webhookDTOConverter.toDTO(new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			Collections.emptyMap(), _dtoConverterRegistry,
			webhook.getWebhookId(), contextAcceptLanguage.getPreferredLocale(),
			contextUriInfo, contextUser), webhook);
	}

	@Reference
	private WebhookService _webhookService;

	@Reference
	private WebhookDTOConverter _webhookDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;
}