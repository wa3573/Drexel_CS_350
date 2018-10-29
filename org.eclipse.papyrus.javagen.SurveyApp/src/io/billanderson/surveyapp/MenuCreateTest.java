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

import java.util.ArrayList;

import io.billanderson.surveyapp.CorrectResponseBoolean;
import io.billanderson.surveyapp.Menu;
import io.billanderson.surveyapp.Test;

/************************************************************/
/**
 * 
 */
public class MenuCreateTest extends MenuCreateSurvey {

    private static final ArrayList<String> KNOWN_STRINGS_FALSE = new ArrayList<String>();
    private static final ArrayList<String> KNOWN_STRINGS_TRUE = new ArrayList<String>();

    /*
     * initKnownStrings() is responsible for populating the lists of known
     * strings which are used to verify input.
     */
    private static void initKnownStrings() {
	KNOWN_STRINGS_FALSE.add("F");
	KNOWN_STRINGS_FALSE.add("f");
	KNOWN_STRINGS_FALSE.add("False");
	KNOWN_STRINGS_FALSE.add("false");

	KNOWN_STRINGS_TRUE.add("T");
	KNOWN_STRINGS_TRUE.add("t");
	KNOWN_STRINGS_TRUE.add("True");
	KNOWN_STRINGS_TRUE.add("true");
    }

    public MenuCreateTest() {
	MenuChoice choice1 = new MenuChoice("Add a new T/F question", 1);
	MenuChoice choice2 = new MenuChoice("Add a new multiple choice question", 2);
	MenuChoice choice3 = new MenuChoice("Add a new short answer question", 3);
	MenuChoice choice4 = new MenuChoice("Add a new essay question", 4);
	MenuChoice choice5 = new MenuChoice("Add a new ranking question", 5);
	MenuChoice choice6 = new MenuChoice("Add a new matching question", 6);
	MenuChoice choice7 = new MenuChoice("Return", 7);

	ArrayList<MenuChoice> choices = new ArrayList<MenuChoice>();

	choices.add(choice1);
	choices.add(choice2);
	choices.add(choice3);
	choices.add(choice4);
	choices.add(choice5);
	choices.add(choice6);
	choices.add(choice7);

	this.setChoices(choices);

	initKnownStrings();

	/*
	 * Initialize local copy of a Test and set it as active in the surveyManager.
	 */
	this.setSurvey(new Test());
	getSurveyManager().setSurveyActive(this.getSurvey());
	/*
	 * Initialize local questions. Set them as the Survey's questions.
	 */
	this.setQuestions(new ArrayList<Question>());
	this.getSurvey().setQuestions(this.getQuestions());
	/*
	 * Even if the user has not added questions, they have created a new Survey.
	 * Thus, it is not saved. The surveyManager is set to reflect this.
	 */
	getSurveyManager().setSaved(false);
    }

    /*
     * isKnownResponseBoolen() checks a given user response
     * and returns true if the user's response appears in any of the responses
     * which are known by the system.
     */
    private boolean isKnownResponseBoolean(String response) {
	boolean isKnownResponse = false;

	String temp = response;
	isKnownResponse = KNOWN_STRINGS_FALSE.stream().anyMatch(str -> str.trim().equals(temp))
		| KNOWN_STRINGS_TRUE.stream().anyMatch(str -> str.trim().equals(temp));

	return isKnownResponse;
    }

    /*
     * Once a boolean user response is verified to be known by the system, it can be
     * parsed
     * and converted to a boolean primitive type, to be further used by the program.
     * parseResponseBoolean() performs this task and returns a primitive boolean.
     */
    private boolean parseResponseBoolean(String response) {
	boolean result = false;
	String temp = response;

	if (KNOWN_STRINGS_TRUE.stream().anyMatch(str -> str.trim().equals(temp))) {
	    result = true;
	}

	return result;
    }

    /*
     * (non-Javadoc)
     * @see io.billanderson.surveyapp.MenuCreateSurvey#selectChoice(int)
     * 
     * Choices are:
     * 
     * 1) Add a new T/F question
     * 2) Add a new multiple choice question
     * 3) Add a new short answer question
     * 4) Add a new essay question
     * 5) Add a new ranking question
     * 6) Add a new matching question
     * 7) Return
     */
    public Menu selectChoice(int index) {
	Menu newMenu;
	String userResponse1 = "";
	Prompt newPrompt;
	CorrectResponse newResponse;
	ArrayList<CorrectResponse> newResponses;
	int numberNewResponses;
	CorrectResponse newAnswer;
	ArrayList<CorrectResponse> newAnswers;

	switch (index) {
	case 1:
	    /* True/False */
	    userResponse1 = this.promptForString("Enter the prompt for your true/false question:");
	    newPrompt = new PromptString(userResponse1);
	    TrueFalse newQuestionTF = new TrueFalse();
	    newQuestionTF.setPrompt(newPrompt);

	    /*
	     * Get correct answer in the form of true/false, verify the user
	     * input, then set the question accordingly.
	     */
	    userResponse1 = this.promptForString("Enter the correct answer for your true/false question:");
	    boolean isKnownResponse = isKnownResponseBoolean(userResponse1);

	    while (!isKnownResponse) {
		userResponse1 = this.promptForString("Response not recognized, please enter either 't' or 'f':");
		isKnownResponse = isKnownResponseBoolean(userResponse1);

	    }

	    boolean parsedResponse = this.parseResponseBoolean(userResponse1);
	    CorrectResponseBoolean newAnswerBoolean = new CorrectResponseBoolean(parsedResponse);

	    newAnswers = new ArrayList<CorrectResponse>();
	    newAnswers.add(newAnswerBoolean);
	    newQuestionTF.setResponsesSystem(newAnswers);

	    this.getQuestions().add(newQuestionTF);
	    newMenu = this;
	    break;
	case 2:
	    /* Multiple Choice */
	    userResponse1 = this.promptForString("Enter the prompt for your multiple choice question:");
	    newPrompt = new PromptString(userResponse1);
	    MultipleChoice newQuestionMC = new MultipleChoice();
	    newQuestionMC.setPrompt(newPrompt);

	    newResponses = new ArrayList<CorrectResponse>();
	    newAnswers = new ArrayList<CorrectResponse>();

	    numberNewResponses = this
		    .promptForInteger("Enter the number of choices for your multiple choice question:");

	    /* Get each choice and determine if it is a correct answer */
	    for (int i = 0; i < numberNewResponses; i++) {
		userResponse1 = this.promptForString("Enter Choice #" + (i + 1) + ":");
		newResponse = new CorrectResponseString(userResponse1);
		newResponses.add(newResponse);

		/* Correct answers are added to a separate list */
		if (this.promptBoolean("Is this a correct answer?")) {
		    newAnswers.add(newResponse);
		}
	    }

	    newQuestionMC.setResponsesPossible(newResponses);
	    newQuestionMC.setResponsesSystem(newAnswers);

	    this.getQuestions().add(newQuestionMC);
	    newMenu = this;
	    break;
	case 3:
	    /* Short answer */

	    userResponse1 = this.promptForString("Enter the prompt for your short answer question:");
	    newPrompt = new PromptString(userResponse1);
	    ShortAnswer newQuestionSA = new ShortAnswer();
	    newQuestionSA.setPrompt(newPrompt);

	    userResponse1 = this.promptForString("Enter the correct answer for your short answer question:");

	    newAnswer = new CorrectResponseString(userResponse1);
	    newAnswers = new ArrayList<CorrectResponse>();
	    newAnswers.add(newAnswer);

	    newQuestionSA.setResponsesSystem(newAnswers);

	    this.getQuestions().add(newQuestionSA);
	    newMenu = this;
	    break;
	case 4:
	    /* Essay */
	    userResponse1 = this.promptForString("Enter the prompt for your essay question:");
	    newPrompt = new PromptString(userResponse1);
	    Essay newQuestionE = new Essay();
	    newQuestionE.setPrompt(newPrompt);

	    this.getQuestions().add(newQuestionE);
	    newMenu = this;
	    break;
	case 5:
	    /* Ranking */
	    userResponse1 = this.promptForString("Enter the prompt for your ranking question:");
	    newPrompt = new PromptString(userResponse1);
	    Ranking newQuestionR = new Ranking();
	    newQuestionR.setPrompt(newPrompt);

	    newResponses = new ArrayList<CorrectResponse>();
	    newAnswers = new ArrayList<CorrectResponse>();
	    numberNewResponses = this.promptForInteger("Enter the number of choices for your ranking question:");

	    System.out.println("When entering choices, do so in the order which should "
		    + "be considered correct. They will be randomized for the user.");
	    for (int i = 0; i < numberNewResponses; i++) {
		System.out.println("Enter Choice #" + (i + 1) + ":");
		userResponse1 = this.getReader().nextLine();

		newResponse = new CorrectResponseString(userResponse1);
		newResponses.add(newResponse);
		newAnswers.add(newResponse);
	    }

	    newQuestionR.setResponsesLeftSystem(newResponses);
	    newQuestionR.setResponsesRight(newAnswers);

	    this.getQuestions().add(newQuestionR);
	    newMenu = this;
	    break;
	case 6:
	    /* Matching */
	    userResponse1 = this.promptForString("Enter the prompt for your matching question:");
	    newPrompt = new PromptString(userResponse1);
	    Matching newQuestionM = new Matching();
	    newQuestionM.setPrompt(newPrompt);

	    newResponses = new ArrayList<CorrectResponse>();
	    newAnswers = new ArrayList<CorrectResponse>();
	    numberNewResponses = this.promptForInteger("Enter the number of choices for your matching question:");

	    for (int i = 0; i < numberNewResponses; i++) {
		System.out.println("Enter Left Choice #" + (i + 1) + ":");
		userResponse1 = this.getReader().nextLine();
		newResponse = new CorrectResponseString(userResponse1);
		newResponses.add(newResponse);

		System.out.println("Enter the corresponding correct answer:");
		userResponse1 = this.getReader().nextLine();
		newResponse = new CorrectResponseString(userResponse1);
		newAnswers.add(newResponse);

	    }

	    newQuestionM.setResponsesLeftSystem(newResponses);
	    newQuestionM.setResponsesRight(newAnswers);

	    this.getQuestions().add(newQuestionM);
	    newMenu = this;
	    break;
	case 7:
	    /* Return to home menu */
	    newMenu = new MenuHome();
	    break;

	default:
	    newMenu = null;
	}

	return newMenu;
    }

    public String toString() {
	String out = DIVIDER + "\n\t\t\t || Test Creation Menu ||\n" + DIVIDER + "\n";

	if (!this.getSurvey().getQuestions().isEmpty()) {
	    out += "\nCurrent test: \n" + this.getSurvey() + "\n" + DIVIDER + "\n";
	}

	out += this.getChoicesString();

	return out;
    }

};
