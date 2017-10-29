package com.adjuster.service.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CalculatorUtilTest {
	@Test
	public void testCalculateProfit() {
		assertTrue(CalculatorUtil.calculateProfit("$1.00", 1).equals("$0.01"));
	}
	
	@Test
	public void testCalculateProfitCpmNull() {
		assertTrue(CalculatorUtil.calculateProfit(null, 1).equals("$0.00"));
	}
	
	@Test
	public void testCalculateProfitViewsNegative() {
		assertTrue(CalculatorUtil.calculateProfit("$1.00", -100).equals("$0.00"));
	}
}
