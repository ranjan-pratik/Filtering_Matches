package org.pr.project.service;

import java.util.List;

import org.pr.project.domain.Match;
import org.pr.project.filters.AbstractFilter;

public interface MatchFilterService {

	List<Match> getAllMatches();

	List<Match> getFilteredMatches(List<AbstractFilter> filters);

}
