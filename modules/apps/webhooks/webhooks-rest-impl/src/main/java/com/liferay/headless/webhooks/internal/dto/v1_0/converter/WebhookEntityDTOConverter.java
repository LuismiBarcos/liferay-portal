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

package com.liferay.headless.webhooks.internal.dto.v1_0.converter;

import com.liferay.headless.webhooks.dto.v1_0.WebhookEntity;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import org.osgi.service.component.annotations.Component;

@Component(
	property = "dto.class.name=com.liferay.webhooks.model.Webhook",
	service = {DTOConverter.class, WebhookEntityDTOConverter.class}
)
public class WebhookEntityDTOConverter implements DTOConverter<com.liferay.webhooks.model.WebhookEntity, WebhookEntity> {
	@Override
	public String getContentType() {
		return WebhookEntity.class.getSimpleName();
	}

	@Override
	public WebhookEntity toDTO(
		DTOConverterContext dtoConverterContext,
		com.liferay.webhooks.model.WebhookEntity webhookEntity) {
		return new WebhookEntity() {
			{
				entity = webhookEntity.getEntityClassName();
				webhookId = webhookEntity.getWebhookId();
			}
		};
	}
}
