package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.strategies.NumericFilteringStrategy;

public class AgeFilter implements AbstractFilter {

	private final NumericFilteringStrategy ageFilteringStrategy;
	
	public AgeFilter(NumericFilteringStrategy ageFilteringStrategy) {
		this.ageFilteringStrategy = ageFilteringStrategy;
	}

	@Override
	public List<Match> runFilter(List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return ageFilteringStrategy.apply(new Double(c.getAge()));
		}).collect(Collectors.toList());
	}

}
