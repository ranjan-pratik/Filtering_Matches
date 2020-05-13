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
import org.pr.project.specifications.AgeSpecification;
import org.pr.project.specifications.CompatibilitySpecification;
import org.pr.project.specifications.HasImageSpecification;
import org.pr.project.specifications.HeightSpecification;
import org.pr.project.specifications.IsFavouriteSpecification;
import org.pr.project.specifications.IsInContactSpecification;
import org.pr.project.strategies.IsExistStrategy;
import org.pr.project.strategies.IsTrueStrategy;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.pr.project.strategies.NumericFilteringStrategy;
import org.pr.project.strategies.PossitiveNumberStrategy;
import org.pr.project.utils.FileReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(classes = { MatchFilterApplication.class })
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class FilterRepoWithStrategyTests {

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

	@Test
	public void test_filetrByAge_useRepository() {
		Criteria query = Criteria.where("age").lte(new Double(40)).gte(new Double(30));
		List<Match> matches = matchFilterRepository.findByCustomCriteria(query);
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(13);
	}

	@Test
	public void test_filetrByAge_useAgeSpecification() {
		List<Match> matches = matchFilterRepository.findAll();
		assertThat(matches.size()).isEqualTo(25);
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(30), new Double(40));
		AgeSpecification specification = new AgeSpecification(fileringStartegy);
		Criteria query = specification.getCriteria();
		matches = matchFilterRepository.findByCustomCriteria(query);
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(13);
	}

	@Test
	public void test_filetrByAge_useInvalidAgeSpecification() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(50), new Double(40));
		AgeSpecification specification = new AgeSpecification(fileringStartegy);
		List<Match> matches = matchFilterRepository.findByCustomCriteria(specification.getCriteria());
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(25);
	}

	@Test
	public void test_filetrByCompatibility_useCompatibilitySpecification() {
		Criteria query = Criteria.where("compatibilityScore").gte(new Double(0.5)).lte(new Double(1.00));
		List<Match> expected = matchFilterRepository.findByCustomCriteria(query);

		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(0.5), new Double(1.00));
		CompatibilitySpecification specification = new CompatibilitySpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		List<Match> matches = matchFilterRepository.findByCustomCriteria(crit);
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(expected.size());
	}

	@Test
	public void test_filetrByCompatibility_useInvertedCompatibilitySpecification() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(50), new Double(40));
		CompatibilitySpecification specification = new CompatibilitySpecification(fileringStartegy);
		List<Match> matches = matchFilterRepository.findByCustomCriteria(specification.getCriteria());
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(25);
	}

	@Test
	public void test_filetrByCompatibility_useInvalidCompatibilitySpecification() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(null, null);
		CompatibilitySpecification specification = new CompatibilitySpecification(fileringStartegy);
		List<Match> matches = matchFilterRepository.findByCustomCriteria(specification.getCriteria());
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(25);
	}

	@Test
	public void test_filetrByHeight_useHeightSpecification() {
		Criteria query = Criteria.where("height").gte(new Double(160.5)).lte(new Double(190));
		List<Match> expected = matchFilterRepository.findByCustomCriteria(query);

		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(160.5), new Double(190));
		HeightSpecification specification = new HeightSpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		List<Match> matches = matchFilterRepository.findByCustomCriteria(crit);
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(expected.size());
	}

	@Test
	public void test_filetrByHeight_useInvertedHeightSpecification() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new Double(50), new Double(40));
		HeightSpecification specification = new HeightSpecification(fileringStartegy);
		List<Match> matches = matchFilterRepository.findByCustomCriteria(specification.getCriteria());
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(25);
	}

	@Test
	public void test_filetrByHeight_useInvalidHeightSpecification() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(null, null);
		HeightSpecification specification = new HeightSpecification(fileringStartegy);
		List<Match> matches = matchFilterRepository.findByCustomCriteria(specification.getCriteria());
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(25);
	}
	


	@Test
	public void test_filetrByHasImage_useHasImageSpecification() {
		Criteria query = Criteria.where("photoURI").ne(null);
		List<Match> expected = matchFilterRepository.findByCustomCriteria(query);
		System.out.println(query.getCriteriaObject().toString()+ " found -" + expected.size());
		IsExistStrategy fileringStartegy = new IsExistStrategy();
		HasImageSpecification specification = new HasImageSpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		List<Match> matches = matchFilterRepository.findByCustomCriteria(crit);
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(expected.size());
	}

	@Test
	public void test_filetrByHeight_useIsInContactSpecification() {
		Criteria query = Criteria.where("contactsExchanged").gt(0d);
		List<Match> expected = matchFilterRepository.findByCustomCriteria(query);
		System.out.println(query.getCriteriaObject().toString()+ " found -" + expected.size());
		NumericFilteringStrategy fileringStartegy = new PossitiveNumberStrategy();
		IsInContactSpecification specification = new IsInContactSpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		List<Match> matches = matchFilterRepository.findByCustomCriteria(crit);
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(expected.size());
	}

	@Test
	public void test_filetrByIsInContact_useInvalidIsInContactSpecification() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(null, null);
		IsInContactSpecification specification = new IsInContactSpecification(fileringStartegy);
		List<Match> matches = matchFilterRepository.findByCustomCriteria(specification.getCriteria());
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(25);
	}

	@Test
	public void test_filetrByIsFavourite_useIsFavouriteSpecification() {
		Criteria query = new Criteria().andOperator(Criteria.where("isFavourite").exists(true),
				Criteria.where("isFavourite").ne(false));
		List<Match> expected = matchFilterRepository.findByCustomCriteria(query);
		System.out.println(query.getCriteriaObject().toString()+ " found -" + expected.size());
		IsTrueStrategy fileringStartegy = new IsTrueStrategy();
		IsFavouriteSpecification specification = new IsFavouriteSpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		List<Match> matches = matchFilterRepository.findByCustomCriteria(crit);
		assertThat(matches).isNotNull();
		assertThat(matches.size()).isEqualTo(expected.size());
	}


	
}
