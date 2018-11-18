package io.billanderson.surveyapp;

import java.util.Comparator;

public class FileNameTimestampComparator implements Comparator<String> {
    private int safeParseInt(String str) {
	int result = 0;
	try {
	    result = Integer.parseInt(str);
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	}

	return result;
    }
    
    private long safeParseLong(String str) {
	long result = 0;
	try {
	    result = Long.parseLong(str);
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	}

	return result;
    }
    
    private String timestampFromFilename(String fileName) {
	String[] fileNameTokens = fileName.split("[.]", 0);

	if (fileNameTokens.length < 3) {
	    System.err.println("tabulateActive(): not enough tokens generated from file: '" + fileName);
	    return null;
	}

	return fileNameTokens[1];
    }
    
    @Override
    public int compare(String fileName1, String fileName2) {
	int result;
	long timestamp1 = this.safeParseLong(timestampFromFilename(fileName1));
	long timestamp2 = this.safeParseLong(timestampFromFilename(fileName2));

	if (timestamp1 > timestamp2) {
	    result = -1;
	} else if (timestamp1 < timestamp2) {
	    result = 1;
	} else {
	    result = 0;
	}

	return result;
    }
}
