package edu.cmu.square.server.business.implementation;

import org.apache.log4j.Logger;
import org.junit.Test;

public class CombinationGeneratorTest
{
	private static Logger logger = Logger.getLogger(CombinationGeneratorTest.class);
	@Test
	public void testCombinationGenerator()
	{
		String[] elements = {"a", "b", "c", "d"};
		int[] indices;
		CombinationGenerator x = new CombinationGenerator (elements.length, 2);
		StringBuffer combination;
		while (x.hasMore ()) {
		  combination = new StringBuffer ();
		  indices = x.getNext ();
		  for (int i = 0; i < indices.length; i++) {
		    combination.append (elements[indices[i]]);
		  }
		  logger.info (combination.toString ());
		}

	}

}
