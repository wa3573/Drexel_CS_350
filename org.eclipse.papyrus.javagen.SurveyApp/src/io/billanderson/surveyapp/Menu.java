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

import java.util.*;

/************************************************************/
/**
 * Menu is the abstract base class upon which all realizable Menus are based
 * upon. They inherit common components, but their functionality is defined
 * largely from overridden methods.
 */
public abstract class Menu {

    private ArrayList<MenuChoice> choices;
    private int numberChoices;
    private Scanner reader;
    public static String DIVIDER = "====================" + "====================" + "===================="
	    + "====================";

    private static SurveyManager surveyManager;
    
    /*
     * Menu constructors handle adding their choices, obtaining the surveyManager,
     * and creating private instances of needed variables
     */
    public Menu() {
	this.reader = new Scanner(System.in);
	setSurveyManager(SurveyManager.getInstance());
    }

    /* TODO: this constructor may be unneeded */
    public Menu(ArrayList<MenuChoice> choices) {
	this.choices = choices;
	this.numberChoices = choices.size();
    }

    /*
     * Overridden by child classes, this controls the basic functionality of the
     * individual menu, and returns a menu which directs the menu manager to the
     * next menu to display. 
     * 
     * Each choice of the menu has its behavior defined within this function.
     * Simple prompts do not constitute menus themselves, as they may have input other
     * than numbered choices used to select choices in the Menu class. As such, these
     * prompts are defined here.
     */
    public Menu selectChoice(int index) {

	return null;
    }

    /*
     * Most child classes override this to provide stylized output of their menu
     * choices, as well as a header stating the name of the menu.
     */
    public String toString() {

	String out = "";

	for (int i = 0; i < this.getNumberChoices(); i++) {
	    MenuChoice thisChoice = this.getChoices().get(i);
	    out += thisChoice.getIndex() + ") " + thisChoice.getValue() + "\n";
	}

	return out;
    }

    /*
     * Virtually all child classes utilize a boolean prompt for user verification.
     * This displays a prompt and returns a boolean based on the user's response.
     */
    protected boolean promptBoolean(String prompt) {
	System.out.print(prompt + " ('y' or 'n'): ");
	char userResponse = reader.nextLine().charAt(0);

	while (userResponse != 'y' && userResponse != 'n') {
	    System.out.print("Please enter a valid choice.\n" + prompt + " ('y' or 'n'): ");
	    userResponse = reader.nextLine().charAt(0);
	}

	if (userResponse == 'y') {
	    return true;
	} else {
	    return false;
	}
    }

    public ArrayList<MenuChoice> getChoices() {
	return this.choices;
    }

    public void setChoices(ArrayList<MenuChoice> choices) {
	this.choices = choices;
	this.numberChoices = choices.size();
    }

    public int getNumberChoices() {
	return this.numberChoices;
    }

    public Scanner getReader() {
        return reader;
    }

    public void setReader(Scanner reader) {
        this.reader = reader;
    }
    
    public static SurveyManager getSurveyManager() {
	return surveyManager;
    }

    public static void setSurveyManager(SurveyManager surveyManager) {
	Menu.surveyManager = surveyManager;
    }
};
