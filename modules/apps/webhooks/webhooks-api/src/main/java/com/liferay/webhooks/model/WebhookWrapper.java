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
 * This class is a wrapper for {@link Webhook}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Webhook
 * @generated
 */
public class WebhookWrapper
	extends BaseModelWrapper<Webhook>
	implements ModelWrapper<Webhook>, Webhook {

	public WebhookWrapper(Webhook webhook) {
		super(webhook);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("webhookId", getWebhookId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("webhookURL", getWebhookURL());
		attributes.put("apiKey", getApiKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long webhookId = (Long)attributes.get("webhookId");

		if (webhookId != null) {
			setWebhookId(webhookId);
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

		String webhookURL = (String)attributes.get("webhookURL");

		if (webhookURL != null) {
			setWebhookURL(webhookURL);
		}

		String apiKey = (String)attributes.get("apiKey");

		if (apiKey != null) {
			setApiKey(apiKey);
		}
	}

	@Override
	public Webhook cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the api key of this webhook.
	 *
	 * @return the api key of this webhook
	 */
	@Override
	public String getApiKey() {
		return model.getApiKey();
	}

	/**
	 * Returns the company ID of this webhook.
	 *
	 * @return the company ID of this webhook
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this webhook.
	 *
	 * @return the create date of this webhook
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this webhook.
	 *
	 * @return the group ID of this webhook
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this webhook.
	 *
	 * @return the modified date of this webhook
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this webhook.
	 *
	 * @return the primary key of this webhook
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this webhook.
	 *
	 * @return the user ID of this webhook
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this webhook.
	 *
	 * @return the user name of this webhook
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this webhook.
	 *
	 * @return the user uuid of this webhook
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this webhook.
	 *
	 * @return the uuid of this webhook
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the webhook ID of this webhook.
	 *
	 * @return the webhook ID of this webhook
	 */
	@Override
	public long getWebhookId() {
		return model.getWebhookId();
	}

	/**
	 * Returns the webhook url of this webhook.
	 *
	 * @return the webhook url of this webhook
	 */
	@Override
	public String getWebhookURL() {
		return model.getWebhookURL();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the api key of this webhook.
	 *
	 * @param apiKey the api key of this webhook
	 */
	@Override
	public void setApiKey(String apiKey) {
		model.setApiKey(apiKey);
	}

	/**
	 * Sets the company ID of this webhook.
	 *
	 * @param companyId the company ID of this webhook
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this webhook.
	 *
	 * @param createDate the create date of this webhook
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this webhook.
	 *
	 * @param groupId the group ID of this webhook
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this webhook.
	 *
	 * @param modifiedDate the modified date of this webhook
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this webhook.
	 *
	 * @param primaryKey the primary key of this webhook
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this webhook.
	 *
	 * @param userId the user ID of this webhook
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this webhook.
	 *
	 * @param userName the user name of this webhook
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this webhook.
	 *
	 * @param userUuid the user uuid of this webhook
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this webhook.
	 *
	 * @param uuid the uuid of this webhook
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the webhook ID of this webhook.
	 *
	 * @param webhookId the webhook ID of this webhook
	 */
	@Override
	public void setWebhookId(long webhookId) {
		model.setWebhookId(webhookId);
	}

	/**
	 * Sets the webhook url of this webhook.
	 *
	 * @param webhookURL the webhook url of this webhook
	 */
	@Override
	public void setWebhookURL(String webhookURL) {
		model.setWebhookURL(webhookURL);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected WebhookWrapper wrap(Webhook webhook) {
		return new WebhookWrapper(webhook);
	}

}