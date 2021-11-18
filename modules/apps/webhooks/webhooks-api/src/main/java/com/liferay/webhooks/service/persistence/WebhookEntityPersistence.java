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

package com.liferay.webhooks.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.webhooks.exception.NoSuchWebhookEntityException;
import com.liferay.webhooks.model.WebhookEntity;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the webhook entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebhookEntityUtil
 * @generated
 */
@ProviderType
public interface WebhookEntityPersistence
	extends BasePersistence<WebhookEntity> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WebhookEntityUtil} to access the webhook entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the webhook entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByUuid(String uuid);

	/**
	 * Returns a range of all the webhook entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @return the range of matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the webhook entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the webhook entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first webhook entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	public WebhookEntity findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
				orderByComparator)
		throws NoSuchWebhookEntityException;

	/**
	 * Returns the first webhook entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	public WebhookEntity fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator);

	/**
	 * Returns the last webhook entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	public WebhookEntity findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
				orderByComparator)
		throws NoSuchWebhookEntityException;

	/**
	 * Returns the last webhook entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	public WebhookEntity fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator);

	/**
	 * Returns the webhook entities before and after the current webhook entity in the ordered set where uuid = &#63;.
	 *
	 * @param webhookEntityId the primary key of the current webhook entity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook entity
	 * @throws NoSuchWebhookEntityException if a webhook entity with the primary key could not be found
	 */
	public WebhookEntity[] findByUuid_PrevAndNext(
			long webhookEntityId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
				orderByComparator)
		throws NoSuchWebhookEntityException;

	/**
	 * Removes all the webhook entities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of webhook entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching webhook entities
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the webhook entity where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchWebhookEntityException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	public WebhookEntity findByUUID_G(String uuid, long groupId)
		throws NoSuchWebhookEntityException;

	/**
	 * Returns the webhook entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	public WebhookEntity fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the webhook entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	public WebhookEntity fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the webhook entity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the webhook entity that was removed
	 */
	public WebhookEntity removeByUUID_G(String uuid, long groupId)
		throws NoSuchWebhookEntityException;

	/**
	 * Returns the number of webhook entities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching webhook entities
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the webhook entities where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the webhook entities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @return the range of matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the webhook entities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the webhook entities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first webhook entity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	public WebhookEntity findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
				orderByComparator)
		throws NoSuchWebhookEntityException;

	/**
	 * Returns the first webhook entity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	public WebhookEntity fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator);

	/**
	 * Returns the last webhook entity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	public WebhookEntity findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
				orderByComparator)
		throws NoSuchWebhookEntityException;

	/**
	 * Returns the last webhook entity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	public WebhookEntity fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator);

	/**
	 * Returns the webhook entities before and after the current webhook entity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param webhookEntityId the primary key of the current webhook entity
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook entity
	 * @throws NoSuchWebhookEntityException if a webhook entity with the primary key could not be found
	 */
	public WebhookEntity[] findByUuid_C_PrevAndNext(
			long webhookEntityId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
				orderByComparator)
		throws NoSuchWebhookEntityException;

	/**
	 * Removes all the webhook entities where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of webhook entities where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching webhook entities
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the webhook entities where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByGroupId(long groupId);

	/**
	 * Returns a range of all the webhook entities where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @return the range of matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the webhook entities where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the webhook entities where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching webhook entities
	 */
	public java.util.List<WebhookEntity> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first webhook entity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	public WebhookEntity findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
				orderByComparator)
		throws NoSuchWebhookEntityException;

	/**
	 * Returns the first webhook entity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	public WebhookEntity fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator);

	/**
	 * Returns the last webhook entity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	public WebhookEntity findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
				orderByComparator)
		throws NoSuchWebhookEntityException;

	/**
	 * Returns the last webhook entity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	public WebhookEntity fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator);

	/**
	 * Returns the webhook entities before and after the current webhook entity in the ordered set where groupId = &#63;.
	 *
	 * @param webhookEntityId the primary key of the current webhook entity
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook entity
	 * @throws NoSuchWebhookEntityException if a webhook entity with the primary key could not be found
	 */
	public WebhookEntity[] findByGroupId_PrevAndNext(
			long webhookEntityId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
				orderByComparator)
		throws NoSuchWebhookEntityException;

	/**
	 * Removes all the webhook entities where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of webhook entities where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching webhook entities
	 */
	public int countByGroupId(long groupId);

	/**
	 * Caches the webhook entity in the entity cache if it is enabled.
	 *
	 * @param webhookEntity the webhook entity
	 */
	public void cacheResult(WebhookEntity webhookEntity);

	/**
	 * Caches the webhook entities in the entity cache if it is enabled.
	 *
	 * @param webhookEntities the webhook entities
	 */
	public void cacheResult(java.util.List<WebhookEntity> webhookEntities);

	/**
	 * Creates a new webhook entity with the primary key. Does not add the webhook entity to the database.
	 *
	 * @param webhookEntityId the primary key for the new webhook entity
	 * @return the new webhook entity
	 */
	public WebhookEntity create(long webhookEntityId);

	/**
	 * Removes the webhook entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webhookEntityId the primary key of the webhook entity
	 * @return the webhook entity that was removed
	 * @throws NoSuchWebhookEntityException if a webhook entity with the primary key could not be found
	 */
	public WebhookEntity remove(long webhookEntityId)
		throws NoSuchWebhookEntityException;

	public WebhookEntity updateImpl(WebhookEntity webhookEntity);

	/**
	 * Returns the webhook entity with the primary key or throws a <code>NoSuchWebhookEntityException</code> if it could not be found.
	 *
	 * @param webhookEntityId the primary key of the webhook entity
	 * @return the webhook entity
	 * @throws NoSuchWebhookEntityException if a webhook entity with the primary key could not be found
	 */
	public WebhookEntity findByPrimaryKey(long webhookEntityId)
		throws NoSuchWebhookEntityException;

	/**
	 * Returns the webhook entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webhookEntityId the primary key of the webhook entity
	 * @return the webhook entity, or <code>null</code> if a webhook entity with the primary key could not be found
	 */
	public WebhookEntity fetchByPrimaryKey(long webhookEntityId);

	/**
	 * Returns all the webhook entities.
	 *
	 * @return the webhook entities
	 */
	public java.util.List<WebhookEntity> findAll();

	/**
	 * Returns a range of all the webhook entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @return the range of webhook entities
	 */
	public java.util.List<WebhookEntity> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the webhook entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of webhook entities
	 */
	public java.util.List<WebhookEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the webhook entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhook entities
	 * @param end the upper bound of the range of webhook entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of webhook entities
	 */
	public java.util.List<WebhookEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebhookEntity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the webhook entities from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of webhook entities.
	 *
	 * @return the number of webhook entities
	 */
	public int countAll();

}