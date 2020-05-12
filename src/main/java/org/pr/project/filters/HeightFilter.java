package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.strategies.NumericFilteringStrategy;

public class HeightFilter implements AbstractFilter {

	private final NumericFilteringStrategy heightBetweenBoundsFilteringStrategy;

	public HeightFilter(NumericFilteringStrategy heightBetweenBoundsStategy) {
		this.heightBetweenBoundsFilteringStrategy = heightBetweenBoundsStategy;
	}

	@Override
	public List<Match> runFilter(List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return heightBetweenBoundsFilteringStrategy.apply(new Double(c.getHeight()));
		}).collect(Collectors.toList());
	}

}
