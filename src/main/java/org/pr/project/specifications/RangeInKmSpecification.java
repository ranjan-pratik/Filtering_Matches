package org.pr.project.specifications;

import org.apache.commons.lang3.NotImplementedException;
import org.pr.project.domain.City;
import org.pr.project.strategies.DistanceFilteringStrategy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;

public class RangeInKmSpecification extends AbstractSpecification<City> {

	public RangeInKmSpecification(
			final DistanceFilteringStrategy distanceRangeBetweenBoundsStrategy) {
		this.field = "distance";
		this.strategy = distanceRangeBetweenBoundsStrategy;
	}

	public NearQuery getNearQuery() {
		if (strategy == null) return null;
		return ((DistanceFilteringStrategy) this.strategy).apply();
	}

	@Override
	public Criteria getCriteria() {
		throw new NotImplementedException(
				"GeoSpatial Specifications do notimplement this mentod.");
	}
}
