package io.billanderson.surveyapp;

import java.util.ArrayList;

/*
 * SurveyModifier provides functionality for user modification of existing
 * survey's, as specified in the assignment.
 */
public class SurveyModifier extends SurveyUtility {
    @Override
    public void start() {
	this.modifyLoop();
    }

    protected String promptForString(String prompt) {
	System.out.println(prompt);
	String result = this.getReader().nextLine();

	return result;
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

    protected int promptForInteger(String prompt) {
	System.out.println(prompt);
	int result = 0;

	while (result == 0) {
	    String nextLine = this.getReader().nextLine();

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

    /*
     * Prompt user and only accept integers within the range of selections present.
     */
    private int promptForQuestionInteger(String prompt) {
	int result = this.promptForInteger(prompt);
	int numChoices = getSurveyManager().getSurveyActive().getQuestions().size();

	/* Make sure the user response is within the range of possible choices. */
	while (result < 1 | result > numChoices) {
	    if (result == 0) {
		return 0;
	    }
	    result = this.promptForInteger("Please enter a valid selection:");
	}

	return result;
    }

    private void modifyMultipleChoice(MultipleChoice multipleChoice, boolean isTest) {
	QuestionIdentificationVisitor identificationVisitor = new QuestionIdentificationVisitor();
	multipleChoice.accept(identificationVisitor);

	/* TrueFalse questions choices are not modifiable. */
	if (!(identificationVisitor.isTrueFalse())) {
	    if (this.promptBoolean("Do you wish to modify choices?")) {
		System.out.println("Current choices:");
		System.out.println(multipleChoice.responsesPossibleToString());

		String userChoice = this.promptForString("Which choice do you want to modify? ");

		while (!multipleChoice.isValidChoice(userChoice)) {
		    userChoice = this.promptForString(
			    "Please select a valid choice. (Enter the uppercase letter associated with the choice) ");
		}

		int choiceIndex = multipleChoice.alphabetToIndex(userChoice) - 1;

		String newChoice = this.promptForString("Enter new choice: ");
		CorrectResponse newAnswer = new CorrectResponseString(newChoice);

		multipleChoice.getResponsesPossible().set(choiceIndex, newAnswer);
	    }
	}

	if (isTest) {
	    if (this.promptBoolean("Do you wish to modify the correct answer?")) {
		System.out.println("Current choices:");
		System.out.println(multipleChoice.responsesPossibleToString());

		String userChoice = this.promptForString("Which choice do you want to be the correct answer? ");

		while (!multipleChoice.isValidChoice(userChoice)) {
		    userChoice = this.promptForString(
			    "Please select a valid choice. (Enter the letter associated with the choice) ");
		}

		int choiceIndex = multipleChoice.alphabetToIndex(userChoice) - 1;
		CorrectResponse correctResponse = multipleChoice.getResponsesPossible().get(choiceIndex);

		ArrayList<CorrectResponse> responses = new ArrayList<CorrectResponse>();
		responses.add(correctResponse);
		/* FIXME: Next line probably unnecessary */
		multipleChoice.setResponsesSystem(responses);

	    }
	}
    }

    private void modifyMatching(Matching matching, boolean isTest) {
	QuestionIdentificationVisitor identificationVisitor = new QuestionIdentificationVisitor();
	matching.accept(identificationVisitor);

	if (this.promptBoolean("Do you wish to modify the left side choices?")) {
	    System.out.println("Current choices:");
	    System.out.println(matching.responsesToString());

	    String userChoice = this.promptForString("Which choice do you want to modify? ");

	    while (!matching.isValidChoice(userChoice)) {
		userChoice = this.promptForString(
			"Please select a valid choice. (Enter the letter associated with the choice) ");
	    }

	    int choiceIndex = matching.alphabetToIndex(userChoice) - 1;

	    String newChoice = this.promptForString("Enter new choice: ");
	    CorrectResponse newAnswer = new CorrectResponseString(newChoice);

	    matching.getResponsesLeftSystem().set(choiceIndex, newAnswer);

	    if (identificationVisitor.isRanking()) {
		matching.setResponsesRight(matching.getResponsesLeftSystem());
	    }
	}

	/* Ranking questions right choices are not modifiable. */
	if (!(identificationVisitor.isRanking())) {
	    if (this.promptBoolean("Do you wish to modify the right side choices?")) {
		System.out.println("Current choices:");
		System.out.println(matching.responsesToString());

		String userChoice = this.promptForString("Which choice do you want to modify? ");

		while (!matching.isValidChoice(userChoice)) {
		    userChoice = this.promptForString(
			    "Please select a valid choice. (Enter the letter associated with the choice) ");
		}

		int choiceIndex = matching.alphabetToIndex(userChoice) - 1;

		String newChoice = this.promptForString("Enter new choice: ");
		CorrectResponse newAnswer = new CorrectResponseString(newChoice);

		matching.getResponsesRight().set(choiceIndex, newAnswer);
	    }
	}

	if (isTest) {
	    if (this.promptBoolean("Do you wish to modify the correct order?")) {
		System.out.println("Current choices:");
		System.out.println(matching.responsesToString());

		String userChoice = this.promptForString("Please select first choice to swap: ");

		while (!matching.isValidChoice(userChoice)) {
		    userChoice = this.promptForString(
			    "Please select a valid choice. (Enter the letter associated with the choice) ");
		}

		int choiceIndex1 = matching.alphabetToIndex(userChoice) - 1;

		userChoice = this
			.promptForString("Please select which choice to swap choice '" + userChoice + "' with: ");

		while (!matching.isValidChoice(userChoice)) {
		    userChoice = this.promptForString(
			    "Please select a valid choice. (Enter the letter associated with the choice) ");
		}

		int choiceIndex2 = matching.alphabetToIndex(userChoice) - 1;

		matching.swapResponsesLeftSystem(choiceIndex1, choiceIndex2);
	    }
	}
    }

    private void modifyLoop() {
	Survey activeSurvey = getSurveyManager().getSurveyActive();
	boolean isTest = getSurveyManager().isSurveyActiveGradable();
	QuestionIdentificationVisitor identificationVisitor = new QuestionIdentificationVisitor();

	int index = 1;

	while (true) {
	    System.out.println("Current: \n" + activeSurvey);
	    index = promptForQuestionInteger("What question do you wish to modify? (Or type 'RETURN' to return)");

	    if (index == 0) {
		break;
	    }

	    /* Account for array indexes starting at 0. */
	    index -= 1;

	    Question selectedQuestion = activeSurvey.getQuestion(index);
	    selectedQuestion.accept(identificationVisitor);

	    System.out.println("Current prompt: " + selectedQuestion.getPrompt());
	    if (this.promptBoolean("Do you wish to modify the prompt?")) {
		System.out.println("Current prompt: " + selectedQuestion.getPrompt());
		String newPrompt = this.promptForString("Enter a new prompt: ");
		selectedQuestion.setPrompt(new PromptString(newPrompt));
	    }

	    if (identificationVisitor.isMultipleChoice()) {
		/* Unique behavior for MultipleChoice */
		MultipleChoice selectedMultipleChoice = (MultipleChoice) selectedQuestion;

		this.modifyMultipleChoice(selectedMultipleChoice, isTest);

	    } else if (identificationVisitor.isMatching()) {
		/* Unique behavior for Matching */
		Matching selectedMatching = (Matching) selectedQuestion;

		this.modifyMatching(selectedMatching, isTest);

	    } else {
		if (isTest && identificationVisitor.isGradable()) {
		    if (this.promptBoolean("Do you wish to modify the correct answer?")) {
			String str = this.promptForString("Please enter the new correct answer");

			ArrayList<CorrectResponse> responses = new ArrayList<CorrectResponse>();
			CorrectResponseString correctResponse = new CorrectResponseString(str);
			responses.add(correctResponse);
			((Gradable) selectedQuestion).setResponsesSystem(responses);
		    }

		}
	    }
	}

    }
}
