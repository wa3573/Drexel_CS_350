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
    public Test() {
    }

    public Test(Test test) {
	this.setQuestions(test.getQuestions());
    }

    public Test(Survey survey) {
	this.setQuestions(survey.getQuestions());
    }

    /*
     * getGrade() returns a double value out of 100, reflecting the percentage of
     * correct answers.
     */
    public double getGrade() {
	int numberQuestions = this.getNumberQuestions();
	double denominator = numberQuestions * 10.0;
	double numerator = 0.0;

	QuestionGradeVisitor gradeVisitor = new QuestionGradeVisitor();

	for (int i = 0; i < numberQuestions; i++) {
	    Question currentQuestion = this.getQuestion(i);

	    currentQuestion.accept(gradeVisitor);

	    if (gradeVisitor.isCorrect()) {
		numerator += 10;
	    }
	}

	double result = (numerator / denominator) * 100;
	return result;
    }

};
