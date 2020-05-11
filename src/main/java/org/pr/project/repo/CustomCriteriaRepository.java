package org.pr.project.repo;

import java.util.List;

import org.pr.project.domain.Match;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCriteriaRepository {

	List<Match> findByCustomCriteria(final Criteria criteria);
}
