package org.pr.project.domain.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;

public class HasImageFilter implements AbstractFilter {

	@Override
	public List<Match> runFilter(List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return (c.getPhotoURI() != null && c.getPhotoURI().length() > 0);
		}).collect(Collectors.toList());
	}

}
