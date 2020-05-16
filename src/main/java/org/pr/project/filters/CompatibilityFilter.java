package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.specifications.CompatibilitySpecification;
import org.pr.project.strategies.NumericFilteringStrategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("compatibility")
public class CompatibilityFilter extends AbstractFilter<Double> {

	@JsonCreator
	public CompatibilityFilter(
			@JsonProperty("strategy") final NumericFilteringStrategy compatibilityBetweenBoundsStategy) {
		this.strategy = compatibilityBetweenBoundsStategy;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return this.strategy.apply(new Double(c.getCompatibilityScore()));
		}).collect(Collectors.toList());
	}

	@Override
	public CompatibilitySpecification getSpecification() {
		return new CompatibilitySpecification(
				(NumericFilteringStrategy) this.strategy);
	}

}
