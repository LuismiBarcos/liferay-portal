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
 * The table class for the &quot;Webhooks_WebhookEntity&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see WebhookEntity
 * @generated
 */
public class WebhookEntityTable extends BaseTable<WebhookEntityTable> {

	public static final WebhookEntityTable INSTANCE = new WebhookEntityTable();

	public final Column<WebhookEntityTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WebhookEntityTable, Long> webhookEntityId =
		createColumn(
			"webhookEntityId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<WebhookEntityTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WebhookEntityTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WebhookEntityTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WebhookEntityTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WebhookEntityTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WebhookEntityTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WebhookEntityTable, String> entityClassName =
		createColumn(
			"entityClassName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<WebhookEntityTable, Long> webhookId = createColumn(
		"webhookId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private WebhookEntityTable() {
		super("Webhooks_WebhookEntity", WebhookEntityTable::new);
	}

}