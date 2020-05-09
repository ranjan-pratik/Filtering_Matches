package org.pr.project.domain.strategies;

import org.pr.project.utils.RestCallerUtility;

public class URIValidatorStrategy implements StringFilteringStrategy {

	private final String URI;
	
	public URIValidatorStrategy(String URI) {
		this.URI = URI;
	}
	
	@Override
	public boolean apply(String candidate) {
		return RestCallerUtility.validateURL(candidate);
	}

}
