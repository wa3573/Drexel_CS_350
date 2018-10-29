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
public class Matching extends Question {

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
     * numUserResponses is typically initialized to the size of the responses on the
     * right, indicating the user can choose no more responses than are available to
     * them. However, functionality could be added to request, say, no more than two
     * answers.
     */
    private int numUserResponses;

    public Matching() {
    }

    public Matching(ArrayList<CorrectResponse> responsesLeftSystem, ArrayList<CorrectResponse> responsesRight) {
	this.responsesLeftSystem = responsesLeftSystem;
	this.responsesRight = responsesRight;
	this.numUserResponses = responsesRight.size();
    }

    /**
     * @param index1  first index to swap with second index
     * @param index2  second index to swap with first index
     */
    public void swapResponsesLeftUser(int index1, int index2) {
	Collections.swap(this.responsesLeftUser, index1, index2);
    }

    public void setResponsesLeftUser(ArrayList<CorrectResponse> responses) {
	this.responsesLeftUser = responses;
    }

    public ArrayList<CorrectResponse> getResponsesLeftUser() {
	return this.responsesLeftUser;
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

    public ArrayList<CorrectResponse> getResponsesLeftSystem() {
	return this.responsesLeftSystem;
    }

    public int getNumUserResponses() {
	return numUserResponses;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     * 
     * TODO: add a more attractive (column based, fixed width?) display.
     */
    public String toString() {
	String currentAnswer;

	if (this.getResponsesUser().isEmpty()) {
	    currentAnswer = "";
	} else {
	    currentAnswer = this.getResponsesUser().get(0).toString();
	}

	String str = "[Matching]\t\t" + this.getPrompt() + " : " + currentAnswer;

	str += "\n\t(Possible choices) " + this.getResponsesLeftSystem();

	if (this.getResponsesRight() != null) {
	    str += "\n\t(Corresponding matches) " + this.getResponsesRight();
	}

	return str;
    }
};
