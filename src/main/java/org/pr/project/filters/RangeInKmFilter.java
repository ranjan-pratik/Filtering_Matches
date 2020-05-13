package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.City;
import org.pr.project.domain.Match;
import org.pr.project.strategies.NumericFilteringStrategy;
import org.pr.project.utils.DistanceOnSurfaceUtility;

public class RangeInKmFilter extends AbstractFilter<Double> {

	private final NumericFilteringStrategy distanceInKmBetweenBoundsFilteringStrategy;
	private final City thisCity;

	public RangeInKmFilter(
			final NumericFilteringStrategy distanceInKmBetweenBoundsStategy,
			final City thisCity) {
		distanceInKmBetweenBoundsFilteringStrategy = distanceInKmBetweenBoundsStategy;
		this.thisCity = thisCity;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		return candidates.stream().filter(c -> {
			if (c.getCity() == null)
				return false;
			// System.out.println(c.getDisplayName() + " :: " +
			// getDistanceOnSurface(c.getCity()) + " KM");
			return distanceInKmBetweenBoundsFilteringStrategy
					.apply(new Double(getDistanceOnSurface(c.getCity())));
		}).collect(Collectors.toList());
	}

	private double getDistanceOnSurface(final City candidate) {
		final double dist = DistanceOnSurfaceUtility.calculateDistanceinKm(
				thisCity.getLat(), thisCity.getLon(), candidate.getLat(),
				candidate.getLon());
		return dist;
	}

}
