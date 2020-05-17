package org.pr.project.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.pr.project.domain.City;
import org.pr.project.domain.FiltersVO;
import org.pr.project.domain.Match;
import org.pr.project.domain.Match.Religion;
import org.pr.project.filters.AbstractFilter;
import org.pr.project.filters.AgeFilter;
import org.pr.project.filters.AndFilter;
import org.pr.project.filters.CompatibilityFilter;
import org.pr.project.filters.HeightFilter;
import org.pr.project.filters.IsFavouriteFilter;
import org.pr.project.service.MatchFilterService;
import org.pr.project.strategies.IsTrueOrFalseStrategy;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringRunner.class)
@WebMvcTest(FilterController.class)
@ContextConfiguration(classes = {EmbeddedMongoAutoConfiguration.class})
@ComponentScan(basePackages = "org.pr.project.rest")
public class RestLayerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	MatchFilterService filterMatcherService;

	List<Match> matches = new ArrayList<Match>();

	@Before
	public void init() throws JsonMappingException, JsonProcessingException {
		final Match candidate1 = new Match("Candidate1", "some Job", 23,
				Religion.Christian, "", 163d, 0.5d, 5, true,
				new City("someCity", 51.509865, -0.118092));
		matches.add(candidate1);

		final Match candidate2 = new Match("Candidate2", "some other Job", 56,
				Religion.Agnostic, "", 155d, 0.65d, 0, false,
				new City("someOtherCity", 23.509865, 0.158092));
		matches.add(candidate2);

		final Match candidate3 = new Match("Candidate3", "third Job", 29,
				Religion.Islam, null, 133d, 0.95d, 0, null,
				new City("otherCity", 51.500065, -0.100092));
		matches.add(candidate3);
	}

	@Test
	public void test_getDefaultFilteredList() throws Exception {

		Mockito.when(filterMatcherService.getAllMatches()).thenReturn(matches);

		mockMvc.perform(MockMvcRequestBuilders.get("/filters/allMatches"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.matches").isArray())
				.andExpect(jsonPath("$.matches", hasSize(3))).andReturn();
	}

	@Test
	public void test_getFiltered_noFilterApplied_nullPassed_shouldReturnAll()
			throws Exception {

		Mockito.when(filterMatcherService.getAllMatches()).thenReturn(matches);

		mockMvc.perform(MockMvcRequestBuilders.post("/filters/filteredMatches")
				.contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.matches").isArray())
				.andExpect(jsonPath("$.matches", hasSize(3))).andReturn();
	}

	@Test
	public void test_getFiltered_noFilterApplied_emptyVOPassed_shouldReturnAll()
			throws Exception {

		Mockito.when(filterMatcherService.getFilteredMatches(Mockito.any()))
				.thenReturn(matches);

		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		final String requestJson = ow.writeValueAsString(new FiltersVO());

		mockMvc.perform(MockMvcRequestBuilders.post("/filters/filteredMatches")
				.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.matches").isArray())
				.andExpect(jsonPath("$.matches", hasSize(3))).andReturn();
	}

	@Test
	public void test_getFiltered_ageFilterApplied_shouldReturnFiltered()
			throws Exception {

		final AgeFilter filter = new AgeFilter(
				new NumberBetweenBoundsStrategy(30d, 60d));

		final List<Match> expectedList = filter.runFilter(matches);

		final List<AbstractFilter> appliedFilters = new ArrayList<>();
		appliedFilters.add(filter);

		final FiltersVO ageFilterOnlyVO = new FiltersVO();
		ageFilterOnlyVO.setAppliedFilters(appliedFilters);

		Mockito.when(filterMatcherService.getFilteredMatches(Mockito.any()))
				.thenReturn(expectedList);

		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		final String requestJson = ow.writeValueAsString(ageFilterOnlyVO);
		System.out.println(requestJson);
		mockMvc.perform(MockMvcRequestBuilders.post("/filters/filteredMatches")
				.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.matches").isArray())
				.andExpect(jsonPath("$.matches", hasSize(1))).andReturn();
	}

	@Test
	public void test_getFiltered_multipleFiltersApplied_shouldReturnFiltered()
			throws Exception {

		final AgeFilter ageFilter = new AgeFilter(
				new NumberBetweenBoundsStrategy(20d, 60d));
		final HeightFilter heightFilter = new HeightFilter(
				new NumberBetweenBoundsStrategy(new Double(140),
						new Double(200)));
		CompatibilityFilter compatibilityFilter = new CompatibilityFilter(
				new NumberBetweenBoundsStrategy(new Double(0.3),
						new Double(0.7)));
		IsTrueOrFalseStrategy isTrueStrategy = new IsTrueOrFalseStrategy(true);
		IsFavouriteFilter isFavouriteFilter = new IsFavouriteFilter(
				isTrueStrategy);

		final List<Match> expectedList = new AndFilter(ageFilter, heightFilter,
				compatibilityFilter, isFavouriteFilter).runFilter(matches);

		final List<AbstractFilter> appliedFilters = new ArrayList<>();
		appliedFilters.add(ageFilter);
		appliedFilters.add(heightFilter);
		appliedFilters.add(compatibilityFilter);
		appliedFilters.add(isFavouriteFilter);

		final FiltersVO ageFilterOnlyVO = new FiltersVO();
		ageFilterOnlyVO.setAppliedFilters(appliedFilters);

		Mockito.when(filterMatcherService.getFilteredMatches(Mockito.any()))
				.thenReturn(expectedList);

		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		final String requestJson = ow.writeValueAsString(ageFilterOnlyVO);
		System.out.println(requestJson);
		mockMvc.perform(MockMvcRequestBuilders.post("/filters/filteredMatches")
				.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.matches").isArray())
				.andExpect(jsonPath("$.matches", hasSize(1))).andReturn();
	}

}
