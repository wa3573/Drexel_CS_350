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

import io.billanderson.surveyapp.CorrectResponse;

/************************************************************/

public class CorrectResponseInteger extends CorrectResponse {

    private static final long serialVersionUID = 447124610794183434L;
    private int response;

    public CorrectResponseInteger() {
    }

    public CorrectResponseInteger(int response) {
	this.response = response;
    }

    public int getResponse() {
	return this.response;
    }

    /**
     * 
     * @param other another object instance of CorrectResponseInteger
     * @return standard compareTo scheme (0 if equal)
     */
    public int compareTo(CorrectResponseInteger other) {
	Integer thisValue = new Integer(this.response);

	return thisValue.compareTo(other.response);
    }

    public void setResponse(int response) {
	this.response = response;
    }
    
    public String toString() {
	Integer thisValue = new Integer(this.response);
	
	return thisValue.toString();
    }
};
