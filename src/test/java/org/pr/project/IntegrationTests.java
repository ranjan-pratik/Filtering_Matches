package org.pr.project;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pr.project.domain.FilteredListVO;
import org.pr.project.domain.FiltersVO;
import org.pr.project.filters.AbstractFilter;
import org.pr.project.filters.AgeFilter;
import org.pr.project.filters.CompatibilityFilter;
import org.pr.project.filters.HasImageFilter;
import org.pr.project.filters.HeightFilter;
import org.pr.project.filters.IsFavouriteFilter;
import org.pr.project.filters.IsInContactFilter;
import org.pr.project.repo.MatchFilterRepository;
import org.pr.project.strategies.IsTrueStrategy;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.pr.project.strategies.NumericFilteringStrategy;
import org.pr.project.strategies.PositiveNumberStrategy;
import org.pr.project.strategies.StringIsNotNullStrategy;
import org.pr.project.utils.FileReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {MatchFilterApplication.class})
public class IntegrationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	MatchFilterRepository matchFilterRepository;

	@Autowired
	@Qualifier("customObjectMapper")
	ObjectMapper mapper;

	@Value("${json.data.file.path}")
	String filePath;

	private static boolean dataCreatedFlag = false;

	@Before
	public void initDB() throws JsonMappingException, JsonProcessingException {
		if (!dataCreatedFlag) {
			String content = FileReaderUtil.read(Paths.get(filePath));
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					true);
			FilteredListVO unfilteredList = mapper.readValue(content,
					FilteredListVO.class);
			assertThat(unfilteredList).isNotNull();
			matchFilterRepository.saveAll(unfilteredList.getMatches());
			dataCreatedFlag = true;
		}
	}

	@Test
	public void test_getDefaultList() {
		ResponseEntity<FilteredListVO> response = restTemplate
				.getForEntity("/filters/allMatches", FilteredListVO.class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		FilteredListVO unfilteredList = response.getBody();

		assertThat(unfilteredList.getMatches()).isNotNull();
		assertThat(unfilteredList.getMatches().size()).isGreaterThan(0);
		assertThat(unfilteredList.getMatches().get(0).getDisplayName())
				.isNotNull();
		assertThat(unfilteredList.getMatches().get(0).getCity().getName())
				.isNotNull();
		assertThat(unfilteredList.getMatches().get(0).getCity().getLat())
				.isNotNull();
		assertThat(unfilteredList.getMatches().get(0).getCity().getPosition())
				.isNotNull();
	}

	@Test
	public void test_getFilteredList() throws Exception {

		final AgeFilter ageFilter = new AgeFilter(
				new NumberBetweenBoundsStrategy(20d, 60d));
		final HeightFilter heightFilter = new HeightFilter(
				new NumberBetweenBoundsStrategy(new Double(140),
						new Double(200)));
		CompatibilityFilter compatibilityFilter = new CompatibilityFilter(
				new NumberBetweenBoundsStrategy(new Double(0.3),
						new Double(0.99)));
		IsTrueStrategy isTrueStrategy = new IsTrueStrategy();
		IsFavouriteFilter isFavouriteFilter = new IsFavouriteFilter(
				isTrueStrategy);
		NumericFilteringStrategy possitiveNumberStrategy = new PositiveNumberStrategy();
		IsInContactFilter contactFilter = new IsInContactFilter(
				possitiveNumberStrategy);
		StringIsNotNullStrategy imageExistsStrategy = new StringIsNotNullStrategy();
		HasImageFilter hasImageFilter = new HasImageFilter(imageExistsStrategy);

		final List<AbstractFilter> appliedFilters = new ArrayList<>();
		appliedFilters.add(ageFilter);
		appliedFilters.add(heightFilter);
		appliedFilters.add(compatibilityFilter);
		appliedFilters.add(isFavouriteFilter);
		appliedFilters.add(contactFilter);
		appliedFilters.add(hasImageFilter);

		final FiltersVO manyFilterOnlyVO = new FiltersVO();
		manyFilterOnlyVO.setAppliedFilters(appliedFilters);

		final ObjectWriter ow = mapper.writer();
		final String requestJson = ow.writeValueAsString(manyFilterOnlyVO);

		System.out.println(requestJson);

		FilteredListVO response = restTemplate.postForObject(
				"/filters/filteredMatches", manyFilterOnlyVO,
				FilteredListVO.class);

		assertThat(response).isNotNull();
		FilteredListVO filteredList = response;

		mapper.readValue(requestJson, FiltersVO.class);

		assertThat(filteredList.getMatches()).isNotNull();
		System.out.println("Filtered Size - " + filteredList.getMatches());
		assertThat(filteredList.getMatches().size()).isGreaterThan(0);
		assertThat(filteredList.getMatches().get(0).getDisplayName())
				.isNotNull();
		assertThat(filteredList.getMatches().get(0).getCity().getName())
				.isNotNull();
		assertThat(filteredList.getMatches().get(0).getCity().getLat())
				.isNotNull();
		assertThat(filteredList.getMatches().get(0).getCity().getPosition())
				.isNotNull();
	}

	private String getContent() {
		String content = "[{\"field\":\"photo\",\"strategy\":{\"name\":\"HasImageStrategy\",\"type\":\"StringStrategy\",\"hasImageValue\":true}},{\"field\":\"favourite\",\"strategy\":{\"name\":\"IsFavouriteStrategy\",\"type\":\"BooleanStrategy\",\"isFavourite\":true}},{\"field\":\"compatibility\",\"strategy\":{\"name\":\"NumberBetweenBoundsStrategy\",\"type\":\"NumericStrategy\",\"lowerBound\":1,\"upperBound\":99}},{\"field\":\"height\",\"strategy\":{\"name\":\"NumberBetweenBoundsStrategy\",\"type\":\"NumericStrategy\",\"lowerBound\":18,\"upperBound\":95}},{\"field\":\"height\",\"strategy\":{\"name\":\"NumberBetweenBoundsStrategy\",\"type\":\"NumericStrategy\",\"lowerBound\":135,\"upperBound\":210}},{\"field\":\"height\",\"strategy\":{\"name\":\"NumberBetweenBoundsStrategy\",\"type\":\"NumericStrategy\",\"lowerBound\":190,\"upperBound\":300}}]\r\n"
				+ "";
		return content;
	}

}
