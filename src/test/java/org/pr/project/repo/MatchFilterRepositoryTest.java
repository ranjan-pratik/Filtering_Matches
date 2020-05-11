package org.pr.project.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pr.project.MatchFilterApplication;
import org.pr.project.domain.FilteredListVO;
import org.pr.project.domain.Match;
import org.pr.project.utils.FileReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(classes = { MatchFilterApplication.class })
public class MatchFilterRepositoryTest {

	@Autowired
	MatchFilterRepository matchFilterRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Value("${json.data.file.path}")
	String filePath;

	private static boolean dataCreatedFlag = false;

	@Before
	public void initDB() throws JsonMappingException, JsonProcessingException {
		if (!dataCreatedFlag) {
			String content = FileReaderUtil.read(Paths.get(filePath));
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			FilteredListVO unfilteredList = mapper.readValue(content, FilteredListVO.class);
			assertThat(unfilteredList).isNotNull();
			matchFilterRepository.saveAll(unfilteredList.getMatches());
			dataCreatedFlag = true;
		}
	}

	@Test
	public void test_unfilteredDataLoad() {
		List<Match> matches = matchFilterRepository.findAll();
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isGreaterThan(0);
		assertThat(matches.size()).isEqualTo(25);
	}
	
	@Test
	public void test_filetrByName_useMongoTemplate() {
		Query query = new Query();
		query.addCriteria(Criteria.where("displayName").in("Caroline", "Sharon"));
		List<Match> matches = mongoTemplate.find(query, Match.class);
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(3);
	}
	
	@Test
	public void test_filetrByName_useRepository() {
		Criteria query = Criteria.where("displayName").in("Caroline", "Sharon");
		List<Match> matches = matchFilterRepository.findByCustomCriteria(query);
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(3);
	}
}
