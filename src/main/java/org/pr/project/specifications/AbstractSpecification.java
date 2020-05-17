package org.pr.project.specifications;

import org.pr.project.strategies.FilteringStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public abstract class AbstractSpecification<T> {

	protected String field;
	protected FilteringStrategy<T> strategy;

	public abstract Criteria getCriteria();

	public FilteringStrategy<T> getStrategy() {
		return strategy;
	}
}
