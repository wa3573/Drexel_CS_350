package io.billanderson.surveyapp;

import java.util.Scanner;

/*
 * As conceived, a SurveyUtility is one which utilizes the active survey
 * in the SurveyManager. For example, SurveyFiller or SurveyManager.
 * All concrete subclasses must implement a start() method.
 */
public abstract class SurveyUtility {
    private static SurveyManager surveyManager;
    private Scanner reader;
    
    public SurveyUtility() {
	this.setReader(new Scanner(System.in));
	setSurveyManager(SurveyManager.getInstance());
    }
    
    public Scanner getReader() {
	return reader;
    }

    public void setReader(Scanner reader) {
	this.reader = reader;
    }

    public static SurveyManager getSurveyManager() {
	return surveyManager;
    }

    public static void setSurveyManager(SurveyManager surveyManager) {
	SurveyUtility.surveyManager = surveyManager;
    }
    
    public abstract void start();
}
