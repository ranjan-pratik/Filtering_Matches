package org.pr.project.strategies;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NumericStrategyTests {

	@Test
	public void test_applyNumberBetweenBoundsStartegy_outofBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(
				new Double(20), new Double(30));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(51)));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(11)));
	}

	@Test
	public void test_applyNumberBetweenBoundsStartegy_withinBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(
				new Double(20), new Double(30));
		assertTrue(ageBetweenBoundsStategy.apply(new Double(21)));
	}

	@Test
	public void test_applyNumberBetweenBoundsStartegy_atBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(
				new Double(20), new Double(30));
		assertTrue(ageBetweenBoundsStategy.apply(new Double(20)));
		assertTrue(ageBetweenBoundsStategy.apply(new Double(30)));
	}

	@Test
	public void test_applyNumberBetweenBoundsStartegy_InvalidAge() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(
				new Double(20), new Double(30));
		assertFalse(ageBetweenBoundsStategy.apply(null));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(-30)));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(0)));
	}

	@Test
	public void test_applyNumberBetweenBoundsStartegy_InvertedBounds() {
		NumericFilteringStrategy ageBetweenBoundsStategy = new NumberBetweenBoundsStrategy(
				new Double(50), new Double(20));
		assertFalse(ageBetweenBoundsStategy.apply(null));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(-30)));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(30)));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(10)));
		assertFalse(ageBetweenBoundsStategy.apply(new Double(60)));
	}

	@Test
	public void test_applyNumberEqualToStartegy() {
		NumericFilteringStrategy numberEqualToS = new NumberEqualToStrategy(
				new Double(50));
		assertTrue(numberEqualToS.apply(50d));
		assertFalse(numberEqualToS.apply(null));
		assertFalse(numberEqualToS.apply(new Double(-30)));
		assertFalse(numberEqualToS.apply(new Double(30)));
		assertFalse(numberEqualToS.apply(new Double(10)));
		assertFalse(numberEqualToS.apply(new Double(60)));
	}

	@Test
	public void test_applyNumberEqualToStartegy_invalid() {
		NumericFilteringStrategy numberEqualToS = new NumberEqualToStrategy(
				null);
		assertFalse(numberEqualToS.apply(50d));
		assertFalse(numberEqualToS.apply(null));
		assertFalse(numberEqualToS.apply(new Double(-30)));
		assertFalse(numberEqualToS.apply(new Double(30)));
		assertFalse(numberEqualToS.apply(new Double(10)));
		assertFalse(numberEqualToS.apply(new Double(60)));
	}

	@Test
	public void test_applyPositiveNumberStartegy() {
		NumericFilteringStrategy positiveNo = new PositiveNumberStrategy();
		assertTrue(positiveNo.apply(50d));
		assertFalse(positiveNo.apply(null));
		assertFalse(positiveNo.apply(new Double(-30)));
		assertTrue(positiveNo.apply(new Double(30)));
		assertTrue(positiveNo.apply(new Double(10)));
		assertTrue(positiveNo.apply(new Double(60)));
	}

	@Test
	public void test_applyNumberGreaterThanStartegy() {
		NumericFilteringStrategy numberGreaterThan = new NumberGreaterThanStrategy(
				new Double(50));
		assertFalse(numberGreaterThan.apply(50d));
		assertFalse(numberGreaterThan.apply(null));
		assertFalse(numberGreaterThan.apply(new Double(-30)));
		assertFalse(numberGreaterThan.apply(new Double(30)));
		assertFalse(numberGreaterThan.apply(new Double(10)));
		assertTrue(numberGreaterThan.apply(new Double(60)));
	}

	@Test
	public void test_applyNumberGreaterThanStartegy_invalid() {
		NumericFilteringStrategy numberGreaterThan = new NumberGreaterThanStrategy(
				null);
		assertFalse(numberGreaterThan.apply(50d));
		assertFalse(numberGreaterThan.apply(null));
		assertFalse(numberGreaterThan.apply(new Double(-30)));
		assertFalse(numberGreaterThan.apply(new Double(30)));
		assertFalse(numberGreaterThan.apply(new Double(10)));
		assertFalse(numberGreaterThan.apply(new Double(60)));
	}

}
