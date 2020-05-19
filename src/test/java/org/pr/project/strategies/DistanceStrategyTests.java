package org.pr.project.strategies;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.pr.project.domain.City;

public class DistanceStrategyTests {
	@Test
	public void test_applyDistanceWithinRangeStrategy() {
		City thisCity = new City("some city", new Double(50), new Double(60));
		DistanceFilteringStrategy distanceInRange = new DistanceWithinRangeStrategy(
				new Double(50), new Double(60), new Double(0), new Double(1));
		assertTrue(distanceInRange.apply(thisCity));
		assertFalse(distanceInRange.apply(null));
		assertFalse(distanceInRange.apply(
				new City("some other city", new Double(50), new Double(98))));
		assertFalse(distanceInRange.apply(
				new City("some diff city", new Double(50), new Double(98))));
		assertFalse(distanceInRange.apply(
				new City("some city there", new Double(89), new Double(60))));
		assertFalse(distanceInRange.apply(
				new City("some city here", new Double(67), new Double(60))));
	}

	@Test
	public void test_applyDistanceWithinRangeStrategy_invalid() {
		DistanceFilteringStrategy distanceInRange = new DistanceWithinRangeStrategy(
				50d, 60d, null, null);
		assertFalse(distanceInRange
				.apply(new City("some city", new Double(50), new Double(60))));
		assertFalse(distanceInRange.apply(null));
		assertFalse(distanceInRange.apply(
				new City("some other city", new Double(50), new Double(98))));
		assertFalse(distanceInRange.apply(
				new City("some diff city", new Double(50), new Double(98))));
		assertFalse(distanceInRange.apply(
				new City("some city there", new Double(89), new Double(60))));
		assertFalse(distanceInRange.apply(
				new City("some city here", new Double(67), new Double(60))));
	}
	
	@Test
	public void test_applyDistanceWithinRangeStrategy_LowerBound() {
		DistanceFilteringStrategy distanceInRange = new DistanceWithinRangeStrategy(
				50d, 60d, 0d, null);
		assertFalse(distanceInRange
				.apply(new City("some city", new Double(50), new Double(60))));
		assertFalse(distanceInRange.apply(null));
		assertFalse(distanceInRange.apply(
				new City("some other city", new Double(50), new Double(98))));
		assertFalse(distanceInRange.apply(
				new City("some diff city", new Double(50), new Double(98))));
		assertFalse(distanceInRange.apply(
				new City("some city there", new Double(89), new Double(60))));
		assertFalse(distanceInRange.apply(
				new City("some city here", new Double(67), new Double(60))));
	}
	
	@Test
	public void test_applyDistanceWithinRangeStrategy_UpperBound() {
		City thisCity = new City("some city", new Double(50), new Double(60));
		DistanceFilteringStrategy distanceInRange = new DistanceWithinRangeStrategy(
				50d, 60d, null, 1d);
		assertFalse(distanceInRange.apply(thisCity));
		assertFalse(distanceInRange
				.apply(new City("some city", new Double(50), new Double(60))));
		assertFalse(distanceInRange.apply(null));
		assertFalse(distanceInRange.apply(
				new City("some other city", new Double(50), new Double(98))));
		assertFalse(distanceInRange.apply(
				new City("some diff city", new Double(50), new Double(98))));
		assertFalse(distanceInRange.apply(
				new City("some city there", new Double(89), new Double(60))));
		assertFalse(distanceInRange.apply(
				new City("some city here", new Double(67), new Double(60))));
	}
}
