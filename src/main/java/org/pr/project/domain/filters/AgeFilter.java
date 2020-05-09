package org.pr.project.domain.filters;

import java.util.List;

import org.pr.project.domain.Match;
import org.pr.project.domain.strategies.FilteringStrategy;

public class AgeFilter {

	private final FilteringStrategy ageBetweenBoundsFilteringStrategy;
	
	public AgeFilter(FilteringStrategy ageBetweenBoundsStategy) {
		this.ageBetweenBoundsFilteringStrategy = ageBetweenBoundsStategy;
	}

	public List<Match> applyFilter(List<Match> matches) {
		// TODO Auto-generated method stub
		return null;
	}

}
