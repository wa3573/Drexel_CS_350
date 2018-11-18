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
 * MenuHome is the first menu which is loaded and can be considered the root of
 * the
 * menu tree: that from which all other menus can be accessed directly or
 * indirectly.
 */
public class MenuHome extends Menu {

    private Scanner reader;

    public MenuHome() {
	this.reader = new Scanner(System.in);

	MenuChoice choice1 = new MenuChoice("Create a new Survey", 1);
	MenuChoice choice2 = new MenuChoice("Create a new Test ", 2);
	MenuChoice choice3 = new MenuChoice("Display a Survey", 3);
	MenuChoice choice4 = new MenuChoice("Display a Test", 4);
	MenuChoice choice5 = new MenuChoice("Load a Survey", 5);
	MenuChoice choice6 = new MenuChoice("Load a Test", 6);
	MenuChoice choice7 = new MenuChoice("Save a Survey", 7);
	MenuChoice choice8 = new MenuChoice("Save a Test", 8);
	MenuChoice choice9 = new MenuChoice("Modify an Existing Survey", 9);
	MenuChoice choice10 = new MenuChoice("Modify an Existing Test ", 10);
	MenuChoice choice11 = new MenuChoice("Take a Survey", 11);
	MenuChoice choice12 = new MenuChoice("Take a Test", 12);
	MenuChoice choice13 = new MenuChoice("Grade a Test", 13);
	MenuChoice choice14 = new MenuChoice("Tabulate a Survey", 14);
	MenuChoice choice15 = new MenuChoice("Tabulate a Test", 15);
	MenuChoice choice16 = new MenuChoice("Quit", 16);

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
	choices.add(choice10);
	choices.add(choice11);
	choices.add(choice12);
	choices.add(choice13);
	choices.add(choice14);
	choices.add(choice15);
	choices.add(choice16);

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

    private int loadSurvey() {
	/* Make sure user doesn't inadvertently lose the active work item */
	promptIfUnsaved();

	getSurveyManager().setSurveyActiveGradable(false);
	return getSurveyManager().loadActive();
    }

    private int loadTest() {
	/* Make sure user doesn't inadvertently lose the active work item */
	promptIfUnsaved();

	getSurveyManager().setSurveyActiveGradable(true);
	return getSurveyManager().loadActive();
    }

    protected int promptForInteger(String prompt) {
	System.out.println(prompt);
	int result = 0;

	while (result == 0) {
	    String nextLine = this.reader.nextLine();

	    /* function returns 0 if the user wishes to quit */
	    if (nextLine.compareTo("RETURN") == 0) {
		return 0;
	    }

	    try {

		result = Integer.parseInt(nextLine);
	    } catch (NumberFormatException e) {
		// e.printStackTrace();
		System.out.println("Please enter an integer");
	    }
	}

	return result;
    }

    protected String promptForString(String prompt) {
	System.out.println(prompt);
	String result = this.getReader().nextLine();

	return result;
    }

    @Override
    public Menu selectChoice(int index) {

	Menu newMenu = this;
	SurveyFiller surveyFiller = null;
	SurveyModifier surveyModifier = null;

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
	    this.loadSurvey();
	    break;

	case 6:
	    /* Load test */
	    this.loadTest();
	    break;

	case 7:
	    /* Save survey */
	    if (getSurveyManager().isSurveyActiveGradable()) {
		System.out.println("Active work item is a test, not a survey");
		break;
	    }

	    getSurveyManager().saveActive();
	    break;

	case 8:
	    /* Save test */
	    if (!getSurveyManager().isSurveyActiveGradable()) {
		System.out.println("Active work item is a survey, not a test");
		break;
	    }

	    getSurveyManager().saveActive();
	    break;

	case 9:
	    /* Modify an Existing Survey */
	    surveyModifier = new SurveyModifier();
	    
	    System.out.println("What survey do you wish to modify?");
	    if (this.loadSurvey() > 0) {
		break;
	    }

	    surveyModifier.start();

	    break;
	case 10:
	    /* Modify an Existing Test */
	    surveyModifier = new SurveyModifier();
	    
	    System.out.println("What test do you wish to modify?");
	    if (this.loadTest() > 0) {
		break;
	    }

	    surveyModifier.start();

	    break;
	case 11:
	    /* Take a Survey */
	    surveyFiller = new SurveyFiller();

	    System.out.println("What survey do you wish to take?");
	    if (this.loadSurvey() > 0) {
		break;
	    }

	    surveyFiller.start();

	    break;
	case 12:
	    /* Take a Test */
	    surveyFiller = new SurveyFiller();

	    System.out.println("What test do you wish to take?");
	    if (this.loadTest() > 0) {
		break;
	    }

	    surveyFiller.start();

	    break;
	case 13:
	    /* Grade a Test */
	    System.out.println("Which test do you wish to grade?");
	    if (this.loadTest() > 0) {
		break;
	    }

	    getSurveyManager().gradeActive();

	    break;
	case 14:
	    /* Tabulate a Survey */
	    System.out.println("What survey do you wish to tabulate?");
	    if (this.loadSurvey() > 0) {
		break;
	    }

	    getSurveyManager().tabulateActive();
	    break;
	case 15:
	    /* Tabulate a Test */
	    System.out.println("What test do you wish to tabulate?");
	    if (this.loadTest() > 0) {
		break;
	    }

	    getSurveyManager().tabulateActive();
	    break;
	case 16:
	    /* Exit */
	    newMenu = null;
	    promptIfUnsaved();
	    break;

	default:
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
