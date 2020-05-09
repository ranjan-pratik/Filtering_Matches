package org.pr.project.domain.filters;

import java.util.List;

import org.pr.project.domain.Match;

public interface AbstractFilter {
	
	public List<Match> runFilter(List<Match> candidates);

}
