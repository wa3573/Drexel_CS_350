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

/*
 * CorrectResponse is the abstract base class upon which specific types of
 * comparable responses are based (Integer, String, etc.).
 */
public abstract class CorrectResponse implements java.io.Serializable {

    private static final long serialVersionUID = 6623864594070392366L;

    public CorrectResponse() {
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
	
//	final CorrectResponseString other = (CorrectResponseString) o;
//	
//	if (this.getResponse() == null) {
//	    if (other.getResponse() != null) {
//		return false;
//	    }
//	} else if (this..compareTo(other.getResponse()) != 0){
//	    return false;
//	}
	
	return true;
	
    }

};
