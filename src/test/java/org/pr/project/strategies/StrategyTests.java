package org.pr.project.strategies;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StrategyTests {

	@Test
	public void test_applyNumberBetweenBoundsStartegy_outofBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(new Double(20), new Double(30));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(51)));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(11)));
	}
	
	@Test
	public void test_applyNumberBetweenBoundsStartegy_withinBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(new Double(20), new Double(30));
		assertTrue(ageBetweenBoundsStategy.apply(new Double(21)));
	}
	
	@Test
	public void test_applyNumberBetweenBoundsStartegy_atBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(new Double(20), new Double(30));
		assertTrue(ageBetweenBoundsStategy.apply(new Double(20)));
		assertTrue(ageBetweenBoundsStategy.apply(new Double(30)));
	}
	
	@Test
	public void test_applyNumberBetweenBoundsStartegy_InvalidAge() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(new Double(20), new Double(30));
		assertFalse(ageBetweenBoundsStategy.apply(null));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(-30)));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(0)));
	}
	
	@Test
	public void test_applyNumberBetweenBoundsStartegy_UnboundInit() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy();
		assertTrue(ageBetweenBoundsStategy.apply(null));
		assertTrue(ageBetweenBoundsStategy.apply(new Double(-30)));
		assertTrue(ageBetweenBoundsStategy.apply(new Double(0)));
	}
	
	@Test
	public void test_applyNumberBetweenBoundsStartegy_InvertedBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(new Double(50), new Double(20));
		assertFalse(ageBetweenBoundsStategy.apply(null));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(-30)));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(30)));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(10)));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(60)));
	}
	
}
