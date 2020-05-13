package org.pr.project.filters;

import java.util.List;

import org.pr.project.domain.Match;

public class AndFilter extends AbstractFilter<Object> {

	private final AbstractFilter[] allMemberFilters;

	public AndFilter(final AbstractFilter... allPredicates) {
		allMemberFilters = allPredicates;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		List<Match> filteredList = candidates;
		for (final AbstractFilter oneFilter : allMemberFilters) {
			filteredList = oneFilter.runFilter(filteredList);
		}
		return filteredList;
	}

}
