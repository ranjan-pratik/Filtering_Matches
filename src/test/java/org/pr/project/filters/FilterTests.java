package org.pr.project.filters;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.pr.project.domain.City;
import org.pr.project.domain.Match;
import org.pr.project.domain.Match.Religion;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;

public class FilterTests {

	List<Match> matches;
	String imageURI = "http://thecatapi.com/api/images/get?format=src&type=gif";

	@Before
	public void initMatchList() {
		matches = new ArrayList<Match>();

		Match candidate1 = new Match("Candidate1", "some Job", 23, Religion.Christian, imageURI, 163d, 15d, 5, true,
				new City("someCity", 51.509865, -0.118092));
		matches.add(candidate1);

		Match candidate2 = new Match("Candidate2", "some other Job", 56, Religion.Agnostic, imageURI, 155d, 65d, 0,
				false, new City("someOtherCity", 23.509865, 0.158092));
		matches.add(candidate2);

		Match candidate3 = new Match("Candidate3", "third Job", 29, Religion.Islam, imageURI, 133d, 95d, 0, null,
				new City("otherCity", 51.500065, -0.100092));
		matches.add(candidate3);

		Match candidate4 = new Match("Candidate4", "some Job", 45, Religion.Christian, "", 173d, 95d, null, false,
				new City("thisCity", 55.509865, -0.198092));
		matches.add(candidate4);

		Match candidate5 = new Match("Candidate5", "some other Job", 33, Religion.Atheist, null, 143d, 67d, 2, false,
				new City("thatCity", 11.509865, -53.538092));
		matches.add(candidate5);

		Match candidate6 = new Match("Candidate6", "third Job", 51, Religion.Islam, imageURI, 169d, 56d, 3, true,
				new City("someCity", 51.509865, -0.118092));
		matches.add(candidate6);
	}

	@Test
	public void test_applyAgeFilter() {

		AgeFilter ageFilter = new AgeFilter(new NumberBetweenBoundsStrategy(new Double(20), new Double(30)));
		List<Match> filteredMatches = ageFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 2);

		ageFilter = new AgeFilter(new NumberBetweenBoundsStrategy(new Double(50), new Double(20)));
		filteredMatches = ageFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 0);

		ageFilter = new AgeFilter(new NumberBetweenBoundsStrategy(new Double(51), new Double(56)));
		AgeFilter anotherAgeFilter = new AgeFilter(
				new NumberBetweenBoundsStrategy(new Double(45), new Double(51)));
		filteredMatches = anotherAgeFilter.runFilter(ageFilter.runFilter(matches));
		assertEquals(filteredMatches.size(), 1);

	}

	@Test
	public void test_applyHeightFilter() {

		HeightFilter heightFilter = new HeightFilter(
				new NumberBetweenBoundsStrategy(new Double(140), new Double(200)));
		List<Match> filteredMatches = heightFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 5);

		heightFilter = new HeightFilter(new NumberBetweenBoundsStrategy(new Double(500), new Double(20)));
		filteredMatches = heightFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 0);

		heightFilter = new HeightFilter(new NumberBetweenBoundsStrategy(new Double(100), new Double(163)));
		HeightFilter anotherHeightFilter = new HeightFilter(
				new NumberBetweenBoundsStrategy(new Double(143), new Double(200)));
		filteredMatches = anotherHeightFilter.runFilter(heightFilter.runFilter(matches));
		assertEquals(filteredMatches.size(), 3);

	}

	@Test
	public void test_applyCompatibilityFilter() {

		CompatibilityFilter compatibilityFilter = new CompatibilityFilter(
				new NumberBetweenBoundsStrategy(new Double(10), new Double(100)));
		List<Match> filteredMatches = compatibilityFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 6);

		compatibilityFilter = new CompatibilityFilter(
				new NumberBetweenBoundsStrategy(new Double(30), new Double(10)));
		filteredMatches = compatibilityFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 0);

		compatibilityFilter = new CompatibilityFilter(
				new NumberBetweenBoundsStrategy(new Double(25), new Double(65)));
		CompatibilityFilter anotherCompatibilityFilter = new CompatibilityFilter(
				new NumberBetweenBoundsStrategy(new Double(65), new Double(75)));
		filteredMatches = anotherCompatibilityFilter.runFilter(compatibilityFilter.runFilter(matches));
		assertEquals(filteredMatches.size(), 1);

	}

	@Test
	public void test_applyHasImageFilter() {

		HasImageFilter hasImageFilter = new HasImageFilter();
		List<Match> filteredMatches = hasImageFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 4);

		hasImageFilter = new HasImageFilter();
		HasImageFilter anotherHasImageFilter = new HasImageFilter();
		filteredMatches = anotherHasImageFilter.runFilter(hasImageFilter.runFilter(matches));
		assertEquals(filteredMatches.size(), 4);

	}

	@Test
	public void test_applyIsInContactFilter() {

		IsInContactFilter isInContactFilter = new IsInContactFilter();
		List<Match> filteredMatches = isInContactFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 3);
	}

	@Test
	public void test_applyIsFavouriteFilter() {

		IsFavouriteFilter isFavouriteFilter = new IsFavouriteFilter();
		List<Match> filteredMatches = isFavouriteFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 2);
	}

	@Test
	public void test_applyDistanceFilter() {

		DistanceInKmFilter distanceInKMFilter = new DistanceInKmFilter(
				new NumberBetweenBoundsStrategy(new Double(20), new Double(30)),
				new City("someCity", 51.509865, -0.118092));
		List<Match> filteredMatches = distanceInKMFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 0);

		distanceInKMFilter = new DistanceInKmFilter(
				new NumberBetweenBoundsStrategy(new Double(0), new Double(1.6551639194378014)),
				new City("otherCity", 51.500065, -0.100092));
		filteredMatches = distanceInKMFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 3);

		distanceInKMFilter = new DistanceInKmFilter(
				new NumberBetweenBoundsStrategy(new Double(50), new Double(20)),
				new City("otherCity", 51.500065, -0.100092));
		filteredMatches = distanceInKMFilter.runFilter(matches);
		assertEquals(filteredMatches.size(), 0);

		distanceInKMFilter = new DistanceInKmFilter(
				new NumberBetweenBoundsStrategy(new Double(51), new Double(56)),
				new City("otherCity", 51.500065, -0.100092));
		DistanceInKmFilter anotherDistanceInKMFilter = new DistanceInKmFilter(
				new NumberBetweenBoundsStrategy(new Double(45), new Double(51)),
				new City("otherCity", 51.500065, -0.100092));
		filteredMatches = anotherDistanceInKMFilter.runFilter(distanceInKMFilter.runFilter(matches));
		assertEquals(filteredMatches.size(), 0);

	}

	@Test
	public void test_combinationFilters() {

		HasImageFilter hasImageFilter = new HasImageFilter();

		NotFilter notHasImageFilter = new NotFilter(hasImageFilter);
		assertEquals(notHasImageFilter.runFilter(matches).size(), 2);

		IsInContactFilter isInContactFilter = new IsInContactFilter();

		AndFilter hasImageAndInContact = new AndFilter(hasImageFilter, isInContactFilter);
		assertEquals(hasImageAndInContact.runFilter(matches).size(), 2);
	}

}
