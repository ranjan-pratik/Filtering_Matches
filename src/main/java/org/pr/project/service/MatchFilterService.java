package org.pr.project.service;

import java.util.ArrayList;
import java.util.List;

import org.pr.project.domain.Match;
import org.pr.project.filters.AbstractFilter;
import org.pr.project.repo.MatchFilterRepository;
import org.pr.project.specifications.AbstractSpecification;
import org.pr.project.specifications.AndSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchFilterService {

	@Autowired
	MatchFilterRepository matchFilterRepository;

	public List<Match> getAllMatches() {
		final List<Match> allMatches = matchFilterRepository.findAll();
		if (allMatches == null)
			return new ArrayList<Match>();
		return allMatches;
	}

	public List<Match> getFilteredMatches(final List<AbstractFilter> filters) {
		if (filters != null && filters.size() > 0) {
			final List<AbstractSpecification> allSpecification = new ArrayList<>();
			for (final AbstractFilter filterToApply : filters) {
				allSpecification.add(filterToApply.getSpecification());
			}
			final AbstractSpecification allSpecificationsWithAnd = new AndSpecification(
					allSpecification.toArray(new AbstractSpecification[0]));
			return matchFilterRepository.findByCustomCriteria(
					allSpecificationsWithAnd.getCriteria());
		}
		return getAllMatches();
	}

}
