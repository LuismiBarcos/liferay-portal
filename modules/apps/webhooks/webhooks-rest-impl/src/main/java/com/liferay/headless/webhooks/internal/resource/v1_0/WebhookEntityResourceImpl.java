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

import com.liferay.headless.webhooks.dto.v1_0.WebhookEntity;
import com.liferay.headless.webhooks.internal.dto.v1_0.converter.WebhookEntityDTOConverter;
import com.liferay.headless.webhooks.resource.v1_0.WebhookEntityResource;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.webhooks.service.WebhookEntityService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import java.util.Collections;

/**
 * @author Luis Miguel Barcos
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/webhook-entity.properties",
	scope = ServiceScope.PROTOTYPE, service = WebhookEntityResource.class
)
public class WebhookEntityResourceImpl extends BaseWebhookEntityResourceImpl {

	@Override
	public Page<WebhookEntity> getSiteWebhooksEntitiesPage(
		Long siteId) throws Exception {
		return Page.of(
			transform(
				_webhookEntityService.getSiteWebhookEntities(siteId),
				this::_toWebhookEntity
			)
		);
	}

	@Override
	public void deleteWebhookEntity(Long webhookEntityId) throws Exception {
		_webhookEntityService.deleteWebhookEntity(webhookEntityId);
	}

	@Override
	public WebhookEntity postSiteWebhooksEntity(
		Long siteId, WebhookEntity webhookEntity) throws Exception {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setScopeGroupId(siteId);
		return _toWebhookEntity(
			_webhookEntityService.addWebhookEntity(webhookEntity.getEntity(),
				contextUser.getUserId(), webhookEntity.getWebhookId(),
				serviceContext));
	}

	private WebhookEntity _toWebhookEntity(
		com.liferay.webhooks.model.WebhookEntity webhookEntity) {
		return _webhookEntityDTOConverter.toDTO(new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			Collections.emptyMap(), _dtoConverterRegistry,
			webhookEntity.getWebhookEntityId(), contextAcceptLanguage.getPreferredLocale(),
			contextUriInfo, contextUser), webhookEntity);
	}

	@Reference
	private WebhookEntityService _webhookEntityService;

	@Reference
	private WebhookEntityDTOConverter _webhookEntityDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;
}