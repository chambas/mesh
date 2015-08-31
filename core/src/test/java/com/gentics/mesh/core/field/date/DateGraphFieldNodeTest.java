package com.gentics.mesh.core.field.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gentics.mesh.core.data.NodeGraphFieldContainer;
import com.gentics.mesh.core.data.node.Node;
import com.gentics.mesh.core.data.node.field.basic.DateGraphField;
import com.gentics.mesh.core.data.service.ServerSchemaStorage;
import com.gentics.mesh.core.rest.node.NodeResponse;
import com.gentics.mesh.core.rest.node.field.impl.DateFieldImpl;
import com.gentics.mesh.core.rest.schema.Schema;
import com.gentics.mesh.core.rest.schema.impl.DateFieldSchemaImpl;
import com.gentics.mesh.graphdb.Trx;
import com.gentics.mesh.json.JsonUtil;
import com.gentics.mesh.test.AbstractDBTest;

public class DateGraphFieldNodeTest extends AbstractDBTest {

	@Autowired
	private ServerSchemaStorage schemaStorage;

	@Before
	public void setup() throws Exception {
		setupData();
	}

	@Test
	public void testDateFieldTransformation() throws IOException, InterruptedException {
		try (Trx tx = db.trx()) {
			Node node = folder("2015");
			Schema schema = node.getSchema();
			DateFieldSchemaImpl dateFieldSchema = new DateFieldSchemaImpl();
			dateFieldSchema.setName("dateField");
			dateFieldSchema.setLabel("Some date field");
			dateFieldSchema.setRequired(true);
			schema.addField(dateFieldSchema);
			node.getSchemaContainer().setSchema(schema);

			NodeGraphFieldContainer container = node.getFieldContainer(english());
			DateGraphField field = container.createDate("dateField");
			field.setDate("01.01.1971");

			String json = getJson(node);
			assertTrue("The json should contain the date but it did not.{" + json + "}", json.indexOf("1971") > 1);
			assertNotNull(json);
			NodeResponse response = JsonUtil.readNode(json, NodeResponse.class, schemaStorage);
			assertNotNull(response);

			com.gentics.mesh.core.rest.node.field.DateField deserializedNodeField = response.getField("dateField", DateFieldImpl.class);
			assertNotNull(deserializedNodeField);
			assertEquals("01.01.1971", deserializedNodeField.getDate());
		}

	}
}