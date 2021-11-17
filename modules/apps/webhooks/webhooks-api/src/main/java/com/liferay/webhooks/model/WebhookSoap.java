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
 * This class is used by SOAP remote services, specifically {@link com.liferay.webhooks.service.http.WebhookServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class WebhookSoap implements Serializable {

	public static WebhookSoap toSoapModel(Webhook model) {
		WebhookSoap soapModel = new WebhookSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setWebhookId(model.getWebhookId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setWebhookURL(model.getWebhookURL());
		soapModel.setApiKey(model.getApiKey());

		return soapModel;
	}

	public static WebhookSoap[] toSoapModels(Webhook[] models) {
		WebhookSoap[] soapModels = new WebhookSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WebhookSoap[][] toSoapModels(Webhook[][] models) {
		WebhookSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new WebhookSoap[models.length][models[0].length];
		}
		else {
			soapModels = new WebhookSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WebhookSoap[] toSoapModels(List<Webhook> models) {
		List<WebhookSoap> soapModels = new ArrayList<WebhookSoap>(
			models.size());

		for (Webhook model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new WebhookSoap[soapModels.size()]);
	}

	public WebhookSoap() {
	}

	public long getPrimaryKey() {
		return _webhookId;
	}

	public void setPrimaryKey(long pk) {
		setWebhookId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getWebhookId() {
		return _webhookId;
	}

	public void setWebhookId(long webhookId) {
		_webhookId = webhookId;
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

	public String getWebhookURL() {
		return _webhookURL;
	}

	public void setWebhookURL(String webhookURL) {
		_webhookURL = webhookURL;
	}

	public String getApiKey() {
		return _apiKey;
	}

	public void setApiKey(String apiKey) {
		_apiKey = apiKey;
	}

	private String _uuid;
	private long _webhookId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _webhookURL;
	private String _apiKey;

}