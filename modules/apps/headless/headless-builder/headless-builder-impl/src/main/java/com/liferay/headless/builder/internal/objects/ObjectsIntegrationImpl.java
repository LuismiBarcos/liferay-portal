package com.liferay.headless.builder.internal.objects;

import com.liferay.headless.builder.internal.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.contracts.PropertyInfo;
import com.liferay.headless.builder.internal.contracts.SourceInformationBridge;
import com.liferay.headless.builder.internal.operation.handler.OperationHandler;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.vulcan.yaml.openapi.Schema;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = SourceInformationBridge.class)
public class ObjectsIntegrationImpl implements SourceInformationBridge {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, OperationHandler.class,
			HeadlessBuilderConstants.OPERATION_NAME);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	public Map<String, PropertyInfo> getPropertiesInfo(
		String entityName, Map<String, Schema> propertySchemas) {

		Map<String, PropertyInfo> stringObjectPropertyMap = new HashMap<>();
		for (Map.Entry<String, Schema> schemaEntry : propertySchemas.entrySet()) {
			String objectFieldName = schemaEntry.getValue().getFieldDefinition().getName();

			stringObjectPropertyMap.put(
				schemaEntry.getKey(),
				new PropertyInfo(schemaEntry.getKey(), objectFieldName));
		}

		return stringObjectPropertyMap;
	}

	@Override
	public Serializable getValue(
		PropertyInfo propertyInfo, Object pathParameterValue)
		throws PortalException {

		ObjectEntry objectEntry = null;

		if(pathParameterValue instanceof Long) {
			objectEntry = _objectEntryLocalService.getObjectEntry(
				(Long) pathParameterValue);
		}

		return getFieldValue(objectEntry, propertyInfo.getInternalName());
	}

	public Map<String, ObjectProperty> getObjectProperties(String entityName, Map<String, Schema> propertySchemas) {
		int objectDefinitionId = Integer.parseInt(entityName.split("#")[1]);

		Map<String, ObjectProperty> stringObjectPropertyMap = new HashMap<>();
		for (Map.Entry<String, Schema> schemaEntry : propertySchemas.entrySet()) {
			String objectFieldName = schemaEntry.getValue().getFieldDefinition().getName();

			stringObjectPropertyMap.put(
				schemaEntry.getKey(),
				new ObjectProperty(objectFieldName));
		}

		return stringObjectPropertyMap;
	}

	public Serializable getValue(ObjectProperty objectProperty, long objectEntryId)
		throws PortalException {

		ObjectEntry objectEntry =
			_objectEntryLocalService.getObjectEntry(objectEntryId);

		return getFieldValue(objectEntry, objectProperty.getObjectFieldName());
	}

	private Serializable getFieldValue(ObjectEntry objectEntry, String name) {
		Serializable systemField = getSystemFields(objectEntry, name);
		if (systemField == null) {
			return getCustomField(objectEntry.getValues(), name);
		}

		return systemField;
	}

	private Serializable getSystemFields(ObjectEntry objectEntry, String name) {
		return name.equals("createDate") ? objectEntry.getCreateDate() : null;
	}

	private Serializable getCustomField(Map<String, Serializable> values, String name) {
		return values.get(name);
	}

	private ServiceTrackerMap<String, OperationHandler> _serviceTrackerMap;


	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;
}
