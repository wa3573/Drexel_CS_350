/*******************************************************************************
 * Copyright (C) 2018 William J. Anderson
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package io.billanderson.surveyapp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import io.billanderson.surveyapp.Survey;
import io.billanderson.surveyapp.Test;

/************************************************************/

public class SurveyManager {
    /**
     * SurveyManager is a singleton class. Any class which needs access to it will
     * call SurveyManager.getInstance() to obtain the statically allocated instance
     * of it. This allows for an active work item as the user traverses different
     * menus.
     */

    /* Note: at this time, filePath only supports the file name itself */
    private String filePath;
    private Survey surveyActive;
    private String surveyActiveName;
    private boolean isSurveyActiveGradable;
    private boolean isSaved;
    private static SurveyManager instance = null;
    private Scanner reader;

    /*
     * While UNIX is less restrictive with characters allowed in file names, the
     * more restrictive requirements of the Windows OS has been chosen, as it will
     * work for the both cases. This pattern detects any invalid characters based on
     * that scheme.
     */
    private static final Pattern FILE_PATH_PATTERN = Pattern.compile("[\\/:?*\"><|]");
    private static final String SAVE_DIR_PATH = "userFiles/";
    private static final int NUMBER_DISPLAY_RECENT = 5;

    private SurveyManager() {
	reader = new Scanner(System.in);
    }

    public static SurveyManager getInstance() {
	if (instance == null) {
	    instance = new SurveyManager();
	}

	return instance;
    }

    public void start() {
	this.surveyActive = null;
	this.filePath = null;
	this.isSurveyActiveGradable = false;
	this.isSaved = true;
    }

    protected String promptForString(String prompt) {
	System.out.println(prompt);
	String result = this.getReader().nextLine();

	return result;
    }

    private void promptForFilePath() {
	String userResponse = "";
	File saveDirectory = new File(SAVE_DIR_PATH);

	if (!saveDirectory.exists()) {
	    saveDirectory.mkdir();
	}

	System.out.print("Enter filename (without extension): ");
	userResponse = getReader().nextLine();
	boolean isNotValidFilename = FILE_PATH_PATTERN.matcher(userResponse).find();

	while (isNotValidFilename) {
	    System.out.print("File names may not contain any of these characters \\ / : ? * \" > < | \n"
		    + "Enter filename (without extension): ");
	    userResponse = getReader().nextLine();
	    isNotValidFilename = FILE_PATH_PATTERN.matcher(userResponse).find();
	}

	this.setSurveyActiveName(userResponse);

	/* Append the appropriate file extension */
	if (this.isSurveyActiveGradable()) {
	    this.filePath = SAVE_DIR_PATH + userResponse + ".test";
	} else {
	    this.filePath = SAVE_DIR_PATH + userResponse + ".survey";
	}
    }

    protected boolean promptBoolean(String prompt) {
	System.out.print(prompt + " ('y' or 'n'): ");
	String nextLine = getReader().nextLine();
	char userResponse = nextLine.charAt(0);

	/*
	 * Request valid output if the user did not enter 'y', 'n', or if they entered
	 * more than one character
	 */
	while ((userResponse != 'y' && userResponse != 'n') || nextLine.length() > 1) {
	    System.out.print("Please enter a valid choice.\n" + prompt + " ('y' or 'n'): ");
	    nextLine = getReader().nextLine();
	    userResponse = nextLine.charAt(0);
	}

	if (userResponse == 'y') {
	    return true;
	} else {
	    return false;
	}
    }

    private boolean isFilePathWrongExtension() {
	boolean result = false;

	if (this.isSurveyActiveGradable()) {
	    /* Active is test */
	    if (this.getFilePath().contains(".survey")) {
		result = true;
	    }
	} else {
	    /* Active is survey */
	    if (this.getFilePath().contains(".test")) {
		result = true;
	    }
	}

	return result;
    }

    public void saveActive() {
	/*
	 * If the manager's file path is not already set, or the file path has the wrong
	 * extensions, prompt the user for it.
	 * Otherwise, ask the user if they would like to keep the current path. If not,
	 * prompt for the new path.
	 */
	if (this.getFilePath() == null || this.isFilePathWrongExtension()) {
	    this.promptForFilePath();
	} else {
	    String prompt = "Current path is '" + this.getFilePath() + "'\n" + "Keep current path?";

	    if (!this.promptBoolean(prompt)) {
		this.promptForFilePath();
	    }
	}

	/* Check if the file exists and ask to overwrite */
	File outputFile = new File(this.getFilePath());

	if (outputFile.exists()) {
	    if (!this.promptBoolean(
		    "File '" + this.getFilePath() + "' already exists.\n" + "Would you like to overwrite it?")) {
		return;
	    }
	}

	/* Write the file */
	try {
	    FileOutputStream file = new FileOutputStream(this.getFilePath());
	    ObjectOutputStream out = new ObjectOutputStream(file);

	    out.writeObject(this.getSurveyActive());

	    out.close();
	    file.close();

	    System.out.println("File succesfully written as: " + this.getFilePath());
	    this.isSaved = true;

	} catch (IOException e) {
	    System.err.println("saveActive(): IOException");
	}
    }

    /*
     * @return 0 if successful, > 0 otherwise
     */
    public int loadActive() {
	Survey loadedSurvey = null; // Storage variable for method's scope

	/*
	 * If the manager's file path is not already set, or the file path has the wrong
	 * extensions, prompt the user for it.
	 * Otherwise, ask the user if they would like to keep the current path. If not,
	 * prompt for the new path.
	 */
	if (this.getFilePath() == null || this.isFilePathWrongExtension()) {
	    this.promptForFilePath();
	} else {
	    String prompt = "Current path is '" + this.getFilePath() + "', keep current path?";

	    if (!this.promptBoolean(prompt)) {
		this.promptForFilePath();
	    }
	}

	/* Make sure file exists */
	File inputFile = new File(this.getFilePath());
	if (!inputFile.exists()) {
	    System.out.println("File '" + this.getFilePath() + "' does not exist.");
	    return 1;
	}

	/* Open file, load into this.surveyActive, catch IOException */
	try {
	    FileInputStream file = new FileInputStream(this.getFilePath());
	    ObjectInputStream in = new ObjectInputStream(file);

	    /* Catch ClassNotFoundException */
	    try {
		if (this.isSurveyActiveGradable()) {
		    loadedSurvey = (io.billanderson.surveyapp.Test) in.readObject();
		} else {
		    loadedSurvey = (io.billanderson.surveyapp.Survey) in.readObject();
		}
	    } catch (ClassNotFoundException e) {
		if (this.isSurveyActiveGradable()) {
		    System.err.println("Class 'Test' not found");
		} else {
		    System.err.println("Class 'Survey' not found");
		}

		file.close();
		in.close();
		return 2;
	    }

	    file.close();
	    in.close();

	} catch (IOException e) {
	    System.err.println("loadActive(): IOException");
	    return 3;
	}

	System.out.println("File succesfully loaded from: " + this.getFilePath());
	this.isSaved = true;

	this.setSurveyActive(loadedSurvey);
	return 0;

    }

    public int saveResults(Survey survey) {
	String folderPath = SAVE_DIR_PATH + "results/";

	if (this.isSurveyActiveGradable()) {
	    folderPath += "test/";
	} else {
	    folderPath += "survey/";
	}

	new File(folderPath).mkdirs();

	Date currentTime = new Date();
	String resultPath = folderPath + surveyActiveName + "." + currentTime.getTime() + ".results";

	/* Check if the file exists and ask to overwrite */
	File outputFile = new File(resultPath);

	while (outputFile.exists()) {
	    currentTime = new Date();
	    resultPath = folderPath + surveyActiveName + "." + currentTime.getTime() + ".results";
	    outputFile = new File(resultPath);

	}

	/* Write the file */
	try {
	    FileOutputStream file = new FileOutputStream(resultPath);
	    ObjectOutputStream out = new ObjectOutputStream(file);

	    out.writeObject(survey);

	    out.close();
	    file.close();

	    System.out.println("Results succesfully written as: " + resultPath);
	    this.isSaved = true;

	} catch (IOException e) {
	    System.err.println("saveActive(): IOException");
	    return 1;
	}

	return 0;
    }

    public Survey loadSurveyFromPath(String path) {
	Survey loadedSurvey = null; // Storage variable for method's scope
	/* Open file, load survey, catch IOException */
	try {
	    FileInputStream file = new FileInputStream(path);
	    ObjectInputStream in = new ObjectInputStream(file);

	    /* Catch ClassNotFoundException */
	    try {
		if (this.isSurveyActiveGradable()) {
		    loadedSurvey = (Test) in.readObject();
		} else {
		    loadedSurvey = (Survey) in.readObject();
		}
	    } catch (ClassNotFoundException e) {
		if (this.isSurveyActiveGradable()) {
		    System.err.println("Class 'Test' not found");
		} else {
		    System.err.println("Class 'Survey' not found");
		}

		file.close();
		in.close();
		return null;
	    }

	    file.close();
	    in.close();

	} catch (IOException e) {
	    System.err.println("loadActive(): IOException");
	    return null;
	}

	return loadedSurvey;
    }

    
    public void tabulateActive() {
	/* Obtain all results files for active survey */

	File folder;
	Survey surveyActive = this.getSurveyActive();
	ArrayList<Survey> results = new ArrayList<Survey>();

	if (this.isSurveyActiveGradable()) {
	    folder = new File(SAVE_DIR_PATH + "results/test/");
	} else {
	    folder = new File(SAVE_DIR_PATH + "results/survey/");
	}

	/* For each file in the results folder */
	for (final File fileEntry : folder.listFiles()) {
	    /* Don't look at directories */
	    if (!fileEntry.isDirectory()) {
		String fileName = fileEntry.getName();
		String[] fileNameTokens = fileName.split("[.]", 0);

		if (fileNameTokens.length < 3) {
		    System.err.println("tabulateActive(): not enough tokens generated");
		    return;
		}

		String surveyName = fileNameTokens[0];
		String surveyTimestamp = fileNameTokens[1];

		if (surveyName.compareTo(this.getSurveyActiveName()) == 0) {
		    /* DEBUG */
//		    System.out.println("Loading results with timestamp: " + surveyTimestamp);

		    Survey loadedSurvey = this.loadSurveyFromPath(fileEntry.getPath());
		    results.add(loadedSurvey);
		}

	    }
	}

	Tabulation tabulation = new Tabulation(surveyActive, results);
	tabulation.tabulate();

    }

    private String timestampFromFilename(String fileName) {
	String[] fileNameTokens = fileName.split("[.]", 0);

	if (fileNameTokens.length < 3) {
	    System.err.println("tabulateActive(): not enough tokens generated from file: '" + fileName);
	    return null;
	}

	return fileNameTokens[1];
    }

    private String surveyNameFromFilename(String fileName) {
	String[] fileNameTokens = fileName.split("[.]", 0);

	if (fileNameTokens.length < 3) {
	    System.err.println("tabulateActive(): not enough tokens generated from file: '" + fileName);
	    return null;
	}

	return fileNameTokens[0];
    }

    private ArrayList<String> sortFileNamesByTimestamp(ArrayList<String> fileNames) {
	ArrayList<String> result = new ArrayList<String>(fileNames);

	result.sort(new FileNameTimestampComparator());

	return result;
    }

    public String recentResultsToString(ArrayList<Survey> results, HashMap<Survey, String> resultFileNames) {
	String str = "";
	int i_max = NUMBER_DISPLAY_RECENT;
	if (results.size() < i_max) {
	    i_max = results.size();
	}

	if (i_max == 0) {
	    return null;
	} else {
	    str += "There are " + i_max + " recent results: \n";
	    for (int i = 0; i < i_max; i++) {
		Survey thisSurvey = results.get(i);
		str += resultFileNames.get(thisSurvey) + "\n";
	    }
	}

	return str;
    }

    public String recentResultsToString(ArrayList<String> results) {
	String str = "";
	int i_max = NUMBER_DISPLAY_RECENT;
	if (results.size() < i_max) {
	    i_max = results.size();
	}

	if (i_max == 0) {
	    return str;
	} else {
	    str += "There are " + i_max + " recent results (most recent first): \n";
	    str += "0) OTHER \n";
	    for (int i = 0; i < i_max; i++) {
		str += (i + 1) + ") " + results.get(i) + "\n";
	    }
	}

	return str;
    }

    protected int promptForInteger(String prompt) {
	System.out.println(prompt);
	int result = 0;

	while (result == 0) {
	    String nextLine = this.reader.nextLine();

	    /* function returns 0 if the user wishes to quit */
	    if (nextLine.compareTo("RETURN") == 0) {
		return 0;
	    }

	    try {

		result = Integer.parseInt(nextLine);
	    } catch (NumberFormatException e) {
		// e.printStackTrace();
		System.out.println("Please enter an integer");
	    }
	}

	return result;
    }

    protected int promptForIntegerInRange(String prompt, int min, int max) {
	int result = promptForInteger(prompt);

	while (result < min || result > max) {
	    result = promptForInteger("Please enter an integer in the range [" + min + " - " + max + "]: ");
	}

	return result;
    }

    public void gradeActive() {
	/* Obtain all results files for active survey */
	File folder;
	ArrayList<String> fileNames = new ArrayList<String>();

	if (this.isSurveyActiveGradable()) {
	    folder = new File(SAVE_DIR_PATH + "results/test/");
	} else {
	    System.err.println("gradeActive(); active work item is not gradable");
	    return;
	}

	/* For each file in the results folder */
	for (final File fileEntry : folder.listFiles()) {
	    /* Don't look at directories */
	    if (!fileEntry.isDirectory()) {
		String fileName = fileEntry.getName();
		String surveyName = this.surveyNameFromFilename(fileName);

		if (surveyName.compareTo(this.getSurveyActiveName()) == 0) {
		    fileNames.add(fileEntry.getName());
		}
	    }
	}

	fileNames = this.sortFileNamesByTimestamp(fileNames);

	String resultPath = null;
	Test selectedResult = null;

	/* Prompt user for which results they would like to grade. */
	if (fileNames.isEmpty()) {
	    System.out.println("There are no results for this survey");
	    return;
	} else {
	    System.out.println(this.recentResultsToString(fileNames));

	    int userResponse = this
		    .promptForIntegerInRange("Please enter the number corresponding to the results you wish to grade, "
			    + "or enter '0' to enter a file name: ", 0, fileNames.size());
	    
	    if (userResponse == 0) {
		/* If the user selects 0, they wish to enter a file name. */
		String userFileName = this.promptForString("Enter file name (without extension): ");
		while (!fileNames.contains(userFileName + ".results")) {
		    userFileName = this.promptForString("The file name '" + userFileName + ".results" + "' is not associated with the active test.\n"
		    	+ "Please enter a valid file name: ");
		}

		resultPath = SAVE_DIR_PATH + "results/test/" + userFileName + ".results";
		
	    } else {
		/* Otherwise they have selected a recent results file. */
		String userFileName = fileNames.get(userResponse - 1);
		resultPath = SAVE_DIR_PATH + "results/test/" + userFileName;
	    }
	    
	    selectedResult = (Test) this.loadSurveyFromPath(resultPath);
	}

	System.out.println("\nSelected results: \n" + selectedResult);
	double grade = selectedResult.getGrade();
	String formattedGrade = String.format("%3.2f", grade);
	
	System.out.println("Grade = " + formattedGrade + "%");

    }

    public String getFilePath() {
	return filePath;
    }

    public void setFilePath(String filePath) {
	this.filePath = filePath;
    }

    public Survey getSurveyActive() {
	return surveyActive;
    }

    public void setSurveyActive(Survey surveyActive) {
	this.surveyActive = surveyActive;

	/*
	 * If new surveyActive is a Test, update isSurveyActiveGradable
	 * to true. Otherwise update to false. This is used to allow for ease
	 * of checking whether the active work item is a Survey or a Test
	 * without using 'instanceof'.
	 */
	if (surveyActive instanceof Test) {
	    this.isSurveyActiveGradable = true;
	} else {
	    this.isSurveyActiveGradable = false;
	}
    }

    public boolean isSurveyActiveGradable() {
	return isSurveyActiveGradable;
    }

    public void setSurveyActiveGradable(boolean isSurveyActiveGradable) {
	this.isSurveyActiveGradable = isSurveyActiveGradable;
    }

    public boolean isSaved() {
	return isSaved;
    }

    public void setSaved(boolean isSaved) {
	this.isSaved = isSaved;
    }

    public Scanner getReader() {
	return reader;
    }

    public void setReader(Scanner reader) {
	this.reader = reader;
    }

    public String getSurveyActiveName() {
	return surveyActiveName;
    }

    public void setSurveyActiveName(String surveyActiveName) {
	this.surveyActiveName = surveyActiveName;
    }
};
