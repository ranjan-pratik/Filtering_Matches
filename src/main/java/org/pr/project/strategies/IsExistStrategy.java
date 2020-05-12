package org.pr.project.strategies;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

public class IsExistStrategy implements StringFilteringStrategy {

	@Override
	public boolean apply(String candidate) {
		return candidate != null && candidate.length() > 0;
	}
	
	@Override
	public List<Criteria> apply(String field, List<Criteria> original) {
		original.add(Criteria.where(field).ne(null));
		return original;
	}

}
