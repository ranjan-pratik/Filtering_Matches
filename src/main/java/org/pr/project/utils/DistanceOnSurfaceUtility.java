package org.pr.project.utils;

public class DistanceOnSurfaceUtility {

	public static double calculateDistanceinKm(final double lat1,
			final double lon1, final double lat2, final double lon2) {

		final double tlon1 = Math.toRadians(lon1);
		final double tlon2 = Math.toRadians(lon2);
		final double tlat1 = Math.toRadians(lat1);
		final double tlat2 = Math.toRadians(lat2);

		// Haversine formula
		final double dlon = tlon2 - tlon1;
		final double dlat = tlat2 - tlat1;
		final double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(tlat1)
				* Math.cos(tlat2) * Math.pow(Math.sin(dlon / 2), 2);

		final double c = 2 * Math.asin(Math.sqrt(a));

		// Radius of earth in kilometers. Use 3956
		// for miles
		final double r = 6371;

		// calculate the result
		return (c * r);

	}

}
