package org.pr.project.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestCallerUtility {

	private static final Logger logger = LoggerFactory
			.getLogger(RestCallerUtility.class);

	public static boolean validateURL(final String URL) {
		if (URL == null || URL.length() == 0)
			return false;
		try {
			final HttpClient client = new DefaultHttpClient();
			final HttpGet request = new HttpGet(URL);
			final HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				return true;

		} catch (final IOException e) {
			logger.warn("Error while making rest api call.");
			return false;
		}
		return false;
	}

}
