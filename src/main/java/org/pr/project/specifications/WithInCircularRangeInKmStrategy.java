package org.pr.project.specifications;

import java.util.List;

import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;


public class WithInCircularRangeInKmStrategy extends NumberBetweenBoundsStrategy {

	protected final Point point;
	
	public WithInCircularRangeInKmStrategy(final Point centre, Double lowerBoundInclusive, Double upperBoundInclusive) {
		super(lowerBoundInclusive, upperBoundInclusive);
		this.point = centre;
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
			original.add(Criteria.where(field).nearSphere(point).maxDistance(upperBound));
	    else if (upperBound == null) 
	    	original.add(Criteria.where(field).nearSphere(point).minDistance(lowerBound));
	    else 
	    	original.add(Criteria.where(field).nearSphere(point).minDistance(lowerBound).maxDistance(upperBound));
		
	    return original;
	}

}
