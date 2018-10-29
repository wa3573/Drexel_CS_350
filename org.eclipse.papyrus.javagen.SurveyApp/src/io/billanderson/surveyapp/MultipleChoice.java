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
    }

    public void setResponsesSystem(ArrayList<CorrectResponse> responses) {
	this.responsesSystem = responses;
    }

    public ArrayList<CorrectResponse> getResponsesSystem() {
	return this.responsesSystem;
    }

    public int getNumberChoices() {
	return numberChoices;
    }

    public boolean isCorrect() {
	boolean result = true;

	/* Check that each system response is contained in the user responses */
	for (int i = 0; i < this.getResponsesSystem().size(); i++) {
	    if (!this.getResponsesUser().contains(this.getResponsesSystem().get(i))) {
		result = false;
	    }
	}

	/* Check that each user response is contained in the system responses */
	for (int i = 0; i < this.getResponsesUser().size(); i++) {
	    if (!this.getResponsesSystem().contains(this.getResponsesUser().get(i))) {
		result = false;
	    }
	}

	return result;
    }

    public ArrayList<CorrectResponse> getResponsesPossible() {
	return responsesPossible;
    }

    public void setResponsesPossible(ArrayList<CorrectResponse> responsesPossible) {
	this.responsesPossible = responsesPossible;
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

	if (this.responsesSystem != null) {
	    str += "\n\t(Correct answers) " + this.getResponsesSystem();
	}

	return str;
    }

};
