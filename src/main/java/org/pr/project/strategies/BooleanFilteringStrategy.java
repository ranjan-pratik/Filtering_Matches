package org.pr.project.strategies;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = IsTrueOrFalseStrategy.class, name = "isTrueOrFalse")})
public interface BooleanFilteringStrategy extends FilteringStrategy<Boolean> {

}
