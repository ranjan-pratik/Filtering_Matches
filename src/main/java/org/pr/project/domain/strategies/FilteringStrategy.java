package org.pr.project.domain.strategies;

import org.pr.project.domain.Match;

public interface FilteringStrategy {

	boolean apply(Match candidate);

}
