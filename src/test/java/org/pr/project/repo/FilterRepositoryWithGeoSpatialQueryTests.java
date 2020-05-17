package org.pr.project.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pr.project.MatchFilterApplication;
import org.pr.project.domain.City;
import org.pr.project.domain.Match;
import org.pr.project.domain.Match.Religion;
import org.pr.project.filters.AgeFilter;
import org.pr.project.specifications.RangeInKmSpecification;
import org.pr.project.strategies.DistanceFilteringStrategy;
import org.pr.project.strategies.DistanceWithinRangeStrategy;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(classes = {MatchFilterApplication.class})
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class FilterRepositoryWithGeoSpatialQueryTests {

	@Autowired
	MatchFilterRepository matchFilterRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	private static boolean dataCreatedFlag = false;
	List<Match> matches = new ArrayList<Match>();

	@Before
	public void initDB() throws JsonMappingException, JsonProcessingException {
		if (!dataCreatedFlag) {
			Match candidate1 = new Match("Candidate1", "some Job", 23,
					Religion.Christian, "some URI", 163d, 15d, 5, true,
					new City("someCity", 51.509865, -0.118092));
			matches.add(candidate1);

			Match candidate2 = new Match("Candidate2", "some other Job", 56,
					Religion.Agnostic, "", 155d, 65d, 0, false,
					new City("someOtherCity", 23.509865, 0.158092));
			matches.add(candidate2);

			Match candidate3 = new Match("Candidate3", "third Job", 29,
					Religion.Islam, "", 133d, 95d, 0, null,
					new City("otherCity", 51.500065, -0.100092));
			matches.add(candidate3);

			Match candidate4 = new Match("Candidate4", "some Job", 45,
					Religion.Christian, "", 173d, 95d, null, false,
					new City("thisCity", 55.509865, -0.198092));
			matches.add(candidate4);
			matchFilterRepository.saveAll(matches);
			mongoTemplate.indexOps(Match.class)
					.ensureIndex(new GeospatialIndex("city.position")
							.typed(GeoSpatialIndexType.GEO_2DSPHERE));
			dataCreatedFlag = true;
		}
	}

	@Test
	public void test_dataLoad_geoSpatialQuerySameCity() {
		Point p = new Point(51.509865, -0.118092);
		Distance d = new Distance(1, Metrics.KILOMETERS);
		List<Match> matches = matchFilterRepository.findByCityPositionNear(p,
				d);

		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(1);
		assertThat(matches.get(0).getDisplayName()).isEqualTo("Candidate1");
		assertThat(matches.get(0).getCity().getName()).isEqualTo("someCity");
		assertThat(matches.get(0).getCity().getLat()).isEqualTo(51.509865);
		assertThat(matches.get(0).getCity().getPosition()[1])
				.isEqualTo(-0.118092);
	}

	@Test
	public void test_dataLoad_geoSpatialQuery_BetweenRange() {

		Point thisCityPoint = new Point(55.509865, -0.198092);

		Distance d = new Distance(500, Metrics.KILOMETERS);
		List<Match> expected = matchFilterRepository
				.findByCityPositionNear(thisCityPoint, d);

		Distance d2 = new Distance(200, Metrics.KILOMETERS);
		List<Match> exclusions = matchFilterRepository
				.findByCityPositionNear(thisCityPoint, d2);

		DistanceFilteringStrategy distanceInRangeStrategy = new DistanceWithinRangeStrategy(
				55.509865, -0.198092, 200d, 500d);
		RangeInKmSpecification specification = new RangeInKmSpecification(
				distanceInRangeStrategy);
		GeoResults<Match> matches = matchFilterRepository
				.findByCustomGeoRangeWithin(specification.getNearQuery());

		assertThat(matches).isNotNull();
		assertThat(matches.getContent().size())
				.isEqualTo(expected.size() - exclusions.size());
	}

	@Test
	public void test_dataLoad_geoSpatialQuery_BetweenRangeInverted_shouldFindNone() {

		DistanceFilteringStrategy distanceInRangeStrategy = new DistanceWithinRangeStrategy(
				55.509865, -0.198092, 500d, 200d);
		RangeInKmSpecification specification = new RangeInKmSpecification(
				distanceInRangeStrategy);
		GeoResults<Match> matches = matchFilterRepository
				.findByCustomGeoRangeWithin(specification.getNearQuery());

		assertThat(matches).isNotNull();
		assertThat(matches.getContent().size()).isEqualTo(0);
	}

	@Test
	public void test_dataLoad_geoSpatialQuery_BetweenRange_0To1Km_shouldFindItself() {

		DistanceFilteringStrategy distanceInRangeStrategy = new DistanceWithinRangeStrategy(
				55.509865, -0.198092, 0d, 1d);
		RangeInKmSpecification specification = new RangeInKmSpecification(
				distanceInRangeStrategy);
		GeoResults<Match> matches = matchFilterRepository
				.findByCustomGeoRangeWithin(specification.getNearQuery());

		assertThat(matches).isNotNull();
		assertThat(matches.getContent().size()).isEqualTo(1);
		assertThat(matches.getContent().get(0).getContent().getDisplayName())
				.isEqualTo("Candidate4");
	}

	@Test
	public void test_dataLoad_geoSpatialQuery_BetweenRange_0To1Km_withOtherQuery_sameResult() {
		DistanceFilteringStrategy distanceInRangeStrategy = new DistanceWithinRangeStrategy(
				55.509865, -0.198092, 200d, 500d);
		RangeInKmSpecification specification = new RangeInKmSpecification(
				distanceInRangeStrategy);

		NearQuery nearQuery = specification.getNearQuery();

		GeoResults<Match> distanceOnlyMatches = matchFilterRepository
				.findByCustomGeoRangeWithin(nearQuery);

		AgeFilter ageFilter = new AgeFilter(
				new NumberBetweenBoundsStrategy(23d, 56d));
		nearQuery.query(new Query(ageFilter.getSpecification().getCriteria()));

		GeoResults<Match> distanceAndAgematches = matchFilterRepository
				.findByCustomGeoRangeWithin(nearQuery);

		assertThat(distanceAndAgematches.getContent().size())
				.isEqualTo(distanceOnlyMatches.getContent().size());

	}

	@Test
	public void test_dataLoad_geoSpatialQuery_BetweenRange_0To1Km_withOtherQuery_differentResult() {
		DistanceFilteringStrategy distanceInRangeStrategy = new DistanceWithinRangeStrategy(
				55.509865, -0.198092, 200d, 500d);
		RangeInKmSpecification specification = new RangeInKmSpecification(
				distanceInRangeStrategy);

		NearQuery nearQuery = specification.getNearQuery();

		GeoResults<Match> distanceOnlyMatches = matchFilterRepository
				.findByCustomGeoRangeWithin(nearQuery);

		AgeFilter ageFilter = new AgeFilter(
				new NumberBetweenBoundsStrategy(33d, 56d));
		nearQuery.query(new Query(ageFilter.getSpecification().getCriteria()));

		GeoResults<Match> distanceAndAgematches = matchFilterRepository
				.findByCustomGeoRangeWithin(nearQuery);

		assertThat(distanceAndAgematches.getContent().size())
				.isNotEqualTo(distanceOnlyMatches.getContent().size());

	}
}
