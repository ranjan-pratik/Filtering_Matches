package org.pr.project.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

public class AndSpecification extends AbstractSpecification<Object> {

	private final AbstractSpecification[] allAndPredicates;

	public AndSpecification(final AbstractSpecification... allAndPredicates) {
		this.allAndPredicates = allAndPredicates;
	}

	@Override
	public Criteria getCriteria() {
		final List<Criteria> andCriteria = new ArrayList<Criteria>();

		for (final AbstractSpecification onePredicate : allAndPredicates) {
			final Criteria c = onePredicate.getCriteria();
			andCriteria.add(c);
		}
		return new Criteria().andOperator(
				andCriteria.toArray(new Criteria[andCriteria.size()]));
	}

}
