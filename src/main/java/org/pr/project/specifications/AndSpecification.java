package org.pr.project.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

public class AndSpecification extends AbstractSpecification {

	private final AbstractSpecification[] allAndPredicates;

	public AndSpecification(AbstractSpecification... allAndPredicates) {
		this.allAndPredicates = allAndPredicates;
	}

	@Override
	public Criteria getSpecification() {
		List<Criteria> andCriteria = new ArrayList<Criteria>();

		for (AbstractSpecification onePredicate : allAndPredicates) {
			Criteria c = onePredicate.getSpecification();
			andCriteria.add(c);
		}
		return new Criteria().andOperator(andCriteria.toArray(new Criteria[andCriteria.size()]));
	}

}
