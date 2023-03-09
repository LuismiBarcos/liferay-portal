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

package com.liferay.object.rest.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Luis Miguel Barcos
 */
public class FilterURLCreatorUtil {

	public static <T> String createFilterWithComparisonOperator(
		FilterOperator.ComparisonOperator comparisonOperator,
		String propertyName, T propertyValue) {

		String apostrophe = StringPool.BLANK;

		if (propertyValue instanceof String) {
			apostrophe = StringPool.APOSTROPHE;
		}

		return _escape(
			StringBundler.concat(
				propertyName, StringPool.SPACE,
				StringUtil.toLowerCase(comparisonOperator.name()),
				StringPool.SPACE, apostrophe, propertyValue, apostrophe));
	}

	public static String createFilterWithLambdaOperator(
		String propertyName, String filter,
		FilterOperator.LambdaOperator lambdaOperator) {

		String lambdaContent = StringBundler.concat(
			propertyName.substring(0, 1), StringPool.COLON, filter);

		return _escape(
			StringBundler.concat(
				propertyName, StringPool.SLASH,
				StringUtil.toLowerCase(lambdaOperator.name()),
				_addParenthesis(lambdaContent)));
	}

	@SafeVarargs
	public static <T> String createFilterWithListOperator(
		FilterOperator.ListOperator listOperator, String propertyName,
		T... propertyValues) {

		StringBuilder propertyValuesStringBuilder = new StringBuilder(
			StringPool.BLANK);

		for (int i = 0; i < propertyValues.length; i++) {
			if (i != (propertyValues.length - 1)) {
				propertyValuesStringBuilder.append(
					StringBundler.concat(
						StringPool.APOSTROPHE, propertyValues[i],
						StringPool.APOSTROPHE, StringPool.COMMA_AND_SPACE));
			}
			else {
				propertyValuesStringBuilder.append(
					StringBundler.concat(
						StringPool.APOSTROPHE, propertyValues[i].toString(),
						StringPool.APOSTROPHE));
			}
		}

		return _escape(
			StringBundler.concat(
				propertyName, StringPool.SPACE,
				StringUtil.toLowerCase(listOperator.name()), StringPool.SPACE,
				_addParenthesis(propertyValuesStringBuilder.toString())));
	}

	public static String createFilterWithLogicalOperators(
		String leftFilter, FilterOperator.LogicalOperator logicalOperator,
		String rightFilter) {

		if (logicalOperator == FilterOperator.LogicalOperator.NOT) {
			return StringBundler.concat(
				StringUtil.toLowerCase(logicalOperator.name()),
				StringPool.SPACE, _addParenthesis(leftFilter));
		}

		return _escape(
			StringBundler.concat(
				leftFilter, StringPool.SPACE,
				StringUtil.toLowerCase(logicalOperator.name()),
				StringPool.SPACE, rightFilter));
	}

	public static String createFilterWithStringOperator(
		String propertyName, String propertyValue,
		FilterOperator.StringOperator stringOperator) {

		String filter = StringBundler.concat(
			propertyName, StringPool.COMMA_AND_SPACE, StringPool.APOSTROPHE,
			propertyValue, StringPool.APOSTROPHE);

		return _escape(
			StringUtil.toLowerCase(stringOperator.name()) +
				_addParenthesis(filter));
	}

	public static List<FilterOperator> getFilterOperators() {
		List<FilterOperator> filterOperators = new ArrayList<>();

		Collections.addAll(
			filterOperators, FilterOperator.ComparisonOperator.values());
		Collections.addAll(
			filterOperators, FilterOperator.LambdaOperator.values());
		Collections.addAll(
			filterOperators, FilterOperator.ListOperator.values());
		Collections.addAll(
			filterOperators, FilterOperator.LogicalOperator.values());
		Collections.addAll(
			filterOperators, FilterOperator.StringOperator.values());

		return filterOperators;
	}

	public interface FilterOperator {

		public enum ComparisonOperator implements FilterOperator {

			EQ, GE, GT, LE, LT, NE

		}

		public enum LambdaOperator implements FilterOperator {

			ANY

		}

		public enum ListOperator implements FilterOperator {

			IN

		}

		public enum LogicalOperator implements FilterOperator {

			AND, NOT, OR

		}

		public enum StringOperator implements FilterOperator {

			CONTAINS, STARTSWITH

		}

	}

	private static String _addParenthesis(String string) {
		return StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, string, StringPool.CLOSE_PARENTHESIS);
	}

	private static String _escape(String string) {
		return URLCodec.encodeURL(string);
	}

}