package org.pr.project.domain.filters;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.domain.strategies.NumericFilteringStrategy;

public class CompatibilityFilter implements AbstractFilter {

	private final NumericFilteringStrategy CompatibilityBetweenBoundsFilteringStrategy;

	public CompatibilityFilter(NumericFilteringStrategy compatibilityBetweenBoundsStategy) {
		this.CompatibilityBetweenBoundsFilteringStrategy = compatibilityBetweenBoundsStategy;
	}

	@Override
	public List<Match> runFilter(List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return CompatibilityBetweenBoundsFilteringStrategy.apply(new BigDecimal(c.getCompatibilityScore()));
		}).collect(Collectors.toList());
	}

}
