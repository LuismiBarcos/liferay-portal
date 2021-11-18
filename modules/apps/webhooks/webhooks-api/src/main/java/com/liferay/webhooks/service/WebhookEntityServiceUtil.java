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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.webhooks.model.WebhookEntity;

import java.util.List;

/**
 * Provides the remote service utility for WebhookEntity. This utility wraps
 * <code>com.liferay.webhooks.service.impl.WebhookEntityServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see WebhookEntityService
 * @generated
 */
public class WebhookEntityServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.webhooks.service.impl.WebhookEntityServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static WebhookEntity addWebhookEntity(
			String entityClassName, long userId, long webhookId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addWebhookEntity(
			entityClassName, userId, webhookId, serviceContext);
	}

	public static WebhookEntity deleteWebhookEntity(long webhookEntityId)
		throws PortalException {

		return getService().deleteWebhookEntity(webhookEntityId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<WebhookEntity> getSiteWebhookEntities(long groupId) {
		return getService().getSiteWebhookEntities(groupId);
	}

	public static WebhookEntityService getService() {
		return _service;
	}

	private static volatile WebhookEntityService _service;

}