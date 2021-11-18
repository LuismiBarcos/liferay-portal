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

package com.liferay.webhooks.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.webhooks.model.WebhookEntity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing WebhookEntity in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WebhookEntityCacheModel
	implements CacheModel<WebhookEntity>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WebhookEntityCacheModel)) {
			return false;
		}

		WebhookEntityCacheModel webhookEntityCacheModel =
			(WebhookEntityCacheModel)object;

		if (webhookEntityId == webhookEntityCacheModel.webhookEntityId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, webhookEntityId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", webhookEntityId=");
		sb.append(webhookEntityId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", entityClassName=");
		sb.append(entityClassName);
		sb.append(", webhookId=");
		sb.append(webhookId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WebhookEntity toEntityModel() {
		WebhookEntityImpl webhookEntityImpl = new WebhookEntityImpl();

		if (uuid == null) {
			webhookEntityImpl.setUuid("");
		}
		else {
			webhookEntityImpl.setUuid(uuid);
		}

		webhookEntityImpl.setWebhookEntityId(webhookEntityId);
		webhookEntityImpl.setGroupId(groupId);
		webhookEntityImpl.setCompanyId(companyId);
		webhookEntityImpl.setUserId(userId);

		if (userName == null) {
			webhookEntityImpl.setUserName("");
		}
		else {
			webhookEntityImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			webhookEntityImpl.setCreateDate(null);
		}
		else {
			webhookEntityImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			webhookEntityImpl.setModifiedDate(null);
		}
		else {
			webhookEntityImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (entityClassName == null) {
			webhookEntityImpl.setEntityClassName("");
		}
		else {
			webhookEntityImpl.setEntityClassName(entityClassName);
		}

		webhookEntityImpl.setWebhookId(webhookId);

		webhookEntityImpl.resetOriginalValues();

		return webhookEntityImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		webhookEntityId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		entityClassName = objectInput.readUTF();

		webhookId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(webhookEntityId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (entityClassName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(entityClassName);
		}

		objectOutput.writeLong(webhookId);
	}

	public String uuid;
	public long webhookEntityId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String entityClassName;
	public long webhookId;

}