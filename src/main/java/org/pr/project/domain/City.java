package org.pr.project.domain;

import java.io.Serializable;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonRootName("city")
@Document
@CompoundIndex(def = "{'name':1, 'position':-1}", name = "city_compound_index")
public class City implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("name")
	private String name;

	@JsonProperty("lat")
	private Double lat;

	@JsonProperty("lon")
	private Double lon;

	@JsonIgnore
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private Double[] position = new Double[2];

	public City() {
	}

	public City(final String name, final double lat, final double lon) {
		this.name = name;
		this.lat = new Double(lat);
		this.lon = new Double(lon);
		position[0] = lat;
		position[1] = lon;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(final Double lat) {
		this.lat = lat;
		position[0] = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(final Double lon) {
		this.lon = lon;
		position[1] = lon;
	}

	public Double[] getPosition() {
		return position;
	}

	protected void setPosition(final Double[] position) {
		this.position = position;
		lat = position[0];
		lon = position[1];
	}
}
