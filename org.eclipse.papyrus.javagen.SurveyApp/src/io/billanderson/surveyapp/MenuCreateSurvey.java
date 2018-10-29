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

import io.billanderson.surveyapp.Menu;
import io.billanderson.surveyapp.Survey;

/************************************************************/

public class MenuCreateSurvey extends Menu {

    private String filePath;
    private Survey survey;
    private ArrayList<Question> questions;
    private Boolean isGradable;

    public MenuCreateSurvey() {
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

	/*
	 * Initialize local copy of a Survey and set it as active in the surveyManager.
	 */
	this.setSurvey(new Survey());
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

    protected int promptForInteger(String prompt) {
	System.out.println(prompt);
	int result = 0;

	/* Catch any invalid (non-integer) input and request valid input. */
	while (result == 0) {
	    try {
		result = Integer.parseInt(this.getReader().nextLine());
	    } catch (NumberFormatException e) {
		// e.printStackTrace();
		System.out.println("Please enter an integer");
	    }
	}

	return result;
    }

    protected String promptForString(String prompt) {
	System.out.println(prompt);
	String result = this.getReader().nextLine();

	return result;
    }

    /*
     * Prompts for n correct responses, one at a time, then returns a new list
     * containing those responses, in order.
     */
    private ArrayList<CorrectResponse> promptForCorrectResponses(int n) {

	String userResponse;
	CorrectResponse newResponse;
	ArrayList<CorrectResponse> newResponses = new ArrayList<CorrectResponse>();

	for (int i = 0; i < n; i++) {
	    userResponse = this.promptForString("Enter Choice #" + (i + 1) + ":");
	    newResponse = new CorrectResponseString(userResponse);
	    newResponses.add(newResponse);
	}

	return newResponses;
    }

    /*
     * (non-Javadoc)
     * 
     * @see io.billanderson.surveyapp.Menu#selectChoice(int)
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
	Question newQuestion;
	Prompt newPrompt;
	CorrectResponse newResponse;
	ArrayList<CorrectResponse> newResponses;
	int numberNewResponses;

	switch (index) {
	case 1:
	    /* True/False */
	    userResponse1 = this.promptForString("Enter the prompt for your true/false question:");
	    newPrompt = new PromptString(userResponse1);
	    newQuestion = new TrueFalse();
	    newQuestion.setPrompt(newPrompt);

	    this.getQuestions().add(newQuestion);
	    newMenu = this;
	    break;
	case 2:
	    /* Multiple Choice */
	    userResponse1 = this.promptForString("Enter the prompt for your multiple choice question:");
	    newPrompt = new PromptString(userResponse1);
	    MultipleChoice newQuestionMC = new MultipleChoice();
	    newQuestionMC.setPrompt(newPrompt);

	    numberNewResponses = this
		    .promptForInteger("Enter the number of choices for your multiple choice question:");
	    newResponses = promptForCorrectResponses(numberNewResponses);
	    newQuestionMC.setResponsesPossible(newResponses);

	    this.getQuestions().add(newQuestionMC);
	    newMenu = this;
	    break;
	case 3:
	    /* Short answer */
	    userResponse1 = this.promptForString("Enter the prompt for your short answer question:");
	    newPrompt = new PromptString(userResponse1);
	    newQuestion = new ShortAnswer();
	    newQuestion.setPrompt(newPrompt);

	    this.getQuestions().add(newQuestion);
	    newMenu = this;
	    break;
	case 4:
	    /* Essay */
	    userResponse1 = this.promptForString("Enter the prompt for your essay question:");
	    newPrompt = new PromptString(userResponse1);
	    newQuestion = new Essay();
	    newQuestion.setPrompt(newPrompt);

	    this.getQuestions().add(newQuestion);
	    newMenu = this;
	    break;
	case 5:
	    /* Ranking */
	    userResponse1 = this.promptForString("Enter the prompt for your ranking question:");
	    newPrompt = new PromptString(userResponse1);
	    Ranking newQuestionR = new Ranking();
	    newQuestionR.setPrompt(newPrompt);

	    numberNewResponses = this.promptForInteger("Enter the number of choices for your ranking question:");
	    newResponses = promptForCorrectResponses(numberNewResponses);
	    newQuestionR.setResponsesLeftSystem(newResponses);

	    this.getQuestions().add(newQuestionR);
	    newMenu = this;
	    break;
	case 6:
	    /* Matching */
	    userResponse1 = this.promptForString("Enter the prompt for your matching question:");
	    newPrompt = new PromptString(userResponse1);
	    Matching newQuestionM = new Matching();
	    newQuestionM.setPrompt(newPrompt);

	    numberNewResponses = this.promptForInteger("Enter the number of choices for your matching question:");

	    /* Get/set left choices. */
	    newResponses = new ArrayList<CorrectResponse>();

	    for (int i = 0; i < numberNewResponses; i++) {
		userResponse1 = this.promptForString("Enter Left Choice #" + (i + 1) + ":");
		newResponse = new CorrectResponseString(userResponse1);
		newResponses.add(newResponse);
	    }

	    newQuestionM.setResponsesLeftSystem(newResponses);

	    /* Get/set right choices. */
	    newResponses = new ArrayList<CorrectResponse>();

	    for (int i = 0; i < numberNewResponses; i++) {
		userResponse1 = this.promptForString("Enter Right Choice #" + (i + 1) + ":");
		newResponse = new CorrectResponseString(userResponse1);
		newResponses.add(newResponse);
	    }

	    newQuestionM.setResponsesRight(newResponses);

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
	String out = DIVIDER + "\n\t\t\t || Survey Creation Menu ||\n" + DIVIDER + "\n";

	/*
	 * If there are questions present, display the current survey to the user when
	 * printing the menu.
	 */
	if (!this.getSurvey().getQuestions().isEmpty()) {
	    out += "\nCurrent survey: \n" + this.getSurvey() + "\n" + DIVIDER + "\n";
	}

	out += this.getChoicesString();

	return out;
    }

    public String getFilePath() {
	return filePath;
    }

    public void setFilePath(String filePath) {
	this.filePath = filePath;
    }

    public Survey getSurvey() {
	return survey;
    }

    public void setSurvey(Survey survey) {
	this.survey = survey;
    }

    public Boolean isGradable() {
	return isGradable;
    }

    public void setGradable(Boolean isGradable) {
	this.isGradable = isGradable;
    }

    public ArrayList<Question> getQuestions() {
	return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
	this.questions = questions;
    }

};
