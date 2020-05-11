package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;

public class NotFilter implements AbstractFilter {

	private final AbstractFilter notCandidate;
	
	public NotFilter(AbstractFilter notCandidate) {
		this.notCandidate = notCandidate;
	}
	
	@Override
	public List<Match> runFilter(List<Match> candidates) {
		List<Match> fileterdList = notCandidate.runFilter(candidates);
		return candidates.stream().filter(item -> {
			return !fileterdList.contains(item);
		}).collect(Collectors.toList());
	}

}
