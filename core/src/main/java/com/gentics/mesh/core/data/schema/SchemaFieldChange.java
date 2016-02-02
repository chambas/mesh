package com.gentics.mesh.core.data.schema;

/**
 * Common field change class which may be used for changes that target a specific field.
 */
public interface SchemaFieldChange extends SchemaChange {

	/**
	 * Set the name of the field which should be handled.
	 * 
	 * @param name
	 *            field name
	 */
	void setFieldName(String name);

	/**
	 * Return the field name which should be handled.
	 * 
	 * @return field name
	 */
	String getFieldName();

}
