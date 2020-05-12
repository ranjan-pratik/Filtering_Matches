package org.pr.project.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.pr.project.strategies.NumberGreaterThanStrategy;
import org.pr.project.strategies.PossitiveNumberStrategy;
import org.springframework.data.mongodb.core.query.Criteria;

public class IsInContactSpecificationTests {

	@Test
	public void test_IsInContactSpecification() {
		PossitiveNumberStrategy fileringStartegy = new PossitiveNumberStrategy();
		IsInContactSpecification specification = new IsInContactSpecification(fileringStartegy);
		Criteria crit = specification.getCriteria();
		assertThat(crit).isNotNull();
		List<String> expected = new ArrayList<String>();
		expected.add("contactsExchanged");
		expected.add("$gt=0.0");
		assertThat(crit.getCriteriaObject().toString()).contains(expected);
	}
}
