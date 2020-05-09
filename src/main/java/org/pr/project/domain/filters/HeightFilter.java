package org.pr.project.domain.filters;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;
import org.pr.project.domain.strategies.NumericFilteringStrategy;

public class HeightFilter implements AbstractFilter {

	private final NumericFilteringStrategy heightBetweenBoundsFilteringStrategy;

	public HeightFilter(NumericFilteringStrategy heightBetweenBoundsStategy) {
		this.heightBetweenBoundsFilteringStrategy = heightBetweenBoundsStategy;
	}

	@Override
	public List<Match> runFilter(List<Match> matches) {
		return matches.stream().filter(c -> {
			return heightBetweenBoundsFilteringStrategy.apply(new BigDecimal(c.getHeight()));
		}).collect(Collectors.toList());
	}

}
