package org.pr.project.strategies;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringStrategyTests {

	@Test
	public void test_applyStringIsNotNullStrategy_valid() {
		StringFilteringStrategy strategy = new StringIsNotNullStrategy();
		assertTrue(strategy.apply("some string"));
		assertFalse(strategy.apply(""));
	}

	@Test
	public void test_applyStringIsNotNullStrategy_invalid() {
		StringFilteringStrategy strategy = new StringIsNotNullStrategy();
		assertFalse(strategy.apply(null));
	}
}
