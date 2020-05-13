package org.pr.project.specifications;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.NearQuery;

public final class InKMRangeSpecification {

	public NearQuery getNearQuery(String field, Point centre, Double lowerBoundInclusive, Double upperBoundInclusive) {
		return NearQuery.near(centre).minDistance(new Distance(lowerBoundInclusive, Metrics.KILOMETERS))
				.maxDistance(new Distance(upperBoundInclusive, Metrics.KILOMETERS));
	}
}
