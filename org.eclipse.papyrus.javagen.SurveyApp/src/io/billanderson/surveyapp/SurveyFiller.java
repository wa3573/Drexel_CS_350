package io.billanderson.surveyapp;

import java.util.ArrayList;

public class SurveyFiller extends SurveyUtility {    
    @Override
    public void start() {
	this.fillLoop();
    }

    protected String promptForString(String prompt) {
	System.out.println(prompt);
	String result = this.getReader().nextLine();

	return result;
    }
    
    private void fillMatching(Matching currentMatching, ArrayList<CorrectResponse> newResponses) {
	currentMatching.initResponsesPossible();
	CorrectResponse newResponse = null;

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
    }
    
    private void fillMultipleChoice(MultipleChoice currentMultipleChoice, ArrayList<CorrectResponse> newResponses) {
	CorrectResponse newResponse = null;
	System.out.println(currentMultipleChoice.responsesPossibleToString());

	String userChoice = this.promptForString("Please select a choice: ");

	while (!currentMultipleChoice.isValidChoice(userChoice)) {
	    userChoice = this.promptForString(
		    "Please select a valid choice. (Enter the letter associated with the choice) ");
	}

	int choiceIndex = currentMultipleChoice.alphabetToIndex(userChoice) - 1;
	newResponse = currentMultipleChoice.getResponsesPossible().get(choiceIndex);
	newResponses.add(newResponse);
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
	    System.out.println("\n" + str);

	    if (identificationVisitor.isMatching()) {
		Matching currentMatching = (Matching) currentQuestion;
		this.fillMatching(currentMatching, newResponses);
		
	    } else if (identificationVisitor.isMultipleChoice()) {
		MultipleChoice currentMultipleChoice = (MultipleChoice) currentQuestion;
		this.fillMultipleChoice(currentMultipleChoice, newResponses);

	    } else {
		String userResponse = this.promptForString("Enter response: ");
		newResponse = new CorrectResponseString(userResponse);
		newResponses.add(newResponse);
	    }

	    currentQuestion.setResponsesUser(newResponses);
	}

	getSurveyManager().saveResults(results);

    }
}
