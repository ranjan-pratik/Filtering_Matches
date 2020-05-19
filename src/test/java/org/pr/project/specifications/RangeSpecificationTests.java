package org.pr.project.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Test;
import org.pr.project.strategies.DistanceFilteringStrategy;
import org.pr.project.strategies.DistanceWithinRangeStrategy;

public class RangeSpecificationTests {

	@Test(expected = NotImplementedException.class)
	public void test_IsInRangeSpecification_getCriteria_throwsException() {
		DistanceFilteringStrategy distanceRangeBetweenBoundsStrategy = new DistanceWithinRangeStrategy(
				0, 0, null, null);
		RangeInKmSpecification specification = new RangeInKmSpecification(distanceRangeBetweenBoundsStrategy);
		specification.getCriteria();
		
	}
	
	@Test
	public void test_IsInRangeSpecification_invalidBounds() {
		DistanceFilteringStrategy distanceRangeBetweenBoundsStrategy = new DistanceWithinRangeStrategy(
				0, 0, null, null);
		RangeInKmSpecification specification = new RangeInKmSpecification(distanceRangeBetweenBoundsStrategy);
		assertThat(specification.getNearQuery()).isNull();
		
	}
	
	@Test
	public void test_IsInRangeSpecification_lowerBound() {
		DistanceFilteringStrategy distanceRangeBetweenBoundsStrategy = new DistanceWithinRangeStrategy(
				0, 0, 0d, null);
		RangeInKmSpecification specification = new RangeInKmSpecification(distanceRangeBetweenBoundsStrategy);
		assertThat(specification.getNearQuery()).isNotNull();
		
	}
	
	@Test
	public void test_IsInRangeSpecification_upperBound() {
		DistanceFilteringStrategy distanceRangeBetweenBoundsStrategy = new DistanceWithinRangeStrategy(
				0, 0, null, 0d);
		RangeInKmSpecification specification = new RangeInKmSpecification(distanceRangeBetweenBoundsStrategy);
		assertThat(specification.getNearQuery()).isNotNull();
		
	}
	
	@Test
	public void test_IsInRangeSpecification() {
		DistanceFilteringStrategy distanceRangeBetweenBoundsStrategy = new DistanceWithinRangeStrategy(
				0, 0, 0d, 10d);
		RangeInKmSpecification specification = new RangeInKmSpecification(distanceRangeBetweenBoundsStrategy);
		assertThat(specification.getNearQuery()).isNotNull();
		
	}
}
