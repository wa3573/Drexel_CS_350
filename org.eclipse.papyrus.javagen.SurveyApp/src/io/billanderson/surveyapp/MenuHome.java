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

import io.billanderson.surveyapp.Menu;
import io.billanderson.surveyapp.MenuChoice;

/************************************************************/

/*
 * MenuHome is the first menu which is loaded and can be considered the root of the
 * menu tree: that from which all other menus can be accessed directly or indirectly.
 */

public class MenuHome extends Menu {

//    public static SurveyManager surveyManager;

    public MenuHome() {
	MenuChoice choice1 = new MenuChoice("Create a new Survey", 1);
	MenuChoice choice2 = new MenuChoice("Create a new Test ", 2);
	MenuChoice choice3 = new MenuChoice("Display a Survey", 3);
	MenuChoice choice4 = new MenuChoice("Display a Test", 4);
	MenuChoice choice5 = new MenuChoice("Load a Survey", 5);
	MenuChoice choice6 = new MenuChoice("Load a Test", 6);
	MenuChoice choice7 = new MenuChoice("Save a Survey", 7);
	MenuChoice choice8 = new MenuChoice("Save a Test", 8);
	MenuChoice choice9 = new MenuChoice("Quit", 9);

	ArrayList<MenuChoice> choices = new ArrayList<MenuChoice>();

	choices.add(choice1);
	choices.add(choice2);
	choices.add(choice3);
	choices.add(choice4);
	choices.add(choice5);
	choices.add(choice6);
	choices.add(choice7);
	choices.add(choice8);
	choices.add(choice9);

	this.setChoices(choices);
    }

    private void promptForUnsaved() {
	if (this.promptBoolean("Active work item is not saved, would you like to save it now?")) {
	    getSurveyManager().saveActive();
	}
    }

    
    private void promptIfUnsaved() {
	if (!getSurveyManager().isSaved()) {
	    this.promptForUnsaved();
	}
    }
    
    /*
     * @override
     */
    public Menu selectChoice(int index) {

	Menu newMenu;

	switch (index) {
	case 1:
	    /* Create new survey */
	    promptIfUnsaved();
	    newMenu = new MenuCreateSurvey();
	    break;
	case 2:
	    /* Create new test */
	    promptIfUnsaved();
	    newMenu = new MenuCreateTest();
	    break;
	case 3:
	    /* Display survey */
	    newMenu = this;

	    /* Check that there is an active survey */
	    if (getSurveyManager().getSurveyActive() == null) {
		System.out.println("There is no active work item");
		break;
	    }

	    /* Make sure work item is actually a survey */
	    if (getSurveyManager().isSurveyActiveGradable()) {
		System.out.println("Active work item is a test, not a survey");
		break;
	    }
	    
	    System.out.println(DIVIDER + "\nActive survey: \n\n" + getSurveyManager().getSurveyActive());

	    break;
	case 4:
	    /* Display test */
	    newMenu = this;

	    /* Check that there is an active survey */
	    if (getSurveyManager().getSurveyActive() == null) {
		System.out.println("There is no active work item");
		break;
	    }

	    /* Make sure work item is actually a test */
	    if (!getSurveyManager().isSurveyActiveGradable()) {
		System.out.println("Active work item is a survey, not a test");
		break;
	    }
	    
	    System.out.println(DIVIDER + "\nActive test: \n\n" + getSurveyManager().getSurveyActive());

	    break;
	case 5:
	    /* Load survey */
	    newMenu = this;
	    /* Make sure user doesn't inadvertently lose the active work item */
	    promptIfUnsaved();

	    getSurveyManager().setSurveyActiveGradable(false);
	    getSurveyManager().loadActive();
	    break;

	case 6:
	    /* Load test */
	    newMenu = this;
	    /* Make sure user doesn't inadvertently lose the active work item */
	    promptIfUnsaved();

	    getSurveyManager().setSurveyActiveGradable(true);
	    getSurveyManager().loadActive();
	    break;

	case 7:
	    /* Save survey */
	    newMenu = this;

	    if (getSurveyManager().isSurveyActiveGradable()) {
		System.out.println("Active work item is a test, not a survey");
		break;
	    }

	    getSurveyManager().saveActive();
	    break;

	case 8:
	    /* Save test */

	    newMenu = this;

	    if (!getSurveyManager().isSurveyActiveGradable()) {
		System.out.println("Active work item is a survey, not a test");
		break;
	    }

	    getSurveyManager().saveActive();
	    break;

	case 9:
	    /* Exit */
	    newMenu = null;
	    promptIfUnsaved();
	    break;

	default:
	    newMenu = this;
	    System.err.println("selectChoice(): invalid index");
	}

	return newMenu;
    }

    public String toString() {
	String out = DIVIDER + "\n\t\t\t\t || Home Menu ||\n" + DIVIDER + "\n";

	out += this.getChoicesString();

	return out;
    }
};
