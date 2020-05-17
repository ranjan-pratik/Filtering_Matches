package org.pr.project.rest;

import org.pr.project.domain.FilteredListVO;
import org.pr.project.domain.FiltersVO;
import org.pr.project.service.MatchFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController()
public class FilterController {

	private static final Logger logger = LoggerFactory
			.getLogger(FilterController.class);

	@Autowired
	MatchFilterService matchFilterService;

	@GetMapping("/filters/allMatches")
	@ResponseBody
	public FilteredListVO getAllMatches() {
		logger.info("Getting all matches.");
		final FilteredListVO filteredListVO = new FilteredListVO();
		filteredListVO.setMatches(matchFilterService.getAllMatches());
		logger.info("Returning all [" + filteredListVO.getMatches().size()
				+ "] matches");
		return filteredListVO;
	}

	@PostMapping("/filters/filteredMatches")
	@ResponseBody
	public FilteredListVO getFilteredMatches(
			@RequestBody(required = false) final FiltersVO appliedFilters) {
		logger.info("Getting filterd matches.");
		if (appliedFilters == null)
			return getAllMatches();
		final FilteredListVO filteredListVO = new FilteredListVO();
		filteredListVO.setMatches(matchFilterService
				.getFilteredMatches(appliedFilters.getAppliedFilters()));
		logger.info("Returning [" + filteredListVO.getMatches().size()
				+ "] filterd matches.");
		return filteredListVO;
	}
}
