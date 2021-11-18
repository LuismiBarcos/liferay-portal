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

package com.liferay.webhooks.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link WebhookEntity}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebhookEntity
 * @generated
 */
public class WebhookEntityWrapper
	extends BaseModelWrapper<WebhookEntity>
	implements ModelWrapper<WebhookEntity>, WebhookEntity {

	public WebhookEntityWrapper(WebhookEntity webhookEntity) {
		super(webhookEntity);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("webhookEntityId", getWebhookEntityId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("entityClassName", getEntityClassName());
		attributes.put("webhookId", getWebhookId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long webhookEntityId = (Long)attributes.get("webhookEntityId");

		if (webhookEntityId != null) {
			setWebhookEntityId(webhookEntityId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String entityClassName = (String)attributes.get("entityClassName");

		if (entityClassName != null) {
			setEntityClassName(entityClassName);
		}

		Long webhookId = (Long)attributes.get("webhookId");

		if (webhookId != null) {
			setWebhookId(webhookId);
		}
	}

	@Override
	public WebhookEntity cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this webhook entity.
	 *
	 * @return the company ID of this webhook entity
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this webhook entity.
	 *
	 * @return the create date of this webhook entity
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the entity class name of this webhook entity.
	 *
	 * @return the entity class name of this webhook entity
	 */
	@Override
	public String getEntityClassName() {
		return model.getEntityClassName();
	}

	/**
	 * Returns the group ID of this webhook entity.
	 *
	 * @return the group ID of this webhook entity
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this webhook entity.
	 *
	 * @return the modified date of this webhook entity
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this webhook entity.
	 *
	 * @return the primary key of this webhook entity
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this webhook entity.
	 *
	 * @return the user ID of this webhook entity
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this webhook entity.
	 *
	 * @return the user name of this webhook entity
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this webhook entity.
	 *
	 * @return the user uuid of this webhook entity
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this webhook entity.
	 *
	 * @return the uuid of this webhook entity
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the webhook entity ID of this webhook entity.
	 *
	 * @return the webhook entity ID of this webhook entity
	 */
	@Override
	public long getWebhookEntityId() {
		return model.getWebhookEntityId();
	}

	/**
	 * Returns the webhook ID of this webhook entity.
	 *
	 * @return the webhook ID of this webhook entity
	 */
	@Override
	public long getWebhookId() {
		return model.getWebhookId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this webhook entity.
	 *
	 * @param companyId the company ID of this webhook entity
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this webhook entity.
	 *
	 * @param createDate the create date of this webhook entity
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the entity class name of this webhook entity.
	 *
	 * @param entityClassName the entity class name of this webhook entity
	 */
	@Override
	public void setEntityClassName(String entityClassName) {
		model.setEntityClassName(entityClassName);
	}

	/**
	 * Sets the group ID of this webhook entity.
	 *
	 * @param groupId the group ID of this webhook entity
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this webhook entity.
	 *
	 * @param modifiedDate the modified date of this webhook entity
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this webhook entity.
	 *
	 * @param primaryKey the primary key of this webhook entity
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this webhook entity.
	 *
	 * @param userId the user ID of this webhook entity
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this webhook entity.
	 *
	 * @param userName the user name of this webhook entity
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this webhook entity.
	 *
	 * @param userUuid the user uuid of this webhook entity
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this webhook entity.
	 *
	 * @param uuid the uuid of this webhook entity
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the webhook entity ID of this webhook entity.
	 *
	 * @param webhookEntityId the webhook entity ID of this webhook entity
	 */
	@Override
	public void setWebhookEntityId(long webhookEntityId) {
		model.setWebhookEntityId(webhookEntityId);
	}

	/**
	 * Sets the webhook ID of this webhook entity.
	 *
	 * @param webhookId the webhook ID of this webhook entity
	 */
	@Override
	public void setWebhookId(long webhookId) {
		model.setWebhookId(webhookId);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected WebhookEntityWrapper wrap(WebhookEntity webhookEntity) {
		return new WebhookEntityWrapper(webhookEntity);
	}

}