package org.pr.project.domain.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;

public class IsFavouriteFilter implements AbstractFilter {

	@Override
	public List<Match> runFilter(List<Match> matches) {
		return matches.stream().filter(c -> {
			return (c.getIsFavourite() != null && c.getIsFavourite());
		}).collect(Collectors.toList());
	}
}
