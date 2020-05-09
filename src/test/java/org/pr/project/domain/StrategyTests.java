package org.pr.project.domain;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.pr.project.domain.strategies.NumberBetweenBoundsStrategy;
import org.pr.project.domain.strategies.NumericFilteringStrategy;

public class StrategyTests {

	@Test
	public void test_applyNumberBetweenBoundsStartegy_outofBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(new BigDecimal(20), new BigDecimal(30));
		assertFalse(ageBetweenBoundsStategy.apply(new BigDecimal(51)));
		assertFalse(ageBetweenBoundsStategy.apply(new BigDecimal(11)));
	}
	
	@Test
	public void test_applyNumberBetweenBoundsStartegy_withinBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(new BigDecimal(20), new BigDecimal(30));
		assertTrue(ageBetweenBoundsStategy.apply(new BigDecimal(21)));
	}
	
	@Test
	public void test_applyNumberBetweenBoundsStartegy_atBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(new BigDecimal(20), new BigDecimal(30));
		assertTrue(ageBetweenBoundsStategy.apply(new BigDecimal(20)));
		assertTrue(ageBetweenBoundsStategy.apply(new BigDecimal(30)));
	}
	
	@Test
	public void test_applyNumberBetweenBoundsStartegy_InvalidAge() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(new BigDecimal(20), new BigDecimal(30));
		assertFalse(ageBetweenBoundsStategy.apply(null));
		assertFalse(ageBetweenBoundsStategy.apply(new BigDecimal(-30)));
		assertFalse(ageBetweenBoundsStategy.apply(new BigDecimal(0)));
	}
	
	@Test
	public void test_applyNumberBetweenBoundsStartegy_UnboundInit() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy();
		assertTrue(ageBetweenBoundsStategy.apply(null));
		assertTrue(ageBetweenBoundsStategy.apply(new BigDecimal(-30)));
		assertTrue(ageBetweenBoundsStategy.apply(new BigDecimal(0)));
	}
	
	@Test
	public void test_applyNumberBetweenBoundsStartegy_InvertedBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(new BigDecimal(50), new BigDecimal(20));
		assertFalse(ageBetweenBoundsStategy.apply(null));
		assertFalse(ageBetweenBoundsStategy.apply(new BigDecimal(-30)));
		assertFalse(ageBetweenBoundsStategy.apply(new BigDecimal(30)));
		assertFalse(ageBetweenBoundsStategy.apply(new BigDecimal(10)));
		assertFalse(ageBetweenBoundsStategy.apply(new BigDecimal(60)));
	}
	
}
