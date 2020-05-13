package org.pr.project.strategies;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("numberBetweenBounds")
public class NumberBetweenBoundsStrategy implements NumericFilteringStrategy {

	protected final Double lowerBound;
	protected final Double upperBound;

	public NumberBetweenBoundsStrategy() {
		lowerBound = new Double(Integer.MIN_VALUE);
		upperBound = new Double(Integer.MAX_VALUE);
	}

	@JsonCreator
	public NumberBetweenBoundsStrategy(final Double lowerBoundInclusive,
			final Double upperBoundInclusive) {
		lowerBound = lowerBoundInclusive;
		upperBound = upperBoundInclusive;
	}

	public Double getLowerBound() {
		return lowerBound;
	}

	public Double getUpperBound() {
		return upperBound;
	}

	@Override
	public boolean apply(final Double candidate) {
		// if no bound is set, return true, even if candidate is null
		if (new Double(Integer.MIN_VALUE).compareTo(lowerBound) == 0
				&& new Double(Integer.MAX_VALUE).compareTo(upperBound) == 0) {
			return true;
		}
		if (candidate == null)
			return false;
		// if candidate is between the date range, return true
		if (lowerBound.compareTo(candidate) > 0) {
			return false;
		} else if (upperBound.compareTo(candidate) < 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<Criteria> apply(final String field,
			final List<Criteria> original) {
		if (lowerBound == null && upperBound == null)
			return original;
		if (lowerBound != null && upperBound != null
				&& lowerBound.compareTo(upperBound) > 0)
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
