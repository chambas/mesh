package com.gentics.mesh.core.schema.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class DateFieldMigrationTest extends AbstractFieldMigrationTest {
	private static final long DATEVALUE = new Date().getTime();

	private static final DataProvider FILL = (container, name) -> container.createDate(name).setDate(DATEVALUE);

	private static final FieldFetcher FETCH = (container, name) -> container.getDate(name);

	@Override
	@Test
	public void testRemove() throws Exception {
		removeField(CREATEDATE, FILL, FETCH);
	}

	@Override
	@Test
	public void testRename() throws Exception {
		renameField(CREATEDATE, FILL, FETCH, (container, name) -> {
			assertThat(container.getDate(name)).as(NEWFIELD).isNotNull();
			assertThat(container.getDate(name).getDate()).as(NEWFIELDVALUE).isEqualTo(DATEVALUE);
		});
	}

	@Override
	@Test
	public void testChangeToBinary() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATEBINARY, (container, name) -> {
			assertThat(container.getBinary(name)).as(NEWFIELD).isNull();
		});
	}

	@Override
	@Test
	public void testChangeToBoolean() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATEBOOLEAN, (container, name) -> {
			assertThat(container.getBoolean(name)).as(NEWFIELD).isNull();
		});
	}

	@Override
	@Test
	public void testChangeToBooleanList() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATEBOOLEANLIST, (container, name) -> {
			assertThat(container.getBooleanList(name)).as(NEWFIELD).isNull();
		});
	}

	@Override
	@Test
	public void testChangeToDate() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATEDATE, (container, name) -> {
			assertThat(container.getDate(name)).as(NEWFIELD).isNotNull();
			assertThat(container.getDate(name).getDate()).as(NEWFIELDVALUE).isEqualTo(DATEVALUE);
		});
	}

	@Override
	@Test
	public void testChangeToDateList() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATEDATELIST, (container, name) -> {
			assertThat(container.getDateList(name)).as(NEWFIELD).isNotNull();
			assertThat(container.getDateList(name).getValues()).as(NEWFIELDVALUE).containsExactly(DATEVALUE);
		});
	}

	@Override
	@Test
	public void testChangeToHtml() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATEHTML, (container, name) -> {
			assertThat(container.getHtml(name)).as(NEWFIELD).isNotNull();
			assertThat(container.getHtml(name).getHTML()).as(NEWFIELDVALUE).isEqualTo(Long.toString(DATEVALUE));
		});
	}

	@Override
	@Test
	public void testChangeToHtmlList() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATEHTMLLIST, (container, name) -> {
			assertThat(container.getHTMLList(name)).as(NEWFIELD).isNotNull();
			assertThat(container.getHTMLList(name).getValues()).as(NEWFIELDVALUE).containsExactly(Long.toString(DATEVALUE));
		});
	}

	@Override
	@Test
	public void testChangeToMicronode() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATEMICRONODE, (container, name) -> {
			assertThat(container.getMicronode(name)).as(NEWFIELD).isNull();
		});
	}

	@Override
	@Test
	public void testChangeToMicronodeList() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATEMICRONODELIST, (container, name) -> {
			assertThat(container.getMicronodeList(name)).as(NEWFIELD).isNull();
		});
	}

	@Override
	@Test
	public void testChangeToNode() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATENODE, (container, name) -> {
			assertThat(container.getNode(name)).as(NEWFIELD).isNull();
		});
	}

	@Override
	@Test
	public void testChangeToNodeList() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATENODELIST, (container, name) -> {
			assertThat(container.getNodeList(name)).as(NEWFIELD).isNull();
		});
	}

	@Override
	@Test
	public void testChangeToNumber() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATENUMBER, (container, name) -> {
			assertThat(container.getNumber(name)).as(NEWFIELD).isNotNull();
			assertThat(container.getNumber(name).getNumber()).as(NEWFIELDVALUE).isEqualTo(DATEVALUE);
		});
	}

	@Override
	@Test
	public void testChangeToNumberList() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATENUMBERLIST, (container, name) -> {
			assertThat(container.getNumberList(name)).as(NEWFIELD).isNotNull();
			assertThat(container.getNumberList(name).getValues()).as(NEWFIELDVALUE).containsExactly(DATEVALUE);
		});
	}

	@Override
	@Test
	public void testChangeToString() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATESTRING, (container, name) -> {
			assertThat(container.getString(name)).as(NEWFIELD).isNotNull();
			assertThat(container.getString(name).getString()).as(NEWFIELDVALUE).isEqualTo(Long.toString(DATEVALUE));
		});
	}

	@Override
	@Test
	public void testChangeToStringList() throws Exception {
		changeType(CREATEDATE, FILL, FETCH, CREATESTRINGLIST, (container, name) -> {
			assertThat(container.getStringList(name)).as(NEWFIELD).isNotNull();
			assertThat(container.getStringList(name).getValues()).as(NEWFIELDVALUE).containsExactly(Long.toString(DATEVALUE));
		});
	}

	@Override
	@Test
	public void testCustomMigrationScript() throws Exception {
		customMigrationScript(CREATEDATE, FILL, FETCH, "function migrate(node, fieldname) {node.fields[fieldname] = node.fields[fieldname] + 86400; return node;}", (container, name) -> {
			assertThat(container.getDate(name)).as(NEWFIELD).isNotNull();
			assertThat(container.getDate(name).getDate()).as(NEWFIELDVALUE).isEqualTo(DATEVALUE + 86400L);
		});
	}

	@Override
	@Test(expected=ExecutionException.class)
	public void testInvalidMigrationScript() throws Exception {
		invalidMigrationScript(CREATEDATE, FILL, INVALIDSCRIPT);
	}

	@Override
	@Test(expected=ExecutionException.class)
	public void testSystemExit() throws Exception {
		invalidMigrationScript(CREATEDATE, FILL, KILLERSCRIPT);
	}
}