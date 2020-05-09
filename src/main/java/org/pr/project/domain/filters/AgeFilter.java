package org.pr.project.domain.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.domain.strategies.FilteringStrategy;

public class AgeFilter implements AbstractFilter {

	private final FilteringStrategy ageBetweenBoundsFilteringStrategy;
	
	public AgeFilter(FilteringStrategy ageBetweenBoundsStategy) {
		this.ageBetweenBoundsFilteringStrategy = ageBetweenBoundsStategy;
	}

	@Override
	public List<Match> runFilter(List<Match> matches) {
		return matches.stream().filter(c -> {
			return ageBetweenBoundsFilteringStrategy.apply(c);
		}).collect(Collectors.toList());
	}

}
