package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.City;
import org.pr.project.domain.Match;
import org.pr.project.specifications.RangeInKmSpecification;
import org.pr.project.strategies.DistanceFilteringStrategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("distanceRangeInKm")
public class DistanceRangeInKmFilter extends AbstractFilter<City> {

	@JsonCreator
	public DistanceRangeInKmFilter(
			@JsonProperty("strategy") final DistanceFilteringStrategy distanceInKmBetweenBoundsStategy) {
		this.strategy = distanceInKmBetweenBoundsStategy;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		return candidates.stream().filter(c -> {
			if (c.getCity() == null)
				return false;
			return this.strategy.apply(c.getCity());
		}).collect(Collectors.toList());
	}

	public RangeInKmSpecification getSpecification() {
		return new RangeInKmSpecification(
				(DistanceFilteringStrategy) this.strategy);
	}

}
