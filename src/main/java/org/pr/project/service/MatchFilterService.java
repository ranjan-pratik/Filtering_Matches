package org.pr.project.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.pr.project.domain.FilteredListVO;
import org.pr.project.domain.Match;
import org.pr.project.utils.FileReaderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MatchFilterService {
	
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Value("${json.data.file.path}")
	String filePath;

	public List<Match> getDefaultMatches() {
		
		String content = FileReaderUtil.read(Paths.get(filePath));
		
		if (content == null ) return null;
		
		List<Match> matches = null;
		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			FilteredListVO all = mapper.readValue(content, FilteredListVO.class);
			matches = all.getMatches();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return matches;
	}
}
