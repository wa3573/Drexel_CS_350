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
import java.util.Collections;

import io.billanderson.surveyapp.Matching;

/************************************************************/
/**
 * 
 */
public class Ranking extends Matching {

    private static final long serialVersionUID = 1343910620528243717L;

    /**
     * 
     */
    public Ranking() {
	this.setResponsesRight(this.getResponsesLeftSystem());
    }
    
    public String responsesToString() {
	String str = "";
	ArrayList<CorrectResponse> responsesLeft = this.getResponsesLeftSystem();
	for (int i = 0; i < responsesLeft.size(); i++) {
	    String strLeft = this.indexToAlphabet(i + 1) + ") " + responsesLeft.get(i);
	    String line = String.format(" %-38s", strLeft);

	    str += line + "\n";
	}
	return str;
    }
    
    /*
     * initResponsesPossible() initializes responsesPossible as a randomized
     * set of responsesLeftSystem.
     */
    public void initResponsesPossible() {
	ArrayList<CorrectResponse> responses = new ArrayList<CorrectResponse>(this.getResponsesLeftSystem());
	
	Collections.shuffle(responses);
	this.setResponsesPossible(responses);
	this.setResponsesRight(this.getResponsesLeftSystem());
    }
    
    public String responsesPossibleToString() {
	String str = "";
	ArrayList<CorrectResponse> responses = this.getResponsesPossible();
	for (int i = 0; i < this.getResponsesPossible().size(); i++) {
	    str += this.indexToAlphabet(i + 1) + ") ";
	    str += responses.get(i) + " ";
	}
	
	return str;
    }
    
    
    public String typeToString() {
	String str = "Ranking";
	
	return str;
    }
    
    public String toString() {
	String currentAnswer = "";

	if (!this.getResponsesUser().isEmpty()) {
	    currentAnswer = this.getResponsesUser().toString();
	}

	String str = "[Ranking]\t\t" + this.getPrompt() + " : " + currentAnswer;

	str += "\n\t(Possible choices) " + this.getResponsesLeftSystem();

	if (!this.getResponsesRight().isEmpty()) {
	    str += "\n\t(Correct order of choices) " + this.getResponsesRight();
	}

	return str;
    }
    
    @Override
    public void accept(QuestionVisitor visitor) {
	visitor.visit(this);

    }
};
