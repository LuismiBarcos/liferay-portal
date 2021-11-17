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

package com.liferay.webhooks.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.webhooks.exception.NoSuchWebhookException;
import com.liferay.webhooks.model.Webhook;
import com.liferay.webhooks.model.WebhookTable;
import com.liferay.webhooks.model.impl.WebhookImpl;
import com.liferay.webhooks.model.impl.WebhookModelImpl;
import com.liferay.webhooks.service.persistence.WebhookPersistence;
import com.liferay.webhooks.service.persistence.WebhookUtil;
import com.liferay.webhooks.service.persistence.impl.constants.WebhooksPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the webhook service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {WebhookPersistence.class, BasePersistence.class})
public class WebhookPersistenceImpl
	extends BasePersistenceImpl<Webhook> implements WebhookPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>WebhookUtil</code> to access the webhook persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		WebhookImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the webhooks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching webhooks
	 */
	@Override
	public List<Webhook> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<Webhook> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

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
	@Override
	public List<Webhook> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Webhook> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

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
	@Override
	public List<Webhook> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Webhook> orderByComparator, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<Webhook> list = null;

		if (useFinderCache) {
			list = (List<Webhook>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Webhook webhook : list) {
					if (!uuid.equals(webhook.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_WEBHOOK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(WebhookModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<Webhook>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first webhook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	@Override
	public Webhook findByUuid_First(
			String uuid, OrderByComparator<Webhook> orderByComparator)
		throws NoSuchWebhookException {

		Webhook webhook = fetchByUuid_First(uuid, orderByComparator);

		if (webhook != null) {
			return webhook;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchWebhookException(sb.toString());
	}

	/**
	 * Returns the first webhook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	@Override
	public Webhook fetchByUuid_First(
		String uuid, OrderByComparator<Webhook> orderByComparator) {

		List<Webhook> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last webhook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	@Override
	public Webhook findByUuid_Last(
			String uuid, OrderByComparator<Webhook> orderByComparator)
		throws NoSuchWebhookException {

		Webhook webhook = fetchByUuid_Last(uuid, orderByComparator);

		if (webhook != null) {
			return webhook;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchWebhookException(sb.toString());
	}

	/**
	 * Returns the last webhook in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	@Override
	public Webhook fetchByUuid_Last(
		String uuid, OrderByComparator<Webhook> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<Webhook> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the webhooks before and after the current webhook in the ordered set where uuid = &#63;.
	 *
	 * @param webhookId the primary key of the current webhook
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook
	 * @throws NoSuchWebhookException if a webhook with the primary key could not be found
	 */
	@Override
	public Webhook[] findByUuid_PrevAndNext(
			long webhookId, String uuid,
			OrderByComparator<Webhook> orderByComparator)
		throws NoSuchWebhookException {

		uuid = Objects.toString(uuid, "");

		Webhook webhook = findByPrimaryKey(webhookId);

		Session session = null;

		try {
			session = openSession();

			Webhook[] array = new WebhookImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, webhook, uuid, orderByComparator, true);

			array[1] = webhook;

			array[2] = getByUuid_PrevAndNext(
				session, webhook, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Webhook getByUuid_PrevAndNext(
		Session session, Webhook webhook, String uuid,
		OrderByComparator<Webhook> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_WEBHOOK_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(WebhookModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(webhook)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Webhook> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the webhooks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (Webhook webhook :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(webhook);
		}
	}

	/**
	 * Returns the number of webhooks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching webhooks
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_WEBHOOK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 = "webhook.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(webhook.uuid IS NULL OR webhook.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the webhook where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchWebhookException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	@Override
	public Webhook findByUUID_G(String uuid, long groupId)
		throws NoSuchWebhookException {

		Webhook webhook = fetchByUUID_G(uuid, groupId);

		if (webhook == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchWebhookException(sb.toString());
		}

		return webhook;
	}

	/**
	 * Returns the webhook where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	@Override
	public Webhook fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the webhook where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	@Override
	public Webhook fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof Webhook) {
			Webhook webhook = (Webhook)result;

			if (!Objects.equals(uuid, webhook.getUuid()) ||
				(groupId != webhook.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_WEBHOOK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<Webhook> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					Webhook webhook = list.get(0);

					result = webhook;

					cacheResult(webhook);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Webhook)result;
		}
	}

	/**
	 * Removes the webhook where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the webhook that was removed
	 */
	@Override
	public Webhook removeByUUID_G(String uuid, long groupId)
		throws NoSuchWebhookException {

		Webhook webhook = findByUUID_G(uuid, groupId);

		return remove(webhook);
	}

	/**
	 * Returns the number of webhooks where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching webhooks
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WEBHOOK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"webhook.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(webhook.uuid IS NULL OR webhook.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"webhook.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the webhooks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching webhooks
	 */
	@Override
	public List<Webhook> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<Webhook> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

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
	@Override
	public List<Webhook> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Webhook> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

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
	@Override
	public List<Webhook> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Webhook> orderByComparator, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<Webhook> list = null;

		if (useFinderCache) {
			list = (List<Webhook>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Webhook webhook : list) {
					if (!uuid.equals(webhook.getUuid()) ||
						(companyId != webhook.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_WEBHOOK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(WebhookModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<Webhook>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first webhook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	@Override
	public Webhook findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<Webhook> orderByComparator)
		throws NoSuchWebhookException {

		Webhook webhook = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (webhook != null) {
			return webhook;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchWebhookException(sb.toString());
	}

	/**
	 * Returns the first webhook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	@Override
	public Webhook fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<Webhook> orderByComparator) {

		List<Webhook> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last webhook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	@Override
	public Webhook findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<Webhook> orderByComparator)
		throws NoSuchWebhookException {

		Webhook webhook = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (webhook != null) {
			return webhook;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchWebhookException(sb.toString());
	}

	/**
	 * Returns the last webhook in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	@Override
	public Webhook fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<Webhook> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<Webhook> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

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
	@Override
	public Webhook[] findByUuid_C_PrevAndNext(
			long webhookId, String uuid, long companyId,
			OrderByComparator<Webhook> orderByComparator)
		throws NoSuchWebhookException {

		uuid = Objects.toString(uuid, "");

		Webhook webhook = findByPrimaryKey(webhookId);

		Session session = null;

		try {
			session = openSession();

			Webhook[] array = new WebhookImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, webhook, uuid, companyId, orderByComparator, true);

			array[1] = webhook;

			array[2] = getByUuid_C_PrevAndNext(
				session, webhook, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Webhook getByUuid_C_PrevAndNext(
		Session session, Webhook webhook, String uuid, long companyId,
		OrderByComparator<Webhook> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_WEBHOOK_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(WebhookModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(webhook)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Webhook> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the webhooks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (Webhook webhook :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(webhook);
		}
	}

	/**
	 * Returns the number of webhooks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching webhooks
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WEBHOOK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"webhook.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(webhook.uuid IS NULL OR webhook.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"webhook.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the webhooks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching webhooks
	 */
	@Override
	public List<Webhook> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<Webhook> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

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
	@Override
	public List<Webhook> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<Webhook> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

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
	@Override
	public List<Webhook> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<Webhook> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<Webhook> list = null;

		if (useFinderCache) {
			list = (List<Webhook>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Webhook webhook : list) {
					if (groupId != webhook.getGroupId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_WEBHOOK_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(WebhookModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<Webhook>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first webhook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	@Override
	public Webhook findByGroupId_First(
			long groupId, OrderByComparator<Webhook> orderByComparator)
		throws NoSuchWebhookException {

		Webhook webhook = fetchByGroupId_First(groupId, orderByComparator);

		if (webhook != null) {
			return webhook;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchWebhookException(sb.toString());
	}

	/**
	 * Returns the first webhook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	@Override
	public Webhook fetchByGroupId_First(
		long groupId, OrderByComparator<Webhook> orderByComparator) {

		List<Webhook> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last webhook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	@Override
	public Webhook findByGroupId_Last(
			long groupId, OrderByComparator<Webhook> orderByComparator)
		throws NoSuchWebhookException {

		Webhook webhook = fetchByGroupId_Last(groupId, orderByComparator);

		if (webhook != null) {
			return webhook;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchWebhookException(sb.toString());
	}

	/**
	 * Returns the last webhook in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	@Override
	public Webhook fetchByGroupId_Last(
		long groupId, OrderByComparator<Webhook> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<Webhook> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the webhooks before and after the current webhook in the ordered set where groupId = &#63;.
	 *
	 * @param webhookId the primary key of the current webhook
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook
	 * @throws NoSuchWebhookException if a webhook with the primary key could not be found
	 */
	@Override
	public Webhook[] findByGroupId_PrevAndNext(
			long webhookId, long groupId,
			OrderByComparator<Webhook> orderByComparator)
		throws NoSuchWebhookException {

		Webhook webhook = findByPrimaryKey(webhookId);

		Session session = null;

		try {
			session = openSession();

			Webhook[] array = new WebhookImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, webhook, groupId, orderByComparator, true);

			array[1] = webhook;

			array[2] = getByGroupId_PrevAndNext(
				session, webhook, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Webhook getByGroupId_PrevAndNext(
		Session session, Webhook webhook, long groupId,
		OrderByComparator<Webhook> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_WEBHOOK_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(WebhookModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(webhook)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Webhook> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the webhooks where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (Webhook webhook :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(webhook);
		}
	}

	/**
	 * Returns the number of webhooks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching webhooks
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_WEBHOOK_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"webhook.groupId = ?";

	private FinderPath _finderPathFetchByG_WU;
	private FinderPath _finderPathCountByG_WU;

	/**
	 * Returns the webhook where groupId = &#63; and webhookURL = &#63; or throws a <code>NoSuchWebhookException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param webhookURL the webhook url
	 * @return the matching webhook
	 * @throws NoSuchWebhookException if a matching webhook could not be found
	 */
	@Override
	public Webhook findByG_WU(long groupId, String webhookURL)
		throws NoSuchWebhookException {

		Webhook webhook = fetchByG_WU(groupId, webhookURL);

		if (webhook == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", webhookURL=");
			sb.append(webhookURL);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchWebhookException(sb.toString());
		}

		return webhook;
	}

	/**
	 * Returns the webhook where groupId = &#63; and webhookURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param webhookURL the webhook url
	 * @return the matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	@Override
	public Webhook fetchByG_WU(long groupId, String webhookURL) {
		return fetchByG_WU(groupId, webhookURL, true);
	}

	/**
	 * Returns the webhook where groupId = &#63; and webhookURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param webhookURL the webhook url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching webhook, or <code>null</code> if a matching webhook could not be found
	 */
	@Override
	public Webhook fetchByG_WU(
		long groupId, String webhookURL, boolean useFinderCache) {

		webhookURL = Objects.toString(webhookURL, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, webhookURL};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByG_WU, finderArgs);
		}

		if (result instanceof Webhook) {
			Webhook webhook = (Webhook)result;

			if ((groupId != webhook.getGroupId()) ||
				!Objects.equals(webhookURL, webhook.getWebhookURL())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_WEBHOOK_WHERE);

			sb.append(_FINDER_COLUMN_G_WU_GROUPID_2);

			boolean bindWebhookURL = false;

			if (webhookURL.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_WU_WEBHOOKURL_3);
			}
			else {
				bindWebhookURL = true;

				sb.append(_FINDER_COLUMN_G_WU_WEBHOOKURL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindWebhookURL) {
					queryPos.add(webhookURL);
				}

				List<Webhook> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_WU, finderArgs, list);
					}
				}
				else {
					Webhook webhook = list.get(0);

					result = webhook;

					cacheResult(webhook);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Webhook)result;
		}
	}

	/**
	 * Removes the webhook where groupId = &#63; and webhookURL = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param webhookURL the webhook url
	 * @return the webhook that was removed
	 */
	@Override
	public Webhook removeByG_WU(long groupId, String webhookURL)
		throws NoSuchWebhookException {

		Webhook webhook = findByG_WU(groupId, webhookURL);

		return remove(webhook);
	}

	/**
	 * Returns the number of webhooks where groupId = &#63; and webhookURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param webhookURL the webhook url
	 * @return the number of matching webhooks
	 */
	@Override
	public int countByG_WU(long groupId, String webhookURL) {
		webhookURL = Objects.toString(webhookURL, "");

		FinderPath finderPath = _finderPathCountByG_WU;

		Object[] finderArgs = new Object[] {groupId, webhookURL};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WEBHOOK_WHERE);

			sb.append(_FINDER_COLUMN_G_WU_GROUPID_2);

			boolean bindWebhookURL = false;

			if (webhookURL.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_WU_WEBHOOKURL_3);
			}
			else {
				bindWebhookURL = true;

				sb.append(_FINDER_COLUMN_G_WU_WEBHOOKURL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindWebhookURL) {
					queryPos.add(webhookURL);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_WU_GROUPID_2 =
		"webhook.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_WU_WEBHOOKURL_2 =
		"webhook.webhookURL = ?";

	private static final String _FINDER_COLUMN_G_WU_WEBHOOKURL_3 =
		"(webhook.webhookURL IS NULL OR webhook.webhookURL = '')";

	public WebhookPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Webhook.class);

		setModelImplClass(WebhookImpl.class);
		setModelPKClass(long.class);

		setTable(WebhookTable.INSTANCE);
	}

	/**
	 * Caches the webhook in the entity cache if it is enabled.
	 *
	 * @param webhook the webhook
	 */
	@Override
	public void cacheResult(Webhook webhook) {
		entityCache.putResult(
			WebhookImpl.class, webhook.getPrimaryKey(), webhook);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {webhook.getUuid(), webhook.getGroupId()}, webhook);

		finderCache.putResult(
			_finderPathFetchByG_WU,
			new Object[] {webhook.getGroupId(), webhook.getWebhookURL()},
			webhook);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the webhooks in the entity cache if it is enabled.
	 *
	 * @param webhooks the webhooks
	 */
	@Override
	public void cacheResult(List<Webhook> webhooks) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (webhooks.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Webhook webhook : webhooks) {
			if (entityCache.getResult(
					WebhookImpl.class, webhook.getPrimaryKey()) == null) {

				cacheResult(webhook);
			}
		}
	}

	/**
	 * Clears the cache for all webhooks.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WebhookImpl.class);

		finderCache.clearCache(WebhookImpl.class);
	}

	/**
	 * Clears the cache for the webhook.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Webhook webhook) {
		entityCache.removeResult(WebhookImpl.class, webhook);
	}

	@Override
	public void clearCache(List<Webhook> webhooks) {
		for (Webhook webhook : webhooks) {
			entityCache.removeResult(WebhookImpl.class, webhook);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(WebhookImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(WebhookImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(WebhookModelImpl webhookModelImpl) {
		Object[] args = new Object[] {
			webhookModelImpl.getUuid(), webhookModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(_finderPathFetchByUUID_G, args, webhookModelImpl);

		args = new Object[] {
			webhookModelImpl.getGroupId(), webhookModelImpl.getWebhookURL()
		};

		finderCache.putResult(_finderPathCountByG_WU, args, Long.valueOf(1));
		finderCache.putResult(_finderPathFetchByG_WU, args, webhookModelImpl);
	}

	/**
	 * Creates a new webhook with the primary key. Does not add the webhook to the database.
	 *
	 * @param webhookId the primary key for the new webhook
	 * @return the new webhook
	 */
	@Override
	public Webhook create(long webhookId) {
		Webhook webhook = new WebhookImpl();

		webhook.setNew(true);
		webhook.setPrimaryKey(webhookId);

		String uuid = PortalUUIDUtil.generate();

		webhook.setUuid(uuid);

		webhook.setCompanyId(CompanyThreadLocal.getCompanyId());

		return webhook;
	}

	/**
	 * Removes the webhook with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webhookId the primary key of the webhook
	 * @return the webhook that was removed
	 * @throws NoSuchWebhookException if a webhook with the primary key could not be found
	 */
	@Override
	public Webhook remove(long webhookId) throws NoSuchWebhookException {
		return remove((Serializable)webhookId);
	}

	/**
	 * Removes the webhook with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the webhook
	 * @return the webhook that was removed
	 * @throws NoSuchWebhookException if a webhook with the primary key could not be found
	 */
	@Override
	public Webhook remove(Serializable primaryKey)
		throws NoSuchWebhookException {

		Session session = null;

		try {
			session = openSession();

			Webhook webhook = (Webhook)session.get(
				WebhookImpl.class, primaryKey);

			if (webhook == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchWebhookException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(webhook);
		}
		catch (NoSuchWebhookException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Webhook removeImpl(Webhook webhook) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(webhook)) {
				webhook = (Webhook)session.get(
					WebhookImpl.class, webhook.getPrimaryKeyObj());
			}

			if (webhook != null) {
				session.delete(webhook);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (webhook != null) {
			clearCache(webhook);
		}

		return webhook;
	}

	@Override
	public Webhook updateImpl(Webhook webhook) {
		boolean isNew = webhook.isNew();

		if (!(webhook instanceof WebhookModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(webhook.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(webhook);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in webhook proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Webhook implementation " +
					webhook.getClass());
		}

		WebhookModelImpl webhookModelImpl = (WebhookModelImpl)webhook;

		if (Validator.isNull(webhook.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			webhook.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (webhook.getCreateDate() == null)) {
			if (serviceContext == null) {
				webhook.setCreateDate(date);
			}
			else {
				webhook.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!webhookModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				webhook.setModifiedDate(date);
			}
			else {
				webhook.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(webhook);
			}
			else {
				webhook = (Webhook)session.merge(webhook);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(WebhookImpl.class, webhookModelImpl, false, true);

		cacheUniqueFindersCache(webhookModelImpl);

		if (isNew) {
			webhook.setNew(false);
		}

		webhook.resetOriginalValues();

		return webhook;
	}

	/**
	 * Returns the webhook with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the webhook
	 * @return the webhook
	 * @throws NoSuchWebhookException if a webhook with the primary key could not be found
	 */
	@Override
	public Webhook findByPrimaryKey(Serializable primaryKey)
		throws NoSuchWebhookException {

		Webhook webhook = fetchByPrimaryKey(primaryKey);

		if (webhook == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchWebhookException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return webhook;
	}

	/**
	 * Returns the webhook with the primary key or throws a <code>NoSuchWebhookException</code> if it could not be found.
	 *
	 * @param webhookId the primary key of the webhook
	 * @return the webhook
	 * @throws NoSuchWebhookException if a webhook with the primary key could not be found
	 */
	@Override
	public Webhook findByPrimaryKey(long webhookId)
		throws NoSuchWebhookException {

		return findByPrimaryKey((Serializable)webhookId);
	}

	/**
	 * Returns the webhook with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webhookId the primary key of the webhook
	 * @return the webhook, or <code>null</code> if a webhook with the primary key could not be found
	 */
	@Override
	public Webhook fetchByPrimaryKey(long webhookId) {
		return fetchByPrimaryKey((Serializable)webhookId);
	}

	/**
	 * Returns all the webhooks.
	 *
	 * @return the webhooks
	 */
	@Override
	public List<Webhook> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<Webhook> findAll(int start, int end) {
		return findAll(start, end, null);
	}

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
	@Override
	public List<Webhook> findAll(
		int start, int end, OrderByComparator<Webhook> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

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
	@Override
	public List<Webhook> findAll(
		int start, int end, OrderByComparator<Webhook> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<Webhook> list = null;

		if (useFinderCache) {
			list = (List<Webhook>)finderCache.getResult(finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_WEBHOOK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_WEBHOOK;

				sql = sql.concat(WebhookModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Webhook>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the webhooks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Webhook webhook : findAll()) {
			remove(webhook);
		}
	}

	/**
	 * Returns the number of webhooks.
	 *
	 * @return the number of webhooks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_WEBHOOK);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "webhookId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_WEBHOOK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WebhookModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the webhook persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathFetchByG_WU = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_WU",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "webhookURL"}, true);

		_finderPathCountByG_WU = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_WU",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "webhookURL"}, false);

		_setWebhookUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setWebhookUtilPersistence(null);

		entityCache.removeCache(WebhookImpl.class.getName());
	}

	private void _setWebhookUtilPersistence(
		WebhookPersistence webhookPersistence) {

		try {
			Field field = WebhookUtil.class.getDeclaredField("_persistence");

			field.setAccessible(true);

			field.set(null, webhookPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = WebhooksPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = WebhooksPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = WebhooksPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_WEBHOOK =
		"SELECT webhook FROM Webhook webhook";

	private static final String _SQL_SELECT_WEBHOOK_WHERE =
		"SELECT webhook FROM Webhook webhook WHERE ";

	private static final String _SQL_COUNT_WEBHOOK =
		"SELECT COUNT(webhook) FROM Webhook webhook";

	private static final String _SQL_COUNT_WEBHOOK_WHERE =
		"SELECT COUNT(webhook) FROM Webhook webhook WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "webhook.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Webhook exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Webhook exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		WebhookPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private WebhookModelArgumentsResolver _webhookModelArgumentsResolver;

}