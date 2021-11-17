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
import com.liferay.webhooks.exception.NoSuchWebhookException;
import com.liferay.webhooks.model.Webhook;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the webhook service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebhookUtil
 * @generated
 */
@ProviderType
public interface WebhookPersistence extends BasePersistence<Webhook> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WebhookUtil} to access the webhook persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the webhooks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching webhooks
	 */
	public java.util.List<Webhook> findByUuid(String uuid);

	/**
	 * Returns a range of all the webhooks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @return the range of matching webhooks
	 */
	public java.util.List<Webhook> findByUuid(String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the webhooks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching webhooks
	 */
	public java.util.List<Webhook> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator);

	/**
	 * Returns an ordered range of all the webhooks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching webhooks
	 */
	public java.util.List<Webhook> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first webhook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	public Webhook findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Webhook>
				orderByComparator)
		throws NoSuchWebhookException;

	/**
	 * Returns the first webhook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	public Webhook fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator);

	/**
	 * Returns the last webhook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	public Webhook findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Webhook>
				orderByComparator)
		throws NoSuchWebhookException;

	/**
	 * Returns the last webhook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	public Webhook fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator);

	/**
	 * Returns the webhooks before and after the current webhook in the ordered set where uuid = &#63;.
	 *
	 * @param webhookId the primary key of the current webhook
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook
	 * @throws NoSuchWebhookException if a webhook with the primary key could not be found
	 */
	public Webhook[] findByUuid_PrevAndNext(
			long webhookId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Webhook>
				orderByComparator)
		throws NoSuchWebhookException;

	/**
	 * Removes all the webhooks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of webhooks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching webhooks
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the webhook where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchWebhookException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	public Webhook findByUUID_G(String uuid, long groupId)
		throws NoSuchWebhookException;

	/**
	 * Returns the webhook where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	public Webhook fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the webhook where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	public Webhook fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the webhook where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the webhook that was removed
	 */
	public Webhook removeByUUID_G(String uuid, long groupId)
		throws NoSuchWebhookException;

	/**
	 * Returns the number of webhooks where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching webhooks
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the webhooks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching webhooks
	 */
	public java.util.List<Webhook> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the webhooks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @return the range of matching webhooks
	 */
	public java.util.List<Webhook> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the webhooks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching webhooks
	 */
	public java.util.List<Webhook> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator);

	/**
	 * Returns an ordered range of all the webhooks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching webhooks
	 */
	public java.util.List<Webhook> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first webhook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	public Webhook findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Webhook>
				orderByComparator)
		throws NoSuchWebhookException;

	/**
	 * Returns the first webhook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	public Webhook fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator);

	/**
	 * Returns the last webhook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	public Webhook findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Webhook>
				orderByComparator)
		throws NoSuchWebhookException;

	/**
	 * Returns the last webhook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	public Webhook fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator);

	/**
	 * Returns the webhooks before and after the current webhook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param webhookId the primary key of the current webhook
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook
	 * @throws NoSuchWebhookException if a webhook with the primary key could not be found
	 */
	public Webhook[] findByUuid_C_PrevAndNext(
			long webhookId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Webhook>
				orderByComparator)
		throws NoSuchWebhookException;

	/**
	 * Removes all the webhooks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of webhooks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching webhooks
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the webhooks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching webhooks
	 */
	public java.util.List<Webhook> findByGroupId(long groupId);

	/**
	 * Returns a range of all the webhooks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @return the range of matching webhooks
	 */
	public java.util.List<Webhook> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the webhooks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching webhooks
	 */
	public java.util.List<Webhook> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator);

	/**
	 * Returns an ordered range of all the webhooks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching webhooks
	 */
	public java.util.List<Webhook> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first webhook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	public Webhook findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Webhook>
				orderByComparator)
		throws NoSuchWebhookException;

	/**
	 * Returns the first webhook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	public Webhook fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator);

	/**
	 * Returns the last webhook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	public Webhook findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Webhook>
				orderByComparator)
		throws NoSuchWebhookException;

	/**
	 * Returns the last webhook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	public Webhook fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator);

	/**
	 * Returns the webhooks before and after the current webhook in the ordered set where groupId = &#63;.
	 *
	 * @param webhookId the primary key of the current webhook
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook
	 * @throws NoSuchWebhookException if a webhook with the primary key could not be found
	 */
	public Webhook[] findByGroupId_PrevAndNext(
			long webhookId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Webhook>
				orderByComparator)
		throws NoSuchWebhookException;

	/**
	 * Removes all the webhooks where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of webhooks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching webhooks
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the webhook where groupId = &#63; and webhookURL = &#63; or throws a <code>NoSuchWebhookException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param webhookURL the webhook url
	 * @return the matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	public Webhook findByG_WU(long groupId, String webhookURL)
		throws NoSuchWebhookException;

	/**
	 * Returns the webhook where groupId = &#63; and webhookURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param webhookURL the webhook url
	 * @return the matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	public Webhook fetchByG_WU(long groupId, String webhookURL);

	/**
	 * Returns the webhook where groupId = &#63; and webhookURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param webhookURL the webhook url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	public Webhook fetchByG_WU(
		long groupId, String webhookURL, boolean useFinderCache);

	/**
	 * Removes the webhook where groupId = &#63; and webhookURL = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param webhookURL the webhook url
	 * @return the webhook that was removed
	 */
	public Webhook removeByG_WU(long groupId, String webhookURL)
		throws NoSuchWebhookException;

	/**
	 * Returns the number of webhooks where groupId = &#63; and webhookURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param webhookURL the webhook url
	 * @return the number of matching webhooks
	 */
	public int countByG_WU(long groupId, String webhookURL);

	/**
	 * Caches the webhook in the entity cache if it is enabled.
	 *
	 * @param webhook the webhook
	 */
	public void cacheResult(Webhook webhook);

	/**
	 * Caches the webhooks in the entity cache if it is enabled.
	 *
	 * @param webhooks the webhooks
	 */
	public void cacheResult(java.util.List<Webhook> webhooks);

	/**
	 * Creates a new webhook with the primary key. Does not add the webhook to the database.
	 *
	 * @param webhookId the primary key for the new webhook
	 * @return the new webhook
	 */
	public Webhook create(long webhookId);

	/**
	 * Removes the webhook with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webhookId the primary key of the webhook
	 * @return the webhook that was removed
	 * @throws NoSuchWebhookException if a webhook with the primary key could not be found
	 */
	public Webhook remove(long webhookId) throws NoSuchWebhookException;

	public Webhook updateImpl(Webhook webhook);

	/**
	 * Returns the webhook with the primary key or throws a <code>NoSuchWebhookException</code> if it could not be found.
	 *
	 * @param webhookId the primary key of the webhook
	 * @return the webhook
	 * @throws NoSuchWebhookException if a webhook with the primary key could not be found
	 */
	public Webhook findByPrimaryKey(long webhookId)
		throws NoSuchWebhookException;

	/**
	 * Returns the webhook with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webhookId the primary key of the webhook
	 * @return the webhook, or <code>null</code> if a webhook with the primary key could not be found
	 */
	public Webhook fetchByPrimaryKey(long webhookId);

	/**
	 * Returns all the webhooks.
	 *
	 * @return the webhooks
	 */
	public java.util.List<Webhook> findAll();

	/**
	 * Returns a range of all the webhooks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @return the range of webhooks
	 */
	public java.util.List<Webhook> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the webhooks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of webhooks
	 */
	public java.util.List<Webhook> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator);

	/**
	 * Returns an ordered range of all the webhooks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhooks
	 * @param end the upper bound of the range of webhooks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of webhooks
	 */
	public java.util.List<Webhook> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Webhook>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the webhooks from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of webhooks.
	 *
	 * @return the number of webhooks
	 */
	public int countAll();

}