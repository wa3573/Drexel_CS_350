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

import io.billanderson.surveyapp.Question;

import java.io.*;

/************************************************************/
/**
 * Since Survey is serializable, all items contained within also must implement
 * java.io.Serializable.
 */
public class Survey implements java.io.Serializable {

    private static final long serialVersionUID = -2996912927221626336L;

    private String author;
    /**
     * 
     */
    private String title;
    /**
     * 
     */
    private ArrayList<Question> questions;
    /**
     * 
     */
    @SuppressWarnings("unused")
    private int numberQuestions;

    public Survey() {
	this.questions = new ArrayList<Question>();
    }

    public void addQuestion(Question question) {
	this.questions.add(question);
    }

    public void replaceQuestion(int index, Question question) {
	this.questions.set(index, question);
    }

    public void removeQuestion(int index) {
	this.questions.remove(index);
    }

    public Question getQuestion(int index) {
	return this.questions.get(index);
    }

    /*
     * TODO: implement fill() and tabulate()
     */
    public void fill() {
    }

    public void tabulate() {
    }

    public String getAuthor() {
	return author;
    }

    public void setAuthor(String author) {
	this.author = author;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public ArrayList<Question> getQuestions() {
	return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
	this.questions = questions;
    }

    public String toString() {
	String out = "";
	for (int i = 0; i < this.getQuestions().size(); i++) {
	    out += (i + 1) + ") " + this.getQuestion(i) + "\n";

	}

	return out;
    }
};
