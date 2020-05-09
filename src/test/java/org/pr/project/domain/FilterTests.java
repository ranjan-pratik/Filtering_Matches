package org.pr.project.domain;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.pr.project.domain.Match.Religion;
import org.pr.project.domain.filters.AgeFilter;
import org.pr.project.domain.filters.CompatibilityFilter;
import org.pr.project.domain.filters.HeightFilter;
import org.pr.project.domain.strategies.NumberBetweenBoundsStrategy;

public class FilterTests {

	List<Match> matches;
	String imageURI = "http://thecatapi.com/api/images/get?format=src&type=gif";

	@Before
	public void initMatchList() {
		matches = new ArrayList<Match>();

		Match candidate1 = new Match("Candidate1", "some Job", 23, Religion.Christian, imageURI, 163d, 15d);
		matches.add(candidate1);

		Match candidate2 = new Match("Candidate2", "some other Job", 56, Religion.Agnostic, imageURI, 155d, 65d);
		matches.add(candidate2);

		Match candidate3 = new Match("Candidate3", "third Job", 29, Religion.Islam, imageURI, 133d, 95d);
		matches.add(candidate3);

		Match candidate4 = new Match("Candidate4", "some Job", 45, Religion.Christian, "", 173d, 95d);
		matches.add(candidate4);

		Match candidate5 = new Match("Candidate5", "some other Job", 33, Religion.Athiest, null, 143d, 67d);
		matches.add(candidate5);

		Match candidate6 = new Match("Candidate6", "third Job", 51, Religion.Islam, imageURI, 169d, 56d);
		matches.add(candidate6);
	}

	@Test
	public void test_applyAgeFilter() {

		AgeFilter ageFilter = new AgeFilter(new NumberBetweenBoundsStrategy(new BigDecimal(20), new BigDecimal(30)));
		List<Match> filteredMatches = ageFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 2);

		ageFilter = new AgeFilter(new NumberBetweenBoundsStrategy(new BigDecimal(50), new BigDecimal(20)));
		filteredMatches = ageFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 0);

		ageFilter = new AgeFilter(new NumberBetweenBoundsStrategy(new BigDecimal(51), new BigDecimal(56)));
		AgeFilter anotherAgeFilter = new AgeFilter(
				new NumberBetweenBoundsStrategy(new BigDecimal(45), new BigDecimal(51)));
		filteredMatches = anotherAgeFilter.runFilter(ageFilter.runFilter(matches));
		assertEquals(filteredMatches.size(), 1);

	}

	@Test
	public void test_applyHeightFilter() {

		HeightFilter heightFilter = new HeightFilter(
				new NumberBetweenBoundsStrategy(new BigDecimal(140), new BigDecimal(200)));
		List<Match> filteredMatches = heightFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 5);

		heightFilter = new HeightFilter(new NumberBetweenBoundsStrategy(new BigDecimal(500), new BigDecimal(20)));
		filteredMatches = heightFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 0);

		heightFilter = new HeightFilter(new NumberBetweenBoundsStrategy(new BigDecimal(100), new BigDecimal(163)));
		HeightFilter anotherHeightFilter = new HeightFilter(
				new NumberBetweenBoundsStrategy(new BigDecimal(143), new BigDecimal(200)));
		filteredMatches = anotherHeightFilter.runFilter(heightFilter.runFilter(matches));
		assertEquals(filteredMatches.size(), 3);

	}

	@Test
	public void test_applyCompatibilityFilter() {

		CompatibilityFilter compatibilityFilter = new CompatibilityFilter(
				new NumberBetweenBoundsStrategy(new BigDecimal(10), new BigDecimal(100)));
		List<Match> filteredMatches = compatibilityFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 6);

		compatibilityFilter = new CompatibilityFilter(
				new NumberBetweenBoundsStrategy(new BigDecimal(30), new BigDecimal(10)));
		filteredMatches = compatibilityFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 0);

		compatibilityFilter = new CompatibilityFilter(
				new NumberBetweenBoundsStrategy(new BigDecimal(25), new BigDecimal(65)));
		CompatibilityFilter anotherCompatibilityFilter = new CompatibilityFilter(
				new NumberBetweenBoundsStrategy(new BigDecimal(65), new BigDecimal(75)));
		filteredMatches = anotherCompatibilityFilter.runFilter(compatibilityFilter.runFilter(matches));
		assertEquals(filteredMatches.size(), 1);

	}

}
