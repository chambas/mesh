package com.gentics.mesh.core.data.node.field.impl.basic;

import com.gentics.mesh.core.data.node.field.basic.AbstractBasicField;
import com.gentics.mesh.core.data.node.field.basic.HTMLField;
import com.syncleus.ferma.AbstractVertexFrame;

public class HTMLFieldImpl extends AbstractBasicField implements HTMLField {

	public HTMLFieldImpl(String fieldKey, AbstractVertexFrame parentContainer) {
		super(fieldKey, parentContainer);
	}

	public void setHTML(String html) {
		setFieldProperty("html", html);
	}

	public String getHTML() {
		return getFieldProperty("html");
	}

}
