package org.pr.project.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DistanceClaculationTests {

	@Test
	public void test_distanceIsZero() {
		
		assertEquals(0d, DistanceOnSurfaceUtility.calculateDistanceinKm(12.908, 89.78, 12.908, 89.78), 0);
		
	}
	
	@Test
	public void test_distanceIsZero_onCompleteRevolution() {
		
		assertEquals(0d, DistanceOnSurfaceUtility.calculateDistanceinKm(0.0, 0.0, 0.0, 359.9999), 0.1);
		
	}
}
