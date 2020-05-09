package org.pr.project.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class City implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private Double lat;
	private Double lon;

	public City(String name, double lat, double lon) {
		this.name = name;
		this.lat = new Double(lat);
		this.lon = new Double(lon);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

}
