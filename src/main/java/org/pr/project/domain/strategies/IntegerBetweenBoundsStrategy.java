package org.pr.project.domain.strategies;

import org.pr.project.domain.Match;

public class IntegerBetweenBoundsStrategy implements FilteringStrategy {

	private final Integer lowerBound;
	private final Integer upperBound;
	
	public IntegerBetweenBoundsStrategy(int lowerBoundInclusive, int upperBoundInclusive) {
		this.lowerBound = lowerBoundInclusive;
		this.upperBound = upperBoundInclusive;
	}

	@Override
	public boolean apply(Match candidate) {
		//if candidate is between the date range, return true
		if (candidate.getAge() == null) return false;
		if (lowerBound.compareTo(candidate.getAge()) > 0) {
			return false;
		} else if (upperBound.compareTo(candidate.getAge()) < 0) {
			return false;
		}
		return true;
	}
}
