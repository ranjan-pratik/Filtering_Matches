package org.pr.project.filters;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.strategies.NumericFilteringStrategy;

public class AgeFilter implements AbstractFilter {

	private final NumericFilteringStrategy ageBetweenBoundsFilteringStrategy;
	
	public AgeFilter(NumericFilteringStrategy ageBetweenBoundsStategy) {
		this.ageBetweenBoundsFilteringStrategy = ageBetweenBoundsStategy;
	}

	@Override
	public List<Match> runFilter(List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return ageBetweenBoundsFilteringStrategy.apply(new BigDecimal(c.getAge()));
		}).collect(Collectors.toList());
	}

}
