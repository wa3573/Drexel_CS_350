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

public class CorrectResponseBoolean extends CorrectResponse {

    private static final long serialVersionUID = 3917886455079420589L;
    public boolean response;

    public void setResponse(boolean response) {
	this.response = response;
    }

    public CorrectResponseBoolean() {
    }

    public CorrectResponseBoolean(boolean response) {
	this.response = response;
    }

    public boolean getResponse() {
	return this.response;
    }

    /**
     * 
     * @param other  another object instance of CorrectResponseBoolean
     * @return  standard compareTo scheme (0 if equal)
     */
    public int compareTo(CorrectResponseBoolean other) {
	Boolean thisValue = new Boolean(this.response);

	return thisValue.compareTo(other.response);
    }

    public String toString() {
	Boolean thisValue = new Boolean(this.getResponse());

	return thisValue.toString();
    }
};
