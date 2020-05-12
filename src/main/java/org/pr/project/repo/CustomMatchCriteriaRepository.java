package org.pr.project.repo;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomMatchCriteriaRepository<Match> {

	List<Match> findByCustomCriteria(final Criteria criteria);
	
}
