package org.pr.project.domain.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.domain.strategies.IntegerFilteringStrategy;

public class AgeFilter implements AbstractFilter {

	private final IntegerFilteringStrategy ageBetweenBoundsFilteringStrategy;
	
	public AgeFilter(IntegerFilteringStrategy ageBetweenBoundsStategy) {
		this.ageBetweenBoundsFilteringStrategy = ageBetweenBoundsStategy;
	}

	@Override
	public List<Match> runFilter(List<Match> matches) {
		return matches.stream().filter(c -> {
			return ageBetweenBoundsFilteringStrategy.apply(c.getAge());
		}).collect(Collectors.toList());
	}

}
