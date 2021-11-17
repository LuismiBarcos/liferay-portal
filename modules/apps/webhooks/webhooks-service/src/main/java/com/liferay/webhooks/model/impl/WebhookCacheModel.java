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
import com.liferay.webhooks.model.Webhook;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Webhook in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WebhookCacheModel implements CacheModel<Webhook>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WebhookCacheModel)) {
			return false;
		}

		WebhookCacheModel webhookCacheModel = (WebhookCacheModel)object;

		if (webhookId == webhookCacheModel.webhookId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, webhookId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", webhookId=");
		sb.append(webhookId);
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
		sb.append(", webhookURL=");
		sb.append(webhookURL);
		sb.append(", apiKey=");
		sb.append(apiKey);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Webhook toEntityModel() {
		WebhookImpl webhookImpl = new WebhookImpl();

		if (uuid == null) {
			webhookImpl.setUuid("");
		}
		else {
			webhookImpl.setUuid(uuid);
		}

		webhookImpl.setWebhookId(webhookId);
		webhookImpl.setGroupId(groupId);
		webhookImpl.setCompanyId(companyId);
		webhookImpl.setUserId(userId);

		if (userName == null) {
			webhookImpl.setUserName("");
		}
		else {
			webhookImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			webhookImpl.setCreateDate(null);
		}
		else {
			webhookImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			webhookImpl.setModifiedDate(null);
		}
		else {
			webhookImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (webhookURL == null) {
			webhookImpl.setWebhookURL("");
		}
		else {
			webhookImpl.setWebhookURL(webhookURL);
		}

		if (apiKey == null) {
			webhookImpl.setApiKey("");
		}
		else {
			webhookImpl.setApiKey(apiKey);
		}

		webhookImpl.resetOriginalValues();

		return webhookImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		webhookId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		webhookURL = objectInput.readUTF();
		apiKey = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(webhookId);

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

		if (webhookURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(webhookURL);
		}

		if (apiKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(apiKey);
		}
	}

	public String uuid;
	public long webhookId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String webhookURL;
	public String apiKey;

}