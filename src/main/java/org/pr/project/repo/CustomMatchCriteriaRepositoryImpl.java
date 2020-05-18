package org.pr.project.repo;

import java.util.List;

import org.pr.project.domain.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomMatchCriteriaRepositoryImpl
		implements
			CustomMatchCriteriaRepository<Match> {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomMatchCriteriaRepositoryImpl.class);

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<Match> findByCustomCriteria(final Criteria criteria) {
		logger.debug("Finding records by custom query.");
		return mongoTemplate.find(new Query().addCriteria(criteria),
				Match.class);
	}

	@Override
	public GeoResults<Match> findByCustomGeoRangeWithin(
			final NearQuery nearQuery) {
		logger.debug("Finding records by geospatial query.");
		logger.debug("Attepting to run geospatial query - "
				+ nearQuery.toDocument().toString());
		this.ensureGeoIndex();
		final GeoResults<Match> geoRes = mongoTemplate.geoNear(nearQuery,
				Match.class);
		logger.debug("Geospatial query returned [" + geoRes.getContent().size()
				+ "] records.");
		return geoRes;
	}

	private void ensureGeoIndex() {
		mongoTemplate.indexOps(Match.class)
				.ensureIndex(new GeospatialIndex("city.position")
						.typed(GeoSpatialIndexType.GEO_2DSPHERE));
	}
}
