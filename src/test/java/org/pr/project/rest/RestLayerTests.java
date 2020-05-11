package org.pr.project.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.pr.project.MatchFilterApplication;
import org.pr.project.domain.City;
import org.pr.project.domain.Match;
import org.pr.project.domain.Match.Religion;
import org.pr.project.service.MatchFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(FilterController.class)
@ContextConfiguration(classes= {MatchFilterApplication.class, EmbeddedMongoAutoConfiguration.class})
public class RestLayerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	MatchFilterService filterMatcherService;
	
	@Test
	public void test_getDefaultFilteredList() throws Exception {
		
		List<Match> matches = new ArrayList<Match>();
		Match candidate1 = new Match("Candidate1", "some Job", 23, Religion.Christian, "", 163d, 15d, 5, true,
				new City("someCity", 51.509865, -0.118092));
		matches.add(candidate1);
		
		Mockito.when(filterMatcherService.getDefaultMatches()).thenReturn(matches);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/filters/defaultList")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.matches").isArray())
		.andExpect(jsonPath("$.matches", hasSize(1)))
		.andReturn();
	}

}
