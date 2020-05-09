package org.pr.project.domain.strategies;

public interface  FilteringStrategy<T> {

	boolean apply(T candidate);

}
