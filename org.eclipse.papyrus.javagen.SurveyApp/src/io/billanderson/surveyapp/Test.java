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
import io.billanderson.surveyapp.Survey;

/************************************************************/
/**
 * 
 */
public class Test extends Survey {
    /**
     * 
     */
    private static final long serialVersionUID = -4569527932381470972L;
    /**
     * 
     */
    private int grade;
    /**
     * 
     */
    private ArrayList<CorrectResponse> correctAnswers;

    /**
     * 
     */
    public Test() {
    }

    /**
     * 
     * @param index
     * @param answer
     * @return
     */
    public void addCorrectAnswer(int index, CorrectResponse answer) {
    }

    /**
     * 
     * @param answer
     * @return
     */
    public void enterAnswer(int index, CorrectResponse answer) {
    }

    /**
     * 
     * @param answers
     * @return
     */
    public void enterAnswers(ArrayList<CorrectResponse> answers) {
    }

    /**
     * 
     * @param index
     * @param answer
     * @return
     */
    public void editAnswer(int index, CorrectResponse answer) {
    }

    /**
     * 
     * @return
     */
    public int getGrade() {
	int denominator = this.correctAnswers.size();
	int numerator = 0;

	for (int i = 0; i < this.correctAnswers.size(); i++) {
	    Question currentQuestion = this.getQuestions().get(i);
	    ArrayList<CorrectResponse> userResponses = currentQuestion.getResponsesUser();

	}

	return 0;
    }

    /**
     * 
     * @param index
     * @return
     */
};
