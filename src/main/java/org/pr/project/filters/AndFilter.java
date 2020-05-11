package org.pr.project.filters;

import java.util.List;

import org.pr.project.domain.Match;

public class AndFilter implements AbstractFilter {

	private final AbstractFilter[] allMemberFilters;
	
	public AndFilter(AbstractFilter... allPredicates) {
		allMemberFilters = allPredicates;
	}
	
	@Override
	public List<Match> runFilter(List<Match> candidates) {
		List<Match> filteredList = candidates;
		for (AbstractFilter oneFilter : allMemberFilters) {
			filteredList = oneFilter.runFilter(filteredList);
		}
		return filteredList;
	}


}
