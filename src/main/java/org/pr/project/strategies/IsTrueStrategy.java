package org.pr.project.strategies;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("isTrue")
public class IsTrueStrategy implements BooleanFilteringStrategy {

	@Override
	public boolean apply(final Boolean candidate) {
		return candidate != null ? candidate : false;
	}

	@Override
	public List<Criteria> apply(final String field,
			final List<Criteria> original) {
		original.add(
				new Criteria().andOperator(Criteria.where(field).exists(true),
						Criteria.where(field).ne(false)));
		return original;
	}

}
