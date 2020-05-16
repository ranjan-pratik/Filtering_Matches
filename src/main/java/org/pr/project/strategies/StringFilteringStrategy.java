package org.pr.project.strategies;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = StringIsNotNullStrategy.class, name = "isNotNull"),
		@JsonSubTypes.Type(value = URIValidatorStrategy.class, name = "uriValidator")})
public interface StringFilteringStrategy extends FilteringStrategy<String> {

}
