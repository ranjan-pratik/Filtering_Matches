package org.pr.project.domain.strategies;

import java.math.BigDecimal;

public interface NumericFilteringStrategy extends FilteringStrategy<BigDecimal>{

	boolean apply(BigDecimal candidate);
}
