package org.pr.project.specifications;

import java.util.ArrayList;
import java.util.List;

import org.pr.project.strategies.NumericFilteringStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class AgeSpecification extends AbstractSpecification<Double> {

	public AgeSpecification(
			final NumericFilteringStrategy ageFilteringStrategy) {
		field = "age";
		strategy = ageFilteringStrategy;
	}

	@Override
	public Criteria getCriteria() {
		if (strategy == null)  return new Criteria();
		List<Criteria> andCriteria = new ArrayList<>();
		andCriteria = strategy.apply(field, andCriteria);
		if (andCriteria.size() == 0) {
			return new Criteria();
		} else if (andCriteria.size() == 1) {
			return andCriteria.get(0);
		} else {
			return new Criteria().andOperator(
					andCriteria.toArray(new Criteria[andCriteria.size()]));
		}

	}

}
