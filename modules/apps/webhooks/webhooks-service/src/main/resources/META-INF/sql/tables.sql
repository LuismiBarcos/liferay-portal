create table Webhooks_Webhook (
	uuid_ VARCHAR(75) null,
	webhookId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	webhookURL VARCHAR(75) null,
	apiKey VARCHAR(75) null
);