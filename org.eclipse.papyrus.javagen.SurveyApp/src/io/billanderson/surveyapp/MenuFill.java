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

/************************************************************/
/**
 * TODO: implement filling menu
 */
public class MenuFill extends MenuHome {
    /**
     * 
     */
    public MenuFill() {
    }
    
    private void fillLoop() {
	Survey results;
	QuestionIdentificationVisitor identificationVisitor = new QuestionIdentificationVisitor();

	if (getSurveyManager().isSurveyActiveGradable()) {
	    results = new Test(getSurveyManager().getSurveyActive());
	} else {
	    results = new Survey(getSurveyManager().getSurveyActive());
	}

	for (int i = 0; i < results.getNumberQuestions(); i++) {
	    Question currentQuestion = results.getQuestion(i);
	    currentQuestion.accept(identificationVisitor);
	    ArrayList<CorrectResponse> newResponses = new ArrayList<CorrectResponse>();
	    CorrectResponse newResponse = null;
	    String str = (i + 1) + ") [" + currentQuestion.typeToString() + "] " + currentQuestion.getPrompt();
	    System.out.println(str);

	    if (identificationVisitor.isMatching()) {
		Matching currentMatching = (Matching) currentQuestion;
		currentMatching.initResponsesPossible();

		System.out.println(currentMatching.responsesPossibleToString());

		for (int j = 0; j < currentMatching.getResponsesPossible().size(); j++) {

		    int choiceIndex;
		    boolean cont = false;

		    String userChoice = this.promptForString("Please select choice number " + (j + 1) + " : ");

		    while (!currentMatching.isValidChoice(userChoice) || !cont) {
			if (!currentMatching.isValidChoice(userChoice)) {
			    userChoice = this.promptForString(
				    "Please select a valid choice. (Enter the letter associated with the choice) ");
			}

			choiceIndex = currentMatching.alphabetToIndex(userChoice) - 1;
			newResponse = currentMatching.getResponsesPossible().get(choiceIndex);

			if (newResponses.contains(newResponse)) {
			    cont = false;
			    userChoice = this
				    .promptForString("Please select a choice which has not already been selected.");
			} else {
			    cont = true;
			}

		    }

		    choiceIndex = currentMatching.alphabetToIndex(userChoice) - 1;
		    newResponse = currentMatching.getResponsesPossible().get(choiceIndex);
		    newResponses.add(newResponse);
		}

		currentMatching.setResponsesLeftUser(newResponses);
	    } else if (identificationVisitor.isMultipleChoice()) {
		MultipleChoice currentMultipleChoice = (MultipleChoice) currentQuestion;

		System.out.println(currentMultipleChoice.responsesPossibleToString());

		String userChoice = this.promptForString("Please select a choice: ");

		while (!currentMultipleChoice.isValidChoice(userChoice)) {
		    userChoice = this.promptForString(
			    "Please select a valid choice. (Enter the letter associated with the choice) ");
		}

		int choiceIndex = currentMultipleChoice.alphabetToIndex(userChoice) - 1;
		newResponse = currentMultipleChoice.getResponsesPossible().get(choiceIndex);
		newResponses.add(newResponse);
	    } else {

		String userResponse = this.promptForString("Enter response: ");
		newResponse = new CorrectResponseString(userResponse);
		newResponses.add(newResponse);
	    }

	    currentQuestion.setResponsesUser(newResponses);
	}

	getSurveyManager().saveResults(results);

    }
};
