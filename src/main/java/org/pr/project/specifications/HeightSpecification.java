package org.pr.project.specifications;

import java.util.ArrayList;
import java.util.List;

import org.pr.project.strategies.NumericFilteringStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class HeightSpecification extends AbstractSpecification<Double> {

	public HeightSpecification(
			final NumericFilteringStrategy heightFilteringStrategy) {
		field = "height";
		strategy = heightFilteringStrategy;
	}

	@Override
	public Criteria getCriteria() {
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
