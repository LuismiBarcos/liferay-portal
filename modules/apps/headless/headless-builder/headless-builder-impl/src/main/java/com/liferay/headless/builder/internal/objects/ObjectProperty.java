package com.liferay.headless.builder.internal.objects;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectProperty {
	private final String objectFieldName;

	public ObjectProperty(
		String objectFieldName) {
		this.objectFieldName = objectFieldName;
	}

	public String getObjectFieldName() {
		return objectFieldName;
	}
}
