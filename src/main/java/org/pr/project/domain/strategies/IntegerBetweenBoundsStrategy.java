package org.pr.project.domain.strategies;

import org.pr.project.domain.Match;

public class IntegerBetweenBoundsStrategy implements IntegerFilteringStrategy {

	private final Integer lowerBound;
	private final Integer upperBound;
	
	public IntegerBetweenBoundsStrategy() {
		this.lowerBound = Integer.MIN_VALUE;
		this.upperBound = Integer.MAX_VALUE;
	}
	
	public IntegerBetweenBoundsStrategy(int upperBoundInclusive) {
		this.lowerBound = Integer.MIN_VALUE;
		this.upperBound = upperBoundInclusive;
	}
	
	public IntegerBetweenBoundsStrategy(int lowerBoundInclusive, int upperBoundInclusive) {
		this.lowerBound = lowerBoundInclusive;
		this.upperBound = upperBoundInclusive;
	}
	
	public Integer getLowerBound() {
		return lowerBound;
	}

	public Integer getUpperBound() {
		return upperBound;
	}

	@Override
	public boolean apply(Integer candidate) {
		// if no bound is set, return true, even if candidate is null
		if (lowerBound.equals(Integer.MIN_VALUE) && upperBound.equals(Integer.MAX_VALUE)) 
			return true;
		if (candidate == null) 
			return false;
		//if candidate is between the date range, return true
		if (lowerBound.compareTo(candidate) > 0) {
			return false;
		} else if (upperBound.compareTo(candidate) < 0) {
			return false;
		}
		return true;
	}
}
