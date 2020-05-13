package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.specifications.AgeSpecification;
import org.pr.project.strategies.NumericFilteringStrategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("age")
public class AgeFilter extends AbstractFilter<Double> {

	private final NumericFilteringStrategy ageFilteringStrategy;

	@JsonCreator
	public AgeFilter(final NumericFilteringStrategy ageFilteringStrategy) {
		this.ageFilteringStrategy = ageFilteringStrategy;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return ageFilteringStrategy.apply(new Double(c.getAge()));
		}).collect(Collectors.toList());
	}

	@Override
	public AgeSpecification getSpecification() {
		return new AgeSpecification(ageFilteringStrategy);
	}

}
