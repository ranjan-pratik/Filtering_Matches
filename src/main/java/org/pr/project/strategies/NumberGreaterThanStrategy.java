package org.pr.project.strategies;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("numberGreaterThan")
public class NumberGreaterThanStrategy implements NumericFilteringStrategy {

	private final Double baseNumber;

	@JsonCreator
	public NumberGreaterThanStrategy(final Double baseNumber) {
		this.baseNumber = baseNumber;
	}

	@Override
	public boolean apply(final Double candidate) {
		if (candidate == null)
			return false;
		return candidate > baseNumber;
	}

	@Override
	public List<Criteria> apply(final String field,
			final List<Criteria> original) {
		original.add(Criteria.where(field).gt(baseNumber));
		return original;
	}
}
