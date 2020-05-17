package org.pr.project.strategies;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.pr.project.utils.RestCallerUtility;
import org.springframework.data.mongodb.core.query.Criteria;

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

	@Override
	public List<Criteria> apply(String field, List<Criteria> original) {
		throw new NotImplementedException(
				"This Strategy does not support creating criteria.");
	}

}
