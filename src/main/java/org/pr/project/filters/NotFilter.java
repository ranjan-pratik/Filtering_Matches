package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;

public class NotFilter<T> extends AbstractFilter<T> {

	private final AbstractFilter<T> notCandidate;

	public NotFilter(final AbstractFilter<T> notCandidate) {
		this.notCandidate = notCandidate;
	}

	@Override
	public List<Match> runFilter(final List<Match> candidates) {
		final List<Match> fileterdList = notCandidate.runFilter(candidates);
		return candidates.stream().filter(item -> {
			return !fileterdList.contains(item);
		}).collect(Collectors.toList());
	}

}
