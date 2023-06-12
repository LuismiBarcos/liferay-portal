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
public class Property {

	public String getName() {
		return _builder._name;
	}

	public String getObjectFieldERC() {
		return _builder._objectFieldERC;
	}

	public static class Builder {

		public Property build() {
			return new Property(this);
		}

		public Builder setName(String name) {
			_name = name;

			return this;
		}

		public Builder setObjectFieldERC(String objectFieldERC) {
			_objectFieldERC = objectFieldERC;

			return this;
		}

		private String _name;
		private String _objectFieldERC;

	}

	private Property(Builder builder) {
		_builder = builder;
	}

	private final Builder _builder;

}