package org.pr.project.strategies;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

public class NumberGreaterThanStrategy implements NumericFilteringStrategy {

	private Double baseNumber;
	public NumberGreaterThanStrategy(Double baseNumber) {
		this.baseNumber = baseNumber;
	}

	@Override
	public boolean apply(Double candidate) {
		if ( candidate == null) return false;
		return candidate > baseNumber;
	}
	
	@Override
	public List<Criteria> apply(String field, List<Criteria> original) {
		original.add(Criteria.where(field).gt(baseNumber));
		return original;
	}
}
