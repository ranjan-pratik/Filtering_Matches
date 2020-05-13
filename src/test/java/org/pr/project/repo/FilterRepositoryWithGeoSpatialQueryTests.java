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
import org.pr.project.specifications.InKMRangeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(classes = { MatchFilterApplication.class })
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
			Match candidate1 = new Match("Candidate1", "some Job", 23, Religion.Christian, "some URI", 163d, 15d, 5,
					true, new City("someCity", 51.509865, -0.118092));
			matches.add(candidate1);

			Match candidate2 = new Match("Candidate2", "some other Job", 56, Religion.Agnostic, "", 155d, 65d, 0, false,
					new City("someOtherCity", 23.509865, 0.158092));
			matches.add(candidate2);

			Match candidate3 = new Match("Candidate3", "third Job", 29, Religion.Islam, "", 133d, 95d, 0, null,
					new City("otherCity", 51.500065, -0.100092));
			matches.add(candidate3);

			Match candidate4 = new Match("Candidate4", "some Job", 45, Religion.Christian, "", 173d, 95d, null, false,
					new City("thisCity", 55.509865, -0.198092));
			matches.add(candidate4);
			matchFilterRepository.saveAll(matches);
			mongoTemplate.indexOps(Match.class)
					.ensureIndex(new GeospatialIndex("city.position").typed(GeoSpatialIndexType.GEO_2DSPHERE));
			dataCreatedFlag = true;
		}
	}

	@Test
	public void test_dataLoad_geoSpatialQuerySameCity() {
		Point p = new Point(51.509865, -0.118092);
		Distance d = new Distance(1, Metrics.KILOMETERS);
		List<Match> matches = matchFilterRepository.findByCityPositionNear(p, d);

		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(1);
		assertThat(matches.get(0).getDisplayName()).isEqualTo("Candidate1");
		assertThat(matches.get(0).getCity().getName()).isEqualTo("someCity");
		assertThat(matches.get(0).getCity().getLat()).isEqualTo(51.509865);
		assertThat(matches.get(0).getCity().getPosition()[1]).isEqualTo(-0.118092);
	}

	@Test
	public void test_dataLoad_geoSpatialQuery_BetweenRange() {
		matchFilterRepository.saveAll(matches);

		Point thisCityPoint = new Point(55.509865, -0.198092);

		Distance d = new Distance(500, Metrics.KILOMETERS);
		List<Match> expected = matchFilterRepository.findByCityPositionNear(thisCityPoint, d);

		Distance d2 = new Distance(200, Metrics.KILOMETERS);
		List<Match> exclusions = matchFilterRepository.findByCityPositionNear(thisCityPoint, d2);

		InKMRangeSpecification specification = new InKMRangeSpecification();
		GeoResults<Match> matches = matchFilterRepository
				.findByCustomGeoRangeWithin(specification.getNearQuery("location", thisCityPoint, 200d, 500d));

		assertThat(matches).isNotNull();
		assertThat(matches.getContent().size()).isEqualTo(expected.size() - exclusions.size());
	}
}
