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

package com.liferay.headless.webhooks.client.dto.v1_0;

import com.liferay.headless.webhooks.client.function.UnsafeSupplier;
import com.liferay.headless.webhooks.client.serdes.v1_0.WebhookEntitySerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Luis Miguel Barcos
 * @generated
 */
@Generated("")
public class WebhookEntity implements Cloneable, Serializable {

	public static WebhookEntity toDTO(String json) {
		return WebhookEntitySerDes.toDTO(json);
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public void setEntity(
		UnsafeSupplier<String, Exception> entityUnsafeSupplier) {

		try {
			entity = entityUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String entity;

	public Long getWebhookId() {
		return webhookId;
	}

	public void setWebhookId(Long webhookId) {
		this.webhookId = webhookId;
	}

	public void setWebhookId(
		UnsafeSupplier<Long, Exception> webhookIdUnsafeSupplier) {

		try {
			webhookId = webhookIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long webhookId;

	@Override
	public WebhookEntity clone() throws CloneNotSupportedException {
		return (WebhookEntity)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WebhookEntity)) {
			return false;
		}

		WebhookEntity webhookEntity = (WebhookEntity)object;

		return Objects.equals(toString(), webhookEntity.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return WebhookEntitySerDes.toJSON(this);
	}

}