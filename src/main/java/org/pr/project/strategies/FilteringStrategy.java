package org.pr.project.strategies;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.mongodb.core.query.Criteria;

public interface  FilteringStrategy<T> {

	boolean apply(T candidate);
	default List<Criteria> apply(String field, List<Criteria> original) {
		throw new NotImplementedException("This Strategy does not implement this function.");
	};

}
