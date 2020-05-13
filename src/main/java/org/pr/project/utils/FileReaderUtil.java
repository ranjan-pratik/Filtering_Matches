package org.pr.project.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class FileReaderUtil {

	public static String read(final Path filePath) {
		if (filePath == null)
			return null;
		final StringBuilder sb = new StringBuilder();
		try (FileReader reader = new FileReader(filePath.toFile())) {
			int i;
			while ((i = reader.read()) != -1)
				sb.append((char) i);

		} catch (final FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

}
