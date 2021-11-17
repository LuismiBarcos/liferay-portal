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

package com.liferay.headless.webhooks.client.serdes.v1_0;

import com.liferay.headless.webhooks.client.dto.v1_0.Webhook;
import com.liferay.headless.webhooks.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Luis Miguel Barcos
 * @generated
 */
@Generated("")
public class WebhookSerDes {

	public static Webhook toDTO(String json) {
		WebhookJSONParser webhookJSONParser = new WebhookJSONParser();

		return webhookJSONParser.parseToDTO(json);
	}

	public static Webhook[] toDTOs(String json) {
		WebhookJSONParser webhookJSONParser = new WebhookJSONParser();

		return webhookJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Webhook webhook) {
		if (webhook == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (webhook.getApiKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"apiKey\": ");

			sb.append("\"");

			sb.append(_escape(webhook.getApiKey()));

			sb.append("\"");
		}

		if (webhook.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			sb.append("\"");

			sb.append(_escape(webhook.getUrl()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WebhookJSONParser webhookJSONParser = new WebhookJSONParser();

		return webhookJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Webhook webhook) {
		if (webhook == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (webhook.getApiKey() == null) {
			map.put("apiKey", null);
		}
		else {
			map.put("apiKey", String.valueOf(webhook.getApiKey()));
		}

		if (webhook.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(webhook.getUrl()));
		}

		return map;
	}

	public static class WebhookJSONParser extends BaseJSONParser<Webhook> {

		@Override
		protected Webhook createDTO() {
			return new Webhook();
		}

		@Override
		protected Webhook[] createDTOArray(int size) {
			return new Webhook[size];
		}

		@Override
		protected void setField(
			Webhook webhook, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "apiKey")) {
				if (jsonParserFieldValue != null) {
					webhook.setApiKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					webhook.setUrl((String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}