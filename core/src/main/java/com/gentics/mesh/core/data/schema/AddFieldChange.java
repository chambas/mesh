package com.gentics.mesh.core.data.schema;

public interface AddFieldChange extends SchemaFieldChange {

	/**
	 * Set the type of the field that should be added.
	 * 
	 * @param type
	 * @return Fluent API
	 */
	AddFieldChange setType(String type);

	/**
	 * Returns the type of the field that should be added.
	 * 
	 * @return
	 */
	String getType();

}
