package com.liferay.headless.builder.internal.objects;

import com.liferay.headless.builder.internal.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.contracts.PropertyInfo;
import com.liferay.headless.builder.internal.contracts.SourceInformationBridge;
import com.liferay.headless.builder.internal.operation.handler.OperationHandler;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.yaml.openapi.Schema;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;
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
				new PropertyInfo(schemaEntry.getKey(), objectFieldName,
					entityName));
		}

		return stringObjectPropertyMap;
	}

	@Override
	public Object getValue(
		PropertyInfo propertyInfo, Object pathParameterValue,
		HttpServletRequest httpServletRequest, UriInfo uriInfo)
		throws Exception {

		long objectEntryId = 0;

		if(pathParameterValue instanceof Long) {
			objectEntryId = (long) pathParameterValue;
		}

		String internalClass = propertyInfo.getInternalClass();
		long objectDefinitionId = Long.parseLong(internalClass.split("#")[1]);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				objectDefinition.getStorageType());

		DTOConverterContext dtoConverterContext =
			_getDTOConverterContext(objectEntryId, objectDefinition, uriInfo,
				httpServletRequest);

		return getFieldValue(objectEntryManager.getObjectEntry(dtoConverterContext,
			objectDefinition, objectEntryId), propertyInfo.getInternalName());
	}

	private DTOConverterContext _getDTOConverterContext(
		long objectEntryId, ObjectDefinition objectDefinition, UriInfo uriInfo,
		HttpServletRequest httpServletRequest) throws PortalException {

		return new DefaultDTOConverterContext(
			false, null,
			_dtoConverterRegistry, httpServletRequest, objectEntryId,
			LocaleUtil.fromLanguageId(objectDefinition.getDefaultLanguageId()), uriInfo,
			_userLocalService.getUser(objectDefinition.getUserId()));
	}

	private Object getFieldValue(
		com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry, String name) {
		Object systemField = getSystemFields(objectEntry, name);
		if (systemField == null) {
			return getCustomField(objectEntry.getProperties(), name);
		}

		return systemField;
	}

	private Object getSystemFields(ObjectEntry objectEntry, String name) {
		return name.equals("createDate") ? objectEntry.getDateCreated() : null;
	}

	private Object getCustomField(Map<String, Object> values, String name) {
		return values.get(name);
	}

	private ServiceTrackerMap<String, OperationHandler> _serviceTrackerMap;


	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private UserLocalService _userLocalService;
}
