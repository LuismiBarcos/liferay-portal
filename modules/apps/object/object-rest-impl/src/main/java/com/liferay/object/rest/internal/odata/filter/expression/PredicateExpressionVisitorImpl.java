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

package com.liferay.object.rest.internal.odata.filter.expression;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectRelationshipTable;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.rest.internal.odata.entity.v1_0.ObjectEntryEntityModel;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceImpl;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.spi.expression.DefaultPredicate;
import com.liferay.petra.sql.dsl.spi.expression.Operand;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.CollectionPropertyExpression;
import com.liferay.portal.odata.filter.expression.ComplexPropertyExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.LambdaFunctionExpression;
import com.liferay.portal.odata.filter.expression.LambdaVariableExpression;
import com.liferay.portal.odata.filter.expression.ListExpression;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MemberExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;
import com.liferay.portal.odata.filter.expression.PrimitivePropertyExpression;
import com.liferay.portal.odata.filter.expression.PropertyExpression;
import com.liferay.portal.odata.filter.expression.UnaryExpression;

import java.sql.Types;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Marco Leo
 */
public class PredicateExpressionVisitorImpl
	implements ExpressionVisitor<Object> {

	public PredicateExpressionVisitorImpl(
		EntityModel entityModel, long objectDefinitionId,
		ObjectFieldBusinessTypeRegistry objectFieldBusinessTypeRegistry,
		ObjectFieldLocalService objectFieldLocalService) {

		this(
			entityModel, new HashMap<>(), objectDefinitionId,
			objectFieldBusinessTypeRegistry, objectFieldLocalService);
	}

	@Override
	public Predicate visitBinaryExpressionOperation(
		BinaryExpression.Operation operation, Object left, Object right) {

		Optional<Predicate> predicateOptional = _getPredicateOptional(
			operation, left, right);

		return predicateOptional.orElseThrow(
			() -> new UnsupportedOperationException(
				"Unsupported method visitBinaryExpressionOperation with " +
					"operation " + operation));
	}

	@Override
	public Predicate visitCollectionPropertyExpression(
			CollectionPropertyExpression collectionPropertyExpression)
		throws ExpressionVisitException {

		LambdaFunctionExpression lambdaFunctionExpression =
			collectionPropertyExpression.getLambdaFunctionExpression();

		return (Predicate)lambdaFunctionExpression.accept(
			new PredicateExpressionVisitorImpl(
				_entityModel,
				Collections.singletonMap(
					lambdaFunctionExpression.getVariableName(),
					collectionPropertyExpression.getName()),
				_objectDefinitionId, _objectFieldBusinessTypeRegistry,
				_objectFieldLocalService));
	}

	@Override
	public Object visitComplexPropertyExpression(
			ComplexPropertyExpression complexPropertyExpression)
		throws ExpressionVisitException {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		ComplexEntityField complexEntityField =
			(ComplexEntityField)entityFieldsMap.get(
				complexPropertyExpression.getName());

		_objectRelationship = _fetchObjectRelationship(complexPropertyExpression.getName());

		PropertyExpression propertyExpression =
			complexPropertyExpression.getPropertyExpression();

		Map<String, EntityField> complexEntityFieldEntityFieldsMap =
			complexEntityField.getEntityFieldsMap();

		EntityField entityField = complexEntityFieldEntityFieldsMap.get(
			propertyExpression.getName());

		_relatedFieldName = entityField.getName();

		return entityField.getName();
	}

	private ObjectRelationship _fetchObjectRelationship(String relationshipName) {
		try {
			return ObjectRelationshipLocalServiceUtil.getObjectRelationshipByObjectDefinitionId(
				_objectDefinitionId, GetterUtil.getString(relationshipName));
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Override
	public Object visitLambdaFunctionExpression(
			LambdaFunctionExpression.Type type, String variableName,
			Expression expression)
		throws ExpressionVisitException {

		return expression.accept(this);
	}

	@Override
	public Object visitLambdaVariableExpression(
		LambdaVariableExpression lambdaVariableExpression) {

		return _lambdaVariableExpressionFieldNames.get(
			lambdaVariableExpression.getVariableName());
	}

	@Override
	public Predicate visitListExpressionOperation(
			ListExpression.Operation operation, Object left,
			List<Object> rights)
		throws ExpressionVisitException {

		if (Objects.equals(ListExpression.Operation.IN, operation)) {
			Column<?, Object> column = _getColumn(left);

			return column.in(
				TransformUtil.transformToArray(
					rights, right -> _getValue(left, right), Object.class));
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitListExpressionOperation with operation " +
				operation);
	}

	@Override
	public Object visitLiteralExpression(LiteralExpression literalExpression) {
		if (Objects.equals(
				LiteralExpression.Type.BOOLEAN, literalExpression.getType())) {

			return GetterUtil.getBoolean(literalExpression.getText());
		}
		else if (Objects.equals(
					LiteralExpression.Type.DATE, literalExpression.getType())) {

			return GetterUtil.getDate(
				literalExpression.getText(),
				DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd"));
		}
		else if (Objects.equals(
					LiteralExpression.Type.DOUBLE,
					literalExpression.getType())) {

			return GetterUtil.getDouble(literalExpression.getText());
		}
		else if (Objects.equals(
					LiteralExpression.Type.INTEGER,
					literalExpression.getType())) {

			return GetterUtil.getLong(literalExpression.getText());
		}
		else if (Objects.equals(
					LiteralExpression.Type.NULL, literalExpression.getType())) {

			return null;
		}
		else if (Objects.equals(
					LiteralExpression.Type.STRING,
					literalExpression.getType())) {

			return StringUtil.unquote(literalExpression.getText());
		}

		return literalExpression.getText();
	}

	@Override
	public Object visitMemberExpression(MemberExpression memberExpression)
		throws ExpressionVisitException {

		Expression expression = memberExpression.getExpression();

		return expression.accept(this);
	}

	@Override
	public Object visitMethodExpression(
		List<Object> expressions, MethodExpression.Type type) {

		if (type == MethodExpression.Type.CONTAINS) {
			if (expressions.size() != 2) {
				throw new UnsupportedOperationException(
					StringBundler.concat(
						"Unsupported method visitMethodExpression with method ",
						"type ", type, " and ", expressions.size(), "params"));
			}

			return _contains(expressions.get(0), expressions.get(1));
		}

		if (type == MethodExpression.Type.STARTS_WITH) {
			if (expressions.size() != 2) {
				throw new UnsupportedOperationException(
					StringBundler.concat(
						"Unsupported method visitMethodExpression with method",
						"type ", type, " and ", expressions.size(), "params"));
			}

			return _startsWith(expressions.get(0), expressions.get(1));
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitMethodExpression with method type " +
				type);
	}

	@Override
	public Object visitPrimitivePropertyExpression(
		PrimitivePropertyExpression primitivePropertyExpression) {

		return primitivePropertyExpression.getName();
	}

	@Override
	public Predicate visitUnaryExpressionOperation(
		UnaryExpression.Operation operation, Object operand) {

		if (!Objects.equals(UnaryExpression.Operation.NOT, operation)) {
			throw new UnsupportedOperationException(
				"Unsupported method visitUnaryExpressionOperation with " +
					"operation " + operation);
		}

		DefaultPredicate defaultPredicate = (DefaultPredicate)operand;

		if (Objects.equals(Operand.IN, defaultPredicate.getOperand())) {
			return new DefaultPredicate(
				defaultPredicate.getLeftExpression(), Operand.NOT_IN,
				defaultPredicate.getRightExpression());
		}

		return Predicate.not(defaultPredicate);
	}

	private PredicateExpressionVisitorImpl(
		EntityModel entityModel,
		Map<String, String> lambdaVariableExpressionFieldNames,
		long objectDefinitionId,
		ObjectFieldBusinessTypeRegistry objectFieldBusinessTypeRegistry,
		ObjectFieldLocalService objectFieldLocalService) {

		_entityModel = entityModel;
		_lambdaVariableExpressionFieldNames =
			lambdaVariableExpressionFieldNames;
		_objectDefinitionId = objectDefinitionId;
		_objectFieldBusinessTypeRegistry = objectFieldBusinessTypeRegistry;
		_objectFieldLocalService = objectFieldLocalService;
	}

	private Predicate _contains(Object fieldName, Object fieldValue) {
		Column<?, Object> column = _getColumn(fieldName);

		return column.like(
			StringPool.PERCENT + _getValue(fieldName, fieldValue) +
				StringPool.PERCENT);
	}

	private Column<?, Object> _getColumn(Object fieldName) {
		EntityField entityField = _getEntityField(fieldName);

		return (Column<?, Object>)_objectFieldLocalService.getColumn(
			_objectDefinitionId, entityField.getFilterableName(null));
	}

	private Column<?, Object> _getColumn(EntityField entityField, long objectDefinitionId) {
		return (Column<?, Object>)_objectFieldLocalService.getColumn(
			objectDefinitionId, entityField.getFilterableName(null));
	}

	private EntityField _getEntityField(Object fieldName) {
		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		return entityFieldsMap.get(GetterUtil.getString(fieldName));
	}

	private Optional<Predicate> _getPredicateOptional(
		BinaryExpression.Operation operation, Object left, Object right) {

		Predicate predicate = null;

		if (Objects.equals(BinaryExpression.Operation.AND, operation)) {
			predicate = Predicate.and((Predicate)left, (Predicate)right);
		}
		else if (Objects.equals(BinaryExpression.Operation.OR, operation)) {
			predicate = Predicate.or((Predicate)left, (Predicate)right);
		}
		else {
			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				_objectDefinitionId, String.valueOf(left));

			if ((objectField != null) &&
				StringUtil.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

				predicate = _contains(left, right);
			}
		}

		if (predicate != null) {
			return Optional.of(predicate);
		}

		if (_objectRelationship != null) {
			try {
				return Optional.ofNullable(_getPredicateForRelationships(operation, left, right));
			}
			catch (Exception exception) {
				return Optional.empty();
			}
		}

		Column<?, Object> column = _getColumn(left);

		Object value = _getValue(left, right);

		if (Objects.equals(BinaryExpression.Operation.EQ, operation)) {
			predicate = column.eq(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.GE, operation)) {
			predicate = column.gte(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.GT, operation)) {
			predicate = column.gt(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.LE, operation)) {
			predicate = column.lte(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.LT, operation)) {
			predicate = column.lt(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.NE, operation)) {
			predicate = column.neq(value);
		}
		else {
			return Optional.empty();
		}

		return Optional.of(predicate);
	}

	private Predicate _getPredicateForRelationships(BinaryExpression.Operation operation, Object left, Object right) throws Exception {
		long relatedObjectDefinitionId = _getRelatedObjectDefinitionId(_objectDefinitionId,
			_objectRelationship);

		Predicate predicate = null;

		ObjectEntryEntityModel objectEntryEntityModel =
			new ObjectEntryEntityModel(
				_objectFieldLocalService.getObjectFields(
					relatedObjectDefinitionId),
				ObjectDefinitionLocalServiceUtil.getObjectDefinition(
					relatedObjectDefinitionId));

		Map<String, EntityField> entityFieldsMap =
			objectEntryEntityModel.getEntityFieldsMap();

		EntityField entityField =
			entityFieldsMap.get(GetterUtil.getString(_relatedFieldName));

		Column<?, Object> relatedObjectColumn =
			_getColumn(entityField, relatedObjectDefinitionId);

		Object value = _getValue(entityField, left, right);

		if (Objects.equals(BinaryExpression.Operation.EQ, operation)) {
			predicate = relatedObjectColumn.eq(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.GE, operation)) {
			predicate = relatedObjectColumn.gte(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.GT, operation)) {
			predicate = relatedObjectColumn.gt(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.LE, operation)) {
			predicate = relatedObjectColumn.lte(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.LT, operation)) {
			predicate = relatedObjectColumn.lt(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.NE, operation)) {
			predicate = relatedObjectColumn.neq(value);
		}
		else {
			return null;
		}

		ObjectDefinition relatedObjectDefinition =
			ObjectDefinitionLocalServiceUtil.getObjectDefinition(
				relatedObjectDefinitionId);

		ObjectField relatedObjectDefinitionObjectField = _objectFieldLocalService.getObjectField(
			relatedObjectDefinition.getTitleObjectFieldId());

		Table<?> relatedObjectTable = _objectFieldLocalService.getTable(
			relatedObjectDefinition.getObjectDefinitionId(), relatedObjectDefinitionObjectField.getName());

		Column<?, ?> subjectIdColumn = relatedObjectTable.getColumn(
			relatedObjectDefinition.getPKObjectFieldName() + "_");

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.getObjectDefinition(
				_objectDefinitionId);

		Map<String, String> pkObjectFieldDBColumnNames =
			ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
				objectDefinition, relatedObjectDefinition,
				_objectRelationship.getReverse());

		DynamicObjectRelationshipMappingTable
			dynamicObjectRelationshipMappingTable =
			new DynamicObjectRelationshipMappingTable(
				pkObjectFieldDBColumnNames.get(
					"pkObjectFieldDBColumnName1"),
				pkObjectFieldDBColumnNames.get(
					"pkObjectFieldDBColumnName2"),
				_objectRelationship.getDBTableName());

		ObjectField objectDefinitionField = _objectFieldLocalService.getObjectField(
			objectDefinition.getTitleObjectFieldId());

		Table<?> objectTable = _objectFieldLocalService.getTable(
			objectDefinition.getObjectDefinitionId(), objectDefinitionField.getName());

		Column<?, ?> studentIdColumn = objectTable.getColumn(
			objectDefinition.getPKObjectFieldName() + "_");

		Column<DynamicObjectRelationshipMappingTable, ?> relatedObjectColumnFromRelationshipTable =
			dynamicObjectRelationshipMappingTable.getColumn(
				relatedObjectDefinition.getPKObjectFieldName() + "_");

		Column<DynamicObjectRelationshipMappingTable, ?> objectColumnFromRelationshipTable =
			dynamicObjectRelationshipMappingTable.getColumn(
				objectDefinition.getPKObjectFieldName() + "_");

		predicate = studentIdColumn.in(DSLQueryFactoryUtil
			.select(objectColumnFromRelationshipTable)
			.from(dynamicObjectRelationshipMappingTable)
			.where(relatedObjectColumnFromRelationshipTable.in(
				DSLQueryFactoryUtil
					.select(subjectIdColumn)
					.from(relatedObjectTable)
					.where(relatedObjectColumn.eq(value)))));


		return predicate;
	}

	private class DynamicObjectRelationshipMappingTable
		extends BaseTable<DynamicObjectRelationshipMappingTable> {

		public DynamicObjectRelationshipMappingTable(
			String primaryKeyColumnName1, String primaryKeyColumnName2,
			String tableName) {

			super(tableName, () -> null);

			createColumn(
				primaryKeyColumnName1, Long.class, Types.BIGINT,
				Column.FLAG_PRIMARY);
			createColumn(
				primaryKeyColumnName2, Long.class, Types.BIGINT,
				Column.FLAG_PRIMARY);
		}
	}

	private long _getRelatedObjectDefinitionId(
		long objectDefinitionId,
		ObjectRelationship objectRelationship) {

		return
			objectRelationship.getObjectDefinitionId1() != objectDefinitionId ?
				objectRelationship.getObjectDefinitionId1() :
				objectRelationship.getObjectDefinitionId2();

	}

	private Object _getValue(EntityField entityField, Object left, Object right) {
		EntityField.Type entityType = entityField.getType();

		DB db = DBManagerUtil.getDB();

		if (entityType.equals(EntityField.Type.DATE_TIME) &&
			(db.getDBType() == DBType.HYPERSONIC)) {

			try {
				Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
					"dd-MMM-yyyy HH:mm:ss.SSS");

				DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss");

				Date date = dateFormat.parse(right.toString());

				right = format.format(date);
			}
			catch (ParseException parseException) {
				throw new RuntimeException(parseException);
			}
		}

		String entityFieldFilterableName = entityField.getFilterableName(null);
		String entityFieldName = entityField.getName();

		if (Objects.equals(entityFieldFilterableName, entityFieldName)) {
			return right;
		}

		try {
			ObjectField objectField = _objectFieldLocalService.getObjectField(
				_objectDefinitionId, entityFieldFilterableName);

			ObjectFieldBusinessType objectFieldBusinessType =
				_objectFieldBusinessTypeRegistry.getObjectFieldBusinessType(
					objectField.getBusinessType());

			Object value = objectFieldBusinessType.getValue(
				objectField, Collections.singletonMap(entityFieldName, right));

			if (value == null) {
				return right;
			}

			return value;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return right;
		}
	}

	private Object _getValue(Object left, Object right) {
		EntityField entityField = _getEntityField(left);

		EntityField.Type entityType = entityField.getType();

		DB db = DBManagerUtil.getDB();

		if (entityType.equals(EntityField.Type.DATE_TIME) &&
			(db.getDBType() == DBType.HYPERSONIC)) {

			try {
				Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
					"dd-MMM-yyyy HH:mm:ss.SSS");

				DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss");

				Date date = dateFormat.parse(right.toString());

				right = format.format(date);
			}
			catch (ParseException parseException) {
				throw new RuntimeException(parseException);
			}
		}

		String entityFieldFilterableName = entityField.getFilterableName(null);
		String entityFieldName = entityField.getName();

		if (Objects.equals(entityFieldFilterableName, entityFieldName)) {
			return right;
		}

		try {
			ObjectField objectField = _objectFieldLocalService.getObjectField(
				_objectDefinitionId, entityFieldFilterableName);

			ObjectFieldBusinessType objectFieldBusinessType =
				_objectFieldBusinessTypeRegistry.getObjectFieldBusinessType(
					objectField.getBusinessType());

			Object value = objectFieldBusinessType.getValue(
				objectField, Collections.singletonMap(entityFieldName, right));

			if (value == null) {
				return right;
			}

			return value;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return right;
		}
	}

	private Predicate _startsWith(Object fieldName, Object fieldValue) {
		Column<?, Object> column = _getColumn(fieldName);

		return column.like(
			_getValue(fieldName, fieldValue) + StringPool.PERCENT);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PredicateExpressionVisitorImpl.class);

	private final EntityModel _entityModel;
	private Map<String, String> _lambdaVariableExpressionFieldNames;
	private final long _objectDefinitionId;
	private final ObjectFieldBusinessTypeRegistry
		_objectFieldBusinessTypeRegistry;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private long _relatedObjectDefinitionId;

	private ObjectRelationship _objectRelationship;
	private String _relatedFieldName;

}