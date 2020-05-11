package org.pr.project.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileReaderUtilTests {

	private final ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void test_readFile_valid() {
		final Path filePath = Paths.get("./src/test/resources/matches.json");
		String content = FileReaderUtil.read(filePath);
		assertNotNull(content);
		assertThat(content).contains("Caroline");
	}

	@Test
	public void test_readFile_invalid() {
		
		final Path filePath = null;
		String content = FileReaderUtil.read(filePath);
		assertThat(content).isNull();
		
		final Path filePath2 = Paths.get("./src/test/resources/invalidFile.txt");
		content = FileReaderUtil.read(filePath2);
		assertThat(content).isNull();

	}

}
