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

import io.billanderson.surveyapp.Essay;

/************************************************************/
/**
 * 
 */
public class ShortAnswer extends Essay implements Gradable {

    private static final long serialVersionUID = -3214850409776728492L;
    private int maxLength;
    private ArrayList<CorrectResponse> responsesSystem;

    public ShortAnswer() {
	this.setMaxNumberResponses(1);
	this.responsesSystem = new ArrayList<CorrectResponse>();
    }

    public void setMaxLength(int length) {
	this.maxLength = length;
    }

    public int getMaxLength() {
	return this.maxLength;
    }

    public ArrayList<CorrectResponse> getResponsesSystem() {
	return this.responsesSystem;
    }

    public CorrectResponse getResponseSystem(int index) {
	return this.responsesSystem.get(index);
    }

    public void setResponsesSystem(ArrayList<CorrectResponse> responses) {
	/* Sanity check: should only be one response */
	if (responses.size() > this.getMaxNumberResponses()) {
	    System.err.println("setResponsesSystem(): Error, more than one response provided");
	}

	this.responsesSystem = responses;
    }

    public <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
	return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public boolean isCorrect() {
	return listEqualsIgnoreOrder(this.getResponsesUser(), this.getResponsesSystem());
    }
    
    public String typeToString() {
	String str = "Short Answer";
	
	return str;
    }

    public String toString() {
	String currentAnswer;

	if (this.getResponsesUser().isEmpty()) {
	    currentAnswer = "";
	} else {
	    currentAnswer = this.getResponsesUser().get(0).toString();
	}

	String str = "[Short Answer]\t" + this.getPrompt() + " : " + currentAnswer;

	if (!this.getResponsesSystem().isEmpty()) {
	    str += "\n\t(Correct answer) " + this.getResponsesSystem().get(0);
	}

	return str;
    }
    
    @Override
    public void accept(QuestionVisitor visitor) {
	visitor.visit(this);
    }
};
