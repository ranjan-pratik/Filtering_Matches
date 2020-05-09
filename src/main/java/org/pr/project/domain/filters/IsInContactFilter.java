package org.pr.project.domain.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.pr.project.domain.Match;

public class IsInContactFilter implements AbstractFilter {

	@Override
	public List<Match> runFilter(List<Match> candidates) {
		return candidates.stream().filter(c -> {
			return (c.getContactsExchanged() != null && c.getContactsExchanged() > 0);
		}).collect(Collectors.toList());
	}
}
