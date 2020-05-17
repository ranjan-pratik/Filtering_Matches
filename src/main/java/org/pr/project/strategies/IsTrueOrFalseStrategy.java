package org.pr.project.strategies;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("isTrueOrFalse")
public class IsTrueOrFalseStrategy implements BooleanFilteringStrategy {

	private final boolean baseOperand;

	@JsonCreator
	public IsTrueOrFalseStrategy(@JsonProperty("value") boolean baseOperand) {
		this.baseOperand = baseOperand;
	}

	@Override
	public boolean apply(final Boolean candidate) {
		return candidate != null ? candidate.equals(baseOperand) : false;
	}

	@Override
	public List<Criteria> apply(final String field,
			final List<Criteria> original) {
		original.add(
				new Criteria().andOperator(Criteria.where(field).exists(true),
						Criteria.where(field).ne(!baseOperand)));
		return original;
	}

}
