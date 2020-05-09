package org.pr.project.domain;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.pr.project.domain.strategies.IntegerBetweenBoundsStrategy;
import org.pr.project.domain.strategies.IntegerFilteringStrategy;

public class StrategyTests {

	@Test
	public void test_applyIntegerBetweenBoundsStartegy_outofBounds() {
		IntegerFilteringStrategy ageBetweenBoundsStategy = new IntegerBetweenBoundsStrategy(20, 30);
		assertFalse(ageBetweenBoundsStategy.apply(51));
		assertFalse(ageBetweenBoundsStategy.apply(11));
	}
	
	@Test
	public void test_applyIntegerBetweenBoundsStartegy_withinBounds() {
		IntegerFilteringStrategy ageBetweenBoundsStategy = new IntegerBetweenBoundsStrategy(20, 30);
		assertTrue(ageBetweenBoundsStategy.apply(21));
	}
	
	@Test
	public void test_applyIntegerBetweenBoundsStartegy_atBounds() {
		IntegerFilteringStrategy ageBetweenBoundsStategy = new IntegerBetweenBoundsStrategy(20, 30);
		assertTrue(ageBetweenBoundsStategy.apply(20));
		assertTrue(ageBetweenBoundsStategy.apply(30));
	}
	
	@Test
	public void test_applyIntegerBetweenBoundsStartegy_InvalidAge() {
		IntegerFilteringStrategy ageBetweenBoundsStategy = new IntegerBetweenBoundsStrategy(20, 30);
		assertFalse(ageBetweenBoundsStategy.apply(null));
		assertFalse(ageBetweenBoundsStategy.apply(-30));
		assertFalse(ageBetweenBoundsStategy.apply(0));
	}
	
	@Test
	public void test_applyIntegerBetweenBoundsStartegy_UnboundInit() {
		IntegerFilteringStrategy ageBetweenBoundsStategy = new IntegerBetweenBoundsStrategy();
		assertTrue(ageBetweenBoundsStategy.apply(null));
		assertTrue(ageBetweenBoundsStategy.apply(-30));
		assertTrue(ageBetweenBoundsStategy.apply(0));
	}
	
	@Test
	public void test_applyIntegerBetweenBoundsStartegy_InvertedBounds() {
		IntegerFilteringStrategy ageBetweenBoundsStategy = new IntegerBetweenBoundsStrategy(50, 20);
		assertFalse(ageBetweenBoundsStategy.apply(null));
		assertFalse(ageBetweenBoundsStategy.apply(-30));
		assertFalse(ageBetweenBoundsStategy.apply(30));
		assertFalse(ageBetweenBoundsStategy.apply(10));
		assertFalse(ageBetweenBoundsStategy.apply(60));
	}
	
}
