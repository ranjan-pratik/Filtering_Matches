package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;

public class IsFavouriteFilter implements AbstractFilter {

	@Override
	public List<Match> runFilter(List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return (c.getIsFavourite() != null && c.getIsFavourite());
		}).collect(Collectors.toList());
	}
}
