package org.pr.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.pr.project.domain.Match;

@RunWith(MockitoJUnitRunner.class)
public class MatchFilterServiceTests {

	private MatchFilterService filterService;
	
	@Before
	public void init () {
		filterService = new MatchFilterService();
		filterService.filePath = "./src/test/resources/matches.json";
	}
	
	@Test
	public void test_getDefaultList() {
		List<Match> unfilteredList = filterService.getDefaultMatches();
		
		assertThat(unfilteredList).isNotNull();
		assertThat(unfilteredList.size()).isGreaterThan(0);
		assertThat(unfilteredList.get(0).getDisplayName()).isNotNull();
	}
}
