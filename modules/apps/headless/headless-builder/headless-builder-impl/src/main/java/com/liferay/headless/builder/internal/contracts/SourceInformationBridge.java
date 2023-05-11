package com.liferay.headless.builder.internal.contracts;

import com.liferay.portal.vulcan.yaml.openapi.Schema;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

/**
 * @author Luis Miguel Barcos
 */
public interface SourceInformationBridge {

	public Map<String, PropertyInfo> getPropertiesInfo(String entityName, Map<String, Schema> propertySchemas);

	public Object getValue(PropertyInfo propertyInfo, Object pathParameterValue,
						   HttpServletRequest httpServletRequest,
						   UriInfo uriInfo) throws Exception;
}
