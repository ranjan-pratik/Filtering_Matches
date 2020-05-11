package org.pr.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.pr.project.domain.FilteredListVO;
import org.pr.project.domain.Match;
import org.pr.project.repo.MatchFilterRepository;
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
	
	@Before
	public void init () {
		filterService = new MatchFilterService();
		filterService.matchFilterRepository = matchFilterRepository;
	}
	
	@Test
	public void test_getDefaultList() throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		FilteredListVO unfilteredVO = mapper.readValue(FileReaderUtil.read(Paths.get(filePath)), FilteredListVO.class);
		
		Mockito.when(matchFilterRepository.findAll()).thenReturn(unfilteredVO.getMatches());
		
		List<Match> unfilteredList = filterService.getDefaultMatches();
		
		assertThat(unfilteredList).isNotNull();
		assertThat(unfilteredList.size()).isGreaterThan(0);
		assertThat(unfilteredList.get(0).getDisplayName()).isNotNull();
	}
}
