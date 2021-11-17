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

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.webhooks.model.Webhook;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for Webhook. This utility wraps
 * <code>com.liferay.webhooks.service.impl.WebhookLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WebhookLocalService
 * @generated
 */
public class WebhookLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.webhooks.service.impl.WebhookLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Webhook addWebhook(
			String apiKey, long userId, String webhookURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addWebhook(
			apiKey, userId, webhookURL, serviceContext);
	}

	/**
	 * Adds the webhook to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebhookLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webhook the webhook
	 * @return the webhook that was added
	 */
	public static Webhook addWebhook(Webhook webhook) {
		return getService().addWebhook(webhook);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new webhook with the primary key. Does not add the webhook to the database.
	 *
	 * @param webhookId the primary key for the new webhook
	 * @return the new webhook
	 */
	public static Webhook createWebhook(long webhookId) {
		return getService().createWebhook(webhookId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the webhook with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebhookLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webhookId the primary key of the webhook
	 * @return the webhook that was removed
	 * @throws PortalException if a webhook with the primary key could not be found
	 */
	public static Webhook deleteWebhook(long webhookId) throws PortalException {
		return getService().deleteWebhook(webhookId);
	}

	/**
	 * Deletes the webhook from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebhookLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webhook the webhook
	 * @return the webhook that was removed
	 */
	public static Webhook deleteWebhook(Webhook webhook) {
		return getService().deleteWebhook(webhook);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.webhooks.model.impl.WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.webhooks.model.impl.WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static Webhook fetchWebhook(long webhookId) {
		return getService().fetchWebhook(webhookId);
	}

	/**
	 * Returns the webhook matching the UUID and group.
	 *
	 * @param uuid the webhook's UUID
	 * @param groupId the primary key of the group
	 * @return the matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	public static Webhook fetchWebhookByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchWebhookByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the webhook with the primary key.
	 *
	 * @param webhookId the primary key of the webhook
	 * @return the webhook
	 * @throws PortalException if a webhook with the primary key could not be found
	 */
	public static Webhook getWebhook(long webhookId) throws PortalException {
		return getService().getWebhook(webhookId);
	}

	/**
	 * Returns the webhook matching the UUID and group.
	 *
	 * @param uuid the webhook's UUID
	 * @param groupId the primary key of the group
	 * @return the matching webhook
	 * @throws PortalException if a matching webhook could not be found
	 */
	public static Webhook getWebhookByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return getService().getWebhookByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the webhooks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.webhooks.model.impl.WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @return the range of webhooks
	 */
	public static List<Webhook> getWebhooks(int start, int end) {
		return getService().getWebhooks(start, end);
	}

	/**
	 * Returns all the webhooks matching the UUID and company.
	 *
	 * @param uuid the UUID of the webhooks
	 * @param companyId the primary key of the company
	 * @return the matching webhooks, or an empty list if no matches were found
	 */
	public static List<Webhook> getWebhooksByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getWebhooksByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of webhooks matching the UUID and company.
	 *
	 * @param uuid the UUID of the webhooks
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching webhooks, or an empty list if no matches were found
	 */
	public static List<Webhook> getWebhooksByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Webhook> orderByComparator) {

		return getService().getWebhooksByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of webhooks.
	 *
	 * @return the number of webhooks
	 */
	public static int getWebhooksCount() {
		return getService().getWebhooksCount();
	}

	/**
	 * Updates the webhook in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebhookLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webhook the webhook
	 * @return the webhook that was updated
	 */
	public static Webhook updateWebhook(Webhook webhook) {
		return getService().updateWebhook(webhook);
	}

	public static WebhookLocalService getService() {
		return _service;
	}

	private static volatile WebhookLocalService _service;

}