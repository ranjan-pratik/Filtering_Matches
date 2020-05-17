package org.pr.project;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pr.project.domain.City;
import org.pr.project.domain.FilteredListVO;
import org.pr.project.domain.FiltersVO;
import org.pr.project.domain.Match;
import org.pr.project.filters.AbstractFilter;
import org.pr.project.filters.AgeFilter;
import org.pr.project.filters.CompatibilityFilter;
import org.pr.project.filters.DistanceRangeInKmFilter;
import org.pr.project.filters.HasImageFilter;
import org.pr.project.filters.HeightFilter;
import org.pr.project.filters.IsFavouriteFilter;
import org.pr.project.filters.IsInContactFilter;
import org.pr.project.repo.MatchFilterRepository;
import org.pr.project.strategies.BooleanFilteringStrategy;
import org.pr.project.strategies.DistanceWithinRangeStrategy;
import org.pr.project.strategies.IsTrueOrFalseStrategy;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.pr.project.strategies.NumberEqualToStrategy;
import org.pr.project.strategies.NumericFilteringStrategy;
import org.pr.project.strategies.PositiveNumberStrategy;
import org.pr.project.strategies.StringIsNotNullStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {MatchFilterApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class IntegrationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	MatchFilterRepository matchFilterRepository;

	@Autowired
	@Qualifier("customObjectMapper")
	ObjectMapper mapper;

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
		IsTrueOrFalseStrategy isTrueStrategy = new IsTrueOrFalseStrategy(true);
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

	}

	@Test
	public void test_getFilteredList_noReturn() throws Exception {

		NumericFilteringStrategy possitiveNumberStrategy = new PositiveNumberStrategy();
		NumericFilteringStrategy numberEqualToStrategy = new NumberEqualToStrategy(
				0d);

		IsInContactFilter isInContactFilter = new IsInContactFilter(
				possitiveNumberStrategy);

		IsInContactFilter notInContactFilter = new IsInContactFilter(
				numberEqualToStrategy);

		final List<AbstractFilter> appliedFilters = new ArrayList<>();
		appliedFilters.add(isInContactFilter);
		appliedFilters.add(notInContactFilter);

		final FiltersVO manyFilterVO = new FiltersVO();
		manyFilterVO.setAppliedFilters(appliedFilters);

		final ObjectWriter ow = mapper.writer();
		final String requestJson = ow.writeValueAsString(manyFilterVO);

		System.out.println(requestJson);

		FilteredListVO response = restTemplate.postForObject(
				"/filters/filteredMatches", manyFilterVO, FilteredListVO.class);

		assertThat(response).isNotNull();
		FilteredListVO filteredList = response;

		mapper.readValue(requestJson, FiltersVO.class);

		assertThat(filteredList.getMatches()).isNotNull();
		System.out.println("Filtered Size - " + filteredList.getMatches());
		assertThat(filteredList.getMatches().size()).isEqualTo(0);
	}

	@Test
	public void test_getFilteredList_WithGeoQuery() throws Exception {

		BooleanFilteringStrategy isTruFilteringStrategy = new IsTrueOrFalseStrategy(
				true);
		IsFavouriteFilter isInContactFilter = new IsFavouriteFilter(
				isTruFilteringStrategy);

		final List<AbstractFilter> appliedFilters = new ArrayList<>();
		appliedFilters.add(isInContactFilter);

		final FiltersVO manyFilterVO = new FiltersVO();
		manyFilterVO.setAppliedFilters(appliedFilters);

		FilteredListVO response = restTemplate.postForObject(
				"/filters/filteredMatches", manyFilterVO, FilteredListVO.class);
		assertThat(response.getMatches().size()).isNotEqualTo(0);

		Match anyOneMatch = response.getMatches().get(0);
		City anyCity = anyOneMatch.getCity();

		DistanceRangeInKmFilter inRangeFIlter = new DistanceRangeInKmFilter(
				new DistanceWithinRangeStrategy(anyCity.getLat(),
						anyCity.getLon(), 0d, .01d));

		appliedFilters.add(inRangeFIlter);

		manyFilterVO.setAppliedFilters(appliedFilters);

		final ObjectWriter ow = mapper.writer();
		final String requestJson = ow.writeValueAsString(manyFilterVO);

		System.out.println(requestJson);

		response = restTemplate.postForObject("/filters/filteredMatches",
				manyFilterVO, FilteredListVO.class);
		assertThat(response.getMatches().size()).isNotEqualTo(0);

	}

	@Test
	public void test_getFilteredList_OnlyGeoQuery() throws Exception {

		final List<AbstractFilter> appliedFilters = new ArrayList<>();

		City anyCity = new City("", 53.801277d, -1.548567d);

		DistanceRangeInKmFilter inRangeFIlter = new DistanceRangeInKmFilter(
				new DistanceWithinRangeStrategy(anyCity.getLat(),
						anyCity.getLon(), 30d, 300d));

		appliedFilters.add(inRangeFIlter);
		FiltersVO manyFilterVO = new FiltersVO();
		manyFilterVO.setAppliedFilters(appliedFilters);

		final ObjectWriter ow = mapper.writer();
		final String requestJson = ow.writeValueAsString(manyFilterVO);

		System.out.println(requestJson);

		FilteredListVO response = restTemplate.postForObject(
				"/filters/filteredMatches", manyFilterVO, FilteredListVO.class);
		assertThat(response.getMatches().size()).isNotEqualTo(0);

	}

}
