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
/**
 * 
 */
public class CorrectResponseString extends CorrectResponse {

    private static final long serialVersionUID = -8674930401831662593L;
    private String response;

    public CorrectResponseString() {
    }

    public CorrectResponseString(String response) {
	this.response = response;
    }

    public String getResponse() {
	return this.response;
    }

    public void setResponse(String response) {
	this.response = response;
    }

    /**
     * 
     * @param other  another object instance of CorrectResponseString
     * @return  standard compareTo scheme (0 if equal)
     */
    public int compareTo(CorrectResponseString other) {
	String thisValue = new String(this.getResponse());

	return thisValue.compareTo(other.getResponse());
    }
    
    @Override
    public boolean equals(Object o) {
	if (this == o) {
	    return true;
	}
	
	if (o == null) {
	    return false;
	}
	
	if (this.getClass() != o.getClass()) {
	    return false;
	}
	
	final CorrectResponseString other = (CorrectResponseString) o;
	
	if (this.getResponse() == null) {
	    if (other.getResponse() != null) {
		return false;
	    }
	} else if (this.getResponse().compareTo(other.getResponse()) != 0){
	    return false;
	}
	
	return true;
	
    }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;

	result = prime * result + ((this.getResponse() == null ? 0 : this.getResponse().hashCode()));

	return result;
    }

    public String toString() {
	return this.response;
    }
};
