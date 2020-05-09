package org.pr.project.domain.strategies;

public interface IntegerFilteringStrategy extends FilteringStrategy<Integer>{

	boolean apply(Integer candidate);
}
