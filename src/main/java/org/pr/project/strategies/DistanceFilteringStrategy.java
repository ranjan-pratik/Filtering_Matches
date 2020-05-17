package org.pr.project.strategies;

import org.pr.project.domain.City;
import org.springframework.data.mongodb.core.query.NearQuery;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = DistanceWithinRangeStrategy.class, name = "distanceWithInRange")})
public interface DistanceFilteringStrategy extends FilteringStrategy<City> {

	Double getLat();

	Double getLon();

	NearQuery apply();
}
