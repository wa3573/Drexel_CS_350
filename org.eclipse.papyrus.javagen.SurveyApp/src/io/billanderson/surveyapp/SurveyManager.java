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

    /* TODO: add functionality to set destination folder */
    private static final String saveDirPath = "userFiles/";

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

    private void promptForFilePath() {
	String userResponse = "";
	File saveDirectory = new File(saveDirPath);

	if (!saveDirectory.exists()) {
	    saveDirectory.mkdir();
	}

	System.out.print("Enter filename (without extension): ");
	userResponse = reader.nextLine();
	boolean isNotValidFilename = FILE_PATH_PATTERN.matcher(userResponse).find();

	while (isNotValidFilename) {
	    System.out.print("File names may not contain any of these characters \\ / : ? * \" > < | \n"
		    + "Enter filename (without extension): ");
	    userResponse = reader.nextLine();
	    isNotValidFilename = FILE_PATH_PATTERN.matcher(userResponse).find();
	}

	/* Append the appropriate file extension */
	if (this.isSurveyActiveGradable) {
	    this.filePath = saveDirPath + userResponse + ".test";
	} else {
	    this.filePath = saveDirPath + userResponse + ".survey";
	}
    }

    private boolean promptBoolean(String prompt) {
	System.out.print(prompt + " ('y' or 'n'): ");
	char userResponse = reader.nextLine().charAt(0);

	while (userResponse != 'y' && userResponse != 'n') {
	    System.out.print("Please enter a valid choice." + prompt + " ('y' or 'n'): ");
	    userResponse = reader.nextLine().charAt(0);
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

    public void loadActive() {
	Survey loadedSurvey = null; // Storage variable for method's scope

	/* Check/prompt for file path */
	if (this.getFilePath() == null) {
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
	    return;
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
		return;
	    }

	    file.close();
	    in.close();

	} catch (IOException e) {
	    System.err.println("loadActive(): IOException");
	    return;
	}

	System.out.println("File succesfully loaded from: " + this.getFilePath());
	this.isSaved = true;

	this.setSurveyActive(loadedSurvey);

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
};
