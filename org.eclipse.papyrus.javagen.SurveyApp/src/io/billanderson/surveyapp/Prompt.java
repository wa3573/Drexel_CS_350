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

/************************************************************/
/**
 * The Prompt base class has been left abstract, so as to allow for different
 * types of prompts later on, such as audio or visual.
 */
public abstract class Prompt implements java.io.Serializable {

    private static final long serialVersionUID = 4768135925705676447L;

    /*
     * toString() is overridden for all child classes to display meaningful output.
     */
    public String toString() {
	return new String("");
    }
};
