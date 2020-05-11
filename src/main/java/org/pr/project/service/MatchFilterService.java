package org.pr.project.service;

import java.util.List;

import org.pr.project.domain.Match;
import org.pr.project.filters.AbstractFilter;
import org.pr.project.repo.MatchFilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchFilterService {
	
	@Autowired
	MatchFilterRepository matchFilterRepository;
	
	public List<Match> getDefaultMatches() {
		return matchFilterRepository.findAll();
	}
	
	public List<Match> applyFilter(List<AbstractFilter> filters) {
		
		List<Match> filteredList = getDefaultMatches();
		for (AbstractFilter filter : filters) {
			filteredList = filter.runFilter(filteredList);
		}
		
		return filteredList;
	}
	
}
