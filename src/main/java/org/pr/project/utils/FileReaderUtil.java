package org.pr.project.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileReaderUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(FileReaderUtil.class);

	public static String read(final Path filePath) {
		if (filePath == null)
			return null;
		final StringBuilder sb = new StringBuilder();
		try (FileReader reader = new FileReader(filePath.toFile())) {
			int i;
			while ((i = reader.read()) != -1)
				sb.append((char) i);

		} catch (final FileNotFoundException e) {
			logger.warn("Could not find file [" + filePath + "] to read.");
			return null;
		} catch (final IOException e) {
			logger.warn("IO error while rading file[" + filePath + "].");
			return null;
		}
		return sb.toString();
	}

}
