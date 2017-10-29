package com.adjuster.service.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.adjuster.service.entity.Campaign;

public class FileUtil {

	// Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";
	// CSV file header
	private static final Object[] FILE_HEADER = { "id", "name", "cpm", "startDate", "views", "clicks", "profit" };

	public static void createCSVFile(List<Object[]> objects) throws FileNotFoundException {
		Campaign campaign = null;

		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		// Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
		try {
			// initialize FileWriter object
			fileWriter = new FileWriter("campaign.csv");
			// initialize CSVPrinter object
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			// Create CSV file header
			csvFilePrinter.printRecord(FILE_HEADER);
			
			for (Object[] object : objects) {
				campaign = (Campaign) object[0];
				
				List<String> csvEntry = new ArrayList<String>();
				
				csvEntry.add("" + campaign.getId());
				csvEntry.add(campaign.getName());
				csvEntry.add(campaign.getCpm());
				csvEntry.add(campaign.getStartDate().toString());
				csvEntry.add(object[1] != null ? "" + object[1] : "0");
				csvEntry.add(object[2] != null ? "" + object[2] : "0");
				csvEntry.add(CalculatorUtil.calculateProfit(campaign.getCpm(), object[1] != null ? (long) object[1] : 0));
				csvFilePrinter.printRecord(csvEntry);
			}
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
				e.printStackTrace();
			}
		}

	}
}
