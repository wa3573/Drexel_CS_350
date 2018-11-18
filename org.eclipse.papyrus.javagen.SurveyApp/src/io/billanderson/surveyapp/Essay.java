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

import io.billanderson.surveyapp.Question;

/************************************************************/
/**
 * 
 */
public class Essay extends Question {

    private static final long serialVersionUID = -2330280967136532299L;
    @SuppressWarnings("unused")
    private int numberResponses = 1;

    public Essay() {
    }
    
    public String typeToString() {
	String str = "Essay";
	
	return str;
    }
    

    public String toString() {
	String str = "[Essay]\t\t" + this.getPrompt() + " : ";

	/* If there is a user response, display it below the prompt,
	 * enclosed in quotation marks.
	 */
	if (!this.getResponsesUser().isEmpty()) {
	    String currentAnswer = this.getResponsesUser().get(0).toString();
	    str += "\n\t\"" + currentAnswer + '"';
	}

	return str;
    }

    @Override
    public void accept(QuestionVisitor visitor) {
	visitor.visit(this);
    }
};
