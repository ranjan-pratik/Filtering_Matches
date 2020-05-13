package org.pr.project.strategies;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("possitiveNumber")
public class PossitiveNumberStrategy extends NumberGreaterThanStrategy {

	public PossitiveNumberStrategy() {
		super(0d);
	}

}
