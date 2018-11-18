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

import java.util.*;

import io.billanderson.surveyapp.Question;

/************************************************************/
/**
 * 
 */
public class Matching extends Question implements Gradable {

    private static final long serialVersionUID = -5773921593227064123L;

    /*
     * responsesRight are in a static order determined by the creator of the
     * survey/test.
     */
    private ArrayList<CorrectResponse> responsesRight;
    /*
     * responsesLeftSystem is the list of correct responses corresponding to the
     * order determined by responsesRight.
     */
    private ArrayList<CorrectResponse> responsesLeftSystem;
    private ArrayList<CorrectResponse> responsesLeftUser;
    /*
     * responsesPossible is used for filling a test/survey, it is initialized
     * as a randomized set of responsesLeftSystem
     */
    private ArrayList<CorrectResponse> responsesPossible;
    /*
     * numUserResponses is typically initialized to the size of the responses on the
     * right, indicating the user can choose no more responses than are available to
     * them. However, functionality could be added to request, say, no more than two
     * answers.
     */
    private int numUserResponses;

    public Matching() {
	this.responsesLeftSystem = new ArrayList<CorrectResponse>();
	this.responsesLeftUser = new ArrayList<CorrectResponse>();
	this.responsesPossible = new ArrayList<CorrectResponse>();
	this.responsesRight = new ArrayList<CorrectResponse>();
    }

    public Matching(ArrayList<CorrectResponse> responsesLeftSystem, ArrayList<CorrectResponse> responsesRight) {
	this.responsesLeftSystem = responsesLeftSystem;
	this.responsesRight = responsesRight;
	this.numUserResponses = responsesRight.size();
	this.responsesLeftUser = new ArrayList<CorrectResponse>();
	this.responsesPossible = new ArrayList<CorrectResponse>();
    }

    /**
     * @param index1 first index to swap with second index
     * @param index2 second index to swap with first index
     */
    public void swapResponsesLeftUser(int index1, int index2) {
	Collections.swap(this.responsesLeftUser, index1, index2);
    }

    /**
     * @param index1 first index to swap with second index
     * @param index2 second index to swap with first index
     */
    public void swapResponsesLeftSystem(int index1, int index2) {
	Collections.swap(this.responsesLeftSystem, index1, index2);
    }

    public void setResponsesLeftUser(ArrayList<CorrectResponse> responses) {
	this.responsesLeftUser = responses;
    }
    
    public void setResponsesUser(ArrayList<CorrectResponse> responses) {
	this.responsesLeftUser = responses;
    }

    public ArrayList<CorrectResponse> getResponsesLeftUser() {
	return this.responsesLeftUser;
    }

    public ArrayList<CorrectResponse> getResponsesUser() {
	return this.responsesLeftUser;
    }

    public CorrectResponse getResponseLeftUser(int index) {
	return this.responsesLeftUser.get(index);
    }
    

    public void setResponsesRight(ArrayList<CorrectResponse> responses) {
	this.responsesRight = responses;
	this.numUserResponses = responses.size();
    }

    public ArrayList<CorrectResponse> getResponsesRight() {
	return this.responsesRight;
    }

    public void setResponsesLeftSystem(ArrayList<CorrectResponse> responses) {
	this.responsesLeftSystem = responses;
    }
    
    public void setResponsesSystem(ArrayList<CorrectResponse> responses) {
	this.responsesLeftSystem = responses;
    }

    public ArrayList<CorrectResponse> getResponsesLeftSystem() {
	return this.responsesLeftSystem;
    }

    public CorrectResponse getResponseLeftSystem(int index) {
	return this.responsesLeftSystem.get(index);
    }

    public ArrayList<CorrectResponse> getResponsesSystem() {
	return this.responsesLeftSystem;
    }

    public int getNumUserResponses() {
	return numUserResponses;
    }

    /* indexToAlphabet() converts an integer to the format where 27 = 'AB' */
    public String indexToAlphabet(int index) {
	int asciiA = 65;
	int iterations = index / 26;
	int count = index;
	String str = "";

	while (count > 0) {
	    if (iterations > 0) {
		str += "A";
	    } else {
		int thisChar = count;
		str += (char) (asciiA + thisChar - 1);
	    }

	    iterations -= 1;
	    count -= 26;
	}

	return str;
    }

    /* alphabetToIndex() is the complement to indexToAlphabet. */
    public int alphabetToIndex(String str) {
	int asciiA = 65;
	int length = str.length();
	int iterations = length - 1;
	int count = 0;
	while (iterations >= 0) {
	    if (iterations > 0) {
		count += 26;
	    } else {
		int thisChar = ((int) str.toUpperCase().charAt(length - 1)) - asciiA + 1;
		count += thisChar;
	    }

	    iterations -= 1;
	}

	return count;
    }

    public boolean isValidChoice(String str) {
	boolean result = false;
	int index = this.alphabetToIndex(str);

	if (index <= this.getResponsesLeftSystem().size() && index >= 0) {
	    result = true;
	}

	return result;
    }

    public String responsesToString() {
	String str = "";
	ArrayList<CorrectResponse> responsesLeft = this.getResponsesLeftSystem();
	ArrayList<CorrectResponse> responsesRight = this.getResponsesRight();
	for (int i = 0; i < responsesRight.size(); i++) {
	    String strLeft = this.indexToAlphabet(i + 1) + ") " + responsesLeft.get(i);
	    String strRight = this.indexToAlphabet(i + 1) + ") " + responsesRight.get(i);
	    String line = String.format(" %-38s ||  %-38s", strLeft, strRight);

	    str += line + "\n";
	}
	return str;
    }

    public boolean isCorrect() {
	return this.getResponsesLeftSystem().equals(this.getResponsesLeftUser());
    }

    public String typeToString() {
	String str = "Matching";

	return str;
    }

    public String toString() {
	String currentAnswer = "";

	if (this.getResponsesUser() != null) {
	    if (!this.getResponsesUser().isEmpty()) {
		currentAnswer = this.getResponsesUser().get(0).toString();
	    }
	    
	}

	String str = "[Matching]\t\t" + this.getPrompt() + " : " + currentAnswer;

	str += "\n\t(Possible choices) " + this.getResponsesLeftSystem();

	if (!this.getResponsesRight().isEmpty()) {
	    str += "\n\t(Corresponding matches) " + this.getResponsesRight();
	}

	return str;
    }

    public ArrayList<CorrectResponse> getResponsesPossible() {
	return responsesPossible;
    }

    public void setResponsesPossible(ArrayList<CorrectResponse> responsesPossible) {
	this.responsesPossible = responsesPossible;
    }

    /*
     * initResponsesPossible() initializes responsesPossible as a randomized
     * set of responsesLeftSystem.
     */
    public void initResponsesPossible() {
	ArrayList<CorrectResponse> responses = new ArrayList<CorrectResponse>(this.getResponsesLeftSystem());

	Collections.shuffle(responses);
	this.setResponsesPossible(responses);
    }

    public String responsesPossibleToString() {
	String str = "";
	ArrayList<CorrectResponse> responsesLeft = this.getResponsesPossible();
	ArrayList<CorrectResponse> responsesRight = this.getResponsesRight();
	for (int i = 0; i < responsesRight.size(); i++) {
	    String strLeft = this.indexToAlphabet(i + 1) + ") " + responsesLeft.get(i);
	    String strRight = this.indexToAlphabet(i + 1) + ") " + responsesRight.get(i);
	    String line = String.format(" %-38s ||  %-38s", strLeft, strRight);

	    str += line + "\n";
	}
	return str;
    }

    @Override
    public void accept(QuestionVisitor visitor) {
	visitor.visit(this);

    }
};
