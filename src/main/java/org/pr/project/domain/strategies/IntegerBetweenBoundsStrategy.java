package org.pr.project.domain.strategies;

public class IntegerBetweenBoundsStrategy implements FilteringStrategy {

	private final Integer lowerBound;
	private final Integer upperBound;
	
	public IntegerBetweenBoundsStrategy(int lowerBound, int upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

}
