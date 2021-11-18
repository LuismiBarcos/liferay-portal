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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.webhooks.service.http.WebhookEntityServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class WebhookEntitySoap implements Serializable {

	public static WebhookEntitySoap toSoapModel(WebhookEntity model) {
		WebhookEntitySoap soapModel = new WebhookEntitySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setWebhookEntityId(model.getWebhookEntityId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setEntityClassName(model.getEntityClassName());
		soapModel.setWebhookId(model.getWebhookId());

		return soapModel;
	}

	public static WebhookEntitySoap[] toSoapModels(WebhookEntity[] models) {
		WebhookEntitySoap[] soapModels = new WebhookEntitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WebhookEntitySoap[][] toSoapModels(WebhookEntity[][] models) {
		WebhookEntitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new WebhookEntitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new WebhookEntitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WebhookEntitySoap[] toSoapModels(List<WebhookEntity> models) {
		List<WebhookEntitySoap> soapModels = new ArrayList<WebhookEntitySoap>(
			models.size());

		for (WebhookEntity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new WebhookEntitySoap[soapModels.size()]);
	}

	public WebhookEntitySoap() {
	}

	public long getPrimaryKey() {
		return _webhookEntityId;
	}

	public void setPrimaryKey(long pk) {
		setWebhookEntityId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getWebhookEntityId() {
		return _webhookEntityId;
	}

	public void setWebhookEntityId(long webhookEntityId) {
		_webhookEntityId = webhookEntityId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getEntityClassName() {
		return _entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		_entityClassName = entityClassName;
	}

	public long getWebhookId() {
		return _webhookId;
	}

	public void setWebhookId(long webhookId) {
		_webhookId = webhookId;
	}

	private String _uuid;
	private long _webhookEntityId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _entityClassName;
	private long _webhookId;

}