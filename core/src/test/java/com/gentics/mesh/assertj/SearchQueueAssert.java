package com.gentics.mesh.assertj;

import static org.junit.Assert.assertEquals;

import org.assertj.core.api.AbstractAssert;

import com.gentics.mesh.core.data.search.SearchQueue;

public class SearchQueueAssert extends AbstractAssert<SearchQueueAssert, SearchQueue> {

	protected SearchQueueAssert(SearchQueue actual) {
		super(actual, SearchQueueAssert.class);
	}

	public SearchQueueAssert hasEntries(int count) {
		isNotNull();
		assertEquals("The search queue did not contain the expected amount of entries.", count, actual.getSize());
		return this;
	}

}