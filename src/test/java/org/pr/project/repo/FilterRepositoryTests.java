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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(classes = { MatchFilterApplication.class })
public class FilterRepositoryTests {

	@Autowired
	MatchFilterRepository matchFilterRepository;

	@Autowired
	MongoTemplate mongoTemplate;
	
	private static boolean dataCreatedFlag = false;
	List<Match> matches = new ArrayList<Match>();

	@Before
	public void initDB() throws JsonMappingException, JsonProcessingException {
		if (!dataCreatedFlag) {
			Match candidate1 = new Match("Candidate1", "some Job", 23, Religion.Christian, "some URI", 163d, 15d, 5, true,
					new City("someCity", 51.509865, -0.118092));
			matches.add(candidate1);
			matchFilterRepository.saveAll(matches);
			mongoTemplate.indexOps(Match.class).ensureIndex(new GeospatialIndex("city.position"));
			dataCreatedFlag = true;
		}
	}

	@Test
	public void test_unfilteredDataLoad() {
		List<Match> matches = matchFilterRepository.findAll();
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(1);
		assertThat(matches.get(0).getDisplayName()).isEqualTo("Candidate1");
		assertThat(matches.get(0).getCity().getName()).isEqualTo("someCity");
		assertThat(matches.get(0).getCity().getLat()).isEqualTo(51.509865);
		assertThat(matches.get(0).getCity().getPosition()[1]).isEqualTo(-0.118092);
	}
	
	@Test
	public void test_dataLoad_qualifyingQuery() {
		List<Match> matches = matchFilterRepository.findByCustomCriteria(Criteria.where("displayName").is("Candidate1"));
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(1);
		assertThat(matches.get(0).getDisplayName()).isEqualTo("Candidate1");
		assertThat(matches.get(0).getCity().getName()).isEqualTo("someCity");
		assertThat(matches.get(0).getCity().getLat()).isEqualTo(51.509865);
		assertThat(matches.get(0).getCity().getPosition()[1]).isEqualTo(-0.118092);
	}
	
	@Test
	public void test_dataLoad_non_qualifyingQuery() {
		List<Match> matches = matchFilterRepository.findByCustomCriteria(Criteria.where("religion").is("Hindu"));
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(0);
	}
	
	@Test
	public void test_dataLoad_nestedQuery() {
		List<Match> matches = matchFilterRepository.findByCustomCriteria(Criteria.where("city.name").is("someCity"));
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(1);
		assertThat(matches.get(0).getDisplayName()).isEqualTo("Candidate1");
		assertThat(matches.get(0).getCity().getName()).isEqualTo("someCity");
		assertThat(matches.get(0).getCity().getLat()).isEqualTo(51.509865);
		assertThat(matches.get(0).getCity().getPosition()[1]).isEqualTo(-0.118092);
	}
	
	@Test
	public void test_dataLoad_geoSpatialQuery() {
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

}
