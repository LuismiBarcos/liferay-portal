create unique index IX_30C90CFC on Webhooks_Webhook (groupId, webhookURL[$COLUMN_LENGTH:75$]);
create index IX_F286444E on Webhooks_Webhook (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_61CF8650 on Webhooks_Webhook (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_6A9C242D on Webhooks_WebhookEntity (groupId);
create index IX_270D95D1 on Webhooks_WebhookEntity (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_8FF35C93 on Webhooks_WebhookEntity (uuid_[$COLUMN_LENGTH:75$], groupId);