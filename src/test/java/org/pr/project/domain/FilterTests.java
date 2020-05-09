package org.pr.project.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.pr.project.domain.Match.Religion;
import org.pr.project.domain.filters.AgeFilter;
import org.pr.project.domain.strategies.IntegerBetweenBoundsStrategy;

public class FilterTests {

	List<Match> matches;
	String imageURI = "http://thecatapi.com/api/images/get?format=src&type=gif";

	@Before
	public void initMatchList() {
		matches = new ArrayList<Match>();

		Match candidate1 = new Match("Candidate1", "some Job", 23, Religion.Christian, imageURI);
		matches.add(candidate1);

		Match candidate2 = new Match("Candidate2", "some other Job", 56, Religion.Agnostic, imageURI);
		matches.add(candidate2);

		Match candidate3 = new Match("Candidate3", "third Job", 29, Religion.Islam, imageURI);
		matches.add(candidate3);

		Match candidate4 = new Match("Candidate4", "some Job", 45, Religion.Christian, "");
		matches.add(candidate4);

		Match candidate5 = new Match("Candidate5", "some other Job", 33, Religion.Athiest, null);
		matches.add(candidate5);

		Match candidate6 = new Match("Candidate6", "third Job", 51, Religion.Islam, imageURI);
		matches.add(candidate6);
	}

	@Test
	public void test_applyAgeFilter() {

		AgeFilter ageFilter = new AgeFilter(new IntegerBetweenBoundsStrategy(20, 30));
		List<Match> filteredMatches = ageFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 2);
		
		ageFilter = new AgeFilter(new IntegerBetweenBoundsStrategy(50, 20));
		filteredMatches = ageFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 0);
		
		ageFilter = new AgeFilter(new IntegerBetweenBoundsStrategy(51, 56));
		AgeFilter anotherAgeFilter = new AgeFilter(new IntegerBetweenBoundsStrategy(45, 51));
		filteredMatches = anotherAgeFilter.runFilter(ageFilter.runFilter(matches));
		assertEquals(filteredMatches.size(), 1);
		
	}

}
