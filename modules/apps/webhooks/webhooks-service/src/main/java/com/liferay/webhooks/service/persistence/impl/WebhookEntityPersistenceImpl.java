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
import com.liferay.webhooks.exception.NoSuchWebhookEntityException;
import com.liferay.webhooks.model.WebhookEntity;
import com.liferay.webhooks.model.WebhookEntityTable;
import com.liferay.webhooks.model.impl.WebhookEntityImpl;
import com.liferay.webhooks.model.impl.WebhookEntityModelImpl;
import com.liferay.webhooks.service.persistence.WebhookEntityPersistence;
import com.liferay.webhooks.service.persistence.WebhookEntityUtil;
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
 * The persistence implementation for the webhook entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {WebhookEntityPersistence.class, BasePersistence.class})
public class WebhookEntityPersistenceImpl
	extends BasePersistenceImpl<WebhookEntity>
	implements WebhookEntityPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>WebhookEntityUtil</code> to access the webhook entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		WebhookEntityImpl.class.getName();

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
	 * Returns all the webhook entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching webhook entities
	 */
	@Override
	public List<WebhookEntity> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<WebhookEntity> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

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
	@Override
	public List<WebhookEntity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WebhookEntity> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

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
	@Override
	public List<WebhookEntity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WebhookEntity> orderByComparator,
		boolean useFinderCache) {

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

		List<WebhookEntity> list = null;

		if (useFinderCache) {
			list = (List<WebhookEntity>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WebhookEntity webhookEntity : list) {
					if (!uuid.equals(webhookEntity.getUuid())) {
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

			sb.append(_SQL_SELECT_WEBHOOKENTITY_WHERE);

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
				sb.append(WebhookEntityModelImpl.ORDER_BY_JPQL);
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

				list = (List<WebhookEntity>)QueryUtil.list(
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
	 * Returns the first webhook entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity findByUuid_First(
			String uuid, OrderByComparator<WebhookEntity> orderByComparator)
		throws NoSuchWebhookEntityException {

		WebhookEntity webhookEntity = fetchByUuid_First(
			uuid, orderByComparator);

		if (webhookEntity != null) {
			return webhookEntity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchWebhookEntityException(sb.toString());
	}

	/**
	 * Returns the first webhook entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity fetchByUuid_First(
		String uuid, OrderByComparator<WebhookEntity> orderByComparator) {

		List<WebhookEntity> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last webhook entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity findByUuid_Last(
			String uuid, OrderByComparator<WebhookEntity> orderByComparator)
		throws NoSuchWebhookEntityException {

		WebhookEntity webhookEntity = fetchByUuid_Last(uuid, orderByComparator);

		if (webhookEntity != null) {
			return webhookEntity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchWebhookEntityException(sb.toString());
	}

	/**
	 * Returns the last webhook entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity fetchByUuid_Last(
		String uuid, OrderByComparator<WebhookEntity> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<WebhookEntity> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the webhook entities before and after the current webhook entity in the ordered set where uuid = &#63;.
	 *
	 * @param webhookEntityId the primary key of the current webhook entity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook entity
	 * @throws NoSuchWebhookEntityException if a webhook entity with the primary key could not be found
	 */
	@Override
	public WebhookEntity[] findByUuid_PrevAndNext(
			long webhookEntityId, String uuid,
			OrderByComparator<WebhookEntity> orderByComparator)
		throws NoSuchWebhookEntityException {

		uuid = Objects.toString(uuid, "");

		WebhookEntity webhookEntity = findByPrimaryKey(webhookEntityId);

		Session session = null;

		try {
			session = openSession();

			WebhookEntity[] array = new WebhookEntityImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, webhookEntity, uuid, orderByComparator, true);

			array[1] = webhookEntity;

			array[2] = getByUuid_PrevAndNext(
				session, webhookEntity, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected WebhookEntity getByUuid_PrevAndNext(
		Session session, WebhookEntity webhookEntity, String uuid,
		OrderByComparator<WebhookEntity> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_WEBHOOKENTITY_WHERE);

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
			sb.append(WebhookEntityModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(
						webhookEntity)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WebhookEntity> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the webhook entities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (WebhookEntity webhookEntity :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(webhookEntity);
		}
	}

	/**
	 * Returns the number of webhook entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching webhook entities
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_WEBHOOKENTITY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"webhookEntity.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(webhookEntity.uuid IS NULL OR webhookEntity.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the webhook entity where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchWebhookEntityException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity findByUUID_G(String uuid, long groupId)
		throws NoSuchWebhookEntityException {

		WebhookEntity webhookEntity = fetchByUUID_G(uuid, groupId);

		if (webhookEntity == null) {
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

			throw new NoSuchWebhookEntityException(sb.toString());
		}

		return webhookEntity;
	}

	/**
	 * Returns the webhook entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the webhook entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity fetchByUUID_G(
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

		if (result instanceof WebhookEntity) {
			WebhookEntity webhookEntity = (WebhookEntity)result;

			if (!Objects.equals(uuid, webhookEntity.getUuid()) ||
				(groupId != webhookEntity.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_WEBHOOKENTITY_WHERE);

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

				List<WebhookEntity> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					WebhookEntity webhookEntity = list.get(0);

					result = webhookEntity;

					cacheResult(webhookEntity);
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
			return (WebhookEntity)result;
		}
	}

	/**
	 * Removes the webhook entity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the webhook entity that was removed
	 */
	@Override
	public WebhookEntity removeByUUID_G(String uuid, long groupId)
		throws NoSuchWebhookEntityException {

		WebhookEntity webhookEntity = findByUUID_G(uuid, groupId);

		return remove(webhookEntity);
	}

	/**
	 * Returns the number of webhook entities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching webhook entities
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WEBHOOKENTITY_WHERE);

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
		"webhookEntity.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(webhookEntity.uuid IS NULL OR webhookEntity.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"webhookEntity.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the webhook entities where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching webhook entities
	 */
	@Override
	public List<WebhookEntity> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<WebhookEntity> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

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
	@Override
	public List<WebhookEntity> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WebhookEntity> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

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
	@Override
	public List<WebhookEntity> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WebhookEntity> orderByComparator,
		boolean useFinderCache) {

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

		List<WebhookEntity> list = null;

		if (useFinderCache) {
			list = (List<WebhookEntity>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WebhookEntity webhookEntity : list) {
					if (!uuid.equals(webhookEntity.getUuid()) ||
						(companyId != webhookEntity.getCompanyId())) {

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

			sb.append(_SQL_SELECT_WEBHOOKENTITY_WHERE);

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
				sb.append(WebhookEntityModelImpl.ORDER_BY_JPQL);
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

				list = (List<WebhookEntity>)QueryUtil.list(
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
	 * Returns the first webhook entity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WebhookEntity> orderByComparator)
		throws NoSuchWebhookEntityException {

		WebhookEntity webhookEntity = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (webhookEntity != null) {
			return webhookEntity;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchWebhookEntityException(sb.toString());
	}

	/**
	 * Returns the first webhook entity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WebhookEntity> orderByComparator) {

		List<WebhookEntity> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last webhook entity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WebhookEntity> orderByComparator)
		throws NoSuchWebhookEntityException {

		WebhookEntity webhookEntity = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (webhookEntity != null) {
			return webhookEntity;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchWebhookEntityException(sb.toString());
	}

	/**
	 * Returns the last webhook entity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WebhookEntity> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<WebhookEntity> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

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
	@Override
	public WebhookEntity[] findByUuid_C_PrevAndNext(
			long webhookEntityId, String uuid, long companyId,
			OrderByComparator<WebhookEntity> orderByComparator)
		throws NoSuchWebhookEntityException {

		uuid = Objects.toString(uuid, "");

		WebhookEntity webhookEntity = findByPrimaryKey(webhookEntityId);

		Session session = null;

		try {
			session = openSession();

			WebhookEntity[] array = new WebhookEntityImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, webhookEntity, uuid, companyId, orderByComparator,
				true);

			array[1] = webhookEntity;

			array[2] = getByUuid_C_PrevAndNext(
				session, webhookEntity, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected WebhookEntity getByUuid_C_PrevAndNext(
		Session session, WebhookEntity webhookEntity, String uuid,
		long companyId, OrderByComparator<WebhookEntity> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_WEBHOOKENTITY_WHERE);

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
			sb.append(WebhookEntityModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(
						webhookEntity)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WebhookEntity> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the webhook entities where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (WebhookEntity webhookEntity :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(webhookEntity);
		}
	}

	/**
	 * Returns the number of webhook entities where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching webhook entities
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WEBHOOKENTITY_WHERE);

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
		"webhookEntity.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(webhookEntity.uuid IS NULL OR webhookEntity.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"webhookEntity.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the webhook entities where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching webhook entities
	 */
	@Override
	public List<WebhookEntity> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<WebhookEntity> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

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
	@Override
	public List<WebhookEntity> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<WebhookEntity> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

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
	@Override
	public List<WebhookEntity> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<WebhookEntity> orderByComparator,
		boolean useFinderCache) {

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

		List<WebhookEntity> list = null;

		if (useFinderCache) {
			list = (List<WebhookEntity>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WebhookEntity webhookEntity : list) {
					if (groupId != webhookEntity.getGroupId()) {
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

			sb.append(_SQL_SELECT_WEBHOOKENTITY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(WebhookEntityModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<WebhookEntity>)QueryUtil.list(
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
	 * Returns the first webhook entity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity findByGroupId_First(
			long groupId, OrderByComparator<WebhookEntity> orderByComparator)
		throws NoSuchWebhookEntityException {

		WebhookEntity webhookEntity = fetchByGroupId_First(
			groupId, orderByComparator);

		if (webhookEntity != null) {
			return webhookEntity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchWebhookEntityException(sb.toString());
	}

	/**
	 * Returns the first webhook entity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity fetchByGroupId_First(
		long groupId, OrderByComparator<WebhookEntity> orderByComparator) {

		List<WebhookEntity> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last webhook entity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity
	 * @throws NoSuchWebhookEntityException if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity findByGroupId_Last(
			long groupId, OrderByComparator<WebhookEntity> orderByComparator)
		throws NoSuchWebhookEntityException {

		WebhookEntity webhookEntity = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (webhookEntity != null) {
			return webhookEntity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchWebhookEntityException(sb.toString());
	}

	/**
	 * Returns the last webhook entity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entity, or <code>null</code> if a matching webhook entity could not be found
	 */
	@Override
	public WebhookEntity fetchByGroupId_Last(
		long groupId, OrderByComparator<WebhookEntity> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<WebhookEntity> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the webhook entities before and after the current webhook entity in the ordered set where groupId = &#63;.
	 *
	 * @param webhookEntityId the primary key of the current webhook entity
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook entity
	 * @throws NoSuchWebhookEntityException if a webhook entity with the primary key could not be found
	 */
	@Override
	public WebhookEntity[] findByGroupId_PrevAndNext(
			long webhookEntityId, long groupId,
			OrderByComparator<WebhookEntity> orderByComparator)
		throws NoSuchWebhookEntityException {

		WebhookEntity webhookEntity = findByPrimaryKey(webhookEntityId);

		Session session = null;

		try {
			session = openSession();

			WebhookEntity[] array = new WebhookEntityImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, webhookEntity, groupId, orderByComparator, true);

			array[1] = webhookEntity;

			array[2] = getByGroupId_PrevAndNext(
				session, webhookEntity, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected WebhookEntity getByGroupId_PrevAndNext(
		Session session, WebhookEntity webhookEntity, long groupId,
		OrderByComparator<WebhookEntity> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_WEBHOOKENTITY_WHERE);

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
			sb.append(WebhookEntityModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						webhookEntity)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WebhookEntity> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the webhook entities where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (WebhookEntity webhookEntity :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(webhookEntity);
		}
	}

	/**
	 * Returns the number of webhook entities where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching webhook entities
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_WEBHOOKENTITY_WHERE);

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
		"webhookEntity.groupId = ?";

	public WebhookEntityPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(WebhookEntity.class);

		setModelImplClass(WebhookEntityImpl.class);
		setModelPKClass(long.class);

		setTable(WebhookEntityTable.INSTANCE);
	}

	/**
	 * Caches the webhook entity in the entity cache if it is enabled.
	 *
	 * @param webhookEntity the webhook entity
	 */
	@Override
	public void cacheResult(WebhookEntity webhookEntity) {
		entityCache.putResult(
			WebhookEntityImpl.class, webhookEntity.getPrimaryKey(),
			webhookEntity);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {webhookEntity.getUuid(), webhookEntity.getGroupId()},
			webhookEntity);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the webhook entities in the entity cache if it is enabled.
	 *
	 * @param webhookEntities the webhook entities
	 */
	@Override
	public void cacheResult(List<WebhookEntity> webhookEntities) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (webhookEntities.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (WebhookEntity webhookEntity : webhookEntities) {
			if (entityCache.getResult(
					WebhookEntityImpl.class, webhookEntity.getPrimaryKey()) ==
						null) {

				cacheResult(webhookEntity);
			}
		}
	}

	/**
	 * Clears the cache for all webhook entities.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WebhookEntityImpl.class);

		finderCache.clearCache(WebhookEntityImpl.class);
	}

	/**
	 * Clears the cache for the webhook entity.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WebhookEntity webhookEntity) {
		entityCache.removeResult(WebhookEntityImpl.class, webhookEntity);
	}

	@Override
	public void clearCache(List<WebhookEntity> webhookEntities) {
		for (WebhookEntity webhookEntity : webhookEntities) {
			entityCache.removeResult(WebhookEntityImpl.class, webhookEntity);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(WebhookEntityImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(WebhookEntityImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		WebhookEntityModelImpl webhookEntityModelImpl) {

		Object[] args = new Object[] {
			webhookEntityModelImpl.getUuid(),
			webhookEntityModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, webhookEntityModelImpl);
	}

	/**
	 * Creates a new webhook entity with the primary key. Does not add the webhook entity to the database.
	 *
	 * @param webhookEntityId the primary key for the new webhook entity
	 * @return the new webhook entity
	 */
	@Override
	public WebhookEntity create(long webhookEntityId) {
		WebhookEntity webhookEntity = new WebhookEntityImpl();

		webhookEntity.setNew(true);
		webhookEntity.setPrimaryKey(webhookEntityId);

		String uuid = PortalUUIDUtil.generate();

		webhookEntity.setUuid(uuid);

		webhookEntity.setCompanyId(CompanyThreadLocal.getCompanyId());

		return webhookEntity;
	}

	/**
	 * Removes the webhook entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webhookEntityId the primary key of the webhook entity
	 * @return the webhook entity that was removed
	 * @throws NoSuchWebhookEntityException if a webhook entity with the primary key could not be found
	 */
	@Override
	public WebhookEntity remove(long webhookEntityId)
		throws NoSuchWebhookEntityException {

		return remove((Serializable)webhookEntityId);
	}

	/**
	 * Removes the webhook entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the webhook entity
	 * @return the webhook entity that was removed
	 * @throws NoSuchWebhookEntityException if a webhook entity with the primary key could not be found
	 */
	@Override
	public WebhookEntity remove(Serializable primaryKey)
		throws NoSuchWebhookEntityException {

		Session session = null;

		try {
			session = openSession();

			WebhookEntity webhookEntity = (WebhookEntity)session.get(
				WebhookEntityImpl.class, primaryKey);

			if (webhookEntity == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchWebhookEntityException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(webhookEntity);
		}
		catch (NoSuchWebhookEntityException noSuchEntityException) {
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
	protected WebhookEntity removeImpl(WebhookEntity webhookEntity) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(webhookEntity)) {
				webhookEntity = (WebhookEntity)session.get(
					WebhookEntityImpl.class, webhookEntity.getPrimaryKeyObj());
			}

			if (webhookEntity != null) {
				session.delete(webhookEntity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (webhookEntity != null) {
			clearCache(webhookEntity);
		}

		return webhookEntity;
	}

	@Override
	public WebhookEntity updateImpl(WebhookEntity webhookEntity) {
		boolean isNew = webhookEntity.isNew();

		if (!(webhookEntity instanceof WebhookEntityModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(webhookEntity.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					webhookEntity);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in webhookEntity proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom WebhookEntity implementation " +
					webhookEntity.getClass());
		}

		WebhookEntityModelImpl webhookEntityModelImpl =
			(WebhookEntityModelImpl)webhookEntity;

		if (Validator.isNull(webhookEntity.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			webhookEntity.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (webhookEntity.getCreateDate() == null)) {
			if (serviceContext == null) {
				webhookEntity.setCreateDate(date);
			}
			else {
				webhookEntity.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!webhookEntityModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				webhookEntity.setModifiedDate(date);
			}
			else {
				webhookEntity.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(webhookEntity);
			}
			else {
				webhookEntity = (WebhookEntity)session.merge(webhookEntity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			WebhookEntityImpl.class, webhookEntityModelImpl, false, true);

		cacheUniqueFindersCache(webhookEntityModelImpl);

		if (isNew) {
			webhookEntity.setNew(false);
		}

		webhookEntity.resetOriginalValues();

		return webhookEntity;
	}

	/**
	 * Returns the webhook entity with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the webhook entity
	 * @return the webhook entity
	 * @throws NoSuchWebhookEntityException if a webhook entity with the primary key could not be found
	 */
	@Override
	public WebhookEntity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchWebhookEntityException {

		WebhookEntity webhookEntity = fetchByPrimaryKey(primaryKey);

		if (webhookEntity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchWebhookEntityException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return webhookEntity;
	}

	/**
	 * Returns the webhook entity with the primary key or throws a <code>NoSuchWebhookEntityException</code> if it could not be found.
	 *
	 * @param webhookEntityId the primary key of the webhook entity
	 * @return the webhook entity
	 * @throws NoSuchWebhookEntityException if a webhook entity with the primary key could not be found
	 */
	@Override
	public WebhookEntity findByPrimaryKey(long webhookEntityId)
		throws NoSuchWebhookEntityException {

		return findByPrimaryKey((Serializable)webhookEntityId);
	}

	/**
	 * Returns the webhook entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webhookEntityId the primary key of the webhook entity
	 * @return the webhook entity, or <code>null</code> if a webhook entity with the primary key could not be found
	 */
	@Override
	public WebhookEntity fetchByPrimaryKey(long webhookEntityId) {
		return fetchByPrimaryKey((Serializable)webhookEntityId);
	}

	/**
	 * Returns all the webhook entities.
	 *
	 * @return the webhook entities
	 */
	@Override
	public List<WebhookEntity> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<WebhookEntity> findAll(int start, int end) {
		return findAll(start, end, null);
	}

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
	@Override
	public List<WebhookEntity> findAll(
		int start, int end,
		OrderByComparator<WebhookEntity> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

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
	@Override
	public List<WebhookEntity> findAll(
		int start, int end, OrderByComparator<WebhookEntity> orderByComparator,
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

		List<WebhookEntity> list = null;

		if (useFinderCache) {
			list = (List<WebhookEntity>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_WEBHOOKENTITY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_WEBHOOKENTITY;

				sql = sql.concat(WebhookEntityModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<WebhookEntity>)QueryUtil.list(
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
	 * Removes all the webhook entities from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WebhookEntity webhookEntity : findAll()) {
			remove(webhookEntity);
		}
	}

	/**
	 * Returns the number of webhook entities.
	 *
	 * @return the number of webhook entities
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_WEBHOOKENTITY);

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
		return "webhookEntityId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_WEBHOOKENTITY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WebhookEntityModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the webhook entity persistence.
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

		_setWebhookEntityUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setWebhookEntityUtilPersistence(null);

		entityCache.removeCache(WebhookEntityImpl.class.getName());
	}

	private void _setWebhookEntityUtilPersistence(
		WebhookEntityPersistence webhookEntityPersistence) {

		try {
			Field field = WebhookEntityUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, webhookEntityPersistence);
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

	private static final String _SQL_SELECT_WEBHOOKENTITY =
		"SELECT webhookEntity FROM WebhookEntity webhookEntity";

	private static final String _SQL_SELECT_WEBHOOKENTITY_WHERE =
		"SELECT webhookEntity FROM WebhookEntity webhookEntity WHERE ";

	private static final String _SQL_COUNT_WEBHOOKENTITY =
		"SELECT COUNT(webhookEntity) FROM WebhookEntity webhookEntity";

	private static final String _SQL_COUNT_WEBHOOKENTITY_WHERE =
		"SELECT COUNT(webhookEntity) FROM WebhookEntity webhookEntity WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "webhookEntity.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No WebhookEntity exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No WebhookEntity exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		WebhookEntityPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private WebhookEntityModelArgumentsResolver
		_webhookEntityModelArgumentsResolver;

}