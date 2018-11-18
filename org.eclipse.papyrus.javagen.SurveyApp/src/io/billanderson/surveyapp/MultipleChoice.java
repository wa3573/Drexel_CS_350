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
public class MultipleChoice extends Question implements java.io.Serializable {

    private static final long serialVersionUID = 5485613461481054312L;

    private int numberChoices;

    /* responsesSystem are those which should be considered correct. */
    private ArrayList<CorrectResponse> responsesSystem;
    private ArrayList<CorrectResponse> responsesPossible;

    public MultipleChoice() {
	this.responsesSystem = new ArrayList<CorrectResponse>();
	this.responsesPossible = new ArrayList<CorrectResponse>();
    }

    public void setResponsesSystem(ArrayList<CorrectResponse> responses) {
	this.responsesSystem = responses;
    }

    public ArrayList<CorrectResponse> getResponsesSystem() {
	return this.responsesSystem;
    }

    public CorrectResponse getResponseSystem(int index) {
	return this.responsesSystem.get(index);
    }

    public int getNumberChoices() {
	this.numberChoices = this.getResponsesPossible().size();
	return numberChoices;
    }

    public <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
	return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public boolean isCorrect() {
	return listEqualsIgnoreOrder(this.getResponsesUser(), this.getResponsesSystem());
    }

    public ArrayList<CorrectResponse> getResponsesPossible() {
	return responsesPossible;
    }

    public void setResponsesPossible(ArrayList<CorrectResponse> responsesPossible) {
	this.responsesPossible = responsesPossible;
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

	if (index <= this.getNumberChoices()) {
	    result = true;
	}

	return result;
    }

    public String responsesPossibleToString() {
	String str = "";
	ArrayList<CorrectResponse> responses = this.getResponsesPossible();
	for (int i = 0; i < this.getNumberChoices(); i++) {
	    str += this.indexToAlphabet(i + 1) + ") ";
	    str += responses.get(i) + " ";
	}

	return str;
    }

    public String typeToString() {
	String str = "Multiple Choice";

	return str;
    }

    public String toString() {
	String currentAnswer;

	if (this.getResponsesUser().isEmpty()) {
	    currentAnswer = "";
	} else {
	    currentAnswer = this.getResponsesUser().get(0).toString();
	}

	String str = "[Multiple Choice]\t" + this.getPrompt() + " : " + currentAnswer;

	str += "\n\t(Possible answers) " + this.getResponsesPossible();

	if (!this.getResponsesSystem().isEmpty()) {
	    str += "\n\t(Correct answers) " + this.getResponsesSystem();
	}

	return str;
    }

    @Override
    public void accept(QuestionVisitor visitor) {
	visitor.visit(this);
    }

};
