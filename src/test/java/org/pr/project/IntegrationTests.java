package org.pr.project;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pr.project.domain.FilteredListVO;
import org.pr.project.repo.MatchFilterRepository;
import org.pr.project.utils.FileReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { MatchFilterApplication.class})
public class IntegrationTests {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	MatchFilterRepository matchFilterRepository;
	
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
	public void test_getDefaultList() {
		ResponseEntity<FilteredListVO> response = restTemplate.getForEntity("/filters/defaultList", FilteredListVO.class) ;
		
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		FilteredListVO unfilteredList = response.getBody();
		
		assertThat(unfilteredList.getMatches()).isNotNull();
		assertThat(unfilteredList.getMatches().size()).isGreaterThan(0);
		assertThat(unfilteredList.getMatches().get(0).getDisplayName()).isNotNull();
	}

}
