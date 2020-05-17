package org.pr.project.service;

import java.util.ArrayList;
import java.util.List;

import org.pr.project.domain.Match;
import org.pr.project.filters.AbstractFilter;
import org.pr.project.filters.DistanceRangeInKmFilter;
import org.pr.project.repo.MatchFilterRepository;
import org.pr.project.specifications.AbstractSpecification;
import org.pr.project.specifications.AndSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service()
public class MatchFilterServiceImpl implements MatchFilterService {

	private static final Logger logger = LoggerFactory
			.getLogger(MatchFilterServiceImpl.class);

	@Autowired
	MatchFilterRepository matchFilterRepository;

	@Override
	@Transactional
	public List<Match> getAllMatches() {
		logger.info("No filters applied. Getting all matches.");
		final List<Match> allMatches = matchFilterRepository.findAll();
		if (allMatches == null)
			return new ArrayList<Match>();
		return allMatches;
	}

	@Override
	@Transactional
	public List<Match> getFilteredMatches(final List<AbstractFilter> filters) {
		if (filters != null && filters.size() > 0) {
			logger.info("Getting filtered matches.");
			final List<AbstractSpecification> allSpecification = new ArrayList<>();
			boolean geoSpatialSpecificationPresent = false;
			DistanceRangeInKmFilter distanceRangeFilter = null;
			for (final AbstractFilter filterToApply : filters) {
				if (filterToApply instanceof DistanceRangeInKmFilter) {
					distanceRangeFilter = (DistanceRangeInKmFilter) filterToApply;
				} else {
					allSpecification.add(filterToApply.getSpecification());
				}
			}
			final AbstractSpecification allSpecificationsWithAnd = new AndSpecification(
					allSpecification.toArray(new AbstractSpecification[0]));
			logger.debug(
					"Processed final criteria ::" + allSpecificationsWithAnd
							.getCriteria().getCriteriaObject().toString());
			if (distanceRangeFilter == null) {
				return matchFilterRepository.findByCustomCriteria(
						allSpecificationsWithAnd.getCriteria());
			} else {
				NearQuery nearQry = distanceRangeFilter.getSpecification()
						.getNearQuery();
				if (allSpecification.size() > 0) {
					Query allOthers = new Query(
							allSpecificationsWithAnd.getCriteria());
					nearQry.query(allOthers);
				}
				GeoResults<Match> geoResults = matchFilterRepository
						.findByCustomGeoRangeWithin(nearQry);

				List<Match> result = new ArrayList<Match>();
				List<GeoResult<Match>> geoResultContent = geoResults
						.getContent();
				for (GeoResult<Match> m : geoResultContent) {
					result.add(m.getContent());
				}
				return result;
			}
		}
		return getAllMatches();
	}

}
