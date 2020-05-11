package org.pr.project.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.pr.project.strategies.NumberBetweenBoundsStrategy;
import org.pr.project.strategies.NumericFilteringStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class AndSpecificationTests {

	@Test
	public void test_and_of_2_ageBetweenValidBounds() {
		NumericFilteringStrategy fileringStartegy = new NumberBetweenBoundsStrategy(new BigDecimal(10), new BigDecimal(20));
		NumericFilteringStrategy fileringStartegy2 = new NumberBetweenBoundsStrategy(new BigDecimal(50), new BigDecimal(90));
		AgeBetweenBoundsSpecification specification = new AgeBetweenBoundsSpecification(fileringStartegy);
		AgeBetweenBoundsSpecification specification2 = new AgeBetweenBoundsSpecification(fileringStartegy2);
		AndSpecification andSpec = new AndSpecification(specification, specification2);
		Criteria crit = andSpec.getSpecification();
		assertThat(crit).isNotNull();
		Criteria expectedAnd =  new Criteria().andOperator(new Criteria().andOperator(Criteria.where("age").gte(10), Criteria.where("age").lte(20))
				,new Criteria().andOperator(Criteria.where("age").gte(50), Criteria.where("age").lte(90)));
		assertThat(crit.getCriteriaObject().toString()).isEqualTo(expectedAnd.getCriteriaObject().toString());
	}
	
}
