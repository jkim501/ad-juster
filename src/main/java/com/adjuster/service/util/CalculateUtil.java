package com.adjuster.service.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalculateUtil {
	public static String calculateProfit(String cpm, long views) {
		DecimalFormat df = new DecimalFormat();
		df.setRoundingMode(RoundingMode.CEILING);	//rounds up the cents
		df.setMaximumFractionDigits(2);				//only 2 decimal places
		
		Double cpmLong = Double.parseDouble(cpm.substring(1));
		
		StringBuilder sb = new StringBuilder();
		sb.append("$");
		sb.append("" + df.format(cpmLong * views / 1000));
		
		return sb.toString();
	}
}
