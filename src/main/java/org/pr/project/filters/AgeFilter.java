package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.specifications.AgeSpecification;
import org.pr.project.strategies.NumericFilteringStrategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("age")
public class AgeFilter extends AbstractFilter<Double> {

	@JsonCreator
	public AgeFilter(
			@JsonProperty("strategy") final NumericFilteringStrategy ageFilteringStrategy) {
		this.strategy = ageFilteringStrategy;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return this.strategy.apply(new Double(c.getAge()));
		}).collect(Collectors.toList());
	}

	@Override
	public AgeSpecification getSpecification() {
		return new AgeSpecification((NumericFilteringStrategy) this.strategy);
	}

}
