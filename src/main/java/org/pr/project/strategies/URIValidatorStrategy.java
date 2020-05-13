package org.pr.project.strategies;

import org.pr.project.utils.RestCallerUtility;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("uriValidator")
public class URIValidatorStrategy implements StringFilteringStrategy {

	private final String URI;

	@JsonCreator
	public URIValidatorStrategy(final String URI) {
		this.URI = URI;
	}

	@Override
	public boolean apply(final String candidate) {
		return RestCallerUtility.validateURL(candidate);
	}

}
