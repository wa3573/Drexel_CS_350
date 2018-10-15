// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package SurveyApp;

import java.util.*;
import SurveyApp.MenuManager;
import SurveyApp.SurveyManager;
import SurveyApp.*;

/************************************************************/
/**
 * 
 */
public class Main {
	public static String DIVIDER = "====================" +
			"====================" + "====================";
	
	public static String APPLICATION_TITLE = "Survey and Test Utility v0.1";
	public static String AUTHOR_NAME = "Bill Anderson";
	
	public static void printDivider() {
		System.out.println(DIVIDER);
	}
	
	public static void printHeader() {
		printDivider();
		System.out.println("\t" +APPLICATION_TITLE);
		System.out.println("\tBy: " + AUTHOR_NAME);
		printDivider();
		System.out.println();	
	}
	/**
	 * 
	 */
	private SurveyManager surveyManager;
	/**
	 * 
	 */
	private MenuManager menuManager;

	/**
	 * 
	 * @param args 
	 * @return 
	 */
	public static void main(String [] args) {
		printHeader();
		
		Output mainOutput = OutputTerminal.getInstance();

		Survey testSurvey = new Survey();
		TrueFalse testQuestion1 = new TrueFalse();
		Prompt testPrompt1 = new PromptString("The sky is blue.");
		CorrectResponseBoolean testAnswer1 = new CorrectResponseBoolean(true);
		ArrayList<CorrectResponseBoolean> testAnswers1 = new ArrayList<CorrectResponseBoolean>();
		testAnswers1.add(testAnswer1);
		
		testQuestion1.setPrompt(testPrompt1);
		testQuestion1.setResponsesSystem(testAnswers1);
		testQuestion1.display(mainOutput);
		
	}
};
