package org.pr.project.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RestCallerUtilityTests {

	@Test
	public void test_validURL() {
		assertTrue(RestCallerUtility.validateURL("http://thecatapi.com/api/images/get?format=src&type=gif"));
	}
	
	@Test
	public void test_invalidURL() {
		assertFalse(RestCallerUtility.validateURL(""));
		assertFalse(RestCallerUtility.validateURL("http://"));
	}

}
