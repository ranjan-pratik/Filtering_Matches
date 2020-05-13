package org.pr.project.repo;

import java.util.List;

import org.pr.project.domain.Match;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchFilterRepository extends MongoRepository<Match, String>, CustomMatchCriteriaRepository<Match> {

	List<Match> findByCityPositionNear(Point p, Distance d);
	
}
