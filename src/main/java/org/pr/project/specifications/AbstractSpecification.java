package org.pr.project.specifications;

import org.pr.project.strategies.FilteringStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public abstract class AbstractSpecification<T> {

	protected String field;
	protected FilteringStrategy<T> strategy;

	public abstract Criteria getSpecification();

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public FilteringStrategy<T> getStrategy() {
		return strategy;
	}

	public void setStrategy(FilteringStrategy<T> strategy) {
		this.strategy = strategy;
	}

}
