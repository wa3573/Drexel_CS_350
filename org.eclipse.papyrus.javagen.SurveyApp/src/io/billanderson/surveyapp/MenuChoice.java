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
 * The MenuChoice class is an object which constitutes the members of a menu, from
 * which the user can make a selection.
 */
public class MenuChoice {
    /**
     * value is a String which is seen by the user indicating the action of the menu
     * item.
     */
    private String value;

    /**
     * index is the number associated with the given menu choice, and that which the
     * user enters to select that choice.
     */
    private int index;

    public MenuChoice() {
	this.value = null;
	this.index = 0;
    }

    public MenuChoice(String value, int index) {
	this.value = value;
	this.index = index;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public void setIndex(int index) {
	this.index = index;
    }

    public String getValue() {
	return this.value;
    }

    public int getIndex() {
	return this.index;
    }
};
