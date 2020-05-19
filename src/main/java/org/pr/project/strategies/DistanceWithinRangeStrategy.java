package org.pr.project.strategies;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.pr.project.domain.City;
import org.pr.project.utils.DistanceOnSurfaceUtility;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("distanceWithInRange")
public class DistanceWithinRangeStrategy implements DistanceFilteringStrategy {

	public final double thisLat;
	public final double thisLon;
	public final Double lowerBound;
	public final Double upperBound;

	@JsonCreator
	public DistanceWithinRangeStrategy(
			@JsonProperty("thisLat") final double thisLat,
			@JsonProperty("thisLon") final double thisLon,
			@JsonProperty("lowerBound") final Double lowerBoundInclusive,
			@JsonProperty("upperBound") final Double upperBoundInclusive) {
		lowerBound = lowerBoundInclusive;
		upperBound = upperBoundInclusive;
		this.thisLat = thisLat;
		this.thisLon = thisLon;
	}

	@Override
	public boolean apply(City candidate) {
		if (candidate == null || candidate.getLat() == null
				|| candidate.getLon() == null) {
			return false;
		}
		final double dist = DistanceOnSurfaceUtility.calculateDistanceinKm(
				getLat(), getLon(), candidate.getLat(), candidate.getLon());
		if (lowerBound != null && upperBound != null && lowerBound <= dist
				&& dist <= upperBound) {
			return true;
		}
		return false;
	}

	@Override
	public NearQuery apply() {
		if (lowerBound == null && upperBound == null) return null;
		if (lowerBound == null) {
			return NearQuery.near(new Point(thisLat, thisLon))
					.maxDistance(new Distance(upperBound, Metrics.KILOMETERS));
		} else if (upperBound == null) {
			return NearQuery.near(new Point(thisLat, thisLon))
					.minDistance(new Distance(lowerBound, Metrics.KILOMETERS));
		}
		return NearQuery.near(new Point(thisLat, thisLon))
				.minDistance(new Distance(lowerBound, Metrics.KILOMETERS))
				.maxDistance(new Distance(upperBound, Metrics.KILOMETERS));
	}

	@Override
	public List<Criteria> apply(final String field,
			final List<Criteria> original) {

		throw new NotImplementedException(
				"Geospatial Strategy does not support criteria calculations.");
	}

	@Override
	@JsonIgnore
	public Double getLat() {
		return this.thisLat;
	}

	@Override
	@JsonIgnore
	public Double getLon() {
		return this.thisLon;
	}

}
