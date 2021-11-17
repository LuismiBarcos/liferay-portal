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
 * Provides a wrapper for {@link WebhookLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WebhookLocalService
 * @generated
 */
public class WebhookLocalServiceWrapper
	implements ServiceWrapper<WebhookLocalService>, WebhookLocalService {

	public WebhookLocalServiceWrapper(WebhookLocalService webhookLocalService) {
		_webhookLocalService = webhookLocalService;
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
	@Override
	public com.liferay.webhooks.model.Webhook addWebhook(
		com.liferay.webhooks.model.Webhook webhook) {

		return _webhookLocalService.addWebhook(webhook);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new webhook with the primary key. Does not add the webhook to the database.
	 *
	 * @param webhookId the primary key for the new webhook
	 * @return the new webhook
	 */
	@Override
	public com.liferay.webhooks.model.Webhook createWebhook(long webhookId) {
		return _webhookLocalService.createWebhook(webhookId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookLocalService.deletePersistedModel(persistedModel);
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
	@Override
	public com.liferay.webhooks.model.Webhook deleteWebhook(long webhookId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookLocalService.deleteWebhook(webhookId);
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
	@Override
	public com.liferay.webhooks.model.Webhook deleteWebhook(
		com.liferay.webhooks.model.Webhook webhook) {

		return _webhookLocalService.deleteWebhook(webhook);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _webhookLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _webhookLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _webhookLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _webhookLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _webhookLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _webhookLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _webhookLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _webhookLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.webhooks.model.Webhook fetchWebhook(long webhookId) {
		return _webhookLocalService.fetchWebhook(webhookId);
	}

	/**
	 * Returns the webhook matching the UUID and group.
	 *
	 * @param uuid the webhook's UUID
	 * @param groupId the primary key of the group
	 * @return the matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	@Override
	public com.liferay.webhooks.model.Webhook fetchWebhookByUuidAndGroupId(
		String uuid, long groupId) {

		return _webhookLocalService.fetchWebhookByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _webhookLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _webhookLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _webhookLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _webhookLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the webhook with the primary key.
	 *
	 * @param webhookId the primary key of the webhook
	 * @return the webhook
	 * @throws PortalException if a webhook with the primary key could not be found
	 */
	@Override
	public com.liferay.webhooks.model.Webhook getWebhook(long webhookId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookLocalService.getWebhook(webhookId);
	}

	/**
	 * Returns the webhook matching the UUID and group.
	 *
	 * @param uuid the webhook's UUID
	 * @param groupId the primary key of the group
	 * @return the matching webhook
	 * @throws PortalException if a matching webhook could not be found
	 */
	@Override
	public com.liferay.webhooks.model.Webhook getWebhookByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookLocalService.getWebhookByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List<com.liferay.webhooks.model.Webhook> getWebhooks(
		int start, int end) {

		return _webhookLocalService.getWebhooks(start, end);
	}

	/**
	 * Returns all the webhooks matching the UUID and company.
	 *
	 * @param uuid the UUID of the webhooks
	 * @param companyId the primary key of the company
	 * @return the matching webhooks, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.webhooks.model.Webhook>
		getWebhooksByUuidAndCompanyId(String uuid, long companyId) {

		return _webhookLocalService.getWebhooksByUuidAndCompanyId(
			uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.webhooks.model.Webhook>
		getWebhooksByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.webhooks.model.Webhook> orderByComparator) {

		return _webhookLocalService.getWebhooksByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of webhooks.
	 *
	 * @return the number of webhooks
	 */
	@Override
	public int getWebhooksCount() {
		return _webhookLocalService.getWebhooksCount();
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
	@Override
	public com.liferay.webhooks.model.Webhook updateWebhook(
		com.liferay.webhooks.model.Webhook webhook) {

		return _webhookLocalService.updateWebhook(webhook);
	}

	@Override
	public WebhookLocalService getWrappedService() {
		return _webhookLocalService;
	}

	@Override
	public void setWrappedService(WebhookLocalService webhookLocalService) {
		_webhookLocalService = webhookLocalService;
	}

	private WebhookLocalService _webhookLocalService;

}