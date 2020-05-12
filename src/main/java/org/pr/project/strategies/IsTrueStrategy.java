package org.pr.project.strategies;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

public class IsTrueStrategy implements BooleanFilteringStrategy {

	@Override
	public boolean apply(Boolean candidate) {
		return candidate != null ? candidate : false;
	}
	
	@Override
	public List<Criteria> apply(String field, List<Criteria> original) {
		original.add(new Criteria().andOperator(
		        Criteria.where(field).exists(true),
		        Criteria.where(field).ne(false)
		    ));
		return original;
	}

}
