package com.adjuster.service.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.adjuster.service.entity.Campaign;

public class FileUtil {
	public static void createCSVFile(List<Object[]> objects) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File("campaign.csv"));
		
		StringBuilder sb = new StringBuilder();
		sb.append("id");
		sb.append(',');
		sb.append("name");
		sb.append(',');
		sb.append("cpm");
		sb.append(',');
		sb.append("startDate");
		sb.append(',');
		sb.append("views");
		sb.append(',');
		sb.append("clicks");
		sb.append(',');
		sb.append("profit");
		sb.append('\n');

		Campaign campaign = null;
		
		for (Object[] object : objects) {
			campaign = (Campaign) object[0];
			
			sb.append(campaign.getId());
			sb.append(',');
			sb.append(campaign.getName());
			sb.append(',');
			sb.append(campaign.getCpm());
			sb.append(',');
			sb.append(campaign.getStartDate());
			sb.append(',');
			sb.append(object[1] != null ? object[1] : 0);
			sb.append(',');
			sb.append(object[2] != null ? object[1] : 0);
			sb.append(',');
			sb.append(CalculateUtil.calculateProfit(campaign.getCpm(), object[1] != null ? (long) object[1] : 0));
			sb.append('\n');
		}

		pw.write(sb.toString());
		pw.close();
	}
}
