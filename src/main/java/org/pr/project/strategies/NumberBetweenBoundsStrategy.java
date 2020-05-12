package org.pr.project.strategies;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

public class NumberBetweenBoundsStrategy implements NumericFilteringStrategy {

	private final Double lowerBound;
	private final Double upperBound;
	
	public NumberBetweenBoundsStrategy() {
		this.lowerBound = new Double(Integer.MIN_VALUE);
		this.upperBound = new Double(Integer.MAX_VALUE);
	}
	
	public NumberBetweenBoundsStrategy(Double lowerBoundInclusive, Double upperBoundInclusive) {
		this.lowerBound = lowerBoundInclusive;
		this.upperBound = upperBoundInclusive;
	}
	
	public Double getLowerBound() {
		return lowerBound;
	}

	public Double getUpperBound() {
		return upperBound;
	}

	@Override
	public boolean apply(Double candidate) {
		// if no bound is set, return true, even if candidate is null
		if (new Double(Integer.MIN_VALUE).compareTo(lowerBound) == 0
				 && new Double(Integer.MAX_VALUE).compareTo(upperBound) ==0) {
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
	
	@Override
	public List<Criteria> apply(String field, List<Criteria> original) {
		if (lowerBound == null && upperBound == null)
			return original;
		if (lowerBound != null && upperBound != null && lowerBound.compareTo(upperBound) > 0) 
			return original;
		else if (lowerBound == null) 
			original.add(Criteria.where(field).lte(upperBound));
	    else if (upperBound == null) 
	    	original.add(Criteria.where(field).gte(lowerBound));
	    else 
	    	original.add(Criteria.where(field).lte(upperBound).gte(lowerBound));
	    
	    return original;
	}
}
