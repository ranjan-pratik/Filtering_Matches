package org.pr.project.strategies;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("positiveNumber")
public class PositiveNumberStrategy extends NumberGreaterThanStrategy {

	public PositiveNumberStrategy() {
		super(0d);
	}

}
