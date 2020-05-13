package org.pr.project.repo;

import java.util.List;

import org.pr.project.domain.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomMatchCriteriaRepositoryImpl implements CustomMatchCriteriaRepository<Match> {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public List<Match> findByCustomCriteria(Criteria criteria) {
		return mongoTemplate.find(new Query().addCriteria(criteria), Match.class);
	}
	
	@Override
	public GeoResults<Match> findByCustomGeoRangeWithin(NearQuery nearQuery){
		
		GeoResults<Match> geoRes =  mongoTemplate.geoNear(nearQuery, Match.class);
		return geoRes;
	}
}
