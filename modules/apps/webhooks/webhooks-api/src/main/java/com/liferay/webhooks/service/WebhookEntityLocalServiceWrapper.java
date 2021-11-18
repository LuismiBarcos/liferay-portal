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
 * Provides a wrapper for {@link WebhookEntityLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WebhookEntityLocalService
 * @generated
 */
public class WebhookEntityLocalServiceWrapper
	implements ServiceWrapper<WebhookEntityLocalService>,
			   WebhookEntityLocalService {

	public WebhookEntityLocalServiceWrapper(
		WebhookEntityLocalService webhookEntityLocalService) {

		_webhookEntityLocalService = webhookEntityLocalService;
	}

	@Override
	public com.liferay.webhooks.model.WebhookEntity addWebhookEntity(
			String entityClassName, long userId, long webhookId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookEntityLocalService.addWebhookEntity(
			entityClassName, userId, webhookId, serviceContext);
	}

	/**
	 * Adds the webhook entity to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebhookEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webhookEntity the webhook entity
	 * @return the webhook entity that was added
	 */
	@Override
	public com.liferay.webhooks.model.WebhookEntity addWebhookEntity(
		com.liferay.webhooks.model.WebhookEntity webhookEntity) {

		return _webhookEntityLocalService.addWebhookEntity(webhookEntity);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookEntityLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new webhook entity with the primary key. Does not add the webhook entity to the database.
	 *
	 * @param webhookEntityId the primary key for the new webhook entity
	 * @return the new webhook entity
	 */
	@Override
	public com.liferay.webhooks.model.WebhookEntity createWebhookEntity(
		long webhookEntityId) {

		return _webhookEntityLocalService.createWebhookEntity(webhookEntityId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookEntityLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the webhook entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebhookEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webhookEntityId the primary key of the webhook entity
	 * @return the webhook entity that was removed
	 * @throws PortalException if a webhook entity with the primary key could not be found
	 */
	@Override
	public com.liferay.webhooks.model.WebhookEntity deleteWebhookEntity(
			long webhookEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookEntityLocalService.deleteWebhookEntity(webhookEntityId);
	}

	/**
	 * Deletes the webhook entity from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebhookEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webhookEntity the webhook entity
	 * @return the webhook entity that was removed
	 */
	@Override
	public com.liferay.webhooks.model.WebhookEntity deleteWebhookEntity(
		com.liferay.webhooks.model.WebhookEntity webhookEntity) {

		return _webhookEntityLocalService.deleteWebhookEntity(webhookEntity);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _webhookEntityLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _webhookEntityLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _webhookEntityLocalService.dynamicQuery();
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

		return _webhookEntityLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.webhooks.model.impl.WebhookEntityModelImpl</code>.
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

		return _webhookEntityLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.webhooks.model.impl.WebhookEntityModelImpl</code>.
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

		return _webhookEntityLocalService.dynamicQuery(
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

		return _webhookEntityLocalService.dynamicQueryCount(dynamicQuery);
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

		return _webhookEntityLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.webhooks.model.WebhookEntity fetchWebhookEntity(
		long webhookEntityId) {

		return _webhookEntityLocalService.fetchWebhookEntity(webhookEntityId);
	}

	/**
	 * Returns the webhook entity matching the UUID and group.
	 *
	 * @param uuid the webhook entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	@Override
	public com.liferay.webhooks.model.WebhookEntity
		fetchWebhookEntityByUuidAndGroupId(String uuid, long groupId) {

		return _webhookEntityLocalService.fetchWebhookEntityByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _webhookEntityLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _webhookEntityLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _webhookEntityLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _webhookEntityLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookEntityLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the webhook entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.webhooks.model.impl.WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @return the range of webhook entities
	 */
	@Override
	public java.util.List<com.liferay.webhooks.model.WebhookEntity>
		getWebhookEntities(int start, int end) {

		return _webhookEntityLocalService.getWebhookEntities(start, end);
	}

	/**
	 * Returns all the webhook entities matching the UUID and company.
	 *
	 * @param uuid the UUID of the webhook entities
	 * @param companyId the primary key of the company
	 * @return the matching webhook entities, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.webhooks.model.WebhookEntity>
		getWebhookEntitiesByUuidAndCompanyId(String uuid, long companyId) {

		return _webhookEntityLocalService.getWebhookEntitiesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of webhook entities matching the UUID and company.
	 *
	 * @param uuid the UUID of the webhook entities
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching webhook entities, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.webhooks.model.WebhookEntity>
		getWebhookEntitiesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.webhooks.model.WebhookEntity> orderByComparator) {

		return _webhookEntityLocalService.getWebhookEntitiesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of webhook entities.
	 *
	 * @return the number of webhook entities
	 */
	@Override
	public int getWebhookEntitiesCount() {
		return _webhookEntityLocalService.getWebhookEntitiesCount();
	}

	/**
	 * Returns the webhook entity with the primary key.
	 *
	 * @param webhookEntityId the primary key of the webhook entity
	 * @return the webhook entity
	 * @throws PortalException if a webhook entity with the primary key could not be found
	 */
	@Override
	public com.liferay.webhooks.model.WebhookEntity getWebhookEntity(
			long webhookEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookEntityLocalService.getWebhookEntity(webhookEntityId);
	}

	/**
	 * Returns the webhook entity matching the UUID and group.
	 *
	 * @param uuid the webhook entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching webhook entity
	 * @throws PortalException if a matching webhook entity could not be found
	 */
	@Override
	public com.liferay.webhooks.model.WebhookEntity
			getWebhookEntityByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webhookEntityLocalService.getWebhookEntityByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Updates the webhook entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebhookEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webhookEntity the webhook entity
	 * @return the webhook entity that was updated
	 */
	@Override
	public com.liferay.webhooks.model.WebhookEntity updateWebhookEntity(
		com.liferay.webhooks.model.WebhookEntity webhookEntity) {

		return _webhookEntityLocalService.updateWebhookEntity(webhookEntity);
	}

	@Override
	public WebhookEntityLocalService getWrappedService() {
		return _webhookEntityLocalService;
	}

	@Override
	public void setWrappedService(
		WebhookEntityLocalService webhookEntityLocalService) {

		_webhookEntityLocalService = webhookEntityLocalService;
	}

	private WebhookEntityLocalService _webhookEntityLocalService;

}