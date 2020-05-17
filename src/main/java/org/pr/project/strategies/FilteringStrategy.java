package org.pr.project.strategies;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = StringIsNotNullStrategy.class, name = "stringIsNotNull"),
		@JsonSubTypes.Type(value = IsTrueOrFalseStrategy.class, name = "isTrueOrFalse"),
		@JsonSubTypes.Type(value = NumberBetweenBoundsStrategy.class, name = "numberBetweenBounds"),
		@JsonSubTypes.Type(value = NumberGreaterThanStrategy.class, name = "numberGreaterThan"),
		@JsonSubTypes.Type(value = PositiveNumberStrategy.class, name = "positiveNumber"),
		@JsonSubTypes.Type(value = NumberEqualToStrategy.class, name = "numberEqualTo"),
		@JsonSubTypes.Type(value = URIValidatorStrategy.class, name = "uriValidator"),
		@JsonSubTypes.Type(value = DistanceWithinRangeStrategy.class, name = "distanceWithInRange")})
public interface FilteringStrategy<T> {

	@JsonIgnore
	boolean apply(T candidate);

	@JsonIgnore
	List<Criteria> apply(final String field, final List<Criteria> original);

}
