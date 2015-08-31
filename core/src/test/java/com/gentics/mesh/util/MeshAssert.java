package com.gentics.mesh.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.gentics.mesh.core.data.root.RootVertex;
import com.gentics.mesh.etc.MeshSpringConfiguration;
import com.gentics.mesh.graphdb.Trx;
import com.gentics.mesh.test.TestUtil;

import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public final class MeshAssert {

	private static final Logger log = LoggerFactory.getLogger(MeshAssert.class);

	private static final Integer CI_TIMEOUT_SECONDS = 10;

	private static final Integer DEV_TIMEOUT_SECONDS = 100000;

	public static void assertSuccess(Future<?> future) {
		if (future.cause() != null) {
			future.cause().printStackTrace();
		}
		assertTrue("The future failed with error {" + (future.cause() == null ? "Unknown error" : future.cause().getMessage()) + "}",
				future.succeeded());
	}

	public static void assertElement(RootVertex<?> root, String uuid, boolean exists) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		root.findByUuid(uuid, rh -> {
			if (exists) {
				assertNotNull("The element should not exist.", rh.result());
			} else {
				assertNull("The element should not exist.", rh.result());
			}
			latch.countDown();
		});
		failingLatch(latch);
	}

	public static int getTimeout() throws UnknownHostException {
		int timeout = DEV_TIMEOUT_SECONDS;
		if (TestUtil.isHost("jenkins.office")) {
			timeout = CI_TIMEOUT_SECONDS;
		}
		log.info("Using test timeout of {" + timeout + "} seconds for host {" + TestUtil.getHostname() + "}");
		return timeout;
	}

	public static void latchFor(Future<?> future) {
		CountDownLatch latch = new CountDownLatch(1);
		future.setHandler(rh -> {
			latch.countDown();
		});
		try {
			assertTrue("The timeout of the latch was reached.", latch.await(getTimeout(), TimeUnit.SECONDS));
		} catch (UnknownHostException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void failingLatch(CountDownLatch latch, int timeoutInSeconds) throws InterruptedException {
		if (!latch.await(timeoutInSeconds, TimeUnit.SECONDS)) {
			fail("Latch timeout reached");
		}
	}

	public static void failingLatch(CountDownLatch latch) throws InterruptedException {
		if (!latch.await(1, TimeUnit.SECONDS)) {
			fail("Latch timeout reached");
		}
	}

	public static void assertDeleted(Map<String, String> uuidToBeDeleted) {
		try (Trx tx = new Trx(MeshSpringConfiguration.getMeshSpringConfiguration().database())) {
			for (Map.Entry<String, String> entry : uuidToBeDeleted.entrySet()) {
				assertFalse("One vertex was not deleted. Uuid: {" + entry.getValue() + "} - Type: {" + entry.getKey() + "}",
						tx.getGraph().v().has("uuid", entry.getValue()).hasNext());
			}
		}
	}

}