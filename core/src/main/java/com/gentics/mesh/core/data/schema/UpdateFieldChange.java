package com.gentics.mesh.core.data.schema;

/**
 * Change entry which contains information on how to update a field (eg. change type, settings)
 */
public interface UpdateFieldChange extends SchemaFieldChange {

	/**
	 * Set a field specific property.
	 * 
	 * @param key
	 * @param value
	 */
	void setFieldProperty(String key, String value);

	/**
	 * Return a field specific property.
	 * 
	 * @param key
	 * @return
	 */
	String getFieldProperty(String key);
}
