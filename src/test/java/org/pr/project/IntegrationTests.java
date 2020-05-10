package org.pr.project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pr.project.domain.FilteredListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void test_getDefaultList() {
		ResponseEntity<FilteredListVO> response = restTemplate.getForEntity("/filters/defaultList", FilteredListVO.class) ;
		
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		FilteredListVO unfilteredList = response.getBody();
		
		assertThat(unfilteredList.getMatches()).isNotNull();
		assertThat(unfilteredList.getMatches().size()).isGreaterThan(0);
		assertThat(unfilteredList.getMatches().get(0).getDisplayName()).isNotNull();
	}

}
