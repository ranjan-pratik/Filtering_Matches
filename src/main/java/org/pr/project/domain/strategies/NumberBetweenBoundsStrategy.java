package org.pr.project.domain.strategies;

import java.math.BigDecimal;

public class NumberBetweenBoundsStrategy implements NumericFilteringStrategy {

	private final BigDecimal lowerBound;
	private final BigDecimal upperBound;
	
	public NumberBetweenBoundsStrategy() {
		this.lowerBound = new BigDecimal(Integer.MIN_VALUE);
		this.upperBound = new BigDecimal(Integer.MAX_VALUE);
	}
	
	public NumberBetweenBoundsStrategy(BigDecimal lowerBoundInclusive, BigDecimal upperBoundInclusive) {
		this.lowerBound = lowerBoundInclusive;
		this.upperBound = upperBoundInclusive;
	}
	
	public BigDecimal getLowerBound() {
		return lowerBound;
	}

	public BigDecimal getUpperBound() {
		return upperBound;
	}

	@Override
	public boolean apply(BigDecimal candidate) {
		// if no bound is set, return true, even if candidate is null
		if (new BigDecimal(Integer.MIN_VALUE).compareTo(lowerBound) == 0
				 && new BigDecimal(Integer.MAX_VALUE).compareTo(upperBound) ==0) {
			return true;
		}
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
