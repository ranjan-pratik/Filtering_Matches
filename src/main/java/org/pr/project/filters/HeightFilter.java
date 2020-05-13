package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.specifications.HeightSpecification;
import org.pr.project.strategies.NumericFilteringStrategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("height")
public class HeightFilter extends AbstractFilter<Double> {

	private final NumericFilteringStrategy heightBetweenBoundsFilteringStrategy;

	@JsonCreator
	public HeightFilter(
			final NumericFilteringStrategy heightBetweenBoundsStategy) {
		heightBetweenBoundsFilteringStrategy = heightBetweenBoundsStategy;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return heightBetweenBoundsFilteringStrategy
					.apply(new Double(c.getHeight()));
		}).collect(Collectors.toList());
	}

	@Override
	public HeightSpecification getSpecification() {
		return new HeightSpecification(heightBetweenBoundsFilteringStrategy);
	}

}
