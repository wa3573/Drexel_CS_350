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
import io.billanderson.surveyapp.MultipleChoice;


/************************************************************/

public class TrueFalse extends MultipleChoice {

    private static final long serialVersionUID = -4970733003633443256L;

    public TrueFalse() {
	ArrayList<CorrectResponse> responsesPossible = this.getResponsesPossible();
	CorrectResponse responseTrue = new CorrectResponseBoolean(true);
	CorrectResponse responseFalse = new CorrectResponseBoolean(false);
	
	responsesPossible.add(responseTrue);
	responsesPossible.add(responseFalse);
	
	this.setResponsesPossible(responsesPossible);
    }

    public String typeToString() {
	String str = "True/False";
	
	return str;
    }
    
    public String toString() {
	String currentAnswer;

	if (this.getResponsesUser().isEmpty()) {
	    currentAnswer = "";
	} else {
	    currentAnswer = this.getResponsesUser().get(0).toString();
	}

	String str = "[True/False]\t\t" + this.getPrompt() + " : " + currentAnswer;

	str += "\n\t(Possible answers) " + this.getResponsesPossible();
	
	if (!this.getResponsesSystem().isEmpty()) {
	    CorrectResponse correctAnswer = this.getResponsesSystem().get(0);
	    str += "\n\t(Correct answer) " + correctAnswer;
	}

	return str;
    }
    
    @Override
    public void accept(QuestionVisitor visitor) {
	visitor.visit(this);
    }
};
