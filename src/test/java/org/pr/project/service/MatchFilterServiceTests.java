package org.pr.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.pr.project.domain.City;
import org.pr.project.domain.FilteredListVO;
import org.pr.project.domain.Match;
import org.pr.project.domain.Match.Religion;
import org.pr.project.filters.AbstractFilter;
import org.pr.project.filters.AgeFilter;
import org.pr.project.repo.MatchFilterRepository;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.pr.project.utils.FileReaderUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class MatchFilterServiceTests {

	private MatchFilterService filterService;
	
	@Mock
	MatchFilterRepository matchFilterRepository;
	
	String filePath = "./src/test/resources/matches.json";
	
	String readFromFile = null;
	
	FilteredListVO unfilteredVO = null;
	
	boolean dataNotRead = true;
	
	@Before
	public void init () throws JsonMappingException, JsonProcessingException {
		filterService = new MatchFilterService();
		filterService.matchFilterRepository = matchFilterRepository;
		if(dataNotRead) {
			ObjectMapper mapper = new ObjectMapper();
			readFromFile = FileReaderUtil.read(Paths.get(filePath));
			unfilteredVO = mapper.readValue(readFromFile, FilteredListVO.class);
			dataNotRead = false;
		}
	}
	
	@Test
	public void test_getDefaultList() {
		
		Mockito.when(matchFilterRepository.findAll()).thenReturn(unfilteredVO.getMatches());
		
		List<Match> unfilteredList = filterService.getAllMatches();
		
		assertThat(unfilteredList).isNotNull();
		assertThat(unfilteredList.size()).isGreaterThan(0);
		assertThat(unfilteredList.get(0).getDisplayName()).isNotNull();
	}
	
	@Test
	public void test_getAll_nullDataStore() {
		
		Mockito.when(matchFilterRepository.findAll()).thenReturn(null);
		
		List<Match> unfilteredList = filterService.getAllMatches();
		
		assertThat(unfilteredList).isNotNull();
		assertThat(unfilteredList.size()).isEqualTo(0);
	}
	
	@Test
	public void test_getAll_emptyDataStore() {
		
		Mockito.when(matchFilterRepository.findAll()).thenReturn(new ArrayList<Match>());
		
		List<Match> unfilteredList = filterService.getAllMatches();
		
		assertThat(unfilteredList).isNotNull();
		assertThat(unfilteredList.size()).isEqualTo(0);
	}
	
	@Test
	public void test_getFiltered_noFilterApplied_nullPassed_shouldReturnAll() {
		
		Mockito.when(matchFilterRepository.findAll()).thenReturn(unfilteredVO.getMatches());
		
		List<Match> unfilteredList = filterService.getFilteredMatches(null);
		
		assertThat(unfilteredList).isNotNull();
		assertThat(unfilteredList.size()).isEqualTo(unfilteredVO.getMatches().size());
	}
	
	@Test
	public void test_getFiltered_noFilterApplied_emptyListPassed_shouldReturnAll() {
		
		Mockito.when(matchFilterRepository.findAll()).thenReturn(unfilteredVO.getMatches());
		
		List<Match> unfilteredList = filterService.getFilteredMatches(new ArrayList<AbstractFilter>());
		
		assertThat(unfilteredList).isNotNull();
		assertThat(unfilteredList.size()).isEqualTo(unfilteredVO.getMatches().size());
	}
	
	@Test
	public void test_getFiltered_ageFilterApplied_shouldReturnFiltered() {
		List<Match> data = new ArrayList<Match>();
		List<Match> filteredData = new ArrayList<Match>();
		Match candidate1 = new Match("Candidate1", "some Job", 23, Religion.Christian, "", 163d, 15d, 5, true,
				new City("someCity", 51.509865, -0.118092));
		data.add(candidate1);
		Match candidate2 = new Match("Candidate2", "some other Job", 56, Religion.Agnostic, "", 155d, 65d, 0,
				false, new City("someOtherCity", 23.509865, 0.158092));
		data.add(candidate2);
		Match candidate3 = new Match("Candidate3", "third Job", 29, Religion.Islam, "", 133d, 95d, 0, null,
				new City("otherCity", 51.500065, -0.100092));
		data.add(candidate3);
		
		List<AbstractFilter> filtersToApply = new ArrayList<AbstractFilter>();
		AbstractFilter ageFilter = new AgeFilter(new NumberBetweenBoundsStrategy(30d, 60d));
		filtersToApply.add(ageFilter);
		List<Match> expected = ageFilter.runFilter(data);
		
		filteredData.add(candidate2);
		Mockito.when(matchFilterRepository.findByCustomCriteria(Mockito.any())).thenReturn(filteredData);
		
		List<Match> filteredList = filterService.getFilteredMatches(filtersToApply);
		assertThat(filteredList).isNotNull();
		assertThat(filteredList.size()).isEqualTo(expected.size());
	}
	
}
