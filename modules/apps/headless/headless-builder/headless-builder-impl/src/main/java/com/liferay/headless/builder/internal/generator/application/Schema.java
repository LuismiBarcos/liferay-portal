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

package com.liferay.headless.builder.internal.generator.application;

/**
 * @author Luis Miguel Barcos
 */
public class Schema {

	public String getMainObjectDefinitionERC() {
		return _builder._mainObjectDefinitionERC;
	}

	public String getName() {
		return _builder._name;
	}

	public static class Builder {

		public Schema build() {
			return new Schema(this);
		}

		public Builder setMainObjectDefinitionERC(
			String mainObjectDefinitionERC) {

			_mainObjectDefinitionERC = mainObjectDefinitionERC;

			return this;
		}

		public Builder setName(String name) {
			_name = name;

			return this;
		}

		private String _mainObjectDefinitionERC;
		private String _name;

	}

	private Schema(Builder builder) {
		_builder = builder;
	}

	private final Builder _builder;

}