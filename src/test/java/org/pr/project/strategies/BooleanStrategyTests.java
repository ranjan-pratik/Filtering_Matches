package org.pr.project.strategies;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BooleanStrategyTests {

	@Test
	public void test_applyStringIsNotNullStrategy_checkTure() {
		BooleanFilteringStrategy strategy = new IsTrueOrFalseStrategy(true);
		assertTrue(strategy.apply(true));
		assertFalse(strategy.apply(false));
		assertFalse(strategy.apply(null));
	}

	@Test
	public void test_applyStringIsNotNullStrategy_checkFalse() {
		BooleanFilteringStrategy strategy = new IsTrueOrFalseStrategy(false);
		assertTrue(strategy.apply(false));
		assertFalse(strategy.apply(true));
		assertFalse(strategy.apply(null));
	}

}
