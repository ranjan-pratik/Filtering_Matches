package org.pr.project.domain.filters;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.City;
import org.pr.project.domain.Match;
import org.pr.project.domain.strategies.NumericFilteringStrategy;
import org.pr.project.utils.DistanceOnSurfaceUtility;

public class DistanceInKmFilter implements AbstractFilter {

	private final NumericFilteringStrategy distanceInKmBetweenBoundsFilteringStrategy;
	private final City thisCity;

	public DistanceInKmFilter(NumericFilteringStrategy distanceInKmBetweenBoundsStategy, City thisCity) {
		this.distanceInKmBetweenBoundsFilteringStrategy = distanceInKmBetweenBoundsStategy;
		this.thisCity = thisCity;
	}

	@Override
	public List<Match> runFilter(List<Match> candidates) {
		return candidates.stream().filter(c -> {
			if (c.getCity() == null)
				return false;
			System.out.println(c.getDisplayName() + " :: " + getDistanceOnSurface(c.getCity()) + " KM");
			return distanceInKmBetweenBoundsFilteringStrategy.apply(new BigDecimal(getDistanceOnSurface(c.getCity())));
		}).collect(Collectors.toList());
	}

	private double getDistanceOnSurface(City candidate) {
		double dist = DistanceOnSurfaceUtility.calculateDistanceinKm(this.thisCity.getLat(), this.thisCity.getLon(),
				candidate.getLat(), candidate.getLon());
		return dist;
	}

}
