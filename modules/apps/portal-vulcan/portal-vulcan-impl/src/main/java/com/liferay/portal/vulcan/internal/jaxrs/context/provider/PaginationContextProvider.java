/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.context.provider;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.internal.configuration.HeadlessAPICompanyConfiguration;
import com.liferay.portal.vulcan.pagination.InvalidPaginationException;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Zoltán Takács
 */
@Provider
public class PaginationContextProvider implements ContextProvider<Pagination> {

	public PaginationContextProvider(
		ConfigurationProvider configurationProvider, Portal portal) {

		_configurationProvider = configurationProvider;
		_portal = portal;
	}

	@Override
	public Pagination createContext(Message message) {
		HttpServletRequest httpServletRequest =
			ContextProviderUtil.getHttpServletRequest(message);

		int page = GetterUtil.getInteger(
			httpServletRequest.getParameter("page"), 1);

		if (page <= 0) {
			throw new InvalidPaginationException(
				String.format(
					"The Page parameter introduced [%s] is not valid. Only " +
						"numbers higher or equal to 1 are accepted.",
					page));
		}

		int pageSize = GetterUtil.getInteger(
			httpServletRequest.getParameter("pageSize"), 20);

		if (pageSize == 0) {
			pageSize = -1;
		}

		try {
			HeadlessAPICompanyConfiguration headlessAPICompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					HeadlessAPICompanyConfiguration.class,
					_portal.getCompanyId(httpServletRequest));

			int maxPageSize =
				headlessAPICompanyConfiguration.paginationSizeLimit();

			if ((maxPageSize > 0) &&
				((pageSize > maxPageSize) || (pageSize <= 0))) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"The pageSize value introduced [", pageSize,
							"] is higher than the Page Size Limit configured [",
							maxPageSize, "] therefore, this last value is ",
							"returned. You can modify this limit through the ",
							"Headless API section found in Instance/System ",
							"settings."));
				}

				pageSize = maxPageSize;
			}
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
			pageSize = 20;
		}

		return Pagination.of(
			GetterUtil.getInteger(page), GetterUtil.getInteger(pageSize));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PaginationContextProvider.class);

	private final ConfigurationProvider _configurationProvider;
	private final Portal _portal;

}