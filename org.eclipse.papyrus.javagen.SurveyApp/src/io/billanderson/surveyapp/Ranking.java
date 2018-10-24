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
    }

    public String toString() {
	String currentAnswer;

	if (this.getResponsesUser().isEmpty()) {
	    currentAnswer = "";
	} else {
	    currentAnswer = this.getResponsesUser().get(0).toString();
	}

	String str = "[Ranking]\t\t" + this.getPrompt() + " : " + currentAnswer;

	str += "\n\t(Possible choices) " + this.getResponsesLeftSystem();

	if (this.getResponsesRight() != null) {
	    str += "\n\t(Correct order of choices) " + this.getResponsesRight();
	}

	return str;
    }
};
