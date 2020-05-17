package org.pr.project.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("not")
public class NotFilter<T> extends AbstractFilter<T> {

	@JsonProperty("candidate")
	private final AbstractFilter<T> notCandidate;

	@JsonCreator
	public NotFilter(
			@JsonProperty("candidate") final AbstractFilter<T> notCandidate) {
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
