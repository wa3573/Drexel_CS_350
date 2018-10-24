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

import io.billanderson.surveyapp.CorrectResponse;

import io.billanderson.surveyapp.Prompt;

/************************************************************/
/**
 * Question is the abstract base class from which all types of questions
 * inherit. All questions must be serializable, and each should have unique
 * serialVerisonUID's in order to be saved and loaded properly.
 */
public abstract class Question implements java.io.Serializable {

    private static final long serialVersionUID = 3960269649400933317L;

    private Prompt prompt;
    private ArrayList<CorrectResponse> responsesUser;
    private int maxNumberResponses = 1;

    public Question() {
	/*
	 * Since all questions have the potential for user responses, a new list
	 * is instantiated to hold them. Child classes will typically not instantiate
	 * other owned lists, so they can be compared against null to determine if
	 * those owned parameters have been used at all.
	 */
	this.responsesUser = new ArrayList<CorrectResponse>();
    }

    public void setPrompt(Prompt prompt) {
	this.prompt = prompt;
    }

    public Prompt getPrompt() {
	return this.prompt;
    }

    public int getNumberResponsesUser() {
	return this.responsesUser.size();
    }

    public void setResponseUser(int index, CorrectResponse response) {
	this.responsesUser.set(index, response);
    }

    public CorrectResponse getResponseUser(int index) {
	return this.responsesUser.get(index);
    }

    public void setResponsesUser(ArrayList<CorrectResponse> responses) {
	this.responsesUser = responses;
    }

    public ArrayList<CorrectResponse> getResponsesUser() {
	return this.responsesUser;
    }

    public int getMaxNumberResponses() {
        return maxNumberResponses;
    }

    public void setMaxNumberResponses(int numberResponses) {
        this.maxNumberResponses = numberResponses;
    }

};
