package com.liferay.headless.builder.internal.contracts;

/**
 * @author Luis Miguel Barcos
 */
public class PropertyInfo {

	private final String name;
	private final String internalName;
	private final String internalClass;

	public PropertyInfo(String name, String internalName, String internalClass) {
		this.name = name;
		this.internalName = internalName;
		this.internalClass = internalClass;
	}

	public String getName() {
		return name;
	}

	public String getInternalName() {
		return internalName;
	}

	public String getInternalClass() {
		return internalClass;
	}
}
