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
    public Boolean response;

    public void setResponse(Boolean response) {
	this.response = response;
    }

    public CorrectResponseBoolean() {
    }

    public CorrectResponseBoolean(Boolean response) {
	this.response = response;
    }

    public Boolean getResponse() {
	return this.response;
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

	final CorrectResponseBoolean other = (CorrectResponseBoolean) o;

	if (this.getResponse() == null) {
	    if (other.getResponse() != null) {
		return false;
	    }
	} else if (this.getResponse().compareTo(other.getResponse()) != 0) {
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
	Boolean thisValue = new Boolean(this.getResponse());

	return thisValue.toString();
    }
};
