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
import com.liferay.webhooks.model.WebhookEntity;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for WebhookEntity. This utility wraps
 * <code>com.liferay.webhooks.service.impl.WebhookEntityLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WebhookEntityLocalService
 * @generated
 */
public class WebhookEntityLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.webhooks.service.impl.WebhookEntityLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static WebhookEntity addWebhookEntity(
			String entityClassName, long userId, long webhookId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addWebhookEntity(
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
	public static WebhookEntity addWebhookEntity(WebhookEntity webhookEntity) {
		return getService().addWebhookEntity(webhookEntity);
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
	 * Creates a new webhook entity with the primary key. Does not add the webhook entity to the database.
	 *
	 * @param webhookEntityId the primary key for the new webhook entity
	 * @return the new webhook entity
	 */
	public static WebhookEntity createWebhookEntity(long webhookEntityId) {
		return getService().createWebhookEntity(webhookEntityId);
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
	public static WebhookEntity deleteWebhookEntity(long webhookEntityId)
		throws PortalException {

		return getService().deleteWebhookEntity(webhookEntityId);
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
	public static WebhookEntity deleteWebhookEntity(
		WebhookEntity webhookEntity) {

		return getService().deleteWebhookEntity(webhookEntity);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.webhooks.model.impl.WebhookEntityModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.webhooks.model.impl.WebhookEntityModelImpl</code>.
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

	public static WebhookEntity fetchWebhookEntity(long webhookEntityId) {
		return getService().fetchWebhookEntity(webhookEntityId);
	}

	/**
	 * Returns the webhook entity matching the UUID and group.
	 *
	 * @param uuid the webhook entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	public static WebhookEntity fetchWebhookEntityByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchWebhookEntityByUuidAndGroupId(uuid, groupId);
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
	public static List<WebhookEntity> getWebhookEntities(int start, int end) {
		return getService().getWebhookEntities(start, end);
	}

	/**
	 * Returns all the webhook entities matching the UUID and company.
	 *
	 * @param uuid the UUID of the webhook entities
	 * @param companyId the primary key of the company
	 * @return the matching webhook entities, or an empty list if no matches were found
	 */
	public static List<WebhookEntity> getWebhookEntitiesByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getWebhookEntitiesByUuidAndCompanyId(
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
	public static List<WebhookEntity> getWebhookEntitiesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WebhookEntity> orderByComparator) {

		return getService().getWebhookEntitiesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of webhook entities.
	 *
	 * @return the number of webhook entities
	 */
	public static int getWebhookEntitiesCount() {
		return getService().getWebhookEntitiesCount();
	}

	/**
	 * Returns the webhook entity with the primary key.
	 *
	 * @param webhookEntityId the primary key of the webhook entity
	 * @return the webhook entity
	 * @throws PortalException if a webhook entity with the primary key could not be found
	 */
	public static WebhookEntity getWebhookEntity(long webhookEntityId)
		throws PortalException {

		return getService().getWebhookEntity(webhookEntityId);
	}

	/**
	 * Returns the webhook entity matching the UUID and group.
	 *
	 * @param uuid the webhook entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching webhook entity
	 * @throws PortalException if a matching webhook entity could not be found
	 */
	public static WebhookEntity getWebhookEntityByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getWebhookEntityByUuidAndGroupId(uuid, groupId);
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
	public static WebhookEntity updateWebhookEntity(
		WebhookEntity webhookEntity) {

		return getService().updateWebhookEntity(webhookEntity);
	}

	public static WebhookEntityLocalService getService() {
		return _service;
	}

	private static volatile WebhookEntityLocalService _service;

}