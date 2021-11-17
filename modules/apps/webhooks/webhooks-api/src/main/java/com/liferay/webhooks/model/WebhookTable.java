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

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Webhooks_Webhook&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Webhook
 * @generated
 */
public class WebhookTable extends BaseTable<WebhookTable> {

	public static final WebhookTable INSTANCE = new WebhookTable();

	public final Column<WebhookTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WebhookTable, Long> webhookId = createColumn(
		"webhookId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<WebhookTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WebhookTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WebhookTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WebhookTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WebhookTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WebhookTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WebhookTable, String> webhookURL = createColumn(
		"webhookURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WebhookTable, String> apiKey = createColumn(
		"apiKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private WebhookTable() {
		super("Webhooks_Webhook", WebhookTable::new);
	}

}