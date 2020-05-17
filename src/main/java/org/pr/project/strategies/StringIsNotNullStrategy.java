package org.pr.project.strategies;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("stringIsNotNull")
public class StringIsNotNullStrategy implements StringFilteringStrategy {

	@Override
	public boolean apply(final String candidate) {
		return candidate != null && candidate.length() > 0;
	}

	@Override
	public List<Criteria> apply(final String field,
			final List<Criteria> original) {
		original.add(Criteria.where(field).ne(null));
		return original;
	}

}
