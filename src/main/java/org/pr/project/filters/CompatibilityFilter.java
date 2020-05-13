package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.strategies.NumericFilteringStrategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("compatibility")
public class CompatibilityFilter extends AbstractFilter<Double> {

	private final NumericFilteringStrategy CompatibilityBetweenBoundsFilteringStrategy;

	@JsonCreator
	public CompatibilityFilter(
			final NumericFilteringStrategy compatibilityBetweenBoundsStategy) {
		CompatibilityBetweenBoundsFilteringStrategy = compatibilityBetweenBoundsStategy;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return CompatibilityBetweenBoundsFilteringStrategy
					.apply(new Double(c.getCompatibilityScore()));
		}).collect(Collectors.toList());
	}

}
