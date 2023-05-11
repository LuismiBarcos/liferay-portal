package com.liferay.headless.builder.internal.contracts;

/**
 * @author Luis Miguel Barcos
 */
public class PropertyInfo {

	private final String name;
	private final String internalName;

	public PropertyInfo(String name, String internalName) {
		this.name = name;
		this.internalName = internalName;
	}

	public String getName() {
		return name;
	}

	public String getInternalName() {
		return internalName;
	}
}
