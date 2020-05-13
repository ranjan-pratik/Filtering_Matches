package org.pr.project.strategies;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.mongodb.core.query.Criteria;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = IsExistStrategy.class, name = "isExist"),
		@JsonSubTypes.Type(value = IsTrueStrategy.class, name = "isTrue"),
		@JsonSubTypes.Type(value = NumberBetweenBoundsStrategy.class, name = "numberBetweenBounds"),
		@JsonSubTypes.Type(value = NumberGreaterThanStrategy.class, name = "numberGreaterThan"),
		@JsonSubTypes.Type(value = PossitiveNumberStrategy.class, name = "possitiveNumber"),
		@JsonSubTypes.Type(value = URIValidatorStrategy.class, name = "uriValidator")})
public interface FilteringStrategy<T> {

	boolean apply(T candidate);
	default List<Criteria> apply(final String field,
			final List<Criteria> original) {
		throw new NotImplementedException(
				"This Strategy does not implement this function.");
	};

}
