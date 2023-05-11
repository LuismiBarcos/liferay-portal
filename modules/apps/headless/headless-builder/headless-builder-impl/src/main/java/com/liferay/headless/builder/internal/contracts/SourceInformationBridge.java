package com.liferay.headless.builder.internal.contracts;

import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Luis Miguel Barcos
 */
public interface SourceInformationBridge {

	public Map<String, PropertyInfo> getPropertiesInfo(String entityName, Map<String, Schema> propertySchemas);

	public Serializable getValue(PropertyInfo propertyInfo, Object pathParameterValue) throws Exception;
}
