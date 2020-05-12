package org.pr.project.repo;

import java.util.List;

import org.pr.project.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

public class CustomCityCriteriaRepositoryImpl{
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public List<City> findByCustomCriteria(Criteria criteria) {
		return mongoTemplate.find(new Query().addCriteria(criteria), City.class);
	}
}