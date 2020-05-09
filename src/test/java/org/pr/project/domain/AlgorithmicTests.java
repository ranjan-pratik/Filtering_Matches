package org.pr.project.domain;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.pr.project.domain.Match;
import org.pr.project.domain.Match.Religion;

public class AlgorithmicTests {

	List<Match> matches;
	
	@Before
	public void initMatchList() {
		matches = new ArrayList<Match>();
		
		Match candidate1 = new Match("Candidate1", "some Job", 23, Religion.Christian);
		matches.add(candidate1);
		
		Match candidate2 = new Match("Candidate2", "some other Job", 56, Religion.Agnostic);
		matches.add(candidate2);
		
		Match candidate3 = new Match("Candidate3", "third Job", 29, Religion.Islam);
		matches.add(candidate3);
		
		Match candidate4 = new Match("Candidate4", "some Job", 45, Religion.Christian);
		matches.add(candidate4);
		
		Match candidate5 = new Match("Candidate5", "some other Job", 33, Religion.Athiest);
		matches.add(candidate5);
		
		Match candidate6 = new Match("Candidate6", "third Job", 51, Religion.Islam);
		matches.add(candidate6);
	}
	
	@Test
	public void checkMatchInit() {
		assertEquals(matches.size(), 6);
	}
}
