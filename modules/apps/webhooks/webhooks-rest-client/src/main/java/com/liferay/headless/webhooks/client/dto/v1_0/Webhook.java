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
import com.liferay.headless.webhooks.client.serdes.v1_0.WebhookSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Luis Miguel Barcos
 * @generated
 */
@Generated("")
public class Webhook implements Cloneable, Serializable {

	public static Webhook toDTO(String json) {
		return WebhookSerDes.toDTO(json);
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setApiKey(
		UnsafeSupplier<String, Exception> apiKeyUnsafeSupplier) {

		try {
			apiKey = apiKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String apiKey;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUrl(UnsafeSupplier<String, Exception> urlUnsafeSupplier) {
		try {
			url = urlUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String url;

	@Override
	public Webhook clone() throws CloneNotSupportedException {
		return (Webhook)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Webhook)) {
			return false;
		}

		Webhook webhook = (Webhook)object;

		return Objects.equals(toString(), webhook.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return WebhookSerDes.toJSON(this);
	}

}