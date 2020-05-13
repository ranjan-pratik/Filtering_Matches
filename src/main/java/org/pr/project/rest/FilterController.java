package org.pr.project.rest;

import org.pr.project.domain.FilteredListVO;
import org.pr.project.domain.FiltersVO;
import org.pr.project.service.MatchFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class FilterController {

	@Autowired
	MatchFilterService matchFilterService;

	@GetMapping("/filters/allMatches")
	@ResponseBody
	public FilteredListVO getAllMatches() {
		final FilteredListVO filteredListVO = new FilteredListVO();
		filteredListVO.setMatches(matchFilterService.getAllMatches());
		return filteredListVO;
	}

	@GetMapping("/filters/filteredMatches")
	@ResponseBody
	public FilteredListVO getFilteredMatches(
			@RequestBody(required = false) final FiltersVO appliedFilters) {
		if (appliedFilters == null)
			return getAllMatches();
		final FilteredListVO filteredListVO = new FilteredListVO();
		filteredListVO.setMatches(matchFilterService
				.getFilteredMatches(appliedFilters.getAppliedFilters()));
		return filteredListVO;
	}
}
