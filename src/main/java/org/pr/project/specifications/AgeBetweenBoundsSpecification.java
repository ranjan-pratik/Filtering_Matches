package org.pr.project.specifications;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.pr.project.strategies.NumericFilteringStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class AgeBetweenBoundsSpecification extends AbstractSpecification<BigDecimal>{

	public AgeBetweenBoundsSpecification(NumericFilteringStrategy ageBetweenBoundsStategy) {
		this.field = "age";
		this.strategy = ageBetweenBoundsStategy;
	}
	
	@Override
	public Criteria getSpecification() {
		List<Criteria> andCriteria = new ArrayList<>();
		andCriteria = this.strategy.apply("age", andCriteria);
		return new Criteria().andOperator(andCriteria.toArray(new Criteria[andCriteria.size()]));
	}

}
