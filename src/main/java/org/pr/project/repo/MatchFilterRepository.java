package org.pr.project.repo;

import org.pr.project.domain.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchFilterRepository extends MongoRepository<Match, String>, CustomCriteriaRepository {

}
