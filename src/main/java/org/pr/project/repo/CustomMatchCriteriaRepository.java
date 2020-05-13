package org.pr.project.repo;

import java.util.List;

import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomMatchCriteriaRepository<Match> {

	List<Match> findByCustomCriteria(final Criteria criteria);

	GeoResults<Match> findByCustomGeoRangeWithin(NearQuery nearQuery);

}
