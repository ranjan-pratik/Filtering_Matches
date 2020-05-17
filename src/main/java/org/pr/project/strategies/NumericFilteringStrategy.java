package org.pr.project.strategies;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = NumberBetweenBoundsStrategy.class, name = "numberBetweenBounds"),
		@JsonSubTypes.Type(value = NumberGreaterThanStrategy.class, name = "numberGreaterThan"),
		@JsonSubTypes.Type(value = PositiveNumberStrategy.class, name = "positiveNumber"),
		@JsonSubTypes.Type(value = NumberEqualToStrategy.class, name = "numberEqualTo")})
public interface NumericFilteringStrategy extends FilteringStrategy<Double> {

}
