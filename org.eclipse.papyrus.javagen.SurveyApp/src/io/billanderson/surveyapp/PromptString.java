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


import io.billanderson.surveyapp.Prompt;

/************************************************************/
/**
 * PromptString is simply a prompt whose main component is a String.
 */
public class PromptString extends Prompt {
    /**
     * 
     */
    private static final long serialVersionUID = -1862130368893315762L;
    /**
     * 
     */
    private String prompt;

    public PromptString() {

    }

    public PromptString(String prompt) {
	this.prompt = prompt;
    }

    public String toString() {
	return this.prompt;
    }

    public String getPrompt() {
	return this.prompt;
    }

    public void setPrompt(String prompt) {
	this.prompt = prompt;
    }
};
