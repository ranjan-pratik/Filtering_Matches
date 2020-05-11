package org.pr.project.rest;

import org.pr.project.domain.FilteredListVO;
import org.pr.project.service.MatchFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class FilterController {

	@Autowired
	MatchFilterService matchFilterService;
	
	@GetMapping("/filters/defaultList")
	@ResponseBody
	public FilteredListVO getDefaultList() {

		FilteredListVO filteredListVO = new FilteredListVO();
		filteredListVO.setMatches(matchFilterService.getDefaultMatches());
		return filteredListVO;
	}
}
